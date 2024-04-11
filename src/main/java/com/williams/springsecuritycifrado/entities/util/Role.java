package com.williams.springsecuritycifrado.entities.util;

import java.util.Arrays;
import java.util.List;

public enum Role {

    CUSTOMER(Arrays.asList(
            Permission.READ_CARS,
            Permission.READ_USER,
            Permission.UPDATE_CAR,
            Permission.SAVE_CAR)),
    ADMINISTRATOR(Arrays.asList(
            Permission.SAVE_CAR,
            Permission.READ_CARS,
            Permission.READ_USER,
            Permission.UPDATE_CAR,
            Permission.UPDATE_ROLE,
            Permission.DELETE_ALL));

    private List<Permission> permissions;

    Role(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

}
