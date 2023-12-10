package com.jwt.security.controller;

import com.jwt.security.model.User;
import com.jwt.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping({"/registerNewUser"})
    public User registerNewUser(@RequestBody User user) {
        return userService.registerNewUser(user);
    }

    @GetMapping("forUser")
    @PreAuthorize("hasRole('user')")
    public String forUser() {
        return "this url is only accesible by user" ;
    }

    @GetMapping("forAdmin")
    @PreAuthorize("hasRole('admin')")
    public String forAdmin() {
        return "this url is only accesible by admin" ;
    }
}
