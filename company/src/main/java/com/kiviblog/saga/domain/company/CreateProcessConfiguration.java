package com.kiviblog.saga.domain.company;

import com.kiviblog.saga.signal.MessageSender;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;

import java.util.EnumSet;

/**
 * @author yangzifeng
 */
@Configuration
@Scope(scopeName = "prototype")
@EnableStateMachine
public class CreateProcessConfiguration {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(CreateProcessConfiguration.class);

    private MessageSender messageSender;

    public CreateProcessConfiguration(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Bean(name = "stateMachine")
    @Scope(scopeName="prototype")
    public StateMachine<CompanyStatus, CompanyEvents> stateMachineTarget() throws Exception {
        StateMachineBuilder.Builder<CompanyStatus, CompanyEvents> builder = StateMachineBuilder.builder();

        builder.configureStates()
                .withStates()
                .initial(CompanyStatus.COMPANY_INIT)
                .states(EnumSet.allOf(CompanyStatus.class))
                .end(CompanyStatus.COMPANY_CONFIRMED)
                .end(CompanyStatus.COMPANY_DELETED);

        builder.configureTransitions()
                .withExternal()
                .source(CompanyStatus.COMPANY_INIT).target(CompanyStatus.COMPANY_CREATED)
                .event(CompanyEvents.COMPANY_CREATE)
                .action(sendCreateEvent())
                .and()
                .withExternal()
                .source(CompanyStatus.COMPANY_CREATED).target(CompanyStatus.WAITING_USER)
                .and()
                .withExternal()
                .source(CompanyStatus.WAITING_USER).target(CompanyStatus.FALLBACK_CHECKED)
                // message listener uaa
                .event(CompanyEvents.USER_FALLBACK)
                .action(checkFallback())
                .and()
                .withExternal()
                .source(CompanyStatus.COMPANY_CREATED).target(CompanyStatus.COMPANY_CONFIRMED)
                // auto send event by checkFallback action
                .event(CompanyEvents.COMPANY_CONFIRM)
                .action(createSuccess())
                .and()
                .withExternal()
                .source(CompanyStatus.COMPANY_CREATED).target(CompanyStatus.COMPANY_DELETED)
                // auto send event by checkFallback action
                .event(CompanyEvents.COMPANY_UNDO)
                .action(deleteEvent());

        builder.configureConfiguration().withConfiguration().autoStartup(true);


        return builder.build();
    }

    @Bean
    public Action<CompanyStatus, CompanyEvents> sendCreateEvent() {
        return context -> {
            logger.info("company created, sending event to mq...");
            String name = String.valueOf(context.getMessageHeader("name"));
            messageSender.send(name);
        };
    }

    @Bean
    public Action<CompanyStatus, CompanyEvents> checkFallback() {
        return context -> {
            logger.info("检查fallback");
            context.getStateMachine().sendEvent(CompanyEvents.COMPANY_UNDO);
        };
    }

    @Bean
    public Action<CompanyStatus, CompanyEvents> createSuccess() {
        return stateContext -> logger.info("创建成功");
    }

    @Bean
    public Action<CompanyStatus, CompanyEvents> deleteEvent() {
        return context -> logger.info("失败删除");
    }
}
