package com.abdelrahman.spokify.security.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.abdelrahman.spokify.dto.ApiResponse;
import com.abdelrahman.spokify.dto.request.UserRegisterRequest;
import com.abdelrahman.spokify.dto.request.UserUpdateRequest;
import com.abdelrahman.spokify.dto.response.UserResponse;
import com.abdelrahman.spokify.exception.AlreadyExistsException;
import com.abdelrahman.spokify.exception.RecordNotFoundException;
import com.abdelrahman.spokify.mapper.UserMapper;
import com.abdelrahman.spokify.model.User;
import com.abdelrahman.spokify.repository.UserRepo;
import com.abdelrahman.spokify.security.dtos.AuthResponse;
import com.abdelrahman.spokify.security.dtos.UserLogin;
import com.abdelrahman.spokify.security.dtos.UserPrinciple;
import com.abdelrahman.spokify.security.jwt.JwtService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final JwtService jwtService;
	private final AuthenticationManager manager;
	private final PasswordEncoder encoder;
	private final UserRepo userRepo;
	private final UserMapper mapper;
	private final JavaMailSender mailSender; 
	
	public UserResponse register(UserRegisterRequest request) {
		if(userRepo.existsByEmail(request.getEmail()))
			throw new AlreadyExistsException("Email already exists");

		User user = mapper.toUser(request);
		user.setPassword(encoder.encode(request.getPassword()));
		userRepo.save(user);
		return mapper.toUserResponse(user);		
	}
	
	public AuthResponse login(UserLogin userLogin) {

		return  verify(userLogin);
	}

	private AuthResponse verify(UserLogin userLogin) {
		Authentication authentication = manager.authenticate(
				new UsernamePasswordAuthenticationToken(
						userLogin.getEmail(),userLogin.getPassword()));

		if(authentication.isAuthenticated()) {
			var user = (UserPrinciple)authentication.getPrincipal();
			String token = jwtService.generateToken(user);
			
			UserResponse response = UserResponse.builder()
					.id(user.getId())
					.userName(user.getUserNameValue())
					.email(user.getUsername())
					.profileImage(user.getProfileImage())
					.build();
			AuthResponse auth = new AuthResponse(true, "Login successfully", token, response);

			return auth;
		}

		throw new BadCredentialsException("Invalid email or password");	
	} 
	
	public AuthResponse updateUser(String id, UserUpdateRequest request) {
		
	    User user = userRepo.findById(id)
	            .orElseThrow(() -> new RecordNotFoundException("User not found"));

	    if (request.getEmail() != null) user.setEmail(request.getEmail());
	    if (request.getUserName() != null) user.setUserName(request.getUserName());

	    if (request.getPassword() != null && !request.getPassword().isBlank()) {
	        user.setPassword(encoder.encode(request.getPassword()));
	    }

	    if (request.getProfileImage() != null) {
	        user.setProfileImage(request.getProfileImage());
	    }

	    User updatedUser = userRepo.save(user);

	    UserResponse userDto = mapper.toUserResponse(updatedUser);
	    return new AuthResponse(true, "User updated successfully", null, userDto);
	}
	
	public ApiResponse forgotPassword(String email) {
		
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RecordNotFoundException("User not found."));

        String resetToken = UUID.randomUUID().toString();
        
        LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(30);

        user.setResetToken(resetToken);
        user.setResetTokenExpires(expiryDate);
        userRepo.save(user);

        // 5. إرسال الإيميل
        sendResetEmail(user.getEmail(), resetToken);

        return new ApiResponse(true, "Reset link sent successfully.");
    }

	public ApiResponse confirmResetPassword(String resetToken, String newPassword) {

		User user = userRepo.findByResetToken(resetToken)
	            .orElseThrow(() -> new RuntimeException("Invalid or expired token."));

	    if (user.getResetTokenExpires().isBefore(LocalDateTime.now())) {
	        throw new RuntimeException("Token expired.");
	    }

	    user.setPassword(encoder.encode(newPassword));

	    user.setResetToken(null);// هنا انا بمسح ال resetToken عشان ميبقاش صالح تاني
	    user.setResetTokenExpires(null); // نفس الكلام بمسح ال expiration بتاعه 
	    
	    userRepo.save(user);

	    return new ApiResponse(true, "Password has been reset successfully.");
	}
	
	private void sendResetEmail(String email, String token) {
	    try {
	        MimeMessage mimeMessage = mailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

	        String resetLink = "http://localhost:5173/reset-password?token=" + token;
	        String htmlContent = """
	            <p>You've requested to reset your password.</p>
	            <a href="%s">Click here to reset</a>
	            <p>This link will expire in 30 minutes.</p>
	        """.formatted(resetLink);

	        helper.setText(htmlContent, true);
	        helper.setTo(email);
	        helper.setSubject("Reset your password");
	        helper.setFrom("SPOKIFY Support <spokify.app@gmail.com>");

	        mailSender.send(mimeMessage);
	    } catch (MessagingException e) {
	        throw new RuntimeException("Failed to send email", e);
	    }
	}
	
	public AuthResponse getUserById(String id) {
		User user = userRepo.findById(id).orElseThrow(()->new RecordNotFoundException("User not found"));
		UserResponse response = mapper.toUserResponse(user);
		return new AuthResponse(true,"User fetched successfully",response);
	}
}
