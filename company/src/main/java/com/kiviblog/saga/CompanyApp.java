package com.kiviblog.saga;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author yangzifeng
 */
@SpringBootApplication
public class CompanyApp {

    public static void main(String[] args) {
        SpringApplication.run(CompanyApp.class, args);
    }
}
