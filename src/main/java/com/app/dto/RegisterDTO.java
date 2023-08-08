package com.app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Builder
@Getter
@Setter
public class RegisterDTO {
    @NotBlank(message = "username no debe estar vacío")
    private String username;
    @NotBlank(message = "password no debe estar vacío")
    private String password;
    private Set<String>roles;
    @NotBlank(message = "firstName no debe estar vacío")
    private String firstName;
    @NotBlank(message = "lastName no debe estar vacío")
    private String lastName;
    @NotNull(message = "age no debe estar vacío")
    private Integer age;
    @NotBlank(message = "email no debe estar vacío")
    @Email(message = "email debe tener un formato correcto")
    private String email;

}
