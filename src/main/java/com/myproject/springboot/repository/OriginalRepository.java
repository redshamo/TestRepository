package com.myproject.springboot.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.myproject.springboot.domain.Original;

@Repository
@Transactional
public class OriginalRepository {

	private static final RowMapper<Original> Original_ROW_MAPPER = (rs, i) -> {
		Integer id = rs.getInt("id");
		String title = rs.getString("title");
		String initial = rs.getString("initial");
		return new Original(id, title, initial);
	};

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	// ひとまず全件検索
	public List<Original> findAll() {
		List<Original> originalList = jdbcTemplate.query("SELECT * FROM original", new MapSqlParameterSource(),
				Original_ROW_MAPPER);
		return originalList;
	}
}
