package com.jdc.domain.repo;

import java.util.Optional;
import java.util.UUID;

import com.jdc.domain.BaseRepository;
import com.jdc.domain.entity.AccountForWallet;

public interface AccountForWalletRepo extends BaseRepository<AccountForWallet, UUID>{

	Optional<AccountForWallet> findOneByPhone(String phone);

}
