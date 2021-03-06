package com.kiviblog.saga.service.async;

import com.kiviblog.saga.domain.user.UserEntity;
import com.kiviblog.saga.service.UserService;
import com.kiviblog.saga.signal.ConsumerCompanyChannel;
import com.kiviblog.saga.signal.MessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author yangzifeng
 */
@Service
public class ConsumerCompanyService {

    private final Logger log = LoggerFactory.getLogger(ConsumerCompanyService.class);

    private MessageSender messageSender;

    private UserService userService;

    public ConsumerCompanyService(MessageSender messageSender, UserService userService) {
        this.messageSender = messageSender;
        this.userService = userService;
    }

    @StreamListener(target = ConsumerCompanyChannel.CHANNEL, condition = "headers['type']=='COMPANY_CREATE'")
    @Transactional(rollbackFor = Exception.class)
    public void consume(@Payload String name) {
        log.info("Received message: {}.", name);

        UserEntity userEntity = userService.createCompanyLeader(name);

        messageSender.send(MessageBuilder.withPayload(userEntity).setHeader("type", "USER_CONFIRM").build());
    }
}
