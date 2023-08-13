package com.app.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class LoginDTO {
    @NotBlank(message = "username no debe estar vacío")
    private String username;
    @NotBlank(message = "password no debe estar vacío")
    private String password;
}
