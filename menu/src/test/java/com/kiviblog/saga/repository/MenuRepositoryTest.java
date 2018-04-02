package com.kiviblog.saga.repository;

import com.kiviblog.saga.domain.menu.MenuEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MenuRepositoryTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MenuRepository menuRepository;

    @Before
    public void init() {
        menuRepository = webApplicationContext.getBean(MenuRepository.class);
    }

    @Test
    public void findAllByParentIsNull() throws Exception {

        List<MenuEntity> rootMenu  = menuRepository.findAllByParentIsNull();
        System.out.println(rootMenu);

        List<MenuEntity> children = rootMenu.get(0).getChild();
        System.out.println(children);
    }

    @Test
    public void saveTree() {

        MenuEntity menuEntity = new MenuEntity();
        menuEntity.setName("11");

        MenuEntity menuEntity1 = new MenuEntity();
        menuEntity1.setName("12");

        MenuEntity menuEntity2 = new MenuEntity();
        menuEntity2.setName("21");
        menuEntity2.setParent(menuEntity);

        MenuEntity menuEntity3 = new MenuEntity();
        menuEntity3.setName("31");
        menuEntity3.setParent(menuEntity2);

        List<MenuEntity> menuEntities = new ArrayList<>();
        menuEntities.add(menuEntity);
        menuEntities.add(menuEntity1);
        menuEntities.add(menuEntity2);
        menuEntities.add(menuEntity3);

        menuRepository.saveAll(menuEntities);

    }

}