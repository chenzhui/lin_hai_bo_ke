package com.example.Entity;

public class BlogLike {
    private int blogId;

    private int likerId;

    public int getBlogId() {
        return this.blogId;
    }

    public void setBlogId(int blogId) {this.blogId = blogId;}

    public int getLikerId() {return likerId;}

    public void setLikerId(int likerId) {this.likerId = likerId;}

    public BlogLike(int likerId, int blogId) {this.blogId = blogId;this.likerId = likerId;}

    public BlogLike() {}
}