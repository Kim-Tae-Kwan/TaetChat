package com.ktk.taekChat.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktk.taekChat.rest.model.dto.SignupRequestDto;
import com.ktk.taekChat.rest.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;
	
//	@PostMapping(value = "/signup")
//	public ResponseEntity<?> signup(SignupRequestDto signupRequest) throws Exception{
//		authService.signup(signupRequest);
//		return ResponseEntity.status(HttpStatus.CREATED).build();
//	}
}
