package com.ktk.taekChat.websocket.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubChatMessage {
	private Long senderId;
	private Long channelId;
	private String content;
	private String senderName;
}
