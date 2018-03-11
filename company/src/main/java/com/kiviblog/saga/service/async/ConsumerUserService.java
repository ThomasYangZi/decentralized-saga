package com.kiviblog.saga.service.async;

import com.kiviblog.saga.domain.company.CompanyEvents;
import com.kiviblog.saga.domain.company.CompanyStatus;
import com.kiviblog.saga.signal.ConsumerUserChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author yangzifeng
 */
@Service
public class ConsumerUserService {

    private final Logger log = LoggerFactory.getLogger(ConsumerUserService.class);

    private StateMachinePersister<CompanyStatus, CompanyEvents, String> stateMachinePersister;

    private StateMachine<CompanyStatus, CompanyEvents> stateMachine;

    public ConsumerUserService(StateMachinePersister<CompanyStatus, CompanyEvents, String> stateMachinePersister, StateMachine<CompanyStatus, CompanyEvents> stateMachine) {
        this.stateMachinePersister = stateMachinePersister;
        this.stateMachine = stateMachine;
    }

    @StreamListener(ConsumerUserChannel.CHANNEL)
    @Transactional(rollbackFor = Exception.class)
    public void consume(@Payload String name) {
        log.info("Received message: {}.", name);

        try {
            stateMachine = stateMachinePersister.restore(stateMachine, name);
            stateMachine.sendEvent(CompanyEvents.USER_FALLBACK);
            stateMachinePersister.persist(stateMachine, name);
        } catch (Exception e) {
            log.error("state machine error : {}", e);
        }
    }
}
