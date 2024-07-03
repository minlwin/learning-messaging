package com.jdc.learning.message.dto;

import java.time.LocalDateTime;

public class TransactionDto  {

	private int id;
	private Action action;
	private LocalDateTime issueAt;

	public enum Action {
		Create, Update, Delete
	}

	public TransactionDto() {
	}

	public TransactionDto(int id, Action action, LocalDateTime issueAt) {
		super();
		this.id = id;
		this.action = action;
		this.issueAt = issueAt;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public LocalDateTime getIssueAt() {
		return issueAt;
	}

	public void setIssueAt(LocalDateTime issueAt) {
		this.issueAt = issueAt;
	}

	@Override
	public String toString() {
		return "TransactionDto [id=" + id + ", action=" + action + ", issueAt=" + issueAt + "]";
	}

}
