package com.example.codemarktesttask.service.implement;


import com.example.codemarktesttask.exception.EmptyDataException;
import com.example.codemarktesttask.exception.InvalidDataException;
import com.example.codemarktesttask.exception.InvalidPasswordException;
import com.example.codemarktesttask.interfaces.*;
import com.example.codemarktesttask.model.Role;
import com.example.codemarktesttask.model.User;
import com.example.codemarktesttask.repository.RoleRepository;
import com.example.codemarktesttask.repository.UserRepository;
import com.example.codemarktesttask.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    @Override
    public GetAllUsersResponse getAllUsers() {
        List<User> listUsers = userRepository.findAll();
        List<String> result = new ArrayList<>();
        listUsers.forEach(users -> result.add(users.getName()));
        GetAllUsersResponse response = new GetAllUsersResponse();
        response.setUsers(result);
        return response;
    }

    @Override
    public GetUserResponse getUser(GetUserRequest request) {
        if (!userRepository.findById(request.getLogin()).isPresent()) {
            throw new InvalidDataException("User with login " + request.getLogin() + " does not exists", "user does not exists");
        }
        GetUserResponse response = new GetUserResponse();
        Users user = new Users();
        BeanUtils.copyProperties(userRepository.getByLogin(request.getLogin()), user);
        response.setLogin(user.getLogin());
        response.setName(user.getName());
        response.setPassword(user.getPassword());
        List<String> roleList = new ArrayList<>();
        userRepository.getByLogin(request.getLogin()).getRoles()
                .forEach(role -> roleList.add(role.getName()));
        response.setRoles(roleList);
        return response;
    }

    @Transactional
    @Override
    public DeleteUserResponse deleteUserByLogin(DeleteUserRequest request) {
        if (!userRepository.findById(request.getLogin()).isPresent()) {
            throw new InvalidDataException("User with login " + request.getLogin() + " does not exists", "user does not exists");
        }
        DeleteUserResponse response = new DeleteUserResponse();
        userRepository.deleteByLogin(request.getLogin());
        response.setMessage("User with login: " + request.getLogin() + " was deleted successfully");
        return response;
    }

    @Transactional
    @Override
    public CreateUserResponse createUser(CreateUserRequest request) {
        CreateUserResponse response = new CreateUserResponse();
        if (userRepository.findById(request.getUsers().getLogin()).isPresent()) {
            throw new InvalidDataException("User with login " + request.getUsers().getLogin() + " already exists", "user already exists");
        }
        User user = new User();
        if (request.getUsers().getLogin().length() == 0 || request.getUsers().getPassword().length() == 0 || request.getUsers().getName().length() == 0) {
            throw new EmptyDataException("Fields name, login, password cannot be empty", "invalid data");
        }
        if (!request.getUsers().getPassword().matches(".*\\d+.*") || !request.getUsers().getPassword().matches(".*[A-Z].*")) {
            throw new InvalidPasswordException("Password must contain an upper case letter and a number", "invalid password");
        }
        user.setLogin(request.getUsers().getLogin());
        user.setName(request.getUsers().getName());
        user.setPassword(request.getUsers().getPassword());
        Set<Role> roleSet = new HashSet<>();
        request.getUsers().getRoleID()
                .forEach(id -> roleSet.add(roleRepository.getReferenceById(id)));
        user.setRoles(roleSet);
        userRepository.save(user);
        response.setMessage("User created successfully");
        return response;
    }

    @Transactional
    @Override
    public UpdateUserResponse update(UpdateUserRequest request) {
        if (!userRepository.findById(request.getUsers().getLogin()).isPresent()) {
            throw new InvalidDataException("User with login " + request.getUsers().getLogin() + " does not exists", "user does not exists");
        }
        if (request.getUsers().getLogin().length() == 0 || request.getUsers().getPassword().length() == 0 || request.getUsers().getName().length() == 0) {
            throw new EmptyDataException("Fields login,name, password cannot be empty", "invalid data");
        }
        if (!request.getUsers().getPassword().matches(".*\\d+.*") || !request.getUsers().getPassword().matches(".*[A-Z].*")) {
            throw new InvalidPasswordException("Password must contain an upper case letter and a number", "invalid password");
        }
        UpdateUserResponse response = new UpdateUserResponse();
        Users data = request.getUsers();
        if (request.getUsers().getRoleID().size() != 0) {
            Set<Role> roleSet = new HashSet<>();
            data.getRoleID()
                    .forEach(id -> roleSet.add(roleRepository.getReferenceById(id)));
            User toUpdate = userRepository.getByLogin(data.getLogin());
            toUpdate.setRoles(roleSet);
            toUpdate.setName(data.getName());
            toUpdate.setPassword(data.getPassword());
            userRepository.save(toUpdate);
        } else {
            User toUpdate = userRepository.getByLogin(data.getLogin());
            toUpdate.setName(data.getName());
            toUpdate.setPassword(data.getPassword());
            userRepository.save(toUpdate);
        }
        response.setMessage("User " + data.getLogin() + " updated successfully");
        return response;
    }

}
