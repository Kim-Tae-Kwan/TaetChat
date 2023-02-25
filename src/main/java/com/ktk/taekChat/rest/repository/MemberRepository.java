package com.ktk.taekChat.rest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ktk.taekChat.rest.model.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{
	
	Optional<Member> findByEmail(String email);
	
	@Query("SELECT m.name FROM Member m WHERE m.id = :id")
	Optional<String> findNameById(Long id);
}
