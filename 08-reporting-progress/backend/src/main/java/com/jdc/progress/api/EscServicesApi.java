package com.jdc.progress.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jdc.progress.api.output.EscServiceTownship;

@RestController
@RequestMapping("services")
public class EscServicesApi {

	@Value("${app.esc.services}")
	private List<String> services;
	
	@GetMapping
	public List<String> getServices() {
		return services;
	}
	
	@GetMapping("township/{service}")
	public List<EscServiceTownship> searchTownshp(@PathVariable String service) {
		return null;
	}
}
