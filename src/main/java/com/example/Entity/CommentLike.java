package com.example.Entity;

public class CommentLike {
    private int cmtId;

    private int id;

    public CommentLike(int cmtId, int authorId) {
        this.id = authorId;
        this.cmtId = cmtId;
    }

    public CommentLike() {}

    public int getCmtId() {
        return cmtId;
    }

    public void setCmtId(int cmtId) {
        this.cmtId = cmtId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}