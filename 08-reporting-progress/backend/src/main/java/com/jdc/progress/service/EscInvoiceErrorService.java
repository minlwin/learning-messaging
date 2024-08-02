package com.jdc.progress.service;

import java.util.UUID;
import java.util.function.Function;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.progress.api.output.EscUploadErrorInfo;
import com.jdc.progress.model.PageResult;
import com.jdc.progress.model.entity.EscUploadError;
import com.jdc.progress.model.entity.EscUploadError_;
import com.jdc.progress.model.entity.EscUploadHistory_;
import com.jdc.progress.model.repo.EscUploadErrorRepo;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EscInvoiceErrorService {
	
	private final EscUploadErrorRepo repo;

	public PageResult<EscUploadErrorInfo> searchError(String historyId, int page, int size) {
		
		Function<CriteriaBuilder, CriteriaQuery<EscUploadErrorInfo>> queryFunc = cb -> {
			var cq = cb.createQuery(EscUploadErrorInfo.class);
			var root = cq.from(EscUploadError.class);
			
			EscUploadErrorInfo.select(cq, root);
			cq.where(cb.equal(root.get(EscUploadError_.history).get(EscUploadHistory_.id), UUID.fromString(historyId)));
			
			return cq;
		};
		
		Function<CriteriaBuilder, CriteriaQuery<Long>> countFunc = cb -> {
			var cq = cb.createQuery(Long.class);
			var root = cq.from(EscUploadError.class);
			
			cq.select(cb.count(root.get(EscUploadError_.id)));
			cq.where(cb.equal(root.get(EscUploadError_.history).get(EscUploadHistory_.id), UUID.fromString(historyId)));
			
			return cq;
		};

		return repo.search(queryFunc, countFunc, page, size);
	}


}
