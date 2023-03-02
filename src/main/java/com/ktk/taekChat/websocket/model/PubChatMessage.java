package com.ktk.taekChat.websocket.model;

import lombok.Data;

@Data
public class PubChatMessage {
	private Long channelId;
	private String content;
}
