package com.jdc.wallet.api.service;

import java.util.List;
import java.util.function.Function;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.domain.entity.WalletTransaction;
import com.jdc.domain.repo.WalletTransactionRepo;
import com.jdc.wallet.api.input.TransactionSearch;
import com.jdc.wallet.api.output.TransactionInfo;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TransactionManagementService {
	
	private final WalletTransactionRepo repo;
	
	public TransactionInfo findById(String id) {
		return repo.findById(id).map(TransactionInfo::from).orElseThrow();
	}

	public List<TransactionInfo> search(TransactionSearch search) {
		return repo.search(queryFunc(search));
	}

	private Function<CriteriaBuilder, CriteriaQuery<TransactionInfo>> queryFunc(TransactionSearch search) {
		return cb -> {
			var cq = cb.createQuery(TransactionInfo.class);
			var root = cq.from(WalletTransaction.class);
			TransactionInfo.select(cq, root);
			cq.where(search.where(cb, root));
			return cq;
		};
	}

}
