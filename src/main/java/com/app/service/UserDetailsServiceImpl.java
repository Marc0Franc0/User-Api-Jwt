package com.app.service;

import com.app.model.UserEntity;
import com.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//Esta clase sirve para retornar un usuario el cual es de tipo User de Spring Security
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    private UserEntityService userEntityService = new UserEntityServiceImpl();

    // Método para cargar un usuario con todos sus datos por medio de sus username
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Se obtiene el usuario de tipo UserEntity con su username
        // En el caso de no encontrarse se lanza una excepción de tipo
        // UsernameNotFoundException
        UserEntity user = userRepository
                .findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("User not found"));
        // Se devuelve un usuario de tipo User (No de tipo UserEntity) esta clase de es de Spring Security
        return userEntityService.createUserSecurity(user);
    }
}
