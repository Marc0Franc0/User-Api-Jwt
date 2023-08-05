package com.app.controller;

import com.app.dto.RegisterDTO;
import com.app.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    RegisterService registerService;
    //Endpoint para poder registrarse y tener acceso
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO){
         return ResponseEntity.status(HttpStatus.OK).body(registerService.registerUser(registerDTO));
    }
}
