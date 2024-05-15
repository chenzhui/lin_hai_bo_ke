package com.example.Entity;

public class CommentLike {
    private int cmtId;

    private int likerId;

    public CommentLike(int cmtId, int likerId) {
        this.likerId = likerId;
        this.cmtId = cmtId;
    }

    public CommentLike() {}

    public int getCmtId() {
        return cmtId;
    }

    public void setCmtId(int cmtId) {
        this.cmtId = cmtId;
    }

    public int getLikerId() {return likerId;}

    public void setLikerId(int likerId) {this.likerId = likerId;}
}