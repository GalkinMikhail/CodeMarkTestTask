package com.example.codemarktesttask.service.interfaces;

import com.example.codemarktesttask.model.User;

import java.util.List;

public interface UserService {
    List<String> getAllUsers();

    User getUser(String login);

    void deleteUserByLogin(String login);
}
