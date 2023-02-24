package com.ktk.taekChat.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ktk.taekChat.rest.model.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{

}
