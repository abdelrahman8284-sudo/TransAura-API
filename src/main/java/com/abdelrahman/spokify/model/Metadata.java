package com.abdelrahman.spokify.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter@Getter@AllArgsConstructor@NoArgsConstructor
public class Metadata {
    private String summary;
    private String topics;
    private String filename;
    private String upload_date;
    private String language = "ar";

}
