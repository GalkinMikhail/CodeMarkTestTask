package com.example.codemarktesttask.service.interfaces;

import com.example.codemarktesttask.interfaces.CreateRoleRequest;
import com.example.codemarktesttask.interfaces.CreateRoleResponse;

public interface RoleService {

    CreateRoleResponse createRole(CreateRoleRequest request);

}
