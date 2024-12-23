package com.jdc.wallet.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.jdc.domain.entity.AccountForWallet;
import com.jdc.domain.repo.AccountForWalletRepo;
import com.jdc.wallet.api.input.WalletAccountForm;
import com.jdc.wallet.api.input.WalletAccountSearch;
import com.jdc.wallet.api.output.WalletAccountInfo;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountManagementService {
	
	private final AccountForWalletRepo repo;

	@Transactional
	public WalletAccountInfo create(WalletAccountForm form) {
		return WalletAccountInfo.from(repo.save(form.entity()));
	}

	@Transactional
	public List<WalletAccountInfo> upload(MultipartFile file) {  
		
		var list = new ArrayList<AccountForWallet>();
		
		try(var br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
			
			String line = null;
			
			while(null != (line = br.readLine())) {
				var array = line.split("\t");
				
				var entity = new AccountForWallet();
				entity.setName(array[0]);
				entity.setPhone(array[1]);
				entity.setAmount(BigDecimal.valueOf(Integer.parseInt(array[2])));
				
				list.add(entity);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(!list.isEmpty()) {
			return repo.saveAll(list).stream().map(WalletAccountInfo::from).toList();
		}
		
		return List.of();
	}

	@Transactional(readOnly = true)
	public List<WalletAccountInfo> search(WalletAccountSearch search) {
		return repo.search(queryFunc(search));
	}

	private Function<CriteriaBuilder, CriteriaQuery<WalletAccountInfo>> queryFunc(WalletAccountSearch search) {
		return cb -> {
			var cq = cb.createQuery(WalletAccountInfo.class);
			var root = cq.from(AccountForWallet.class);
			
			WalletAccountInfo.select(cq, root);
			cq.where(search.where(cb, root));
			
			return cq;
		};
	}

}
