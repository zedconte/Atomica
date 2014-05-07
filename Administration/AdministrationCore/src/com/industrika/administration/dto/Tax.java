package com.industrika.administration.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity						
@Table(name="taxes")
public class Tax implements java.io.Serializable{
	/**
	 * 
	 */
	@Transient
	private static final long serialVersionUID = 1L;
	public enum TAXTYPE { PERCENTAGE,AMOUNT};
	
	@Id					
    @GeneratedValue
	private Integer idTax;
	private String name;
	private String initials;
	private Double taxValue;
	private Boolean percentage;
	
	public Integer getIdTax() {
		return idTax;
	}
	public void setIdTax(Integer idTax) {
		this.idTax = idTax;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInitials() {
		return initials;
	}
	public void setInitials(String initials) {
		this.initials = initials;
	}
	
	public Double getTaxValue() {
		return taxValue;
	}
	public void setTaxValue(Double taxValue) {
		this.taxValue = taxValue;
	}
	public Boolean isPercentage() {
		return percentage;
	}
	public void setPercentage(Boolean percentage) {
		this.percentage = percentage;
	}
}
