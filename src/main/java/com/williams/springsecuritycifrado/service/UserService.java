package com.williams.springsecuritycifrado.service;

import com.williams.springsecuritycifrado.entities.Car;
import com.williams.springsecuritycifrado.entities.User;
import com.williams.springsecuritycifrado.entities.util.Role;

import java.util.List;
import java.util.Optional;

public interface UserService {

    // spring repository methods
    List<User> findAll();

    Optional<User> findById(Long id);

    Long count();

    User save(User user);

    void deleteById(Long id);

    void deleteAll();

    void deleteAll(List<User> users);

    void deleteAllById(List<Long> ids);

    // custom methods

    List<User> findByRole(Role role);
}
