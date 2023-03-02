package com.ktk.taekChat.websocket.controller;

import java.security.Principal;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;

import com.ktk.taekChat.rest.model.dto.ChatMessageDto;
import com.ktk.taekChat.rest.model.entity.ChatMessage;
import com.ktk.taekChat.rest.service.ChatService;
import com.ktk.taekChat.rest.service.MemberService;
import com.ktk.taekChat.websocket.model.PubChatMessage;
import com.ktk.taekChat.websocket.model.SubChatMessage;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {
	
	private final ChatService chatService;
	
	@MessageMapping("/chat/{channelId}")
	@SendTo("/sub/chat/{channelId}")
	public ChatMessageDto sendMessage(@Payload PubChatMessage pubChatMessage, Principal principal) throws Exception {
		return chatService.saveChatMessage(pubChatMessage, Long.valueOf(principal.getName()));
	}
}
