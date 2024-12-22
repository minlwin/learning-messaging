package com.jdc.agent.api.service;

import java.util.List;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.agent.api.input.AgentHistorySearch;
import com.jdc.agent.api.output.AgentAccountHistoryInfo;
import com.jdc.domain.entity.AccountForAgentHistory;
import com.jdc.domain.repo.AccountForAgentHistoryRepo;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;

@Service
public class AgentAccountHistoryService {
	
	@Autowired
	private AccountForAgentHistoryRepo historyRepo;
	
	@Transactional(readOnly = true)
	public List<AgentAccountHistoryInfo> search(AgentHistorySearch search) {
		return historyRepo.search(queryFunc(search));
	}
	
	private Function<CriteriaBuilder, CriteriaQuery<AgentAccountHistoryInfo>> queryFunc(AgentHistorySearch search) {
		return cb -> {
			var cq = cb.createQuery(AgentAccountHistoryInfo.class);
			var root = cq.from(AccountForAgentHistory.class);
			
			AgentAccountHistoryInfo.select(cq, root);
			cq.where(search.where(cb, root));
			
			return cq;
		};
	}

}
