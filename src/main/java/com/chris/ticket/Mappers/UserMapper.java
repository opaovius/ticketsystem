package com.chris.ticket.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.chris.ticket.Dtos.UserDtos.RegisterUserRequest;
import com.chris.ticket.Dtos.UserDtos.UpdateUserRequest;
import com.chris.ticket.Dtos.UserDtos.UserDto;
import com.chris.ticket.Entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

	UserDto toDto(User user);
	
	User toEntity(RegisterUserRequest request);
	
	void update(UpdateUserRequest request, @MappingTarget User user);
	
}
