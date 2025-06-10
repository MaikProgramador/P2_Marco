package com.example.gestaocursos.controllers;

import com.example.gestaocursos.dto.LoginDTO;
import com.example.gestaocursos.dto.LoginResponseDTO;
import com.example.gestaocursos.dto.RegisterDTO;
import com.example.gestaocursos.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController 
@RequestMapping("/auth") 
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login") 
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO data) {
        String token = authService.login(data);
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register") 
    public ResponseEntity<Void> register(@RequestBody RegisterDTO data) {
        authService.register(data);
       
        return ResponseEntity.ok().build();
    }
}