package com.yeonsung.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yeonsung.dao.BoardDao;
import com.yeonsung.dto.BoardDto;
@Service
public class BoardService {
    
    @Autowired
    private BoardDao boardDao;

    // 모든 게시물을 날짜 역순으로 가져오기
    public List<BoardDto> getAllPosts() {
        return boardDao.getAllPosts();
    }

    // 새로운 게시물 추가하기
    public void insertPost(BoardDto post) {
        boardDao.insertPost(post);
    }

    // ID로 특정 게시물 가져오기
    public BoardDto getPostById(int postId) {
        return boardDao.getPostById(postId);
    }

    // 게시물 수정하기
    public void updatePost(BoardDto post) {
        boardDao.updatePost(post);
    }

    // 게시물 삭제하기
    public void deletePost(int postId) {
        boardDao.deletePost(postId);
    }

    // 특정 페이지의 게시물 목록 가져오기
    public List<BoardDto> getPostsByPage(int pageNumber, int postsPerPage) {
        return boardDao.getPostsByPage(pageNumber, postsPerPage);
    }

    // 전체 게시물 수 가져오기
    public int getTotalPostCount() {
        return boardDao.getTotalPostCount();
    }
}
