package com.jdc.progress.model.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.jdc.progress.api.output.EscTownship;
import com.jdc.progress.model.BaseRepository;
import com.jdc.progress.model.entity.EscServiceTownship;

public interface EscServiceTownshipRepo extends BaseRepository<EscServiceTownship, String>{

	@Query("select new com.jdc.progress.api.output.EscTownship(e) from EscServiceTownship e where e.service = ?1")
	List<EscTownship> searchByService(String system);

}
