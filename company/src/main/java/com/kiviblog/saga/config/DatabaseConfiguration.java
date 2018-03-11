package com.kiviblog.saga.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author yangzifeng
 */
@Configuration
@EnableJpaRepositories("com.kiviblog.saga.repository")
@EntityScan("com.kiviblog.saga.domain")
@EnableTransactionManagement
public class DatabaseConfiguration {
}
