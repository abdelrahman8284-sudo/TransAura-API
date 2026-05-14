package com.abdelrahman.spokify.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter@Getter@AllArgsConstructor
public class Metadata {
    private String summary;
    private String topics;
    private String filename;
    private String upload_date;
    private String language = "ar";
    
    public Metadata() {
        this.upload_date = LocalDateTime.now().toString(); // أو استخدم DateTimeFormatter للـ ISO
    }
}
