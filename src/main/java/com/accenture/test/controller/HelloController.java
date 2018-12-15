package com.accenture.test.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController 
@RequestMapping(value="/")
public class HelloController {
	
	@RequestMapping("/hello")
	public String hello(){
		return "hello";
	}
}
