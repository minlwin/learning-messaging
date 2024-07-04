package com.jdc.learning.message.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.learning.message.api.input.SaleItemForm;
import com.jdc.learning.message.api.input.SaleSearch;
import com.jdc.learning.message.api.output.SaleInfo;
import com.jdc.learning.message.model.entity.Sale;
import com.jdc.learning.message.model.entity.SaleItem;
import com.jdc.learning.message.model.repo.ProductRepo;
import com.jdc.learning.message.model.repo.SaleRepo;
import com.jdc.learning.message.utils.ApiExcception;

@Service
public class SaleService {
	
	@Autowired
	private SaleRepo saleRepo;
	@Autowired
	private ProductRepo productRepo;
	
	@Autowired
	private TopicExchange exchange;
	
	@Autowired
	private RabbitTemplate template;

	@Transactional(readOnly = true)
	public List<SaleInfo> search(SaleSearch form) {
		return saleRepo.search(cb -> {
			var cq = cb.createQuery(SaleInfo.class);
			var root = cq.from(Sale.class);
			SaleInfo.project(cb, cq, root);
			cq.where(form.where(cb, root));
			return cq;
		});
	}

	@Transactional
	public SaleInfo create(List<SaleItemForm> items) {
		
		var saleItems = new ArrayList<SaleItem>();
		
		for(var item : items) {
			var product = productRepo.findById(item.productId()).orElse(null);
			if(null == product) {
				throw new ApiExcception("Invalid Product ID.");
			}
			
			saleItems.add(new SaleItem(product, item.quantity()));
		}
		
		var sale = saleRepo.save(new Sale(saleItems));
		
		template.convertAndSend(exchange.getName(), "sale", String.valueOf(sale.getId()));
		
		return SaleInfo.from(sale);
	}

}
