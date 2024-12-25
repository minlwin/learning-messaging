package com.jdc.domain.repo;

import java.util.Optional;
import java.util.UUID;

import com.jdc.domain.BaseRepository;
import com.jdc.domain.entity.AccountForAgent;

public interface AccountForAgentRepo extends BaseRepository<AccountForAgent, UUID>{

	Optional<AccountForAgent> findOneByPhone(String phone);

}
