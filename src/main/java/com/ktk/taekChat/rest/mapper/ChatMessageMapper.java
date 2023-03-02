package com.ktk.taekChat.rest.mapper;

import java.time.LocalDateTime;
import java.util.Date;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import com.ktk.taekChat.rest.model.dto.ChannelDto;
import com.ktk.taekChat.rest.model.dto.ChatMessageDto;
import com.ktk.taekChat.rest.model.dto.MemberDto;
import com.ktk.taekChat.rest.model.entity.Channel;
import com.ktk.taekChat.rest.model.entity.ChatMessage;
import com.ktk.taekChat.rest.model.entity.Member;
import com.ktk.taekChat.rest.repository.ChannelRepository;
import com.ktk.taekChat.rest.repository.MemberRepository;
import com.ktk.taekChat.websocket.model.PubChatMessage;

@Mapper(componentModel = "spring")
public abstract class ChatMessageMapper {
	
	@Autowired private MemberMapper memberMapper;
	@Autowired private ChannelMapper channelMapper;
	@Autowired private MemberRepository memberRepository;
	@Autowired private ChannelRepository channelRepository;
	
	@Mappings({
		@Mapping(target = "id", source = "id"),
		@Mapping(target = "content", source = "content"),
		@Mapping(target = "sender", source = "sender", qualifiedByName = "getMemberDto"),
		@Mapping(target = "channel", source = "channel", qualifiedByName = "getChannelDto"),
		@Mapping(target = "createdAt", source = "createdAt"),
	})
	public abstract ChatMessageDto toDto(ChatMessage chatMessage);
	
	@Mappings({
		@Mapping(target = "id", ignore = true),
		@Mapping(target = "content", source = "message.content"),
		@Mapping(target = "sender", source = "memberId", qualifiedByName = "getMember"),
		@Mapping(target = "channel", source = "message", qualifiedByName = "getChannel"),
	})
	public abstract ChatMessage toEntity(PubChatMessage message, Long memberId);
	
	@Named("getMember")
	Member getMember(Long memberId) {
		return memberRepository.findById(memberId).get();
	}
	
	
	@Named("getMemberDto")
	MemberDto getMemberDto(Member member) {
		return memberMapper.toDto(member);
	}
	
	@Named("getChannel")
	Channel getChannel(PubChatMessage message) {
		return channelRepository.findById(message.getChannelId()).get();
	}
	
	@Named("getChannelDto")
	ChannelDto getChannelDto(Channel channel) {
		return channelMapper.toDto(channel);
	}
}
