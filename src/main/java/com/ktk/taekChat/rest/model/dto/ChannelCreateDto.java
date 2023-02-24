package com.ktk.taekChat.rest.model.dto;

import lombok.Data;

@Data
public class ChannelCreateDto {
	private String channelName;
	private Long memberId;
}
