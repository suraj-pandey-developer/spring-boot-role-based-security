package com.jwt.security.service;

import com.jwt.security.model.User;
import com.jwt.security.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public User registerNewUser(User user) {
        return userDao.save(user);
    }
}
