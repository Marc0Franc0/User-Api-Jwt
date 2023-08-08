package com.app.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Builder
@Getter
@Setter
public class RegisterDTO {
    private String username;
    private String password;
    private Set<String>roles;
}
