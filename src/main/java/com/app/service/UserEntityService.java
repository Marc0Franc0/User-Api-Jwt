package com.app.service;

import com.app.dto.LoginDTO;
import com.app.dto.RegisterDTO;
import com.app.model.UserEntity;
import org.springframework.security.core.userdetails.User;

public interface UserEntityService {
    public UserEntity registerUser(RegisterDTO registerDTO);
    public User createUserSecurity(UserEntity userEntity);
    public void authenticate(LoginDTO loginDTO);
}
