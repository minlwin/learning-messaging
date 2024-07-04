package com.jdc.learning.message.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.learning.message.api.input.ProductSearch;
import com.jdc.learning.message.api.output.ProductInfo;
import com.jdc.learning.message.model.entity.Product;
import com.jdc.learning.message.model.repo.ProductRepo;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepo repo;

	@Transactional(readOnly = true)
	public List<ProductInfo> search(ProductSearch form) {
		return repo.search(cb -> {
			var cq = cb.createQuery(ProductInfo.class);
			var root = cq.from(Product.class);
			ProductInfo.project(cq, root);
			cq.where(form.where(cb, root));
			return cq;
		});
	}

}
