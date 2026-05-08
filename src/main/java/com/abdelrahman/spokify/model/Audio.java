package com.abdelrahman.spokify.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter@Getter@AllArgsConstructor@NoArgsConstructor
public class Audio {
    private String originalName;
    private String mimeType;
    private Long size;
    private String url;
}
