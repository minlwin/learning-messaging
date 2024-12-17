package com.jdc.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jdc.domain.entity.AccountForAgentHistory;
import com.jdc.domain.entity.AccountForAgentHistoryPk;

public interface AccountForAgentHistoryRepo extends JpaRepository<AccountForAgentHistory, AccountForAgentHistoryPk>{

}
