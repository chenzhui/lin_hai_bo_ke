package com.example.Entity;

public class Blog {
    private int blogId;

    private int authorId;

    private String title;

    private String content;

    private String birthTime;

    private int liked;

    private String type;

    private String authorName;


    public int getBlogId() {
        return this.blogId;
    }

    public int getAuthorId() {
        return this.authorId;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

    public String getBirthTime() {
        return this.birthTime;
    }

    public int getLiked() {
        return this.liked;
    }

    public String getType() {
        return this.type;
    }

    public String getAuthorName() {
        return this.authorName;
    }

    public Blog(int authorId, String title, String content, String type, String authorName) {
        this.authorId = authorId;
        this.title = title;
        this.content = content;
        this.type = type;
        this.authorName = authorName;
    }

    public Blog(String title, String type, String content, int blogId) {
        this.blogId = blogId;
        this.title = title;
        this.content = content;
        this.type = type;
    }

    public Blog(int authorId, String authorName) {
        this.authorId = authorId;
        this.authorName = authorName;
    }

    public Blog() {}
}