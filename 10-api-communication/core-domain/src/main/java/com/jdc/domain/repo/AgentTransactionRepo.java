package com.jdc.domain.repo;

import java.util.Optional;

import com.jdc.domain.BaseRepository;
import com.jdc.domain.entity.AgentTransaction;

public interface AgentTransactionRepo extends BaseRepository<AgentTransaction, String>{

	Optional<AgentTransaction> findOneByGlobalNumber(String id);
}
