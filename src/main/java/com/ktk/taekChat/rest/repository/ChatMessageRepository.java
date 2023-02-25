package com.ktk.taekChat.rest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ktk.taekChat.rest.model.entity.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long>{
	
	@Query("SELECT c FROM ChatMessage c "
			+ "WHERE c.channel.id = :channelId "
			+ "ORDER BY c.createdAt DESC")
	List<ChatMessage> findAllByChannelId(Long channelId);
}
