package com.ktk.taekChat.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktk.taekChat.rest.model.entity.Member;
import com.ktk.taekChat.rest.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberContoller {
	private final MemberService memberService;
	
	@PostMapping
	public ResponseEntity<Member> create(@RequestBody Member member){
		Member savedMember = memberService.create(member);
		return ResponseEntity.ok(savedMember);
	}
}
