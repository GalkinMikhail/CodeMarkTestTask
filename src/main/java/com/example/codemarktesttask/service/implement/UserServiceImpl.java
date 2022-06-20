package com.example.codemarktesttask.service.implement;


import com.example.codemarktesttask.model.User;
import com.example.codemarktesttask.repository.UserRepository;
import com.example.codemarktesttask.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<String> getAllUsers() {
        List<User> listUsers = userRepository.findAll();
        List<String> result = new ArrayList<>();
        listUsers.forEach(users -> result.add(users.getName()));
        return result;
    }

    @Override
    public User getUser(String login) {
        return userRepository.getByLogin(login);
    }

    @Override
    public void deleteUserByLogin(String login) {
        userRepository.deleteByLogin(login);
    }


}
