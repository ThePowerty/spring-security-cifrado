package com.williams.springsecuritycifrado.dto;

import com.williams.springsecuritycifrado.entities.Car;
import com.williams.springsecuritycifrado.entities.User;

import java.util.List;

public class UserListDTO {

    private List<User> users;

    public UserListDTO() {}

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
