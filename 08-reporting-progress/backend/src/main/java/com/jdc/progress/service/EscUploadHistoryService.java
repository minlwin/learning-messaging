package com.jdc.progress.service;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.progress.api.input.EscUploadHistorySearch;
import com.jdc.progress.api.output.EscUploadHistoryListItem;
import com.jdc.progress.model.PageResult;
import com.jdc.progress.model.entity.EscUploadHistory;
import com.jdc.progress.model.entity.EscUploadHistory_;
import com.jdc.progress.model.repo.EscUploadHistoryRepo;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;

@Service
@Transactional(readOnly = true)
public class EscUploadHistoryService {
	
	@Autowired
	private EscUploadHistoryRepo repo;
	
	public PageResult<EscUploadHistoryListItem> search(EscUploadHistorySearch form, int page, int size) {
		return repo.search(queryFunc(form), countFunc(form), page, size);
	}
	
	private Function<CriteriaBuilder, CriteriaQuery<EscUploadHistoryListItem>> queryFunc(EscUploadHistorySearch form) {
		return cb -> {
			var cq = cb.createQuery(EscUploadHistoryListItem.class);
			
			var root = cq.from(EscUploadHistory.class);	
			EscUploadHistoryListItem.select(cq, root);
			cq.where(form.where(cb, root));
			
			return cq;
		};
	}

	private Function<CriteriaBuilder, CriteriaQuery<Long>> countFunc(EscUploadHistorySearch form) {
		return cb -> {
			var cq = cb.createQuery(Long.class);
			
			var root = cq.from(EscUploadHistory.class);
			cq.select(cb.count(root.get(EscUploadHistory_.id)));
			cq.where(form.where(cb, root));
			
			return cq;
		};
	}
}
