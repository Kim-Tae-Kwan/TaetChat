package com.ktk.taekChat.rest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.ktk.taekChat.rest.model.dto.ChatMessageDto;
import com.ktk.taekChat.rest.model.entity.ChatMessage;

@Mapper(componentModel = "spring")
public abstract class ChatMessageMapper {
	@Mappings({
		@Mapping(target = "id", source = "chatMessage.id"),
		@Mapping(target = "content", source = "chatMessage.content"),
		@Mapping(target = "senderName", source = "chatMessage.sender.name"),
		@Mapping(target = "channelId", source = "chatMessage.channel.id"),
	})
	public abstract ChatMessageDto toDto(ChatMessage chatMessage);
}
