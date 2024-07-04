package com.jdc.learning.message.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.learning.message.api.input.SaleItemForm;
import com.jdc.learning.message.api.input.SaleSearch;
import com.jdc.learning.message.api.output.SaleInfo;
import com.jdc.learning.message.model.entity.Sale;
import com.jdc.learning.message.model.repo.SaleRepo;

@Service
public class SaleService {
	
	@Autowired
	private SaleRepo repo;

	@Transactional(readOnly = true)
	public List<SaleInfo> search(SaleSearch form) {
		return repo.search(cb -> {
			var cq = cb.createQuery(SaleInfo.class);
			var root = cq.from(Sale.class);
			SaleInfo.project(cb, cq, root);
			cq.where(form.where(cb, root));
			return cq;
		});
	}

	public SaleInfo create(SaleItemForm[] items) {
		// TODO Auto-generated method stub
		return null;
	}

}
