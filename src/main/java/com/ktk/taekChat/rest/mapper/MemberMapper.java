package com.ktk.taekChat.rest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ktk.taekChat.rest.model.dto.MemberDto;
import com.ktk.taekChat.rest.model.dto.SignupRequestDto;
import com.ktk.taekChat.rest.model.entity.Member;

@Mapper(componentModel = "spring")
public abstract class MemberMapper {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Mappings({
		@Mapping(target = "id", source = "id"),
		@Mapping(target = "name", source = "name"),
	})
	public abstract MemberDto toDto(Member member);
	
	@Mappings({
		@Mapping(target = "id", ignore = true),
		@Mapping(target = "email", source = "email"),
		@Mapping(target = "name", source = "username"),
		@Mapping(target = "password", source = "password", qualifiedByName = "encryptPassword"),
	})
	public abstract Member toEntity(SignupRequestDto dto);
	
	@Named("encryptPassword")
	String encryptPassword(String password) {
		return passwordEncoder.encode(password);
	}
}
