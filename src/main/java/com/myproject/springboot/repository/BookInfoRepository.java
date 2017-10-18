package com.myproject.springboot.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.myproject.springboot.domain.Book;

@Repository
@Transactional
public class BookInfoRepository {
	private static final RowMapper<Book> BOOK_ROW_MAPPER = (rs, i) -> {
		Integer id = rs.getInt("id");
		boolean rFlag = (rs.getInt("rFlag") == 1);
		String title = rs.getString("title");
		String fileName = rs.getString("fileName");
		String original = rs.getString("original");
		Integer star = rs.getInt("star");
		return new Book(id, rFlag, title, fileName, original, star);
	};

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	// とりあえず全件検索
	public List<Book> findAll() {
		List<Book> bookList = jdbcTemplate.query("SELECT * FROM bookinfo ORDER BY id", BOOK_ROW_MAPPER);
		return bookList;
	}
}
