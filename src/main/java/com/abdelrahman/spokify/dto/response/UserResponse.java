package com.abdelrahman.spokify.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
	private String id;
    private String userName;
    private String email;
    private String profileImage;
}
