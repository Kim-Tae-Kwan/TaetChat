package com.ktk.taekChat.rest.model.dto;

import lombok.Data;

@Data
public class SignupRequestDto {
	private String email;
	private String username;
	private String password;
}
