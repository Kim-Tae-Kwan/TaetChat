package com.ktk.taekChat.rest.mapper;

import java.time.LocalDateTime;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import com.ktk.taekChat.rest.model.dto.ChatMessageDto;
import com.ktk.taekChat.rest.model.entity.Channel;
import com.ktk.taekChat.rest.model.entity.ChatMessage;
import com.ktk.taekChat.rest.model.entity.Member;
import com.ktk.taekChat.rest.repository.ChannelRepository;
import com.ktk.taekChat.rest.repository.MemberRepository;
import com.ktk.taekChat.websocket.model.PubChatMessage;

@Mapper(componentModel = "spring")
public abstract class ChatMessageMapper {
	
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private ChannelRepository channelRepository;
	
	@Mappings({
		@Mapping(target = "id", source = "chatMessage.id"),
		@Mapping(target = "content", source = "chatMessage.content"),
		@Mapping(target = "senderId", source = "chatMessage.sender.id"),
		@Mapping(target = "channelId", source = "chatMessage.channel.id"),
	})
	public abstract ChatMessageDto toDto(ChatMessage chatMessage);
	
	@Mappings({
		@Mapping(target = "id", ignore = true),
		@Mapping(target = "content", source = "message.content"),
		@Mapping(target = "sender", expression = "java(getMember(message))"),
		@Mapping(target = "channel", expression = "java(getChannel(message))"),
	})
	public abstract ChatMessage toEntity(PubChatMessage message);
	
	Member getMember(PubChatMessage message) {
		return memberRepository.getReferenceById(message.getSenderId());
	}
	
	Channel getChannel(PubChatMessage message) {
		return channelRepository.getReferenceById(message.getChannelId());
	}
}
