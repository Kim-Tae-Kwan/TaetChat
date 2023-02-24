package com.ktk.taekChat.websocket.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.ktk.taekChat.websocket.model.PubChatMessage;

@Controller
public class ChatWebSocketController {
	
	@MessageMapping("/chat/{channelId}")
	@SendTo("/sub/chat/{channelId}")
	public PubChatMessage sendMessage(@Payload PubChatMessage message) {
		return message;
	}
}
