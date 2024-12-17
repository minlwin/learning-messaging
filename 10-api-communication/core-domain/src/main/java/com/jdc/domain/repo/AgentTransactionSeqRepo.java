package com.jdc.domain.repo;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jdc.domain.entity.AgentTransactionSeq;

public interface AgentTransactionSeqRepo extends JpaRepository<AgentTransactionSeq, LocalDate>{

}
