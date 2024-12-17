package com.jdc.domain.repo;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jdc.domain.entity.GlobalTransactionSeq;

public interface GlobalTransactionSeqRepo extends JpaRepository<GlobalTransactionSeq, LocalDate>{

}
