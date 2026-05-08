package com.abdelrahman.spokify.service;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.abdelrahman.spokify.configuration.AudioProperties;
import com.abdelrahman.spokify.model.Audio;
import com.abdelrahman.spokify.model.Metadata;
import com.abdelrahman.spokify.model.Transcription;
import com.abdelrahman.spokify.repository.TranscriptionRepo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AudioService {

	private final TranscriptionRepo transcriptionRepo;
	private final AudioStorageService storageService;
	private final AudioProperties properties;

	@Transactional
	public Audio upload(MultipartFile file) throws IOException {
		validateAudio(file);
		String path;
		try (InputStream input = file.getInputStream()) {
			path = storageService.storeFile(input, file.getOriginalFilename());
		}

		return new Audio(file.getOriginalFilename(), file.getContentType(), file.getSize(), path);
	}

	private void validateAudio(MultipartFile file) {
		if (file.isEmpty()) {
			throw new IllegalArgumentException("File is empty");
		}

		String mimeType = file.getContentType();
		if (mimeType == null || !properties.getAllowedMimeTypes().contains(mimeType)) {
			throw new IllegalArgumentException("Invalid mime type");
		}
	}

	public Resource getAudioResource(String transcriptionId) throws IOException {
		Transcription transcription = transcriptionRepo.findById(transcriptionId)
				.orElseThrow(() -> new RuntimeException("Transcription not found"));

		return storageService.getFileResource(transcription.getAudio().getUrl());
	}

	public Metadata getAudioMetaData(String transcriptionId) throws IOException {
		Transcription transcription = transcriptionRepo.findById(transcriptionId)
				.orElseThrow(() -> new RuntimeException("Audio not found"));
		return transcription.getMetadata();
	}

}
