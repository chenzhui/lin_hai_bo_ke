package com.example.Entity;

public class Message {
    private int id;

    private String name;

    private String message;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Message(int id, String name, String message) {
        this.id = id;
        this.name = name;
        this.message = message;
    }

    public Message() {}
}
