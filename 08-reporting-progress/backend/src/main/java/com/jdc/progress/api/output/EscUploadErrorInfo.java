package com.jdc.progress.api.output;

import com.jdc.progress.model.entity.EscUploadError;
import com.jdc.progress.model.entity.EscUploadError_;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public record EscUploadErrorInfo(
		String seqNumber,
		String ledgerNo,
		String meterNo,
		String customerId,
		String customerName,
		String address,
		String billCode,
		String lastDate,
		String usageUnit,
		String usageFees,
		String serviceCharges,
		String horsePower,
		String discount,
		String lastBalance,
		String total,
		String remainAmount,
		String connectionFees,
		String allTotal,
		String error) {
	
	public static void select(CriteriaQuery<EscUploadErrorInfo> cq, Root<EscUploadError> root) {
		cq.multiselect(
			root.get(EscUploadError_.seqNumber),
			root.get(EscUploadError_.ledgerNo),
			root.get(EscUploadError_.meterNo),
			root.get(EscUploadError_.customerId),
			root.get(EscUploadError_.customerName),
			root.get(EscUploadError_.address),
			root.get(EscUploadError_.billCode),
			root.get(EscUploadError_.lastDate),
			root.get(EscUploadError_.usageUnit),
			root.get(EscUploadError_.usageFees),
			root.get(EscUploadError_.serviceCharges),
			root.get(EscUploadError_.horsePower),
			root.get(EscUploadError_.discount),
			root.get(EscUploadError_.lastBalance),
			root.get(EscUploadError_.total),
			root.get(EscUploadError_.remainAmount),
			root.get(EscUploadError_.connectionFees),
			root.get(EscUploadError_.allTotal),
			root.get(EscUploadError_.error)
		);
	}
}
