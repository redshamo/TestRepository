package com.myproject.springboot.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.springboot.domain.Book;
import com.myproject.springboot.repository.BookInfoRepository;

@Service
public class BookInfoService {

	@Autowired
	BookInfoRepository bookInfoRepository;

	// 何も考えずに全検索
	public List<Book> findAll(Boolean eFlag) {
		int id = eFlag ? 1 : 0;
		List<Book> bookList = bookInfoRepository.findAll(id);
		return bookList;
	}

	// 絞り込み検索
	public List<Book> search(Boolean rFlag, Integer star, Integer originalId) {
		int id = rFlag ? 1 : 0;
		List<Book> bookList;
		if (star == -1 && originalId == -1) {
			bookList = bookInfoRepository.findAll(id);
		} else if (star == -1) {
			bookList = bookInfoRepository.searchByTitle(id, originalId);
		} else if (originalId == -1) {
			bookList = bookInfoRepository.searchByStar(id, star);
		} else {
			bookList = bookInfoRepository.searchByAll(id, star, originalId);
		}
		return bookList;
	}

	// フォルダオープン
	public void openFolder(String path) {
		try {
			Runtime rt = Runtime.getRuntime();
			String cmd;
			String osNAME = System.getProperty("os.name").toLowerCase();
			System.out.println(osNAME);
			path = "/Users/youmon/Downloads";
			if (osNAME.equals("mac os x")) {
				cmd = "/usr/bin/open -a /System/Library/CoreServices/Finder.app " + path;
				rt.exec(cmd);
			} else if (osNAME.equals("windows")) {
				cmd = "explorer " + path;
				rt.exec(cmd);
			} else {
				// 保証対象外のOSです。的なメッセージを返す？
			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}
}
