package com.app.security.jwt;

import java.io.IOException;

import com.app.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*Esta clase es un filtro de autorización de Spring que se encarga de
    validar y procesar el token JWT en cada solicitud entrante.*/
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Override
    protected void doFilterInternal(  // Solicitud entrante
                                      @NonNull
                                      HttpServletRequest request,
                                      // Respuesta saliente
                                      @NonNull
                                          HttpServletResponse response,
                                      // Mecanismo para invocar el siguiente filtro en la siguiente cadena de filtros
                                      @NonNull
                                          FilterChain filterChain) throws ServletException, IOException {
        //Obtención del header "Authorization" el cual contiene el token
        String headerAuthorization = request.getHeader("Authorization");
        //Validación de contenido del token
        if(//Se espera que no sea nulo y comienze con "Bearer "
        headerAuthorization!=null && headerAuthorization.startsWith("Bearer ")){
        //Se crea un substring que solo contiene el token
        String token = headerAuthorization.substring(7);
        //Se valida el token obtenido con el método creado en la clase JwtTokenProvider
        if(jwtTokenProvider.isTokenValid(token)){
            //Obtención del username que contiene el token con método de la clase JwtTokenProvider
            String username = jwtTokenProvider.getUsernameFromToken(token);
            /*
             * Se crea un objeto userDetails el cual contendrá todos
             * los detalles de nuestro username según el método loadUserByUsername implementado
             * en la clase UserDetailsServiceImpl
             */
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            //Se crea la autenticación de tipo UsernamePasswordAuthenticationToken
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(username,null, userDetails.getAuthorities());
            //Se establece la autenticación creada en el contexto de la aplicación
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        }
// Permite que la solicitud continue hacia el siguiente filtro en
        // la cadena de filtro establcida en la configuración
        filterChain.doFilter(request, response);
    }
}
