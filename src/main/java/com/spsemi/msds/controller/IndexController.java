package com.spsemi.msds.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spsemi.msds.Crawling;

@Controller
public class IndexController {

	@RequestMapping("/")
	public String index() {
		System.out.println("index");
		Crawling crawling = new Crawling();
		crawling.process();
		return "index";
	}
	//Crawling
	@RequestMapping("/index")
	public String index2() {
		System.out.println("index2");
		return "index";
	}
	
}
