package com.cos.blog.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice //모든 exception이 발생했을때 이 클레스로 넘어옴
@RestController
public class GlobalExceptionHandler {

	@ExceptionHandler(value = Exception.class)
	public String handleArgumentException(Exception e) {
		return "<h1>" + e.getMessage() + "</h1>";
	}
	
}
