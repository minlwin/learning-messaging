package com.jdc.progress.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jdc.progress.api.output.EscTownship;
import com.jdc.progress.service.EscTownshipService;

@RestController
@RequestMapping("services")
public class EscServicesApi {
	
	@Autowired
	private EscTownshipService service;

	@Value("${app.esc.services}")
	private List<String> services;
	
	@GetMapping
	public List<String> getServices() {
		return services;
	}
	
	@GetMapping("township/{system}")
	public List<EscTownship> searchTownshp(@PathVariable String system) {
		return service.search(system);
	}
}
