package com.kiviblog.saga.repository;

import com.kiviblog.saga.domain.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author yangzifeng
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, String>{

}
