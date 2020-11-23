package com.devcolibri.servlet.objects;

public class Role {
    private int id;
    private String name;
    private int privilegeLevel;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrivilegeLevel() {
        return this.privilegeLevel;
    }

    public void setPrivilegeLevel(int privilegeLevel) {
        this.privilegeLevel = privilegeLevel;
    }

    public Role (String name, int privilegeLevel) {
        this.name = name;
        this.privilegeLevel = privilegeLevel;
    }

    public Role (int id, String name, int privilegeLevel) {
        this.id = id;
        this.name = name;
        this.privilegeLevel = privilegeLevel;
    }
}
