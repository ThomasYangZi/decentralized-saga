package com.kiviblog.saga.signal;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author yangzifeng
 */
public interface ConsumerOrgChannel {
    String CHANNEL = "org-input";

    @Input(CHANNEL)
    SubscribableChannel input();
}
