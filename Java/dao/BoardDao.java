package com.yeonsung.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.yeonsung.dto.BoardDto;

@Repository
public class BoardDao {

    // JdbcTemplate 객체 주입
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 모든 게시물을 날짜 역순으로 가져오기
    public List<BoardDto> getAllPosts() {
        String sql = "SELECT * FROM board ORDER BY date DESC"; 
        //쿼리 실행 후 결과를 List<BoardDto>로 반환
        return jdbcTemplate.query(sql, new RowMapper<BoardDto>() {
            @Override
            public BoardDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                // extractPostFromResultSet 메서드 사용
                return extractPostFromResultSet(rs);
            }
        });
    }

    // 새로운 게시물 추가하기
    public void insertPost(BoardDto post) {
        String sql = "INSERT INTO board (title, content, author_id) VALUES (?, ?, ?)"; 
        //쿼리 실행하여 새로운 게시물 추가
        jdbcTemplate.update(sql, post.getTitle(), post.getContent(), post.getAuthorId());
    }

    // ID로 특정 게시물 가져오기
    public BoardDto getPostById(int postId) {
        String sql = "SELECT * FROM board WHERE id = ?"; 
        //쿼리 실행 후 결과를 BoardDto 객체로 반환
        return jdbcTemplate.queryForObject(sql, new Object[]{postId}, new RowMapper<BoardDto>() {
            @Override
            public BoardDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                // extractPostFromResultSet 메서드 사용
                return extractPostFromResultSet(rs);
            }
        });
    }

    // 게시물 수정하기
    public void updatePost(BoardDto post) {
        String sql = "UPDATE board SET title = ?, content = ?, author_id = ? WHERE id = ?"; 
        //쿼리 실행 => 게시물 수정
        jdbcTemplate.update(sql, post.getTitle(), post.getContent(), post.getAuthorId(), post.getId());
    }

    // 게시물 삭제하기
    public void deletePost(int postId) {
        String sql = "DELETE FROM board WHERE id = ?"; 
        //쿼리 실행 => 게시물 삭제
        jdbcTemplate.update(sql, postId);
    }

    // 특정 페이지의 게시물 목록 가져오기
    public List<BoardDto> getPostsByPage(int pageNumber, int postsPerPage) {
        // 페이지 번호가 1부터 시작하므로, 1페이지는 0부터 시작
        int startRow = (pageNumber - 1) * postsPerPage; // 시작 행 계산
        String sql = "SELECT * FROM board ORDER BY date DESC LIMIT ?, ?"; 
        //쿼리 실행 후 결과를 List<BoardDto>로 반환
        return jdbcTemplate.query(sql, new Object[]{startRow, postsPerPage}, new RowMapper<BoardDto>() {
            @Override
            public BoardDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                // extractPostFromResultSet 메서드 사용
                return extractPostFromResultSet(rs);
            }
        });
    }

    // 전체 게시물 수 가져오기
    public int getTotalPostCount() {
        String sql = "SELECT COUNT(*) FROM board"; 
        //쿼리 실행하고 결과를 정수로 반환
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    // ResultSet에서 BoardDto 객체 추출하기
    private BoardDto extractPostFromResultSet(ResultSet rs) throws SQLException {
        BoardDto post = new BoardDto(); // 새로운 BoardDto 객체 생성
        post.setId(rs.getInt("id")); 
        post.setTitle(rs.getString("title")); 
        post.setContent(rs.getString("content")); 
        post.setDate(rs.getTimestamp("date")); 
        post.setAuthorId(rs.getString("author_id")); 
        return post; 
    }
}
