package com.juliancambraia.cognitobackend.controller;

import com.juliancambraia.cognitobackend.dto.MessageDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class MainController {
    @GetMapping("/hello-admin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<MessageDTO> helloAdmin() {
        return ResponseEntity.ok(new MessageDTO("Olá ADMIN logado no Cognito!"));

    }

    @GetMapping("/hello-user")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<MessageDTO> helloUser() {
        return ResponseEntity.ok(new MessageDTO("Olá USER logado no Cognito!"));

    }
}
