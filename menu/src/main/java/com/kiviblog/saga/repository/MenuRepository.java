package com.kiviblog.saga.repository;

import com.kiviblog.saga.domain.menu.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author yangzifeng
 */
@Repository
public interface MenuRepository extends JpaRepository<MenuEntity, String> {

}
