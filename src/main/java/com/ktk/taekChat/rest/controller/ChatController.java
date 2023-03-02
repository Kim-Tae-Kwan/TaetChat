package com.ktk.taekChat.rest.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktk.taekChat.rest.model.dto.ChannelCreateDto;
import com.ktk.taekChat.rest.model.dto.ChatMessageDto;
import com.ktk.taekChat.rest.model.entity.Channel;
import com.ktk.taekChat.rest.service.ChatService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
public class ChatController {
	private final ChatService chatService;
	
	@GetMapping("/messages/{channelId}")
	public ResponseEntity<List<ChatMessageDto>> getAllChatMessage(@PathVariable Long channelId){
		List<ChatMessageDto> messages = chatService.findAllChatMessage(channelId);
		return ResponseEntity.ok(messages);
	}
	
	@GetMapping("/channels")
	public ResponseEntity<List<Channel>> getAllChannel(){
		return ResponseEntity.ok(chatService.findAllChannel());
	}
	
	@PostMapping("/channels")
	public ResponseEntity<Channel> createChannel(@RequestBody ChannelCreateDto channelCreateDto){
		Channel savedChannel = chatService.createChannel(channelCreateDto);
		return ResponseEntity.ok(savedChannel);
	}
	
	@DeleteMapping("/channel/{channelId}/member/{memberId}")
	public ResponseEntity<Channel> exitChannel(@PathVariable Long channelId, @PathVariable Long memberId) throws Exception{
		chatService.exitChannel(channelId, memberId);
		return ResponseEntity.ok().build();
	}
}
