package com.kiviblog.saga.repository;

import com.kiviblog.saga.domain.menu.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yangzifeng
 */
@Repository
public interface MenuRepository extends JpaRepository<MenuEntity, String> {

    /**
     * 根结点
     * @return 根结点列表
     */
    List<MenuEntity> findAllByParentIsNull();
}
