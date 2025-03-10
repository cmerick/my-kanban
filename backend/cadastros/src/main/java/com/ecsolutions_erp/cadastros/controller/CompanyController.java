package com.ecsolutions_erp.cadastros.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @GetMapping
    @PreAuthorize("hasAnyAuthority('user')")
    public String list() {
        return "Listando produtos";
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('user')")
    public String create() {
        return "Cadastrando produtos";
    }
}
