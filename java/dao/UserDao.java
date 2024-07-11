package com.yeonsung.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.yeonsung.dto.UserDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

// Spring 컨테이너에 Bean 등록, 데이터에 접근 가능 Dao 기능 
@Repository
public class UserDao {

	// DB 연결 JdbcTemplate
	// Autowired를 사용하여 JdbcTemplate 객체 주입
	@Autowired
	private JdbcTemplate jdbcTemplate;

	// User 정보를 DB에 넣어주는 insert 선언
	// INSERT 문을 사용 => JdbcTemplate.update 를 사용해 값을 넣어줌
	public void insert(UserDto user) {
		String sql = "INSERT INTO member (id, passwd, name, email, postcode, address, detailaddress, extraaddress) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, user.getId(), user.getPasswd(), user.getName(), user.getEmail(), user.getPostcode(),
				user.getAddress(), user.getDetailAddress(), user.getExtraAddress());
	}

	// ID가 존재하는지 확인하는 메소드
	// COUNT 는 값이 항상 정수지만, SQL 실행이 실패하거나 결과가 없을 경우 null 이 나올수도 있기에 Integer를 사용하여
	// null을 처리할 수 있게 함
	// new Object[]{id} 로 사용자가 입력한 값 넣기
	public boolean existsById(String id) {
		String sql = "SELECT COUNT(*) FROM member WHERE id = ?";
		Integer count = jdbcTemplate.queryForObject(sql, new Object[] { id }, Integer.class);
		return count != null && count > 0;
	}

	// 비밀번호가 맞는지 확인하는 메소드
	public boolean isPasswordCorrect(String id, String passwd) {
		String sql = "SELECT COUNT(*) FROM member WHERE id = ? AND passwd = ?";
		Integer count = jdbcTemplate.queryForObject(sql, new Object[] { id, passwd }, Integer.class);
		return count != null && count > 0;
	}

	// ID와 Password로 로그인 확인하는 메소드
	// Optinal을 활용해 null 값 관리 용이,ofNullabe를 사용해 값이 null일 경우 비어있는 Optinal 값 반환
	// Exception e 예외 처리를 사용해 예외 처리가 있을 경우 비어있는 Optinal 값 반환하여 메서드가 예외를 던지지 않도록
	public Optional<UserDto> login(String id, String passwd) {
        String sql = "SELECT id, passwd, name, email, postcode, address FROM member WHERE id = ? AND passwd = ?";
		try {
			return Optional
					.ofNullable(jdbcTemplate.queryForObject(sql, new Object[] { id, passwd }, new LoginMapper()));
		} catch (Exception e) {
			return Optional.empty();
		}
	}

	// 로그인 확인을 위한 RowMapper 구현 클래스
	// Spring의 RowMapper 인터페이스를 구현하여 데이터베이스 결과를 UserDto 객체로 매핑
	// 이 메서드를 통해 데이터베이스의 담긴 사용자 정보를 가져오고 사용할 수 있음
	private static final class LoginMapper implements RowMapper<UserDto> {

		// 데이터베이스의 결과 셋에서 한 행을 UserDto 객체로 매핑하는 메소드
		@Override
		public UserDto mapRow(ResultSet rs, int rowNum) throws SQLException {
			// 새로운 UserDto 객체 생성
			UserDto user = new UserDto();

			user.setId(rs.getString("id"));
			user.setPasswd(rs.getString("passwd"));
			user.setName(rs.getString("name"));
			user.setEmail(rs.getString("email"));
			user.setPostcode(rs.getString("postcode"));
			user.setAddress(rs.getString("address"));
	
			// 매핑된 UserDto 객체 반환
			return user;
		}
	}
}
