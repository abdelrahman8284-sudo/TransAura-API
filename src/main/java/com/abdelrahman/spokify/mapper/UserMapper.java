package com.abdelrahman.spokify.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.abdelrahman.spokify.dto.request.UserRegisterRequest;
import com.abdelrahman.spokify.dto.response.UserResponse;
import com.abdelrahman.spokify.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

	UserResponse toUserResponse(User user);
	@Mapping(source = "imageProfile",target ="profileImage")
	User toUser(UserRegisterRequest request);
	
	List<UserResponse> toListResponse(List<User> users);
}
