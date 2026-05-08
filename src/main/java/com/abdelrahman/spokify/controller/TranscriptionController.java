package com.abdelrahman.spokify.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.abdelrahman.spokify.dto.ApiResponse;
import com.abdelrahman.spokify.model.Audio;
import com.abdelrahman.spokify.model.Transcription;
import com.abdelrahman.spokify.service.AudioService;
import com.abdelrahman.spokify.service.TranscriptionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Log4j2
public class TranscriptionController {

    private final TranscriptionService transcriptionService;
    private final AudioService audioService;

    @PostMapping("/transcriptions")
    public ResponseEntity<ApiResponse> createTranscription(
            @RequestParam(required = false) MultipartFile audio,
            @RequestParam String transcription,
            @RequestParam String enhanced,
            @RequestParam(required = false) String summary,
            @RequestParam(required = false) String topics,
            @RequestParam(required = false) String tasks,
            @RequestParam(name = "user") String user
    ) {

        if (transcription == null || transcription.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Transcription text is required.", null, null));
        }

        if (user == null || user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(false, "Unauthorized", null, null));
        }

        try {
            Audio audioObj = null;

            if (audio != null && !audio.isEmpty()) {
                audioObj = audioService.upload(audio);
            }

            Transcription result = transcriptionService.create(
                    transcription,
                    enhanced,
                    summary,
                    topics,
                    tasks,
                    user,
                    audioObj
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new ApiResponse(true, "Transcription saved successfully", result, null)
            );

        } catch (Exception e) {
            log.error("Error saving transcription:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Internal server error", null, null));
        }
    }

    @GetMapping("/transcriptions/getAll")
    public ResponseEntity<ApiResponse> getAll() {
        try {
            List<Transcription> transcriptions = transcriptionService.getAll();

            log.info("✅ Done fetching. {}", transcriptions);

            return ResponseEntity.ok(
                    new ApiResponse(
                            true,
                            null,
                            transcriptions,
                            transcriptions.size()
                    )
            );

        } catch (Exception e) {
            log.error("Error getting transcriptions:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Server Error. Could not retrieve transcriptions.", null, null));
        }
    }

    @GetMapping("/getTrancriptionById/{id}")
    public ResponseEntity<ApiResponse> getTranscriptionById(@PathVariable String id) {
        try {
            Transcription transcription = transcriptionService.getTranscriptionById(id);

            return ResponseEntity.ok(
                    new ApiResponse(true, null, transcription, null)
            );

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, "Transcription not found", null, null));
        } catch (Exception e) {
            log.error("Error getting transcription by ID:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Internal server error", null, null));
        }
    }

    @DeleteMapping("/transcriptions/{id}")
    public ResponseEntity<ApiResponse> deleteTranscription(@PathVariable String id) {
        try {
            transcriptionService.delete(id);

            return ResponseEntity.ok(
                    new ApiResponse(true, "Transcription deleted successfully", null, null)
            );

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, "Transcription not found", null, null));
        } catch (Exception e) {
            log.error("Error deleting transcription:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Server error", null, null));
        }
    }

    @GetMapping("/transcriptions/user/{userId}")
    public ResponseEntity<ApiResponse> getByUser(@PathVariable String userId) {
        try {
            List<Map<String, Object>> data = transcriptionService.getUserTranscriptions(userId);

            return ResponseEntity.ok(
                    new ApiResponse(
                            true,
                            null,
                            data,
                            data.size()
                    )
            );

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, "No transcriptions found for this user.", null, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Internal server error", null, null));
        }
    }
}