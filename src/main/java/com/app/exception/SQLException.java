package com.app.exception;

import com.zaxxer.hikari.pool.HikariPool;
import org.hibernate.HibernateException;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.ConnectException;
import java.sql.SQLIntegrityConstraintViolationException;
@RestControllerAdvice
public class SQLException {
    //Validaciones al registrarse (username duplicado en la base de datos)
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity captureSQLIntegrityConstraintViolationException
    (SQLIntegrityConstraintViolationException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Message: ".concat("Pruebe con otro username"));
    }

}
