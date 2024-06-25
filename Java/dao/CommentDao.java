package com.yeonsung.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.yeonsung.dto.CommentDto;

@Repository
public class CommentDao {
    
    // JdbcTemplate 객체를 자동으로 주입
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 댓글 추가 메서드
    public void insertComment(CommentDto comment) {
        String sql = "INSERT INTO comments (postId, content, user_id) VALUES (?, ?, ?)"; 
        jdbcTemplate.update(sql, comment.getPostId(), comment.getContent(), comment.getUserId());
    }

    // 게시물에 대한 페이징된 댓글 조회 메서드
    public List<CommentDto> getCommentsByPostIdPaged(int postId, int offset, int pageSize) {
        String sql = "SELECT * FROM comments WHERE postId = ? ORDER BY date DESC LIMIT ?, ?"; 
        return jdbcTemplate.query(sql, new Object[] { postId, offset, pageSize }, new RowMapper<CommentDto>() {
            @Override
            public CommentDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                // ResultSet에서 CommentDto 객체 추출
                CommentDto comment = new CommentDto();
                comment.setId(rs.getInt("id")); 
                comment.setPostId(rs.getInt("postId")); 
                comment.setContent(rs.getString("content")); 
                comment.setDate(rs.getTimestamp("date")); 
                comment.setUserId(rs.getString("user_id")); 
                return comment;
            }
        });
    }

    // 게시물에 대한 댓글 수 조회 메서드
    public int getCommentCountByPostId(int postId) {
        String sql = "SELECT COUNT(*) FROM comments WHERE postId = ?"; 
        return jdbcTemplate.queryForObject(sql, Integer.class, postId);
    }
}
