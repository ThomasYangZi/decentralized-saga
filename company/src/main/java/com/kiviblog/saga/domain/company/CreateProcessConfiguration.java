package com.kiviblog.saga.domain.company;

import com.kiviblog.saga.signal.MessageSender;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.support.MessageBuilder;
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

    /**
     * 问题：
     *    1.保证公司创建和消息发送的原子性
     *    2.如果失败如何处理
     *    3.如何逆转状态机，或执行callback链
     * @param transitions transitions
     * @throws Exception exception
     */
    @Override
    public void configure(StateMachineTransitionConfigurer<CompanyStatus, CompanyEvents> transitions) throws Exception {
        transitions
                // 请求进来，创建公司
                .withExternal()
                .source(CompanyStatus.COMPANY_INIT).target(CompanyStatus.COMPANY_CREATED)
                .event(CompanyEvents.COMPANY_CREATE)
                .action(sendCreateEvent())
                .and()
                // UAA返回确认，将返回消息记录到header里，在action里取出header并转发消息到RES服务
                .withExternal()
                .source(CompanyStatus.COMPANY_CREATED).target(CompanyStatus.UAA_CHECKED)
                .event(CompanyEvents.UAA_FALLBACK)
                .action(uaaFallback())
                .and()
                // RES返回确认
                .withExternal()
                .source(CompanyStatus.UAA_CHECKED).target(CompanyStatus.RES_CHECKED)
                .event(CompanyEvents.COMPANY_CONFIRM)
                .action(createSuccess())
                .and()
                // 如果哪里发生错误，比如超时，就发送UNDO事件
                // 实际情况下，应该从undo列表里自动的执行逆操作或者callback方法
                .withExternal()
                .source(CompanyStatus.RES_CHECKED).target(CompanyStatus.COMPANY_DELETED)
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
            context.getExtendedState().getVariables().put("company",name);
            messageSender.send(MessageBuilder.withPayload(name).setHeader("type", CompanyEvents.COMPANY_CREATE.toString()).build());
        };
    }

    @Bean
    public Action<CompanyStatus, CompanyEvents> uaaFallback() {
        return context -> logger.info("uaa fallback");
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
