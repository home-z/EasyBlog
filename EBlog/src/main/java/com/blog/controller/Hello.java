package com.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class Hello {
	
	// 不标明class的RequestMapping，则所有的save都会请求到这里
	@RequestMapping("/hello")
	public String hello() {
		System.out.println("call hello method.");
		return "test/listBoard";// 返回listBoard视图
	}

	@RequestMapping("/test/bye")
	public String bye() {
		System.out.println("bye");
		return "test/listBoard";// 返回listBoard视图
	}
}