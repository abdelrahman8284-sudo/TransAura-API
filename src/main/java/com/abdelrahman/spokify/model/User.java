package com.abdelrahman.spokify.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "users")
@Setter@Getter@AllArgsConstructor@NoArgsConstructor
public class User {

	@Id
	private String id;

	@Email(message = "Email invalid")
	@NotBlank
	@Indexed(unique = true)
	private String email;
	private String userName;
	private String password;
	private String resetToken;
	private LocalDateTime resetTokenExpires;
	
	private String profileImage;
	
	@CreatedDate
	private LocalDateTime createdAt;

	@LastModifiedDate
	private LocalDateTime updatedAt;
}
