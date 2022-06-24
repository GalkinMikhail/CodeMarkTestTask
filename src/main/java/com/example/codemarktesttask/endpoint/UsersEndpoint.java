package com.example.codemarktesttask.endpoint;


import com.example.codemarktesttask.interfaces.*;
import com.example.codemarktesttask.service.implement.RoleServiceImpl;
import com.example.codemarktesttask.service.implement.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
@RequiredArgsConstructor
public class UsersEndpoint {

    private static final String NAMESPACE_URI = "http://soap.rca/classc/courses";

    private final UserServiceImpl userService;
    private final RoleServiceImpl roleService;


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "CreateUserRequest")
    @ResponsePayload
    public CreateUserResponse create(@RequestPayload CreateUserRequest request) {
        return userService.createUser(request);
    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAllUsersRequest")
    @ResponsePayload
    public GetAllUsersResponse getAll(@RequestPayload GetAllUsersRequest request) {
        return userService.getAllUsers();
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetUserRequest")
    @ResponsePayload
    public GetUserResponse getUser(@RequestPayload GetUserRequest request) {
        return userService.getUser(request);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "UpdateUserRequest")
    @ResponsePayload
    public UpdateUserResponse update(@RequestPayload UpdateUserRequest request) {
        return userService.update(request);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "DeleteUserRequest")
    @ResponsePayload
    public DeleteUserResponse deleteUser(@RequestPayload DeleteUserRequest request) {
        return userService.deleteUserByLogin(request);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "CreateRoleRequest")
    @ResponsePayload
    public CreateRoleResponse createRole(@RequestPayload CreateRoleRequest request) {
        return roleService.createRole(request);
    }

}
