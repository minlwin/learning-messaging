package com.jdc.progress.api.input;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EscUploadForm(
		@NotBlank(message = "Please enter system.")
		String system,
		@NotBlank(message = "Please enter upload user name.")
		String uploadBy,
		@NotNull(message = "Please select upload file.")
		MultipartFile file
		) {

}
