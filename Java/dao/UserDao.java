package com.yeonsung.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.yeonsung.dto.UserDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

// Spring 컨테이너에 Bean 등록, 데이터에 접근 가능   
@Repository
public class UserDao {

    // DB 연결 JdbcTemplate
    // Autowired를 사용하여 JdbcTemplate 객체 주입
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // User 정보를 DB에 넣어주는 insert 선언
    public void insert(UserDto user) {
        String sql = "INSERT INTO member (id, passwd, name, email, postcode, address, detailaddress, extraaddress) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getId(), user.getPasswd(), user.getName(), user.getEmail(),
                            user.getPostcode(), user.getAddress(), user.getDetailAddress(), user.getExtraAddress());
    }

    // ID와 Password로 로그인 확인하는 메소드
    public Optional<UserDto> login(String id, String passwd) {
        String sql = "SELECT id, passwd FROM member WHERE id = ? AND passwd = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new Object[]{id, passwd}, new LoginMapper()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    // ID가 존재하는지 확인하는 메소드
    public boolean existsById(String id) {
        String sql = "SELECT COUNT(*) FROM member WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{id}, Integer.class);
        return count != null && count > 0;
    }

    // 비밀번호가 맞는지 확인하는 메소드
    public boolean isPasswordCorrect(String id, String passwd) {
        String sql = "SELECT COUNT(*) FROM member WHERE id = ? AND passwd = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{id, passwd}, Integer.class);
        return count != null && count > 0;
    }

    // 로그인 확인을 위한 RowMapper
    private static final class LoginMapper implements RowMapper<UserDto> {
        @Override
        public UserDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            UserDto user = new UserDto();
            user.setId(rs.getString("id"));
            user.setPasswd(rs.getString("passwd"));
            return user;
        }
    }
}
