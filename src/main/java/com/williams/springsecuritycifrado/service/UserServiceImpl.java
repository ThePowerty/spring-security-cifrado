package com.williams.springsecuritycifrado.service;

import com.williams.springsecuritycifrado.entities.User;
import com.williams.springsecuritycifrado.entities.util.Role;
import com.williams.springsecuritycifrado.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public List<User> findAll() {
        log.info("Executing findAll Users");
        return this.userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        log.info("Executing findById");
        return this.userRepository.findById(id);
    }

    @Override
    public Long count() {
        log.info("Get total number of users");
        return this.userRepository.count();
    }

    @Override
    public User save(User user) {
        log.info("Creating / Updating User");

        if (!validate(user))
            return null;

        return this.userRepository.save(user);
    }

    private boolean validate(User user) {
        if (user == null) {
            log.warn("Trying to create null user");
            return false;
        }
        if (user.getRole() == null) {
            log.warn("Trying to create user with not role");
        }
        return true;
    }

    @Override
    public void deleteById(Long id) {
        log.info("Deleting user by id: " + id);
        if (id == null || id <= 0 ) {
            log.warn("Trying to delete user with wrong id");
            return;
        }

        try {
            this.userRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Error trying to delete user by id {}", id, e);
        }
    }

    @Override
    public void deleteAll() {
        log.info("Deleting all users");
        this.userRepository.deleteAll();
    }

    @Override
    public void deleteAll(List<User> users) {
        log.info("Deleting user by List");
        if (CollectionUtils.isEmpty(users)) {
            log.warn("Trying to delete an empty or null user list");
            return;
        }
        this.userRepository.deleteAll(users);
    }

    @Override
    public void deleteAllById(List<Long> ids) {
        log.info("Deleting user by list of ids");
        if (CollectionUtils.isEmpty(ids)) {
            log.warn("Trying to delete an empty or null user list");
            return;
        }
        this.userRepository.deleteAllById(ids);
    }

    @Override
    public List<User> findByRole(Role role) {
        log.info("Executing findByRole");
        return this.userRepository.findByRole(role);
    }

}
