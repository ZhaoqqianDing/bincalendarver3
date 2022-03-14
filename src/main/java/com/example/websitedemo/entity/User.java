package com.example.websitedemo.entity;


import lombok.Data;

@Data
public class User {
    private String userId;
    private String userName;
    private String password;
    private String userType;

    @Override
    public String toString() {
        return "User{" +
                "id=" +userId +
                ", userName='" + userName + '\'' +
                ", passWord='" + password + '\'' +
                ", realName='" + userType + '\'' +
                '}';
    }
}