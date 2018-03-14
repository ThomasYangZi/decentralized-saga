package com.kiviblog.saga.config;

import com.kiviblog.saga.signal.ConsumerResChannel;
import com.kiviblog.saga.signal.ConsumerUserChannel;
import com.kiviblog.saga.signal.ProducerChannel;
import org.springframework.cloud.stream.annotation.EnableBinding;

/**
 * @author yangzifeng
 */
@EnableBinding({ConsumerUserChannel.class, ProducerChannel.class, ConsumerResChannel.class})
public class MessagingConfiguration {
}
