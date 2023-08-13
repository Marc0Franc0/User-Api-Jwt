package com.app.service;

import com.app.dto.LoginDTO;
import com.app.dto.PersonalData;
import com.app.dto.RegisterDTO;
import com.app.model.ERole;
import com.app.model.RoleEntity;
import com.app.model.UserEntity;
import com.app.repository.UserRepository;
import com.app.security.jwt.JwtAuthenticationFilter;
import com.app.security.jwt.JwtTokenProvider;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserEntityServiceImpl implements UserEntityService{
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    //Método para registrar un usuario nuevo
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
                .personalData( new PersonalData(
                registerDTO.getFirstName(),
                        registerDTO.getLastName(),
                        registerDTO.getAge(),
                        registerDTO.getEmail()
                ))
                .build();

      return userRepository.save(userEntity);
    }

    //Método para la creación de un usuario de seguridad de Spring
    public User createUserSecurity(UserEntity userEntity){
        return new User(
                userEntity.getUsername(),
                userEntity.getPassword(),
                true,
                true,
                true,
                true,
                userEntity.getAuthorities()
        );
    }

    @Override
    public void authenticate(LoginDTO loginDTO) {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtTokenProvider);
        //Se envia al filtro creado el usernmae y password del usuario que desea autenticarse
        jwtAuthenticationFilter.setUsernameParameter(loginDTO.getUsername());
        jwtAuthenticationFilter.setPasswordParameter(loginDTO.getPassword());
    }
}
