package com.yeonsung.dto;

import java.sql.Timestamp; // java.sql.Timestamp를 import하여 사용

//데이터 전송 객체 클래스
public class BoardDto {
    
    private int id; 
    private String title; 
    private String content; 
    private String authorId; 
    private Timestamp date; 

    // Getter와 Setter
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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

    public String getAuthorId() {
        return authorId;
    }
    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public Timestamp getDate() {
        return date;
    }
    public void setDate(Timestamp date) {
        this.date = date;
    }
}
