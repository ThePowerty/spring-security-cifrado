package com.williams.springsecuritycifrado.entities.util;

import java.util.Arrays;
import java.util.List;

public enum Role {

    CUSTOMER(Arrays.asList(Permission.READ_ALL_CARS)),
    ADMINISTRATOR(Arrays.asList(Permission.SAVE_CAR, Permission.READ_ALL_CARS, Permission.UPDATE_CAR, Permission.DELETE_CAR));

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
