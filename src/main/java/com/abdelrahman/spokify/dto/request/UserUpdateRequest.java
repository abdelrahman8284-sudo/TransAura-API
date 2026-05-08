package com.abdelrahman.spokify.dto.request;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder@AllArgsConstructor@NoArgsConstructor@Setter@Getter
public class UserUpdateRequest {

	@Email(message = "Email invalid")
	private String email;
	private String userName;
	private String password;
	private String profileImage;

}
