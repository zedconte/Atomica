package com.industrika.business.dto;
import com.industrika.sales.dto.Sale;
public class SaleReport extends Report {
	
	private Sale mySale;

	public Sale getMySale() {
		return mySale;
	}

	public void setMySale(Sale mySale) {
		this.mySale = mySale;
	}
	
}
