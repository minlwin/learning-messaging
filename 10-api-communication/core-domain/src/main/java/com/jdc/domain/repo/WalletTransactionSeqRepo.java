package com.jdc.domain.repo;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jdc.domain.entity.WalletTransactionSeq;

public interface WalletTransactionSeqRepo extends JpaRepository<WalletTransactionSeq, LocalDate>{

}
