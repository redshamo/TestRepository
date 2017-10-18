package com.myproject.springboot.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.myproject.springboot.domain.Book;
import com.myproject.springboot.repository.BookInfoRepository;

@RestController
public class MainController {

	@Autowired
	BookInfoRepository bookInfoRepository;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView index(ModelAndView mav) {
		mav.setViewName("index");
		mav.addObject("msg", "input your name :"); // 表示メッセージ
		return mav;
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ModelAndView send(@RequestParam("name") String name, ModelAndView mav) {
		mav.setViewName("index");
		mav.addObject("msg", "Hello " + name + " !"); // 表示メッセージ
		mav.addObject("value", name); // 入力テキストに入力値を表示
		return mav;
	}

	@RequestMapping(value = "/bookList", method = RequestMethod.GET)
	public ModelAndView viewList(@RequestParam("rFlag") boolean rFlag, ModelAndView mav) {
		List<Book> bookList = bookInfoRepository.findAll(rFlag);
		mav.setViewName("bookList");
		mav.addObject(bookList);
		mav.addObject("rFlag", rFlag);
		mav.addObject("value", "タイトルまたは元の作品名を入力してください");
		return mav;
	}

	@RequestMapping(value = "/bookList", method = RequestMethod.POST)
	public ModelAndView searchList(@RequestParam("star") Integer star, @RequestParam("rFlag") boolean rFlag,
			@RequestParam("word") String word, ModelAndView mav) {
		// 仮置き
		List<Book> bookList = bookInfoRepository.findAll(rFlag);
		mav.addObject(bookList);
		mav.setViewName("bookList");
		mav.addObject("rFlag", rFlag);
		mav.addObject("value", "タイトルまたは元の作品名を入力してください");
		mav.addObject("word", word);
		return mav;
	}
}
