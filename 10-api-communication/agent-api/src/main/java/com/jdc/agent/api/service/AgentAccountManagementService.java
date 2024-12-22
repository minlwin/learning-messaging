package com.jdc.agent.api.service;

import java.util.List;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.jdc.agent.api.input.AgentAccountForm;
import com.jdc.agent.api.input.AgentAccountSearch;
import com.jdc.agent.api.output.AgentAccountInfo;
import com.jdc.domain.entity.AccountForAgent;
import com.jdc.domain.repo.AccountForAgentRepo;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;

@Service
public class AgentAccountManagementService {
	
	@Autowired
	private AccountForAgentRepo agentRepo;

	@Transactional
	public AgentAccountInfo create(AgentAccountForm form) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public List<AgentAccountInfo> upload(MultipartFile file) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional(readOnly = true)
	public List<AgentAccountInfo> search(AgentAccountSearch search) {
		return agentRepo.search(queryFunc(search));
	}

	private Function<CriteriaBuilder, CriteriaQuery<AgentAccountInfo>> queryFunc(AgentAccountSearch search) {
		return cb -> {
			var cq = cb.createQuery(AgentAccountInfo.class);
			var root = cq.from(AccountForAgent.class);
			
			AgentAccountInfo.select(cq, root);
			cq.where(search.where(cb, root));
			
			return cq;
		};
	}

}
