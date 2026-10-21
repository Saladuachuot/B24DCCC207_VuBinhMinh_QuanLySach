package com.example.test2;
import java.io.Serializable;

public class Book implements Serializable {
    private String id;
    private String title;
    private String author;
    private String category;
    private String status;
    private float rating;
    private String note;
    public Book(String id, String title, String author, String category, String status, float rating, String note) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.status = status;
        this.rating = rating;
        this.note = note;
    }
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public float getRating() { return rating; }
    public void setRating(float rating) { this.rating = rating; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}