package com.app.controller;

import com.app.dto.RegisterDTO;
import com.app.service.UserEntityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("private")
public class AdminController {
    @Autowired
    UserEntityService userEntityService;

    //Endpoint para poder registrarse y tener acceso como admin
    @PostMapping("/auth/register")
    public ResponseEntity<?> register(@Valid  @RequestBody RegisterDTO registerDTO) {
        RegisterDTO user = registerDTO;
        user.getRoles().clear();
        user.setRoles(Set.of("ADMIN","USER"));
        return ResponseEntity.status(HttpStatus.OK).body(userEntityService.registerUser(registerDTO));
    }
}
