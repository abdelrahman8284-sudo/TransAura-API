package com.abdelrahman.spokify.dto.request;

public record TranscriptionRequest(
	    String transcription,
	    String enhanced,
	    String summary,
	    String topics,
	    String tasks,
	    String User 
	) {}
