package com.ktk.taekChat.rest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ktk.taekChat.rest.model.entity.Channel;
import com.ktk.taekChat.rest.model.entity.MemberChannel;

public interface MemberChannelRepository extends JpaRepository<MemberChannel, Long>{
	
	List<MemberChannel> findAllByChannel(Channel channel);
}
