package com.example.Entity;

public class Letter {
    private int letterId;

    private int smallId;

    private int bigId;

    private String time;

    private String content;

    private int towardId;

    public Letter(int smallId, int bigId, String content, int towardId) {
        this.smallId = smallId;
        this.bigId = bigId;
        this.content = content;
        this.towardId = towardId;
    }

    public Letter() {}
}
