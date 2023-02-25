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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage extends BaseTimeEntity {
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
}
