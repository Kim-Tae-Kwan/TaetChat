package com.ktk.taekChat.rest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ktk.taekChat.rest.model.entity.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long>{
	
	List<ChatMessage> findAllByChannelId(Long channelId);
}
