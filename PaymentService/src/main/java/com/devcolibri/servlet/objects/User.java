package com.devcolibri.servlet.objects;

public class User {
    private int id;
    private String username;
    private String email;
    private String passHash;
    private String firstName;
    private String lastName;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassHash() {
        return this.passHash;
    }

    public void setPassHash(String passHash) {
        this.passHash = passHash;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public User (String username, String email, String passHash, String firstName, String lastName) {
        this.username = username;
        this.email = email;
        this.passHash = passHash;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User (int id, String username, String email, String passHash, String firstName, String lastName) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.passHash = passHash;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
