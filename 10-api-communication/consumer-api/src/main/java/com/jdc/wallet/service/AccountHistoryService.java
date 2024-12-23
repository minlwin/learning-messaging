package com.jdc.wallet.service;

import java.util.List;
import java.util.function.Function;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.domain.entity.AccountForWalletHistory;
import com.jdc.domain.repo.AccountForWalletHistoryRepo;
import com.jdc.wallet.api.input.WalletHistorySearch;
import com.jdc.wallet.api.output.WalletAccountHistoryInfo;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountHistoryService {
	
	private final AccountForWalletHistoryRepo historyRepo;

	@Transactional(readOnly = true)
	public List<WalletAccountHistoryInfo> search(WalletHistorySearch search) {
		return historyRepo.search(queryFunc(search));
	}

	private Function<CriteriaBuilder, CriteriaQuery<WalletAccountHistoryInfo>> queryFunc(WalletHistorySearch search) {
		return cb -> {
			var cq = cb.createQuery(WalletAccountHistoryInfo.class);
			var root = cq.from(AccountForWalletHistory.class);
			
			WalletAccountHistoryInfo.select(cq, root);
			cq.where(search.where(cb, root));
			
			return cq;
		};
	}

}
