package com.accenture.test.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController 
@RequestMapping(value="/")
public class HelloController {
	
	@ApiOperation(value="Hello", notes="Test if bearer is working")
	@RequestMapping("/hello")
	public String hello(){
		return "hello";
	}
}
