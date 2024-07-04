package com.jdc.learning.message.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.learning.message.api.input.InventorySearch;
import com.jdc.learning.message.api.output.InventoryInfo;
import com.jdc.learning.message.model.entity.Inventory;
import com.jdc.learning.message.model.repo.InventoryRepo;

@Service
public class InventoryService {
	
	@Autowired
	private InventoryRepo repo;

	@Transactional(readOnly = true)
	public List<InventoryInfo> search(InventorySearch form) {
		return repo.search(cb -> {
			var cq = cb.createQuery(InventoryInfo.class);
			var root = cq.from(Inventory.class);
			InventoryInfo.project(cq, root);
			cq.where(form.where(cb, root));
			return cq;
		});
	}
	
	public void handleOnSale(int saleId) {
		
	}

}
