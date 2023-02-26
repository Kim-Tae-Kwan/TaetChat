package com.ktk.taekChat.rest.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TaetChatExceptionHandler {
//	@ExceptionHandler(value = RedditException.class)
//	public ResponseEntity<?> notFoundException(RedditException ex){
//		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionResponse(ex.getMessage(), 500));
//	}
}
