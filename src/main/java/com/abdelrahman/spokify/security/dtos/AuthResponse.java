package com.abdelrahman.spokify.security.dtos;


import com.abdelrahman.spokify.dto.response.UserResponse;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL) // عشان تمنع ال null من الظهور
@Setter@Getter
public class AuthResponse {
	private boolean success;
	private String message;
	private String token;
	private UserResponse user;
	
	public AuthResponse(boolean success, String message, UserResponse user) {
		super();
		this.success = success;
		this.message = message;
		this.user = user;
	}

	public AuthResponse(boolean success, String message, String token, UserResponse user) {
		super();
		this.success = success;
		this.message = message;
		this.token = token;
		this.user = user;
	}

	public AuthResponse() {
		super();
	}
	
	
	
}
