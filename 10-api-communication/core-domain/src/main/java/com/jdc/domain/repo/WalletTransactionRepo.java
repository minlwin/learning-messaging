package com.jdc.domain.repo;

import java.util.Optional;

import com.jdc.domain.BaseRepository;
import com.jdc.domain.entity.WalletTransaction;

public interface WalletTransactionRepo extends BaseRepository<WalletTransaction, String>{

	Optional<WalletTransaction> findOneByGlobalNumber(String id);

}
