package com.ktk.taekChat.rest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.ktk.taekChat.rest.model.dto.ChannelCreateDto;
import com.ktk.taekChat.rest.model.dto.ChannelDto;
import com.ktk.taekChat.rest.model.entity.Channel;

@Mapper(componentModel = "spring")
public abstract class ChannelMapper {
	
	@Mappings({
		@Mapping(target = "id", ignore = true),
		@Mapping(target = "name", source = "channelName"),
	})
	public abstract Channel toEntity(ChannelCreateDto dto);
	
	@Mappings({
		@Mapping(target = "id", source = "id"),
		@Mapping(target = "name", source = "name"),
	})
	public abstract ChannelDto toDto(Channel channel);
	
//	String getDuration(Comment comment) {
//    	PrettyTime prettyTime = new PrettyTime();
//    	return prettyTime.format(Date.from(comment.getCreatedDate()));
//    }
//	
//	List<CommentDto> getSubCommentDto(List<Comment> comments){
//		return comments.stream().map(this::toDto).collect(Collectors.toList());
//	}
//	
//	Comment getParentComment(Long parentId) {
//		if(parentId == null) return null;
//		return commentRepository.findById(parentId)
//								.orElseThrow(() -> new RedditException("Not found parent comment by id - " + parentId));
//	}
}
