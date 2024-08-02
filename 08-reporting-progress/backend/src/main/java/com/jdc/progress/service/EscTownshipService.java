package com.jdc.progress.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.progress.api.output.EscTownship;
import com.jdc.progress.model.repo.EscServiceTownshipRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EscTownshipService {
	
	private final EscServiceTownshipRepo repo;

	@Transactional(readOnly = true)
	public List<EscTownship> search(String system) {
		return repo.searchByService(system);
	}

}
