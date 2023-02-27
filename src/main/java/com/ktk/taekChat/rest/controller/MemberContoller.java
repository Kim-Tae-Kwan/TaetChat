package com.ktk.taekChat.rest.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktk.taekChat.rest.model.dto.MemberDto;
import com.ktk.taekChat.rest.model.entity.Member;
import com.ktk.taekChat.rest.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberContoller {
	private final MemberService memberService;
	
	@GetMapping("/{channelId}")
	public ResponseEntity<List<MemberDto>> getMemberByChannel(@PathVariable Long channelId){
		List<MemberDto> memberDtos = memberService.getMemberByChannel(channelId);
		return ResponseEntity.ok(memberDtos);
	}
	
	@PostMapping
	public ResponseEntity<Member> create(@RequestBody Member member){
		Member savedMember = memberService.create(member);
		return ResponseEntity.ok(savedMember);
	}
}
