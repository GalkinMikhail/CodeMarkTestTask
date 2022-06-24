package com.example.codemarktesttask.service.implement;

import com.example.codemarktesttask.exception.InvalidDataException;
import com.example.codemarktesttask.interfaces.CreateRoleRequest;
import com.example.codemarktesttask.interfaces.CreateRoleResponse;
import com.example.codemarktesttask.model.Role;
import com.example.codemarktesttask.repository.RoleRepository;
import com.example.codemarktesttask.service.interfaces.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Transactional
    @Override
    public CreateRoleResponse createRole(CreateRoleRequest request) {
        if (roleRepository.findByName(request.getName()) != null) {
            throw new InvalidDataException("Role " + request.getName() + " already exists", "role already exists");
        }
        CreateRoleResponse response = new CreateRoleResponse();
        Role role = new Role();
        role.setName(request.getName());
        roleRepository.save(role);
        response.setMessage("Role " + request.getName() + " created successfully");
        return response;
    }
}
