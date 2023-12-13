package com.example.springorient.domain;

import com.orientechnologies.orient.core.record.OElement;

import jakarta.validation.constraints.NotNull;

public class Article {
    @NotNull
    private String title;
    private String content;
    private String author;

    public Article() {

    }

    public static Article fromOElement(OElement oElement) {
        Article article = new Article();
        article.title = oElement.getProperty("title");
        article.content = oElement.getProperty("content");
        article.author = oElement.getProperty("author");
    
        return article;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Article [title=" + title + ", content=" + content + ", author=" + author + "]";
    }
    
    
    
    
}
