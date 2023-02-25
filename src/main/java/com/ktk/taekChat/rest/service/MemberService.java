package com.ktk.taekChat.rest.service;

import org.springframework.stereotype.Service;

import com.ktk.taekChat.rest.model.entity.Member;
import com.ktk.taekChat.rest.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;
	
	public Member create(Member member) {
		return memberRepository.save(member);
	}
	
	public String getNameById(Long id) throws Exception {
		return memberRepository.findNameById(id).orElseThrow(() -> new Exception("Not Found Memeber name by id : " + id));
	}
}