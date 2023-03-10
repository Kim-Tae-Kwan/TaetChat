package com.ktk.taekChat.rest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ktk.taekChat.rest.mapper.ChannelMapper;
import com.ktk.taekChat.rest.mapper.ChatMessageMapper;
import com.ktk.taekChat.rest.model.dto.ChannelCreateDto;
import com.ktk.taekChat.rest.model.dto.ChannelDto;
import com.ktk.taekChat.rest.model.dto.ChatMessageDto;
import com.ktk.taekChat.rest.model.entity.Channel;
import com.ktk.taekChat.rest.model.entity.ChatMessage;
import com.ktk.taekChat.rest.model.entity.Member;
import com.ktk.taekChat.rest.model.entity.MemberChannel;
import com.ktk.taekChat.rest.repository.ChannelRepository;
import com.ktk.taekChat.rest.repository.ChatMessageRepository;
import com.ktk.taekChat.rest.repository.MemberChannelRepository;
import com.ktk.taekChat.rest.repository.MemberRepository;
import com.ktk.taekChat.websocket.model.PubChatMessage;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {
	private final ChannelMapper channelMapper;
	private final ChatMessageMapper chatMessageMapper;
	
	private final MemberRepository memberRepository;
	private final ChannelRepository channelRepository;
	private final ChatMessageRepository chatMessageRepository;
	private final MemberChannelRepository memberChannelRepository;
	
	public List<ChannelDto> findAllChannel(Long memberId) {
		Member member = memberRepository.getReferenceById(memberId);
		List<MemberChannel> memberChannels = memberChannelRepository.findAllByMember(member);
		
		List<ChannelDto> channels = new ArrayList<>();
		memberChannels.stream().forEach((memberChannel) -> {
			Channel channel = memberChannel.getChannel();
			channels.add(channelMapper.toDto(channel));
		});
		
		return channels;
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
	
	public ChatMessageDto saveChatMessage(PubChatMessage messageDto, Long memberId) {
		// DTO -> Entity
		ChatMessage chatMessage = chatMessageMapper.toEntity(messageDto, memberId);

		// save
		ChatMessage savedChatMessage = chatMessageRepository.save(chatMessage);
		
		return chatMessageMapper.toDto(savedChatMessage); 
	}
	
	@Transactional(rollbackOn = RuntimeException.class)
	public void exitChannel(Long channelId, Long memberId) throws Exception {
		Channel channel = channelRepository.getReferenceById(channelId);
		Member member = memberRepository.getReferenceById(memberId);
		memberChannelRepository.deleteByChannelAndMember(channel, member);
	}
}
