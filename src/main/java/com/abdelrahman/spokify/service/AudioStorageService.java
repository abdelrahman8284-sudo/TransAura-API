package com.abdelrahman.spokify.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import com.abdelrahman.spokify.configuration.AudioProperties;

import lombok.RequiredArgsConstructor;

@Service
public class AudioStorageService {
	private final AudioProperties properties;
	private final Path rootPath;
	

	public AudioStorageService(AudioProperties properties) {
		super();
		this.properties = properties;
		this.rootPath = Paths.get(properties.getBasePath());
	}
	
	public String storeFile(InputStream inputStream, String originalName) throws IOException {
		// first how i want to store by date ? 

		LocalDate today = LocalDate.now();
		Path dateDirectory = this.rootPath.resolve(
				today.getYear() +File.separator +
				String.format("%02d", today.getMonthValue()) +File.separator +
				String.format("%02d", today.getDayOfMonth()) +File.separator
				);
		
		Files.createDirectories(dateDirectory);
		String ext = getFileExtension(originalName);
		String fileName = UUID.randomUUID()+(ext.isEmpty() ? "" :"."+ext);
		
		Path filePath = dateDirectory.resolve(fileName);
		
		try(OutputStream out = Files.newOutputStream(filePath, StandardOpenOption.CREATE_NEW)){
			StreamUtils.copy(inputStream, out);
		}
		
		return this.rootPath.relativize(filePath).toString().replace("\\", "/");		
	}

	private String getFileExtension(String fileName) {

		int lastDot = fileName.lastIndexOf(".");
		return lastDot == -1 ? "" : fileName.substring(lastDot+1);
	}

	public Resource getFileResource(String storedPath) throws IOException {
		Path filePath = this.rootPath.resolve(storedPath).normalize().toAbsolutePath();
		Path normalizedRoot = this.rootPath.normalize().toAbsolutePath();
		if(!filePath.startsWith(normalizedRoot)) {
			throw new SecurityException("Access Denied");
		}
		if(!Files.exists(filePath)) {
			throw new FileNotFoundException("File not found");
		}
		return new UrlResource(filePath.toUri());
	}
	
	public void deleteFile(String storedPath) throws IOException {
	    // 1. تحديد المسار الكامل للملف
	    Path filePath = this.rootPath.resolve(storedPath).normalize().toAbsolutePath();
	    
	    // 2. التأكد إن المسار لسه جوه الـ rootPath (زيادة أمان)
	    if (!filePath.startsWith(this.rootPath.toAbsolutePath())) {
	        throw new SecurityException("Access Denied: Cannot delete file outside storage directory");
	    }
	    
	    // 3. المسح الفعلي
	    Files.deleteIfExists(filePath);
	}
}
