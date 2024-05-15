package com.example.Entity;

import java.io.Serializable;

public class Letter implements Serializable {
    private int letterId;

    private int id1;

    private int id2;

    public int getLetterId() {
        return letterId;
    }

    public void setLetterId(int letterId) {
        this.letterId = letterId;
    }

    public int getId1() {
        return id1;
    }

    public void setId1(int id1) {
        this.id1 = id1;
    }

    public int getId2() {
        return id2;
    }

    public void setId2(int id2) {
        this.id2 = id2;
    }

    public String getId1Name() {
        return id1Name;
    }

    public void setId1Name(String id1Name) {
        this.id1Name = id1Name;
    }

    public String getId2Name() {
        return id2Name;
    }

    public void setId2Name(String id2Name) {
        this.id2Name = id2Name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getTowardId() {
        return towardId;
    }

    public void setTowardId(int towardId) {
        this.towardId = towardId;
    }

    private String id1Name;

    private String id2Name;

    private String time;

    private String content;

    private int towardId;

    public Letter(int letterId,int id1,int id2,String id1Name,String id2Name,String content,int towardId) {
        this.letterId=letterId;
        this.id1=id1;
        this.id2=id2;
        this.content = content;
        this.towardId = towardId;
        this.id1Name=id1Name;
        this.id2Name=id2Name;
    }

    public Letter(){}
}
