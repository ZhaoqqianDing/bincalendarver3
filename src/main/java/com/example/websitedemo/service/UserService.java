package com.example.websitedemo.service;

import com.example.websitedemo.entity.User;

public interface UserService {
    User Sel(int id);
    User login(String userName, String passWord);
     int register(User user);
     User selectUserByUserName(String userName);
}
