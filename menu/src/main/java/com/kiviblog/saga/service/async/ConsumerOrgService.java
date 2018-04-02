package com.kiviblog.saga.service.async;

import com.kiviblog.saga.domain.menu.MenuEntity;
import com.kiviblog.saga.repository.MenuRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangzifeng
 */
@Service
public class ConsumerOrgService {

    private MenuRepository menuRepository;

    public ConsumerOrgService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    void copyMenuTree() {
        List<MenuEntity> rootMenus  = menuRepository.findAllByParentIsNull();
        List<MenuEntity> menuEntities = new ArrayList<>();

        rootMenus.forEach(menu-> turnToList(menu, null, menuEntities));

        menuRepository.saveAll(menuEntities);
    }

    private static void turnToList(MenuEntity menu, MenuEntity parent, List<MenuEntity> menuEntities) {
        MenuEntity menuEntity = new MenuEntity();
        menuEntity.setName(menu.getName());
        menuEntity.setParent(parent);
        menuEntity.setCompany(menu.getCompany());
        menuEntity.setUsername(menu.getUsername());
        menuEntities.add(menuEntity);
        if (menu.getChild() != null) {
            List<MenuEntity> menuChild = menu.getChild();
            menuChild.forEach(child -> turnToList(child, menuEntity, menuEntities));
        }

    }
}
