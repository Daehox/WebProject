package com.yeonsung.dto;

import java.sql.Timestamp;

//데이터 전송 객체 클래스
public class CommentDto {
	
	//필드 선언
    private int id;
    private int postId;
    private String content;
    private Timestamp date;
    private String userId; 

    
    // Getter와 Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
