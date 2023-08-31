package com.example.Entity;

public class Type {
    private int tId;
    private String kind;

    private String birthTime;

    public Type(int tId,String kind, String birthTime) {
        this.kind = kind;
        this.birthTime = birthTime;
    }

    public Type() {
    }

    public String getKind() {
        return kind;
    }

    public int gettId() {
        return tId;
    }

    public void settId(int tId) {
        this.tId = tId;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getBirthTime() {
        return birthTime;
    }

    public void setBirthTime(String birthTime) {
        this.birthTime = birthTime;
    }
}