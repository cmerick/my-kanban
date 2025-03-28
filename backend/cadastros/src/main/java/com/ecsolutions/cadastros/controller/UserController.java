package com.ecsolutions.cadastros.controller;

import com.ecsolutions.cadastros.model.dtos.acesso.UserRegisterRequest;
import com.ecsolutions.cadastros.model.dtos.acesso.UserResponse;
import com.ecsolutions.cadastros.model.dtos.acesso.UserSearchRequest;
import com.ecsolutions.cadastros.model.mappers.UserMapper;
import com.ecsolutions.cadastros.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("usuarios-sistema")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    private final UserMapper usuarioSistemaMapper;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<Collection<UserResponse>> getAll(UserSearchRequest requestEntity) {
        return ResponseEntity.ok(this.service.getAll(requestEntity));
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<UserResponse> getById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(this.service.getById(id));
    }


    @PostMapping
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<UserResponse> create(@RequestBody UserRegisterRequest requestEntity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSistemaMapper.map(this.service.create(requestEntity)));
    }

}
