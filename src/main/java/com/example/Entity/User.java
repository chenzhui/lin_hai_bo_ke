package com.example.Entity;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = -3889422580868218723L;

    private int id;

    private String name;

    private String phone;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    private String password;

    private String avatar;

    public User(int id, String phone, String password, String avatar) {
        this.id = id;
        this.phone = phone;
        this.password = password;
        this.avatar = avatar;
    }

    public User() {}
}