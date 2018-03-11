package com.kiviblog.saga.signal;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

/**
 * @author yangzifeng
 */
@Component
public interface ProducerChannel {

    String CHANNEL = "org-output";

    /**
     * company output channel
     * @return message channel
     */
    @Output(CHANNEL)
    MessageChannel output();
}
