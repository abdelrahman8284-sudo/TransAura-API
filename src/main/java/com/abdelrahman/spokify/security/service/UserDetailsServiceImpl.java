package com.abdelrahman.spokify.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.abdelrahman.spokify.model.User;
import com.abdelrahman.spokify.repository.UserRepo;
import com.abdelrahman.spokify.security.dtos.UserPrinciple;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepo userRepo;
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepo.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("Email not found"));
		return new UserPrinciple(user);
	}

}
