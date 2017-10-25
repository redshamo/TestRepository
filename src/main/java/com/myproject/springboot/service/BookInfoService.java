package com.myproject.springboot.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.springboot.domain.Book;
import com.myproject.springboot.domain.Original;
import com.myproject.springboot.repository.BookInfoRepository;
import com.myproject.springboot.repository.OriginalRepository;

@Service
public class BookInfoService {

	@Autowired
	BookInfoRepository bookInfoRepository;

	@Autowired
	OriginalRepository originalRepository;

	// 何も考えずに全検索
	public List<Book> findAll(Boolean eFlag) {
		int id = eFlag ? 1 : 0;
		return bookInfoRepository.findAll(id);
	}

	// 詳細表示
	public Book findDetail(Integer bookId) {
		return bookInfoRepository.findDetail(bookId);
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
	
	// 評価更新
	public void updateStar(String strId,String strStar) {
		Integer bookId = Integer.parseInt(strId);
		Integer star = Integer.parseInt(strStar);
		bookInfoRepository.updateStar(bookId, star);
	}

	// パスを作成
	public String createPath(Integer bookId) {
		Book book = bookInfoRepository.findDetail(bookId);
		Original original = originalRepository.findById(book.getOriginalId());
		String path = original.getTitle() + "/" + book.getTitle();
		System.out.println("PATH作成完了！："+path);
		return path;
	}

	// フォルダオープン
	public void openFolder(String path) {
		try {
			// 起動中のOS名を取得し、それぞれの環境用のコマンドを流す
			Runtime rt = Runtime.getRuntime();
			String cmd;
			String masterPath;
			String osNAME = System.getProperty("os.name").toLowerCase();
			System.out.println(osNAME);
			if (osNAME.equals("mac os x")) {
				masterPath = "/Users/youmon/Downloads";
				path = masterPath + "/" + path;
				cmd = "/usr/bin/open -a /System/Library/CoreServices/Finder.app " + path;
				rt.exec(cmd);
			} else if (osNAME.equals("windows")) {
				masterPath = "";
				path = masterPath + "/" + path;
				cmd = "explorer " + path;
				rt.exec(cmd);
			} else {
				// 保証対象外のOSです。的なメッセージを返す？
			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	// 表紙をコピーしてくる
	public void copyFrontPage() {
		String fileName = "test.jpg";
		FileSystem fileSystem = FileSystems.getDefault();
		Path inputPath = fileSystem.getPath("/Users/youmon/Desktop/" + fileName);
		Path outputPath = Paths
				.get(new File(".").getAbsoluteFile().getParent() + "/src/main/resources/static/images/" + fileName);
		try {
			Files.copy(inputPath, outputPath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	}
}
