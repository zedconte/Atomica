package com.industrika.inventory.dto;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity		
@Table(name="provider")
public class Provider extends Company {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8382407250436160826L;
	private String purchasesContactName;

	public String getPurchasesContactName() {
		return purchasesContactName;
	}

	public void setPurchasesContactName(String purchasesContactName) {
		this.purchasesContactName = purchasesContactName;
	}
}
