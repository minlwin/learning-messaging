package com.jdc.domain.entity;

import java.time.LocalDate;

import com.jdc.domain.utils.TrxSeqUtils;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@AllArgsConstructor
@Table(name = "GLOBAL_TRANSACTION_SEQ")
public class GlobalTransactionSeq {

	@Id
	@Column(nullable = false, name = "issue_at")
	private LocalDate issueAt;
	
	@Column(nullable = false, name = "seq_number")
	private int seqNumber;
	
	public GlobalTransactionSeq(LocalDate issueAt) {
		super();
		this.issueAt = issueAt;
	}
	
	public GlobalTransactionSeq next() {
		++ seqNumber;
		return this;
	}
	
	public String code() {
		return TrxSeqUtils.getCode("GL", issueAt, seqNumber);
	}

}
