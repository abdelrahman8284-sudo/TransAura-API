package com.abdelrahman.spokify.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "transcriptions")
@Setter@Getter@AllArgsConstructor@NoArgsConstructor
public class Transcription {

    @Id
    private String id;

    @NotBlank
    private String transcription;
    @NotBlank
    private String enhanced;

    private Audio audio;
    private Metadata metadata;

    private String tasks;

    @NotBlank
    private String user; // userId
    
    @CreatedDate
    private LocalDateTime createdAt; 

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
