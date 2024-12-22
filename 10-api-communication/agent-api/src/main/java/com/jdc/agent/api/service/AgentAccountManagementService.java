package com.jdc.agent.api.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
		
		var entity = new AccountForAgent();
		
		entity.setPhone(form.phone());
		entity.setShopName(form.shopName());
		entity.setAmount(form.amount());
		entity.setCreatedAt(LocalDateTime.now());
		
		entity = agentRepo.save(entity);
		return AgentAccountInfo.from(entity);
	}

	@Transactional
	public List<AgentAccountInfo> upload(MultipartFile file) {
		
		List<AccountForAgent> list = new ArrayList<AccountForAgent>();
		
		try(var br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
			
			String line = null;
			
			while(null != (line = br.readLine())) {
				
				var array = line.split("\t");
				var entity = new AccountForAgent();
				
				entity.setShopName(array[0]);
				entity.setPhone(array[1]);
				entity.setAmount(BigDecimal.valueOf(Long.parseLong(array[2])));
				entity.setCreatedAt(LocalDateTime.now());
				
				list.add(entity);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(!list.isEmpty()) {
			list = agentRepo.saveAll(list);
		}
		
		return list.stream().map(AgentAccountInfo::from).toList();
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
