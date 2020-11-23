package com.devcolibri.servlet.objects;

public class UserRole {
    private int id;
    private int userId;
    private int roleId;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoleId() {
        return this.roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public UserRole (int userId, int roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

    public UserRole (int id, int userId, int roleId) {
        this.id = id;
        this.userId = userId;
        this.roleId = roleId;
    }
}
