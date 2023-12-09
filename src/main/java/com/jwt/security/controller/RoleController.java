package com.jwt.security.controller;

import com.jwt.security.model.Role;
import com.jwt.security.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleController {
    @Autowired
    private RoleService roleService;
    @PostMapping({"/createNewRole"})
    public Role createNewRole(@RequestBody Role role) {
        System.out.println("-----------role " + role.toString());
        return roleService.createNewRole(role);
    }
}
