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
import com.myproject.springboot.repository.BookInfoRepository;
import com.myproject.springboot.repository.OriginalRepository;

@RestController
public class MainController {

	@Autowired
	BookInfoRepository bookInfoRepository;

	@Autowired
	OriginalRepository originalRepository;

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
		List<Original> originalList = originalRepository.findAll();
		mav.setViewName("bookList");
		mav.addObject(bookList);
		mav.addObject(originalList);
		mav.addObject("rFlag", rFlag);
		return mav;
	}

	@RequestMapping(value = "/bookList", method = RequestMethod.POST)
	public ModelAndView searchList(@RequestParam("star") Integer star, @RequestParam("rFlag") boolean rFlag,
			@RequestParam("id") Integer id, ModelAndView mav) {
		// 仮置き
		// id...original.id
		List<Book> bookList = bookInfoRepository.findAll(rFlag);
		List<Original> originalList = originalRepository.findAll();
		mav.addObject(bookList);
		mav.setViewName("bookList");
		mav.addObject(originalList);
		mav.addObject("rFlag", rFlag);
		mav.addObject("id", id);
		mav.addObject("star", star);
		
		return mav;
	}
}
