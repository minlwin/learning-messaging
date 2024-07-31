package com.jdc.progress.api.input;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.util.StringUtils;

import com.jdc.progress.model.entity.EscInvoice;
import com.jdc.progress.model.entity.EscInvoice.Status;
import com.jdc.progress.model.entity.EscInvoicePk_;
import com.jdc.progress.model.entity.EscInvoice_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public record EscInvoiceSearch(
		String service,
		String township,
		String customerCode,
		Status status,
		LocalDate lastFrom,
		LocalDate lastTo
		) {

	public Predicate[] where(CriteriaBuilder cb, Root<EscInvoice> root) {
		var list = new ArrayList<Predicate>();
		
		if(StringUtils.hasLength(service)) {
			list.add(cb.equal(root.get(EscInvoice_.id).get(EscInvoicePk_.service), service));
		}
		
		if(StringUtils.hasLength(township)) {
			list.add(cb.equal(root.get(EscInvoice_.township), township));
		}

		if(StringUtils.hasLength(customerCode)) {
			list.add(cb.equal(root.get(EscInvoice_.id).get(EscInvoicePk_.customerId), customerCode));
		}

		if(null != status) {
			list.add(cb.equal(root.get(EscInvoice_.status), status));
		}
		
		if(null != lastFrom) {
			list.add(cb.greaterThanOrEqualTo(root.get(EscInvoice_.id).get(EscInvoicePk_.lastDate), lastFrom));
		}

		if(null != lastTo) {
			list.add(cb.lessThanOrEqualTo(root.get(EscInvoice_.id).get(EscInvoicePk_.lastDate), lastTo));
		}

		return list.toArray(size -> new Predicate[size]);
	}
}
