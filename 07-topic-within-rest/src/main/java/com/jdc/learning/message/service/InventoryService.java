package com.jdc.learning.message.service;

import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.learning.message.api.input.InventorySearch;
import com.jdc.learning.message.api.output.InventoryInfo;
import com.jdc.learning.message.model.entity.Inventory;
import com.jdc.learning.message.model.repo.InventoryRepo;
import com.jdc.learning.message.model.repo.SaleRepo;

@Service
public class InventoryService {
	
	@Autowired
	private InventoryRepo inventoryRepo;
	
	@Autowired
	private SaleRepo saleRepo;

	@Transactional(readOnly = true)
	public List<InventoryInfo> search(InventorySearch form) {
		return inventoryRepo.search(cb -> {
			var cq = cb.createQuery(InventoryInfo.class);
			var root = cq.from(Inventory.class);
			InventoryInfo.project(cq, root);
			cq.where(form.where(cb, root));
			return cq;
		});
	}
	
	@Transactional
	@RabbitListener(queues = "#{saleQueue.name}")
	public void handleOnSale(int saleId) {
		
		saleRepo.findById(saleId).ifPresent(sale -> {
			for(var item : sale.getSaleItem()) {
				var inventory = inventoryRepo.findById(item.getProduct().getId()).orElseGet(() -> {
					var entity = new Inventory();
					entity.setProduct(item.getProduct());
					return inventoryRepo.save(entity);
				});
				
				inventory.setSaleCount(inventory.getSaleCount() + item.getQuantity());
				inventory.setTotalSale(inventory.getTotalSale() + (item.getQuantity() * item.getProduct().getPrice()));
			}
		});
	}

}
