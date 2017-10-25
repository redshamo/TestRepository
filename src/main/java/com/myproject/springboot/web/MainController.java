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
		return mav;
	}

	// テスト用 TOPに戻る
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ModelAndView send(@RequestParam("name") String name, ModelAndView mav) {
		mav.setViewName("index");
		// 仮置きテスト 後で消す
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
		mav.setViewName("bookdetail");
		mav.addObject(book);
		mav.addObject("rFlag", rFlag);
		return mav;
	}

	// 登録ページ遷移
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public ModelAndView viewInsert(@RequestParam("pass") String pass, ModelAndView mav) {
		if (pass.equals("master")) {
			List<Original> originalList = originalRepository.findAll();
			mav.addObject(originalList);
			mav.setViewName("insert");
		} else {
			mav.setViewName("index");
		}
		return mav;
	}

	// 登録実施
	@RequestMapping(value = "/insert", method = RequestMethod.GET)
	public ModelAndView insert(@RequestParam("title") String title, @RequestParam("rFlag") Integer rFlag,
			@RequestParam("originalId") Integer originalId, ModelAndView mav) {
		/** ここで新規登録 */
		List<Original> originalList = originalRepository.findAll();
		mav.addObject(originalList);
		mav.addObject("originalId",originalId);
		mav.addObject("title", title);
		mav.setViewName("insert");
		mav.addObject("msg", "登録完了しました！");
		return mav;
	}

	// 本を開く(非同期
	@RequestMapping(value = "/openBook", method = RequestMethod.GET)
	public String openFolder(String bookId) {
		String path = bookInfoService.createPath(Integer.parseInt(bookId));
		bookInfoService.openFolder(path);
		return "表示に成功しました！読み終わったら評価の更新もお願いします！";
	}

	// 評価更新(非同期
	@RequestMapping(value = "/openBook", method = RequestMethod.POST)
	public String updateStar(String[] values) {
		// values:{bookID,star}
		bookInfoService.updateStar(values[0], values[1]);
		return "更新成功しました！";
	}
}
