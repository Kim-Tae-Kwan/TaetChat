package com.ktk.taekChat.rest.model.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ktk.taekChat.rest.model.entity.Channel;
import com.ktk.taekChat.rest.model.entity.Member;

import lombok.Data;

@Data
public class ChatMessageDto {
	private Long id;
	private String content;
	private MemberDto sender;
	private ChannelDto channel;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime createdAt;
}
