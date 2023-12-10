package com.jwt.security.model.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequest {
    private String username;
    private String userPassword;
}
