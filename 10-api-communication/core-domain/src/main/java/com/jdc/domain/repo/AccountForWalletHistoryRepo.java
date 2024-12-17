package com.jdc.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jdc.domain.entity.AccountForWalletHistory;
import com.jdc.domain.entity.AccountForWalletHistoryPk;

public interface AccountForWalletHistoryRepo extends JpaRepository<AccountForWalletHistory, AccountForWalletHistoryPk>{

}
