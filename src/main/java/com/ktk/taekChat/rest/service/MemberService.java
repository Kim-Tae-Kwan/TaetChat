package com.ktk.taekChat.rest.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ktk.taekChat.rest.mapper.MemberMapper;
import com.ktk.taekChat.rest.model.dto.MemberDto;
import com.ktk.taekChat.rest.model.entity.Channel;
import com.ktk.taekChat.rest.model.entity.Member;
import com.ktk.taekChat.rest.model.entity.MemberChannel;
import com.ktk.taekChat.rest.repository.ChannelRepository;
import com.ktk.taekChat.rest.repository.MemberChannelRepository;
import com.ktk.taekChat.rest.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberMapper memberMapper;
	private final MemberRepository memberRepository;
	private final ChannelRepository channelRepository;
	private final MemberChannelRepository memberChannelRepository;
	
	public Member create(Member member) {
		return memberRepository.save(member);
	}
	
	public String getNameById(Long id) throws Exception {
		return memberRepository.findNameById(id).orElseThrow(() -> new Exception("Not Found Memeber name by id : " + id));
	}
	
	public MemberDto getMemberById(Long memberId) throws Exception {
		Member member = memberRepository.findById(memberId).orElseThrow(() -> new Exception("Not Found Memeber by email : " + memberId));
		return memberMapper.toDto(member);
	}
	
	public MemberDto getMemberByEmail(String email) throws Exception {
		Member member = memberRepository.findByEmail(email).orElseThrow(() -> new Exception("Not Found Memeber by email : " + email));
		return memberMapper.toDto(member);
	}

	public List<MemberDto> getMemberByChannel(Long channelId) {
		Channel channel = channelRepository.getReferenceById(channelId);
		List<MemberChannel> memberChannel = memberChannelRepository.findAllByChannel(channel);
		
		return memberChannel.stream()
					.map(mc -> memberMapper.toDto(mc.getMember()))
					.toList();
	}
}