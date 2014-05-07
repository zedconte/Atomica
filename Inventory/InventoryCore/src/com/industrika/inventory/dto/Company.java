package com.industrika.inventory.dto;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.industrika.commons.dto.Person;

@Entity		
@Table(name="company")
public class Company extends Person {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2253227202271711685L;
	@SerializedName("providerName")
	@Expose
	protected String businessName;
	protected String rfc;
	protected Double acumulated;
	protected Double balance;
	
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getRfc() {
		return rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	public Double getAcumulated() {
		return acumulated;
	}
	public void setAcumulated(Double acumulated) {
		this.acumulated = acumulated;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}	
}
