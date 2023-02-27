package com.ktk.taekChat.websocket.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.ktk.taekChat.rest.model.dto.ChatMessageDto;
import com.ktk.taekChat.rest.service.ChatService;
import com.ktk.taekChat.rest.service.MemberService;
import com.ktk.taekChat.websocket.model.PubChatMessage;
import com.ktk.taekChat.websocket.model.SubChatMessage;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {
	
	private final ChatService chatService;
	private final MemberService memberService;
	
	@MessageMapping("/chat/{channelId}")
	@SendTo("/sub/chat/{channelId}")
	public PubChatMessage sendMessage(@Payload PubChatMessage pubChatMessage) throws Exception {
		chatService.saveChatMessage(pubChatMessage);
		return pubChatMessage;
	}
}
