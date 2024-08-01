package com.jdc.learning.model.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class ProgressHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	private String title;
	private int delayInSec;
	
	@JsonFormat(pattern = "yyyyMMdd HH:mm:ss")
	private LocalDateTime startAt;

	@JsonFormat(pattern = "yyyyMMdd HH:mm:ss")
	private LocalDateTime endAt;
}
