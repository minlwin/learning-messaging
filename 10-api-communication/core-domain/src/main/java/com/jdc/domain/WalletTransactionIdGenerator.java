package com.jdc.domain;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.domain.entity.WalletTransactionSeq;
import com.jdc.domain.repo.WalletTransactionSeqRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRES_NEW)
public class WalletTransactionIdGenerator {

	private final WalletTransactionSeqRepo repo;
	
	@Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRES_NEW)
	public String next(LocalDate issueAt) {
		var seq = repo.findById(issueAt)
			.orElseGet(() -> repo.save(new WalletTransactionSeq(issueAt)));
		return seq.next().code();
	}

}
