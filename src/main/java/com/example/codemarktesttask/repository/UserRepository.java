package com.example.codemarktesttask.repository;

import com.example.codemarktesttask.model.Role;
import com.example.codemarktesttask.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, String> {
    User getByLogin(String login);

    void deleteByLogin(String login);
}
