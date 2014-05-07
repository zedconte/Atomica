package com.industrika.administration.dto;
import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity						
@Table(name="bankoperation")
public class BankOperation implements Serializable{

	@Transient
	private static final long serialVersionUID = 1L;
	
	public enum OperationType { RETIRO,DEPOSITO,TRANSPASO};

	@Id						
	private Integer idOperation;
	
	private String description;
	
	private String operationType;
	
	private Double amount;
	
	@Transient
	private String bankAccounts;
	
	@Transient
	private Integer bankId;
	
	@ManyToOne
	@JoinColumn(name="bank")
	private Bank bank;

	public Integer getIdOperation() {
		return idOperation;
	}

	public void setIdOperation(Integer idOperation) {
		this.idOperation = idOperation;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public String getBankAccounts() {
		return bankAccounts;
	}

	public void setBankAccounts(String bankAccounts) {
		this.bankAccounts = bankAccounts;
	}

	public Integer getBankId() {
		return bankId;
	}

	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}

	@Override
	public String toString() {
		return "BankOperation [idOperation=" + idOperation + ", description="
				+ description + ", operationType=" + operationType
				+ ", amount=" + amount + ", bankAccounts=" + bankAccounts
				+ ", bankId=" + bankId + "]";
	}
}
