package com.app.controller;

import com.app.dto.LoginDTO;
import com.app.dto.RegisterDTO;
import com.app.service.UserEntityService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("public/auth")
@SecurityRequirement(name="Bearer Authentication")
public class Controller {
    @Autowired
    UserEntityService userEntityService;
    //Endpoint para poder registrarse y tener acceso
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDTO registerDTO){
        RegisterDTO user = registerDTO;
        user.getRoles().clear();
        user.setRoles(Set.of("USER"));
        return ResponseEntity.status(HttpStatus.OK).body(userEntityService.registerUser(registerDTO));
    }
    @PostMapping("/login")
    public void authenticate(@Valid @RequestBody LoginDTO loginDTO){
        LoginDTO user = loginDTO;
        userEntityService.authenticate(loginDTO);
    }
}
