package com.example.bank.service;

import com.example.bank.dao.UserDao;
import com.example.bank.model.User;

public class AuthService {

    private final UserDao userDao = new UserDao();

    public User login(String username, String password) throws Exception {
        return userDao.authenticate(username, password);
    }
}
