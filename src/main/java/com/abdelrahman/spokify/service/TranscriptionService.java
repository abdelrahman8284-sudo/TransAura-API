package com.abdelrahman.spokify.service;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.abdelrahman.spokify.dto.request.TranscriptionRequest;
import com.abdelrahman.spokify.model.Audio;
import com.abdelrahman.spokify.model.Metadata;
import com.abdelrahman.spokify.model.Transcription;
import com.abdelrahman.spokify.model.User;
import com.abdelrahman.spokify.repository.TranscriptionRepo;
import com.abdelrahman.spokify.repository.UserRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TranscriptionService {

	private final TranscriptionRepo transcriptionRepo;
	private final AudioStorageService storageService;
	private final UserRepo userRepo;
	
	public Transcription create(TranscriptionRequest request, Audio audioObj) {
	    Transcription transcription = new Transcription();
	    
	    transcription.setTranscription(request.transcription());
	    transcription.setEnhanced(request.enhanced());
	    transcription.setTasks(request.tasks());
	    transcription.setUser(request.User());
	    transcription.setAudio(audioObj);

	    // إعداد الـ Metadata
	    Metadata metadata = new Metadata();
	    metadata.setSummary(request.summary());
	    metadata.setTopics(request.topics());
	    metadata.setFilename(audioObj != null ? audioObj.getOriginalName() : "recording.wav");
	    metadata.setUpload_date(LocalDateTime.now().toString());
	    
	    transcription.setMetadata(metadata);

	    return transcriptionRepo.save(transcription);
	}
	
	public List<Transcription> getAll(){
		return transcriptionRepo.findAllByOrderByCreatedAtDesc();
	}
	
	public Transcription getTranscriptionById(String transcriptionId) {
		return transcriptionRepo.findById(transcriptionId).orElseThrow();	
	}

	
	public void delete(String transcriptionId) throws IOException {
		Transcription transcription = transcriptionRepo.findById(transcriptionId)
                .orElseThrow();

        // 2. مسح الملف الفعلي من الهارد ديسك
        if (transcription.getAudio() != null && transcription.getAudio().getUrl() != null) {
            String relativePath = transcription.getAudio().getUrl();
            storageService.deleteFile(relativePath);
        }
		transcriptionRepo.deleteById(transcriptionId); // هنا بقى المسح العادي من ال database 
	}
	// هنا الدالة دي عايزنها ترجع بشكل معين 
	/*
	 {
	 _id:
	 transcription:
	 enhanced:
	 summary: 
	 topics:
	 tasks:
	 upload_date
	 audio{
	 filename:
	 downloadUrl:
	 }
	 userName:
	 }
	*/
	public List<Map<String, Object>> getUserTranscriptions(String userId) {

	    List<Transcription> transcriptions = transcriptionRepo.findByUserOrderByCreatedAtDesc(userId);

	    if (transcriptions.isEmpty()) {
	        throw new RuntimeException("No transcriptions found for this user.");
	    }

	    return transcriptions.stream().map(item -> {
	        Map<String, Object> response = new HashMap<>();

	        // 1. إضافة _id عشان الـ Frontend والـ Sidebar
	        response.put("_id", item.getId()); 
	        response.put("id", item.getId());
	        
	        response.put("transcription", item.getTranscription());
	        response.put("enhanced", item.getEnhanced());
	        response.put("summary", item.getMetadata() != null ? item.getMetadata().getSummary() : null);
	        response.put("topics", item.getMetadata() != null ? item.getMetadata().getTopics() : null);
	        response.put("tasks", item.getTasks());
	        response.put("upload_date", item.getMetadata() != null ? item.getMetadata().getUpload_date() : null);

	        // audio
	        if (item.getAudio() != null && item.getAudio().getUrl() != null) {
	            Map<String, String> audioMap = new HashMap<>();
	            audioMap.put("filename", item.getAudio().getOriginalName());

	            // 🔥 الحل السحري: نبعت الـ URL زي ما هو في الداتابيز (المسار فقط)
	            // لأن الـ Frontend بيضيف localhost:4000/audio/ من عنده
	            audioMap.put("downloadUrl", item.getAudio().getUrl());

	            response.put("audio", audioMap);
	        } else {
	            response.put("audio", null);
	        }

	        // userName
	        User user = userRepo.findById(item.getUser()).orElse(null);
	        response.put("userName", user != null ? user.getUserName() : "Unknown");

	        return response;
	    }).toList();// عشان يرجع ك list
	}
}
