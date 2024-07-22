package com.jdc.progress.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.esc")
public class ApplicationProperties {

	private String services;
	private String storagePath;
	private String progressExchange;
	private String stateExchange;
	private int tempFileSize;
}
