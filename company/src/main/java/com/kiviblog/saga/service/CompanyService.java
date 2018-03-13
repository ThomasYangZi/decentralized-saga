package com.kiviblog.saga.service;

import com.kiviblog.saga.domain.company.CompanyEntity;
import com.kiviblog.saga.domain.company.CompanyEvents;
import com.kiviblog.saga.domain.company.CompanyStatus;
import com.kiviblog.saga.repository.CompanyRepository;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * @author yangzifeng
 */
@Service
public class CompanyService {

    private CompanyRepository companyRepository;

    private StateMachineFactory<CompanyStatus, CompanyEvents> stateMachineFactory;

    private StateMachinePersister<CompanyStatus, CompanyEvents, String> stateMachinePersister;

    public CompanyService(StateMachineFactory<CompanyStatus, CompanyEvents> stateMachineFactory, StateMachinePersister<CompanyStatus, CompanyEvents, String> stateMachinePersister, CompanyRepository companyRepository) {
        Assert.notNull(stateMachineFactory, "StateMachineFactory must be set");
        Assert.notNull(stateMachinePersister, "StateMachinePersister must be set");
        this.stateMachineFactory = stateMachineFactory;
        this.stateMachinePersister = stateMachinePersister;
        this.companyRepository = companyRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public void createCompany(String name) {
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setName(name);
        companyRepository.save(companyEntity);

        StateMachine<CompanyStatus, CompanyEvents> stateMachine = stateMachineFactory.getStateMachine();

        stateMachine.sendEvent(MessageBuilder
                .withPayload(CompanyEvents.COMPANY_CREATE)
                .setHeader("name", name)
                .build());

        try {
            stateMachinePersister.persist(stateMachine, name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
