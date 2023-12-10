package com.jwt.security.controller;

import com.jwt.security.model.payload.AuthRequest;
import com.jwt.security.model.payload.AuthResponse;
import com.jwt.security.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtController {

    @Autowired
    private JwtService jwtService;

    @PostMapping({"/authenticate"})
    public AuthResponse createJwtToken(@RequestBody AuthRequest authRequest) throws Exception {
        return jwtService.createJwtToken(authRequest);
    }
}
