package com.ktk.taekChat.rest.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ktk.taekChat.rest.mapper.MemberMapper;
import com.ktk.taekChat.rest.model.dto.SignupRequestDto;
import com.ktk.taekChat.rest.model.entity.Member;
import com.ktk.taekChat.rest.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	
	private final MemberMapper memberMapper;
	private final MemberRepository memberRepository;
	
	public void signup(SignupRequestDto signupRequestDto) throws Exception {
		boolean existsEmail = memberRepository.existsByEmail(signupRequestDto.getEmail());
		
		if(existsEmail) throw new Exception("Duplicate email");
		else {
			Member newMember = memberMapper.toEntity(signupRequestDto);
			System.out.println("newMember : " + newMember.getEmail());
			System.out.println("newMember : " + newMember.getName());
			System.out.println("newMember : " + newMember.getPassword());
			memberRepository.save(newMember);
		}
	}
	
	public Member getCurrentMember() throws Exception {
    	try {
    		User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    		return memberRepository.findByEmail(principal.getUsername())
    				.orElseThrow(() -> new UsernameNotFoundException("user name not found - " + principal.getUsername()));
    	}catch(ClassCastException ex){
    		return null;
    	}catch(Exception ex){
    		ex.printStackTrace();
    		throw new Exception("getCurrentMember Error...");
    	}
    }

}
