package com.jdc.agent.api.service;

import java.util.List;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.agent.api.input.TransactionForm;
import com.jdc.agent.api.input.TransactionSearch;
import com.jdc.agent.api.output.TransactionInfo;
import com.jdc.core.exceptions.ApiBusinessException;
import com.jdc.domain.entity.AgentTransaction;
import com.jdc.domain.repo.AgentTransactionRepo;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;

@Service
public class TransactionManagementService {
	
	@Autowired
	private AgentTransactionRepo transactionRepo;

	@Transactional
	public TransactionInfo initiate(TransactionForm form) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional(readOnly = true)
	public TransactionInfo findById(String id) {
		return transactionRepo.findById(id)
				.map(TransactionInfo::from)
				.orElseThrow(() -> new ApiBusinessException("Invalid agent transaction id."));
	}

	@Transactional(readOnly = true)
	public List<TransactionInfo> search(TransactionSearch search) {
		return transactionRepo.search(queryFunc(search));
	}

	private Function<CriteriaBuilder, CriteriaQuery<TransactionInfo>> queryFunc(TransactionSearch search) {
		return cb -> {
			var cq = cb.createQuery(TransactionInfo.class);
			var root = cq.from(AgentTransaction.class);
			
			TransactionInfo.select(cq, root);
			cq.where(search.where(cb, root));
			
			return cq;
		};
	}

}
