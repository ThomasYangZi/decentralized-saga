package com.kiviblog.saga.signal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

/**
 * @author yangzifeng
 */
@Component("messageSender")
public class MessageSender {

    private MessageChannel userOutputChannel;

    @Autowired
    public MessageSender(ProducerChannel producerChannel) {
        this.userOutputChannel = producerChannel.output();
    }

    public void send(Message message) {
        try {
            userOutputChannel.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Could not send message due to: "+ e.getMessage(), e);
        }
    }

}
