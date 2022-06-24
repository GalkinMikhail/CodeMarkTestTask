package com.example.codemarktesttask.service.interfaces;

import com.example.codemarktesttask.interfaces.*;
import com.example.codemarktesttask.model.User;

import java.util.List;

public interface UserService {
    GetAllUsersResponse getAllUsers();

    GetUserResponse getUser(GetUserRequest request);

    DeleteUserResponse deleteUserByLogin(DeleteUserRequest request);

    CreateUserResponse createUser(CreateUserRequest request);

    UpdateUserResponse update(UpdateUserRequest request);
}
