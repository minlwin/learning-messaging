package com.jdc.progress.model.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "ESC_UPLOAD_HISTORY")
public class EscUploadHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(nullable = false)
	private String system;
	
	@Column(nullable = false)
	private String fileName;
	
	@Column(nullable = false)
	private String uploadBy;
	
	@Column(nullable = false)
	private UploadState state;

	@Column(nullable = false)
	private LocalDateTime uploadAt;
	private LocalDateTime readAt;
	private LocalDateTime savedAt;
	private LocalDateTime validatedAt;
	private LocalDateTime createdAt;
	private LocalDateTime finishedAt;
	
	private ErrorType errorType;
	
	private int records;
	private int errors;
	
	public enum ErrorType {
		Validation, System
	}
	
	public enum UploadState {
		Upload, Read, Save, Validate, Create, Success, Error
	}
}
