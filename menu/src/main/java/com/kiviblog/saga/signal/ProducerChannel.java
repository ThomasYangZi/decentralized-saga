package com.kiviblog.saga.signal;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

/**
 * @author yangzifeng
 */
@Component
public interface ProducerChannel {
    String CHANNEL = "res-output";

    /**
     * user消息发送频道
     * @return messageChannel
     */
    @Output(CHANNEL)
    MessageChannel output();
}
