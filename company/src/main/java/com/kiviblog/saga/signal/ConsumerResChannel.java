package com.kiviblog.saga.signal;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author yangzifeng
 */
public interface ConsumerResChannel {

    String CHANNEL = "res-input";

    @Input(CHANNEL)
    SubscribableChannel input();
}
