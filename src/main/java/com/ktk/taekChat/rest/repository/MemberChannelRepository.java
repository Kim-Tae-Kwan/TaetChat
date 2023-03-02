package com.ktk.taekChat.rest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ktk.taekChat.rest.model.entity.Channel;
import com.ktk.taekChat.rest.model.entity.Member;
import com.ktk.taekChat.rest.model.entity.MemberChannel;

public interface MemberChannelRepository extends JpaRepository<MemberChannel, Long>{
	
	List<MemberChannel> findAllByChannel(Channel channel);
	Optional<MemberChannel> findByChannelAndMember(Channel channel, Member member);
	void deleteByChannelAndMember(Channel channel, Member member);
}
