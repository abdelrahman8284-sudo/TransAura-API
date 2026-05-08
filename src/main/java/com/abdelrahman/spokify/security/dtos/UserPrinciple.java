package com.abdelrahman.spokify.security.dtos;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.abdelrahman.spokify.model.User;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
@Setter
@RequiredArgsConstructor
public class UserPrinciple implements UserDetails {

	private final User user;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.emptyList();
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		
		return user.getEmail();
	}
	
	public String getId() {
		return user.getId();
	}
	
	public String getUserNameValue() {
		return user.getUserName();
	}
	
	public LocalDateTime getCreatedAt() {
		return user.getCreatedAt();
	}
	
	public String getProfileImage() {
		return user.getProfileImage();
	}

}
