package com.finshine.dao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table EARTICLE.
 */
public class EArticle {

    private Long id;
    private Long articleId;
    private String title;
    private Long postTime;
    private Long created;
    private Long lastmod;
    private String resource;
    private String author;
    private String content;
    private String status;

    public EArticle() {
    }

    public EArticle(Long id) {
        this.id = id;
    }

    public EArticle(Long id, Long articleId, String title, Long postTime, Long created, Long lastmod, String resource, String author, String content, String status) {
        this.id = id;
        this.articleId = articleId;
        this.title = title;
        this.postTime = postTime;
        this.created = created;
        this.lastmod = lastmod;
        this.resource = resource;
        this.author = author;
        this.content = content;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getPostTime() {
        return postTime;
    }

    public void setPostTime(Long postTime) {
        this.postTime = postTime;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Long getLastmod() {
        return lastmod;
    }

    public void setLastmod(Long lastmod) {
        this.lastmod = lastmod;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}