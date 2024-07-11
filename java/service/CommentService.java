package com.yeonsung.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yeonsung.dao.CommentDao;
import com.yeonsung.dto.CommentDto;

@Service
public class CommentService {
    
    @Autowired
    private CommentDao commentDao;

    // 댓글 추가
    public void addComment(CommentDto comment) {
        commentDao.insertComment(comment);
    }

    // 특정 게시물의 모든 댓글 조회
    public List<CommentDto> getCommentsByPostIdPaged(int postId, int page, int pageSize) {
        int offset = (page - 1) * pageSize; // 가져올 댓글의 시작 인덱스
        return commentDao.getCommentsByPostIdPaged(postId, offset, pageSize);
    }

    // 특정 게시물의 댓글 수 조회
    public int getCommentCountByPostId(int postId) {
        return commentDao.getCommentCountByPostId(postId);
    }

}
