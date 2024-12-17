package com.jdc.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jdc.domain.entity.WalletTransaction;

public interface WalletTransactionRepo extends JpaRepository<WalletTransaction, String>{

}
