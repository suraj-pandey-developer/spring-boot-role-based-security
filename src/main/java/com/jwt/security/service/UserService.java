package com.jwt.security.service;

import com.jwt.security.model.User;
import com.jwt.security.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerNewUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getUserPassword());
        user.setUserPassword(encodedPassword);
        return userDao.save(user);
    }
}
