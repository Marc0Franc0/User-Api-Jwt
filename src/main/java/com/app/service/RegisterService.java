package com.app.service;

import com.app.dto.RegisterDTO;
import com.app.model.ERole;
import com.app.model.RoleEntity;
import com.app.model.UserEntity;
import com.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RegisterService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    public UserEntity registerUser(RegisterDTO registerDTO){

        Set<RoleEntity> roles =
                //obtención de roles
                registerDTO.getRoles()
                        //mapeamiento de cada uno
                .stream().map(role -> RoleEntity.builder()
                        .name(ERole.valueOf(role))
                        .build())
                .collect(Collectors.toSet());

        UserEntity userEntity = UserEntity.builder()
                //nombre de usuario
                .username(registerDTO.getUsername())
                //password encriptada
                .password(passwordEncoder.encode(registerDTO.getPassword()))
                //roles del usuario
                .roles(roles)
                .build();

      return userRepository.save(userEntity);
    }
}
