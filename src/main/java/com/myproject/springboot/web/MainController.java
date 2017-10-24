package com.myproject.springboot.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.myproject.springboot.domain.Book;
import com.myproject.springboot.domain.Original;
import com.myproject.springboot.repository.OriginalRepository;
import com.myproject.springboot.service.BookInfoService;

@RestController
public class MainController {

	@Autowired
	BookInfoService bookInfoService;

	@Autowired
	OriginalRepository originalRepository;

	// 初期表示 TOP
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView index(ModelAndView mav) {
		mav.setViewName("index");
		mav.addObject("msg", "input your name :"); // 表示メッセージ
		return mav;
	}

	// テスト用 TOPに戻る
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ModelAndView send(@RequestParam("name") String name, ModelAndView mav) {
		mav.setViewName("index");
		// 仮置きテスト 後で消す
		mav.addObject("msg", "Hello " + name + " !"); // 表示メッセージ
		mav.addObject("value", name); // 入力テキストに入力値を表示
		bookInfoService.openFolder(null); // フォルダオープンテスト
		bookInfoService.copyFrontPage(); // 表紙コピーテスト
		return mav;
	}

	// 一覧表示(初回)
	@RequestMapping(value = "/bookList", method = RequestMethod.GET)
	public ModelAndView viewList(@RequestParam("rFlag") boolean rFlag, ModelAndView mav) {
		List<Book> bookList = bookInfoService.findAll(rFlag);
		List<Original> originalList = originalRepository.findAll();
		mav.setViewName("bookList");
		mav.addObject(bookList);
		mav.addObject(originalList);
		mav.addObject("rFlag", rFlag);
		return mav;
	}

	// 一覧表示(検索)
	@RequestMapping(value = "/bookList", method = RequestMethod.POST)
	public ModelAndView searchList(@RequestParam("star") Integer star, @RequestParam("rFlag") boolean rFlag,
			@RequestParam("id") Integer id, ModelAndView mav) {
		// id = original.id
		System.out.println("star:" + star);
		System.out.println("元ネタID:" + id);
		List<Book> bookList = bookInfoService.search(rFlag, star, id);
		List<Original> originalList = originalRepository.findAll();
		mav.addObject(bookList);
		mav.setViewName("bookList");
		mav.addObject(originalList);
		mav.addObject("rFlag", rFlag);
		mav.addObject("id", id);
		mav.addObject("star", star);
		return mav;
	}

	// 詳細表示 BOOKページ表示
	@RequestMapping(value = "/bookDetail")
	public ModelAndView viewDetail(@RequestParam("rFlag") boolean rFlag, @RequestParam("bookId") Integer bookId,
			ModelAndView mav) {
		Book book = bookInfoService.findDetail(bookId);
		if (rFlag) {
			mav.setViewName("rdetail");
		} else {
			mav.setViewName("normaldetail");
		}
		mav.addObject(book);
		mav.addObject("rFlag", rFlag);
		return mav;
	}

	// 本を開く ※できれば非同期で行いたい
	@RequestMapping(value = "/openBook", method = RequestMethod.GET)
	public ModelAndView openFolder(@RequestParam("rFlag") boolean rFlag, @RequestParam("bookId") Integer bookId,
			ModelAndView mav) {
		bookInfoService.openFolder(null); // フォルダオープンテスト
		Book book = bookInfoService.findDetail(bookId);
		if (rFlag) {
			mav.setViewName("rdetail");
		} else {
			mav.setViewName("normaldetail");
		}
		mav.addObject(book);
		mav.addObject("rFlag", rFlag);
		return mav;
	}

	// 評価更新 ※できれば非同期で行いたい
	@RequestMapping(value = "/openBook", method = RequestMethod.POST)
	public ModelAndView updateStar(@RequestParam("rFlag") boolean rFlag, @RequestParam("bookId") Integer bookId,
			@RequestParam("star") Integer star, ModelAndView mav) {
		// ここで更新処理
		Book book = bookInfoService.findDetail(bookId);
		if (rFlag) {
			mav.setViewName("rdetail");
		} else {
			mav.setViewName("normaldetail");
		}
		mav.addObject(book);
		mav.addObject("rFlag", rFlag);
		return mav;
	}
}
