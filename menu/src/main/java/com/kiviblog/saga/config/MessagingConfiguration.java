package com.kiviblog.saga.config;

import com.kiviblog.saga.signal.ConsumerOrgChannel;
import com.kiviblog.saga.signal.ProducerChannel;
import org.springframework.cloud.stream.annotation.EnableBinding;

/**
 * @author yangzifeng
 */
@EnableBinding({ConsumerOrgChannel.class, ProducerChannel.class})
public class MessagingConfiguration {

}
