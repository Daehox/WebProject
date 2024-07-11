package com.yeonsung.dao;

import java.util.logging.Logger;
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

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 모든 게시물을 날짜 역순으로 가져오기
    public List<BoardDto> getAllPosts() {
        String sql = "SELECT * FROM board ORDER BY date DESC"; 
        return jdbcTemplate.query(sql, new RowMapper<BoardDto>() {
            @Override
            // ResultSet 을 통해서 BoardDto 객체로 변환
            public BoardDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return ResultSet(rs);
            }
        });
    }
    
    // ResultSet에서 BoardDto 객체 추출하기
    private BoardDto ResultSet(ResultSet rs) throws SQLException {
    	// 새로운 BoardDto 객체 생성
        BoardDto post = new BoardDto(); 
        post.setId(rs.getInt("id")); 
        post.setTitle(rs.getString("title")); 
        post.setContent(rs.getString("content")); 
        post.setDate(rs.getTimestamp("date")); 
        post.setAuthorId(rs.getString("author_id")); 
        post.setImagePath(rs.getString("image_path")); 
        return post; 
    }

    // 새로운 게시물 추가하기
    public void insertPost(BoardDto post) {
        String sql = "INSERT INTO board (title, content, author_id, image_path) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, post.getTitle(), post.getContent(), post.getAuthorId(), post.getImagePath());
    }

    // 게시물 상세 보기
    // 사용자가 클릭한 게시물의 ID를 가져와 @GetMapping("/view/{postId}") 과 연결 시켜줌
    public BoardDto getPostById(int postId) {
        String sql = "SELECT * FROM board WHERE id = ?"; 
        return jdbcTemplate.queryForObject(sql, new Object[]{postId}, new RowMapper<BoardDto>() {
            @Override
            public BoardDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return ResultSet(rs);
            }
        });
    }

    // 게시물 수정하기
    public void updatePost(BoardDto post) {
        String sql = "UPDATE board SET title = ?, content = ?"; 
        jdbcTemplate.update(sql, post.getTitle(), post.getContent());
    }

    // 게시물 삭제하기
    public void deletePost(int postId) {
        String sql = "DELETE FROM board WHERE id = ?"; 
        jdbcTemplate.update(sql, postId);
    }

    // 페이징 표시를 하기 위한 메서드
    // pageNumber = 요청된 페이지 번호, postsPerPage = 한 페이지에 표시할 게시물 수 
    public List<BoardDto> getPostsByPage(int pageNumber, int postsPerPage) {
        int startRow = (pageNumber - 1) * postsPerPage;
        String sql = "SELECT * FROM board ORDER BY date DESC LIMIT ?, ?"; 
        return jdbcTemplate.query(sql, new Object[]{startRow, postsPerPage}, new RowMapper<BoardDto>() {
            @Override
            public BoardDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return ResultSet(rs);
            }
        });
    }

    // 전체 게시물 수 가져오기
    // 페이지네이션에서 활용
    public int getTotalPostCount() {
        String sql = "SELECT COUNT(*) FROM board"; 
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
    
    // 제목으로 게시물 검색하기
    // 검색 결과를 List에 저장
    public List<BoardDto> searchPostsByTitle(String title) {
        // 검색 패턴 설정 => 공백이 있어도 검색 가능
        String searchPattern = "%" + title.replaceAll("\\s+", "%") + "%";
        // 제목에 공백을 제거한 후 검색 패턴과 일치하는 행 선택
        String sql = "SELECT * FROM board WHERE REPLACE(title, ' ', '') LIKE ? ORDER BY date DESC";
        // 사용자가 입력한 searchPattern을 사용해 검색 후 결과값 객체로 매핑
        List<BoardDto> result = jdbcTemplate.query(sql, new Object[]{searchPattern}, new RowMapper<BoardDto>() {
            @Override
            public BoardDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return ResultSet(rs);
            }
        });

        // 결과가 비어있을 경우
        // 검색결과가 없습니다. 출력.
        if (result.isEmpty()) {
            Logger logger = Logger.getLogger("SearchLogger");
            logger.info("검색결과가 없습니다.");
        }
        return result;
    }

}
