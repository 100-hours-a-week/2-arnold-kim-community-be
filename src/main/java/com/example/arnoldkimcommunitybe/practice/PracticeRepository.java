package com.example.arnoldkimcommunitybe.practice;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Repository
@RequestMapping
@RequiredArgsConstructor
public class PracticeRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Practice> userRowMapper = (rs, rowNum) -> new Practice(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("email")
    );

    // 모든 사용자 조회
    public List<Practice> findAllUsers() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, userRowMapper);
    }
}
