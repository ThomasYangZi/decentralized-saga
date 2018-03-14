package com.kiviblog.saga.service;

import com.kiviblog.saga.domain.user.UserEntity;
import com.kiviblog.saga.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author yangzifeng
 */
@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public UserEntity createCompanyLeader(String companyName) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(companyName + "Leader");
        userEntity.setCompany(companyName);
        return userRepository.save(userEntity);
    }
}
