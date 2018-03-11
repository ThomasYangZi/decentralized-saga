package com.kiviblog.saga.config;

import com.kiviblog.saga.signal.ConsumerCompanyChannel;
import com.kiviblog.saga.signal.ProducerChannel;
import org.springframework.cloud.stream.annotation.EnableBinding;

/**
 * @author yangzifeng
 */
@EnableBinding(value = {ConsumerCompanyChannel.class, ProducerChannel.class})
public class MessagingConfiguration {
}
