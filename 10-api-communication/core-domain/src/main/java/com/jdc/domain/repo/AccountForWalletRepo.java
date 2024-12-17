package com.jdc.domain.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jdc.domain.entity.AccountForWallet;

public interface AccountForWalletRepo extends JpaRepository<AccountForWallet, UUID>{

}
