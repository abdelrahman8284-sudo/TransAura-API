package com.abdelrahman.spokify.configuration;

import java.util.Set;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@ConfigurationProperties("app.audio-storage")
@Getter
public class AudioProperties {

	private String basePath;
	
	private Set<String> allowedMimeTypes;

	public AudioProperties() {
		this.basePath="./public";
		this.allowedMimeTypes = Set.of(
				"audio/wav",        
		        "audio/mpeg",       
		        "audio/mp3",        
		        "audio/x-wav",      
		        "audio/webm",       
		        "audio/ogg",        
		        "audio/m4a"
				);
	}
}
