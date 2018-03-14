package com.kiviblog.saga.service;

import com.kiviblog.saga.domain.menu.MenuEntity;
import com.kiviblog.saga.repository.MenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author yangzifeng
 */
@Service
public class MenuService {

    private MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public void createMenu(String username, String company) {
        MenuEntity menuEntity = new MenuEntity();
        menuEntity.setName("menu");
        menuEntity.setName(username);
        menuEntity.setCompany(company);
        menuRepository.save(menuEntity);
    }

}
