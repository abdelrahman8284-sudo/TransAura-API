package com.abdelrahman.spokify.security.dtos;

import lombok.Data;

@Data
public class UserLogin {

	private String email;
	
	private String password;
}
