package com.jdc.domain.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.domain.entity.GlobalTransactionSeq;
import com.jdc.domain.repo.GlobalTransactionSeqRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRES_NEW)
public class GlobalTransactionIdGenerator {

	private final GlobalTransactionSeqRepo repo;
	
	@Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRES_NEW)
	public String next(LocalDate issueAt) {
		var seq = repo.findById(issueAt)
			.orElseGet(() -> repo.save(new GlobalTransactionSeq(issueAt)));
		return seq.next().code();
	}
}
