package com.abdelrahman.spokify.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.abdelrahman.spokify.model.Transcription;
@Repository
public interface TranscriptionRepo extends MongoRepository<Transcription, String>{

	List<Transcription> findByUserOrderByCreatedAtDesc(String user);
	List<Transcription> findAllByOrderByCreatedAtDesc();
}
