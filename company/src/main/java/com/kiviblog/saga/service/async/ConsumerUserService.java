package com.kiviblog.saga.service.async;

import com.kiviblog.saga.domain.company.CompanyEvents;
import com.kiviblog.saga.domain.company.CompanyStatus;
import com.kiviblog.saga.signal.ConsumerUserChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
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

    private StateMachineFactory<CompanyStatus, CompanyEvents> stateMachineFactory;

    public ConsumerUserService(StateMachinePersister<CompanyStatus, CompanyEvents, String> stateMachinePersister, StateMachineFactory<CompanyStatus, CompanyEvents> stateMachineFactory) {
        this.stateMachinePersister = stateMachinePersister;
        this.stateMachineFactory = stateMachineFactory;
    }

    @StreamListener(target = ConsumerUserChannel.CHANNEL, condition = "headers['type']=='USER_CONFIRM'")
    @Transactional(rollbackFor = Exception.class)
    public void consume(@Payload String name) {
        log.info("Received message: {}.", name);

        StateMachine<CompanyStatus, CompanyEvents> stateMachine = stateMachineFactory.getStateMachine();

        try {
            stateMachine = stateMachinePersister.restore(stateMachine, name);
            log.info(stateMachine.toString());
            stateMachine.sendEvent(CompanyEvents.UAA_FALLBACK);
            stateMachine.sendEvent(CompanyEvents.COMPANY_UNDO);
            log.info(stateMachine.toString());
            stateMachinePersister.persist(stateMachine, name);
        } catch (Exception e) {
            log.error("state machine error : {}", e);
        }
    }
}
