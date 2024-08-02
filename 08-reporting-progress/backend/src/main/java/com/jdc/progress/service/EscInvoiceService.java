package com.jdc.progress.service;

import java.util.function.Function;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.progress.api.input.EscInvoiceSearch;
import com.jdc.progress.api.output.EscInvoiceInfo;
import com.jdc.progress.model.PageResult;
import com.jdc.progress.model.entity.EscInvoice;
import com.jdc.progress.model.entity.EscInvoice_;
import com.jdc.progress.model.repo.EscInvoiceRepo;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EscInvoiceService {
	
	private final EscInvoiceRepo repo;

	public PageResult<EscInvoiceInfo> search(EscInvoiceSearch form, int page, int size) {	
		return repo.search(queryFunc(form), countFunc(form), page, size);
	}
	
	private Function<CriteriaBuilder, CriteriaQuery<EscInvoiceInfo>> queryFunc(EscInvoiceSearch form) {
		return cb -> {
			var cq = cb.createQuery(EscInvoiceInfo.class);
			var root = cq.from(EscInvoice.class);
			
			EscInvoiceInfo.select(cq, root);
			cq.where(form.where(cb, root));
			
			cq.orderBy(cb.asc(root.get(EscInvoice_.ledgerNo)));
			
			return cq;
		};
	}

	private Function<CriteriaBuilder, CriteriaQuery<Long>> countFunc(EscInvoiceSearch form) {
		return cb -> {
			var cq = cb.createQuery(Long.class);
			var root = cq.from(EscInvoice.class);
			
			cq.select(cb.count(root.get(EscInvoice_.id)));
			cq.where(form.where(cb, root));
			
			return cq;
		};
	}
}
