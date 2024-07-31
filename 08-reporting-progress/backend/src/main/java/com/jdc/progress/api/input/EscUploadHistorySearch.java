package com.jdc.progress.api.input;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.util.StringUtils;

import com.jdc.progress.model.entity.EscUploadHistory;
import com.jdc.progress.model.entity.EscUploadHistory.UploadState;
import com.jdc.progress.model.entity.EscUploadHistory_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public record EscUploadHistorySearch(
		String service,
		UploadState state,
		LocalDate from,
		LocalDate to) {

	public Predicate[] where(CriteriaBuilder cb, Root<EscUploadHistory> root) {
		var list = new ArrayList<Predicate>();
		
		if(StringUtils.hasLength(service)) {
			list.add(cb.equal(root.get(EscUploadHistory_.system), service));
		}
		
		if(null != state) {
			list.add(cb.equal(root.get(EscUploadHistory_.state), state));
		}
		
		if(null != from) {
			list.add(cb.greaterThanOrEqualTo(root.get(EscUploadHistory_.uploadAt), from.atStartOfDay()));
		}
		
		if(null != to) {
			list.add(cb.lessThan(root.get(EscUploadHistory_.uploadAt), to.plusDays(1).atStartOfDay()));
		}
		
		return list.toArray(size -> new Predicate[size]);
	}
}
