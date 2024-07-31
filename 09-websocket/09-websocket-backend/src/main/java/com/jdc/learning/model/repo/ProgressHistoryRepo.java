package com.jdc.learning.model.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jdc.learning.model.entity.ProgressHistory;

public interface ProgressHistoryRepo extends JpaRepository<ProgressHistory, UUID>{

}
