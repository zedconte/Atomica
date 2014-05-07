package com.industrika.administration.dto;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity	
@Table(name="accounts")
public class Account implements Serializable{
	@Transient
	private static final long serialVersionUID = -5473849964262931864L;
	
	@Id
	private String refNumber;
	private String accountName;
	private Integer level;
	private Double balance;
	
	@Transient
	private Integer position;
	
	public String getRefNumber() {
		return refNumber;
	}
	public void setRefNumber(String refNumber) {
		this.refNumber = refNumber;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	
	public Integer getPosition() {
		return position;
	}
	public void setPosition(Integer position) {
		this.position = position;
	}
	
	@Override
	public String toString() {
		return "Account [refNumber=" + refNumber + ", accountName="
				+ accountName + ", level=" + level + ", balance=" + balance
				+ ", position=" + position + "]";
	}
	
}
