package com.kiviblog.saga.config;

import com.kiviblog.saga.domain.company.CompanyEvents;
import com.kiviblog.saga.domain.company.CompanyStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.data.redis.RedisStateMachineContextRepository;
import org.springframework.statemachine.data.redis.RedisStateMachinePersister;
import org.springframework.statemachine.persist.RepositoryStateMachinePersist;

/**
 * @author yangzifeng
 */
@Configuration
public class StateMachinePersisterConfiguration {

    private final RedisConnectionFactory redisConnectionFactory;

    @Autowired
    public StateMachinePersisterConfiguration(RedisConnectionFactory redisConnectionFactory) {
        this.redisConnectionFactory = redisConnectionFactory;
    }

    @Bean
    public StateMachinePersist<CompanyStatus, CompanyEvents, String> stateMachinePersist() {
        RedisStateMachineContextRepository<CompanyStatus, CompanyEvents> repository =
                new RedisStateMachineContextRepository<>(redisConnectionFactory);
        return new RepositoryStateMachinePersist<>(repository);
    }

    @Bean
    public RedisStateMachinePersister<CompanyStatus, CompanyEvents> redisStateMachinePersister(
            StateMachinePersist<CompanyStatus, CompanyEvents, String> stateMachinePersist) {
        return new RedisStateMachinePersister<>(stateMachinePersist);
    }
}
