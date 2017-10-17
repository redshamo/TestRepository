package com.myproject.springboot;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	@RequestMapping
	public String index(){
		return "Hello world!!";
	}
}
