package com.kiviblog.saga.resource;

import com.kiviblog.saga.service.CompanyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangzifeng
 */
@RestController
@RequestMapping("/company")
public class CompanyResource {

    private CompanyService companyService;

    public CompanyResource(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/create/{name}")
    public void createCompany(@PathVariable("name") String name) {
        companyService.createCompany(name);
    }
}
