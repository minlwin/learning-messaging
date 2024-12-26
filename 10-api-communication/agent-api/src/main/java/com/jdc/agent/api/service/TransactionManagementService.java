package com.jdc.agent.api.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.agent.api.input.TransactionForm;
import com.jdc.agent.api.input.TransactionSearch;
import com.jdc.agent.api.output.TransactionInfo;
import com.jdc.core.exceptions.ApiBusinessException;
import com.jdc.domain.entity.AgentTransaction;
import com.jdc.domain.repo.AccountForAgentRepo;
import com.jdc.domain.repo.AccountForWalletRepo;
import com.jdc.domain.repo.AgentTransactionRepo;
import com.jdc.domain.service.AgentTransactionIdGenerator;
import com.jdc.domain.service.GlobalTransactionIdGenerator;
import com.jdc.domain.utils.TransactionStatus;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionManagementService {
	
	private final AgentTransactionRepo transactionRepo;
	private final AccountForAgentRepo agentRepo;
	private final AccountForWalletRepo walletRepo;
	
	private final AgentTransactionIdGenerator idGenerator;
	private final GlobalTransactionIdGenerator globalTransactionIdGenerator;
	
	@Transactional
	public TransactionInfo initiate(TransactionForm form) {
		
		var agent = agentRepo.findOneByPhone(form.agent())
				.orElseThrow(() -> new ApiBusinessException("There is no agent with phone %s.".formatted(form.agent())));
		
		var consumer = walletRepo.findOneByPhone(form.consumer())
				.orElseThrow(() -> new ApiBusinessException("There is no wallet with phone %s.".formatted(form.consumer())));
		
		var transaction = new AgentTransaction();
		transaction.setAgent(agent);
		transaction.setConsumer(consumer);
		
		transaction.setId(idGenerator.next(LocalDate.now()));
		transaction.setGlobalNumber(globalTransactionIdGenerator.next(LocalDate.now()));
		
		transaction.setAmount(form.amount());
		transaction.setParticular(form.particular());
		transaction.setCreatedAt(LocalDateTime.now());
		transaction.setStatus(TransactionStatus.Initiate);
		
		transaction = transactionRepo.save(transaction);
		
		return TransactionInfo.from(transaction);
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
