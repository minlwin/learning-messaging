package com.jdc.wallet.api.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.core.exceptions.ApiBusinessException;
import com.jdc.domain.entity.WalletTransaction;
import com.jdc.domain.repo.AgentTransactionRepo;
import com.jdc.domain.repo.WalletTransactionRepo;
import com.jdc.domain.service.WalletTransactionIdGenerator;
import com.jdc.domain.utils.TransactionStatus;
import com.jdc.wallet.api.input.AgentCashOutSearch;
import com.jdc.wallet.api.input.TransactionStatusForm;
import com.jdc.wallet.api.output.AgentCashOutInfo;
import com.jdc.wallet.api.output.AgentCashOutResult;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AgentTransactionService {
	
	private final AgentTransactionRepo agentTransactionRepo;
	private final WalletTransactionRepo walletTransactionRepo;
	
	private final WalletTransactionIdGenerator idGenerator;

	@Transactional(readOnly = true)
	public AgentCashOutInfo search(AgentCashOutSearch search) {
		return agentTransactionRepo.findById(search.cashOutId())
				.filter(a -> a.getConsumer().getPhone().equals(search.phone()))
				.filter(a -> a.getStatus() == TransactionStatus.Initiate)
				.map(AgentCashOutInfo::from)
				.orElseThrow(() -> new ApiBusinessException("There is no active transaction."));
	}

	@Transactional
	public AgentCashOutResult confirm(TransactionStatusForm form) {
		
		var agentTransaction = agentTransactionRepo.findById(form.id())
				.filter(a -> a.getStatus() == TransactionStatus.Initiate)
				.orElseThrow(() -> new ApiBusinessException("There is no active transaction."));
		
		if(form.confirmed()) {
			// Update Agent Transaction
			agentTransaction.setStatus(TransactionStatus.Success);
			
			// Create Wallet Transaction
			var walletTransaction = new WalletTransaction();
			walletTransaction.setId(idGenerator.next(LocalDate.now()));
			walletTransaction.setAgent(agentTransaction.getAgent());
			walletTransaction.setConsumer(agentTransaction.getConsumer());
			walletTransaction.setGlobalNumber(agentTransaction.getGlobalNumber());
			walletTransaction.setAgentTrx(agentTransaction);
			walletTransaction.setCreatedAt(LocalDateTime.now());
			walletTransaction.setFinishedAt(LocalDateTime.now());
			walletTransaction.setRemark(form.remark());
			walletTransaction.setStatus(TransactionStatus.Success);
			
			walletTransaction = walletTransactionRepo.save(walletTransaction);
			agentTransaction.setWalletTrx(walletTransaction);
		} else {
			agentTransaction.setStatus(TransactionStatus.Canceled);
			agentTransaction.setErrorMessage(form.remark());
		}
		
		agentTransaction.setFinishedAt(LocalDateTime.now());
		
		return AgentCashOutResult.from(agentTransaction);
	}

}
