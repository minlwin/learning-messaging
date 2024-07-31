package com.jdc.progress.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.progress.api.output.EscTownship;
import com.jdc.progress.model.repo.EscServiceTownshipRepo;

@Service
public class EscTownshipService {
	
	@Autowired
	private EscServiceTownshipRepo repo;

	@Transactional(readOnly = true)
	public List<EscTownship> search(String system) {
		return repo.searchByService(system);
	}

}
