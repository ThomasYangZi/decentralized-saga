package com.kiviblog.saga.service.async;

import com.kiviblog.saga.repository.MenuRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ConsumerOrgServiceTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private ConsumerOrgService consumerOrgService;

    @Before
    public void init() {
        consumerOrgService = webApplicationContext.getBean(ConsumerOrgService.class);
    }

    @Test
    public void copyMenuTree() throws Exception {

        consumerOrgService.copyMenuTree();

    }

}