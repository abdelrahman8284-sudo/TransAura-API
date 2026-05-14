package com.abdelrahman.spokify.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abdelrahman.spokify.dto.ApiResponse;
import com.abdelrahman.spokify.dto.request.UserRegisterRequest;
import com.abdelrahman.spokify.dto.request.UserUpdateRequest;
import com.abdelrahman.spokify.dto.response.UserResponse;
import com.abdelrahman.spokify.exception.AlreadyExistsException;
import com.abdelrahman.spokify.security.dtos.AuthResponse;
import com.abdelrahman.spokify.security.dtos.UserLogin;
import com.abdelrahman.spokify.security.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"}, allowCredentials = "true")
public class AuthController {

	private final AuthService authService;
	
	@PostMapping("/register")
	public ResponseEntity<AuthResponse> register(@RequestBody@Valid UserRegisterRequest request){
			UserResponse userResponse = authService.register(request);
			return ResponseEntity.status(HttpStatus.CREATED)
		            .body(new AuthResponse(true, "User created successfully", userResponse));	
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody UserLogin login){
			AuthResponse response = authService.login(login);
			return ResponseEntity.status(HttpStatus.OK)
		            .body(response);	
	}
	
	@PutMapping("/updateProfile/{id}")
	public ResponseEntity<AuthResponse> updateUser(
	        @PathVariable String id, 
	        @RequestBody UserUpdateRequest request) {
	    
	    AuthResponse response = authService.updateUser(id, request);
	    return ResponseEntity.ok(response);
	}
	
	@PostMapping("/forgot-password")
	public ResponseEntity<ApiResponse> forgotPassword(@RequestBody Map<String, String> request) {
	    String email = request.get("email");
	    return ResponseEntity.ok(authService.forgotPassword(email));
	}

	@PostMapping("/reset-password")
	public ResponseEntity<ApiResponse> confirmReset(@RequestBody Map<String, String> request) {
	    String token = request.get("token");
	    String newPassword = request.get("newPassword");
	    return ResponseEntity.ok(authService.confirmResetPassword(token, newPassword));
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity<AuthResponse> getUserById(@PathVariable String id){
		return ResponseEntity.ok(authService.getUserById(id));
	}
}
