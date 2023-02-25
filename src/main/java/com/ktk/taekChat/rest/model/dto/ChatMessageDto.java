package com.ktk.taekChat.rest.model.dto;

import com.ktk.taekChat.rest.model.entity.Channel;
import com.ktk.taekChat.rest.model.entity.Member;

import lombok.Data;

@Data
public class ChatMessageDto {
	private Long id;
	private String content;
	private Long senderId;
	private Long channelId;
}
