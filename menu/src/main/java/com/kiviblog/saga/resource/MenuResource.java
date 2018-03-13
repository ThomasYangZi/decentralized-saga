package com.kiviblog.saga.resource;

import com.kiviblog.saga.service.MenuService;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangzifeng
 */
@RestController
public class MenuResource {

    private MenuService menuService;

    public MenuResource(MenuService menuService) {
        this.menuService = menuService;
    }


}
