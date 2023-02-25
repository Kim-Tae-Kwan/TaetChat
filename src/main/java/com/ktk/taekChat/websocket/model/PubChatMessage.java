package com.ktk.taekChat.websocket.model;

import lombok.Data;

@Data
public class PubChatMessage {
	private Long senderId;
	private Long channelId;
	private String content;
	
	public SubChatMessage toSubChatMessage() {
		return SubChatMessage.builder()
							.senderId(senderId)
							.channelId(channelId)
							.content(content)
							.build();
	}
}
