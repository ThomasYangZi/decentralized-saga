package com.kiviblog.saga.domain.company;

import com.kiviblog.saga.signal.MessageSender;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

/**
 * @author yangzifeng
 */
@Configuration
@EnableStateMachineFactory(name = "stateMachineFactory")
public class CreateProcessConfiguration extends EnumStateMachineConfigurerAdapter<CompanyStatus, CompanyEvents> {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(CreateProcessConfiguration.class);

    private MessageSender messageSender;

    public CreateProcessConfiguration(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Override
    public void configure(StateMachineStateConfigurer<CompanyStatus, CompanyEvents> states) throws Exception {
        states
                .withStates()
                .initial(CompanyStatus.COMPANY_INIT)
                .states(EnumSet.allOf(CompanyStatus.class))
                .end(CompanyStatus.COMPANY_DELETED)
                .end(CompanyStatus.COMPANY_CONFIRMED);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<CompanyStatus, CompanyEvents> transitions) throws Exception {
        transitions
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
                .source(CompanyStatus.FALLBACK_CHECKED).target(CompanyStatus.COMPANY_CONFIRMED)
                // auto send event by checkFallback action
                .event(CompanyEvents.COMPANY_CONFIRM)
                .action(createSuccess())
                .and()
                .withExternal()
                .source(CompanyStatus.FALLBACK_CHECKED).target(CompanyStatus.COMPANY_DELETED)
                // auto send event by checkFallback action
                .event(CompanyEvents.COMPANY_UNDO)
                .action(deleteEvent());
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<CompanyStatus, CompanyEvents> config) throws Exception {
        config
                .withConfiguration()
                .autoStartup(true);
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
        return context -> logger.info("check fallback");
    }

    @Bean
    public Action<CompanyStatus, CompanyEvents> createSuccess() {
        return context -> logger.info("create success");
    }

    @Bean
    public Action<CompanyStatus, CompanyEvents> deleteEvent() {
        return context -> logger.info("delete");
    }

}
