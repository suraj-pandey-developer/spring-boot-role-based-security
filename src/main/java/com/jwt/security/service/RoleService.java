package com.jwt.security.service;

import com.jwt.security.model.Role;
import com.jwt.security.repository.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleDao roleDao;
    public Role createNewRole(Role role) {
        return roleDao.save(role);
    }
}
