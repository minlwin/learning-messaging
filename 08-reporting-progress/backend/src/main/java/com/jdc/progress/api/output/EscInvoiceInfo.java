package com.jdc.progress.api.output;

import java.time.LocalDate;

import com.jdc.progress.model.entity.EscInvoice;
import com.jdc.progress.model.entity.EscInvoice.Status;
import com.jdc.progress.model.entity.EscInvoicePk;
import com.jdc.progress.model.entity.EscInvoice_;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public record EscInvoiceInfo(
		EscInvoicePk id,
		int seqNumber,
		String ledgerNo,
		String customerName,
		String meterNo,
		String address,
		String billCode,
		int usageUnit,
		int usageFees,
		int serviceCharges,
		int horsePower,
		int discount,
		int lastBalance,
		int total,
		int remainAmount,
		int connectionFees,
		int allTotal,
		Status status,
		LocalDate mtbDueDate) {

	public static void select(CriteriaQuery<EscInvoiceInfo> cq, Root<EscInvoice> root) {
		cq.multiselect(
			root.get(EscInvoice_.id),
			root.get(EscInvoice_.seqNumber),
			root.get(EscInvoice_.ledgerNo),
			root.get(EscInvoice_.customerName),
			root.get(EscInvoice_.meterNo),
			root.get(EscInvoice_.address),
			root.get(EscInvoice_.billCode),
			root.get(EscInvoice_.usageUnit),
			root.get(EscInvoice_.usageFees),
			root.get(EscInvoice_.serviceCharges),
			root.get(EscInvoice_.horsePower),
			root.get(EscInvoice_.discount),
			root.get(EscInvoice_.lastBalance),
			root.get(EscInvoice_.total),
			root.get(EscInvoice_.remainAmount),
			root.get(EscInvoice_.connectionFees),
			root.get(EscInvoice_.allTotal),
			root.get(EscInvoice_.status),
			root.get(EscInvoice_.mtbDueDate)
		);
	}

}
