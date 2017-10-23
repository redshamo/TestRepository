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
		Integer originalId = rs.getInt("original_id");
		Integer star = rs.getInt("star");
		return new Book(id, rFlag, title, fileName, originalId, star);
	};

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	// とりあえず全件検索
	public List<Book> findAll(Integer id) {
		List<Book> bookList = jdbcTemplate.query("SELECT * FROM bookinfo WHERE rflag = :id ORDER BY id",
				new MapSqlParameterSource().addValue("id", id), BOOK_ROW_MAPPER);
		return bookList;
	}

	// 元ネタから検索
	public List<Book> searchByTitle(Integer id, Integer originalId) {
		System.out.println("元ネタから検索");
		List<Book> bookList = jdbcTemplate.query(
				"SELECT * FROM bookinfo WHERE rflag = :id AND original_id = :originalId ORDER BY id",
				new MapSqlParameterSource().addValue("id", id).addValue("originalId", originalId), BOOK_ROW_MAPPER);
		return bookList;
	}

	// 評価から検索
	public List<Book> searchByStar(Integer id, Integer star) {
		System.out.println("評価から検索");
		List<Book> bookList = jdbcTemplate.query("SELECT * FROM bookinfo WHERE rflag = :id AND star = :star ORDER BY id",
				new MapSqlParameterSource().addValue("id", id).addValue("star", star), BOOK_ROW_MAPPER);
		return bookList;
	}

	// 元ネタ、評価で検索
	public List<Book> searchByAll(Integer id, Integer star, Integer originalId) {
		System.out.println("両方で検索");
		List<Book> bookList = jdbcTemplate.query(
				"SELECT * FROM bookinfo WHERE rflag = :id AND star = :star AND original_id = :originalId ORDER BY id",
				new MapSqlParameterSource().addValue("id", id).addValue("star", star).addValue("originalId",
						originalId),
				BOOK_ROW_MAPPER);
		return bookList;
	}

}
