package com.abdelrahman.spokify.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) // عشان تمنع ال null من الظهور
public class ApiResponse {
	private boolean success;
	private String message;
	private Object data;
	private Integer count;
	private String error;
	public ApiResponse(boolean success, String message, Object data, Integer count) {
		super();
		this.success = success;
		this.message = message;
		this.data = data;
		this.count = count;
	}
	public ApiResponse(boolean success, String message, Object data, Integer count, String error) {
		super();
		this.success = success;
		this.message = message;
		this.data = data;
		this.count = count;
		this.error = error;
	}
	
	public ApiResponse(boolean success, String message) {
	    this.success = success;
	    this.message = message;
	}
	
}
