package com.ktk.taekChat.rest.model.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.springframework.data.annotation.CreatedDate;

import lombok.Getter;

@Entity
@Getter
public class ChatMessage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String content;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "senderId", referencedColumnName = "id")
	private Member sender;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "channelId", referencedColumnName = "id")
	private Channel channel;
	
	@CreatedDate
	private LocalDateTime createdAt;
}
