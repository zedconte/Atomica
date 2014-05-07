package com.industrika.sales.dto;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.industrika.inventory.dto.Company;

@Entity		
@Table(name="customer")
public class Customer extends Company {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4008734781739445367L;
	private String salesContactName;

	public String getSalesContactName() {
		return salesContactName;
	}

	public void setSalesContactName(String salesContactName) {
		this.salesContactName = salesContactName;
	}
}
