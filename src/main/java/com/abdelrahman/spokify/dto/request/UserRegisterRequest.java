package com.abdelrahman.spokify.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRegisterRequest {
	@Email(message = "Email invalid")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Username is required")
    private String userName;

    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    private String imageProfile;
}
