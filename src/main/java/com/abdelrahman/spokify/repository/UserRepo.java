package com.abdelrahman.spokify.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.abdelrahman.spokify.model.User;
@Repository
public interface UserRepo extends MongoRepository<User, String>{

	Optional<User> findByEmail(String email);
	boolean existsByEmail(String email);
	Optional<User> findByResetToken(String resetToken);
	Optional<User> findById(String id);
}
