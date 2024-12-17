package com.jdc.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jdc.domain.entity.AgentTransaction;

public interface AgentTransactionRepo extends JpaRepository<AgentTransaction, String>{

}
