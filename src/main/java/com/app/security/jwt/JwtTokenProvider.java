package com.app.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

//Esta clase contiene métodos referentes a la generación, validación y obtención de contenido de un token
@Component
@Slf4j
public class JwtTokenProvider {

    // Se utiliza para el tiempo de validez de los tokens
    @Value("${jwt.time.expiration}")
    private String timeExpiration;

    //Se utiliza para firmar los tokens
    @Value("${jwt.secret.key}")
    private String secretKey;

    // Método para la generación de un token de acceso
    public String generateAccessToken(String username){

        //Creación del token para retornarlo en el método
       return Jwts.builder()
                //Username del token
                .setSubject(username)
               //Fecha de creación del token
               .setIssuedAt(new Date(System.currentTimeMillis()))
               .setExpiration(new Date(System.currentTimeMillis()+Long.parseLong(timeExpiration)))
               //Firma del token
               .signWith(getKey())
               .compact();

    }

    // Método para validar el token de acceso
    public boolean isTokenValid(String token){
        boolean isValid=false;
        try{
            //Obtención de contenido del token
           getAllClaims(token);
            isValid =true;
        }catch (Exception e){
            log.error("Token invalido, error: ".concat(e.getMessage()));
            isValid=false;
        }
        return isValid;
    }

    //Método el cual retorna la firma del token codificada
    public Key getKey(){
        // Se docodifica la secretKey
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        // Se vuelve a codificar en una codificación util a la hora de generar un token
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Método para obtener el username del token
    public String getUsernameFromToken(String token) {
        //Retorna el valor username del token
        //Pasando como parámetro el token y el método que se
        // quiere utilizar para la obtención del mismo
        return getClaim(token,Claims::getSubject);
    }

    //Obtención de una propiedad del token
    public  <T> T getClaim(String token, Function<Claims, T> claimsTFunction){
        //Obtención de todo el contenido del token
        Claims claims = getAllClaims(token);
        //Método que obtiene la propiedad
        return claimsTFunction.apply(claims);
    }

    //Obtención de todo el contenido de un token
    public Claims getAllClaims(String token){
        // El método parser se utiliza para leer un token
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
