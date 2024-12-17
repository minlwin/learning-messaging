package com.jdc.domain.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jdc.domain.entity.AccountForAgent;

public interface AccountForAgentRepo extends JpaRepository<AccountForAgent, UUID>{

}
