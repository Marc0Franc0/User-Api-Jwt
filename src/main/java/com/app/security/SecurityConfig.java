package com.app.security;

import com.app.security.jwt.JwtAuthenticationFilter;
import com.app.security.jwt.JwtAuthorizationFilter;
import com.app.security.jwt.JwtTokenProvider;
import com.app.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    JwtAuthorizationFilter jwtAuthorizationFilter;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    //Este objeto se encarga de la administración de la autenticación de los usuarios
    @Bean
    AuthenticationManager authenticationManager(HttpSecurity httpSecurity, PasswordEncoder passwordEncoder)
            throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }


    //Objeto el cual se encarga de la encriptación de contraseñas
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    /*
     * Se establece una cadena de filtros de seguridad en toda la aplicacion
     * Aquí se determinan los permisos según los roles de usuario para acceder a la
     * aplicación y demas configuraciones
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager)
            throws Exception {
        //Se utiliza el filtro de autenticación creado
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtTokenProvider);
        //Se le establece un AutenticacionManager para que pueda funcionar correctamente el cual
        //es creado dentro de esta clase
        jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);
        //Se configura el endpoint para autenticarse
        jwtAuthenticationFilter.setFilterProcessesUrl("/public/auth/login");

        return http
                // Se deshabilita Cross-site request forgery
                .csrf(config->config.disable())
                .sessionManagement(session->{
                    // Permite la gestion de sesiones de tipo STATELESS
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .authorizeHttpRequests(
                        //Configuración de acceso a los endpoints
                        auth-> {
                            //Endpoint de documentación
                            auth.requestMatchers
                                    ("/swagger-ui.html","/v3/api-docs/**","/swagger-ui/**").permitAll();
                            //endpoint para poder registrarse por primera vez y autenticarse
                            auth.requestMatchers("/public/auth/register").permitAll();
                            auth.requestMatchers("/public/auth/login").permitAll();
                            //endpoint público con acceso para usuarios y administradores
                            auth.requestMatchers("/public/**").hasAnyRole("USER","ADMIN");
                            //endpoint privado con acceso solo para administradores
                            auth.requestMatchers("/private/**").hasRole("ADMIN");
                            //para demas endpoint solo se debe estar autenticado
                            auth.anyRequest().authenticated();
                        }
                )
                //Filtro creado el cual es necesario para autenticar un usuario con su username y password
                .addFilter(jwtAuthenticationFilter)
                //Filtro creado el cual es necesario para autorizar utilizando un token
                //Se ejecuta luego del filtro de autenticación
                .addFilterBefore(jwtAuthorizationFilter,UsernamePasswordAuthenticationFilter.class)
                .build();


    }

}
