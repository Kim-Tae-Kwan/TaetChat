package com.ktk.taekChat.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ktk.taekChat.rest.model.entity.Channel;

public interface ChannelRepository extends JpaRepository<Channel, Long>{

}
