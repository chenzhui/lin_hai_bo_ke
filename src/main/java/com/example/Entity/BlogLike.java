package com.example.Entity;

public class BlogLike {
    private int blogId;

    private int id;

    public int getBlogId() {
        return this.blogId;
    }

    public int getId() {return id;}

    public void setId(int id) {
        this.id = id;
    }

    public BlogLike(int id, int blogId) {
        this.blogId = blogId;
        this.id = id;
    }

    public BlogLike() {}
}