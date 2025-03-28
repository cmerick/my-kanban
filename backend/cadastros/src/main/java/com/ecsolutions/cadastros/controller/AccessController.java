package com.ecsolutions.cadastros.controller;

import com.ecsolutions.cadastros.model.dtos.acesso.ChangePasswordRequest;
import com.ecsolutions.cadastros.service.AccessService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AccessController {

    private final AccessService service;

    @GetMapping("me")
    public ResponseEntity<Object> me() {
        return ResponseEntity.ok(this.service.me());
    }

    @PutMapping("change-password")
    public ResponseEntity<Void> trocaSenha(@RequestBody ChangePasswordRequest requestEntity) {
        this.service.changePassword(requestEntity);
        return ResponseEntity.accepted().build();
    }

}
