package com.app.service;

import com.app.model.UserEntity;
import com.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;
//Esta clase sirve para retornar un usuario el cual es de tipo User de Spring Security
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    // Método para cargar un usuario con todos sus datos por medio de sus username
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Se obtiene el usuario de tipo UserEntity con su username
        // En el caso de no encontrarse se lanza una excepción de tipo
        // UsernameNotFoundException
        UserEntity user = userRepository
                .findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("User not found"));
        //Colleccion la cual contiene el tipo o tipos de autoridad que tiene el usuario
        Collection<? extends GrantedAuthority> authorities = user
                    //Obtención de roles y mapeo de roles
                         .getRoles()
                    .stream().
                    /*Cada rol es guardado en un objeto de tipo SimpleGrantedAuthority con el nombre de "ROLE_" y
                     se le concatena el rol del usuario*/
                     map(role->new SimpleGrantedAuthority("ROLE_".concat(role.getName().name())))
                    //Finalmente se convierte todo en una colleccion de objetos SimpleGrantedAuthority los cuales son los roles
                    .collect(Collectors.toSet());
        // Se devuelve un usuario de tipo User (No de tipo UserEntity) esta clase de es de Spring Security
        return new User(
                user.getUsername(),//Usernmae del usuario
                user.getPassword(),//Contraseña del usuario
                true,//Usuario activo
                true,//La cuenta no expira
                true,//Las credenciales no expiran
                true,//La cuenta no se bloquea
                authorities//Roles del usuario
        );
    }
}
