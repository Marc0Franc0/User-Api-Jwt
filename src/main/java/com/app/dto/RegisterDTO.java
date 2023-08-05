package com.app.dto;

import lombok.Builder;
import lombok.Getter;
import java.util.Set;

@Builder
@Getter
public class RegisterDTO {
    private String username;
    private String password;
    private Set<String>roles;
}
