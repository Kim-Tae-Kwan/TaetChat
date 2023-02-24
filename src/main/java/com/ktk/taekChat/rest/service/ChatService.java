package com.ktk.taekChat.rest.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ktk.taekChat.rest.mapper.ChannelMapper;
import com.ktk.taekChat.rest.mapper.ChatMessageMapper;
import com.ktk.taekChat.rest.model.dto.ChannelCreateDto;
import com.ktk.taekChat.rest.model.dto.ChatMessageDto;
import com.ktk.taekChat.rest.model.entity.Channel;
import com.ktk.taekChat.rest.model.entity.ChatMessage;
import com.ktk.taekChat.rest.model.entity.Member;
import com.ktk.taekChat.rest.model.entity.MemberChannel;
import com.ktk.taekChat.rest.repository.ChannelRepository;
import com.ktk.taekChat.rest.repository.ChatMessageRepository;
import com.ktk.taekChat.rest.repository.MemberChannelRepository;
import com.ktk.taekChat.websocket.model.PubChatMessage;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {
	private final ChannelMapper channelMapper;
	private final ChatMessageMapper chatMessageMapper;
	private final ChannelRepository channelRepository;
	private final ChatMessageRepository chatMessageRepository;
	private final MemberChannelRepository memberChannelRepository;
	
	public List<Channel> findAllChannel() {
		return channelRepository.findAll();
	}
	
	public List<ChatMessageDto> findAllChatMessage(Long ChannelId){
		List<ChatMessage> ChatMessages = chatMessageRepository.findAllByChannelId(ChannelId);
		return ChatMessages.stream().map(chatMessageMapper::toDto).collect(Collectors.toList());
	}
	
	@Transactional(rollbackOn = RuntimeException.class)
	public Channel createChannel(ChannelCreateDto dto) {
		
		Channel savedChannel =  channelRepository.save(channelMapper.toEntity(dto));
		Member member = Member.builder()
								.id(dto.getMemberId())
								.build();
		
		MemberChannel memberChannel = MemberChannel.builder()
													.member(member)
													.channel(savedChannel)
													.build();
		
		memberChannelRepository.save(memberChannel);
		
		return savedChannel;
	}
	
	public void saveChatMessage(PubChatMessage messageDto) {
		// DTO -> Entity
		
		// Me
		
		// save
	}
}
