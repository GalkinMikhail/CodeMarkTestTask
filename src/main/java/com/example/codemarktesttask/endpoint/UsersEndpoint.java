package com.example.codemarktesttask.endpoint;


import com.example.codemarktesttask.exception.EmptyDataException;
import com.example.codemarktesttask.exception.InvalidPasswordException;
import com.example.codemarktesttask.interfaces.*;
import com.example.codemarktesttask.model.Role;
import com.example.codemarktesttask.model.User;
import com.example.codemarktesttask.repository.RoleRepository;
import com.example.codemarktesttask.repository.UserRepository;
import com.example.codemarktesttask.service.implement.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Endpoint
@RequiredArgsConstructor
public class UsersEndpoint {

    private static final String NAMESPACE_URI = "http://soap.rca/classc/courses";

    private final UserServiceImpl userService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Transactional
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "CreateUserRequest")
    @ResponsePayload
    public CreateUserResponse create(@RequestPayload CreateUserRequest request) {
        CreateUserResponse response = new CreateUserResponse();
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


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAllUsersRequest")
    @ResponsePayload
    public GetAllUsersResponse getAll(@RequestPayload GetAllUsersRequest request) {
        GetAllUsersResponse response = new GetAllUsersResponse();
        response.setUsers(userService.getAllUsers());
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetUserRequest")
    @ResponsePayload
    public GetUserResponse getUser(@RequestPayload GetUserRequest request) {
        GetUserResponse response = new GetUserResponse();
        Users user = new Users();
        BeanUtils.copyProperties(userService.getUser(request.getLogin()), user);
        response.setLogin(user.getLogin());
        response.setName(user.getName());
        response.setPassword(user.getPassword());
        List<String> roleList = new ArrayList<>();
        userService.getUser(request.getLogin()).getRoles()
                .forEach(role -> roleList.add(role.getName()));
        response.setRoles(roleList);
        return response;
    }

    @Transactional
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "UpdateUserRequest")
    @ResponsePayload
    public UpdateUserResponse update(@RequestPayload UpdateUserRequest request) {
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

    @Transactional
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "DeleteUserRequest")
    @ResponsePayload
    public DeleteUserResponse deleteUser(@RequestPayload DeleteUserRequest request) {
        DeleteUserResponse response = new DeleteUserResponse();
        userService.deleteUserByLogin(request.getLogin());
        response.setMessage("User with login: " + request.getLogin() + " was deleted successfully");
        return response;
    }

}
