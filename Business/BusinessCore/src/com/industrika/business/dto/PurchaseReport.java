package com.industrika.business.dto;

import com.industrika.inventory.dto.Purchase;

public class PurchaseReport extends Report{

	private Purchase myPurchase;

	public Purchase getMyPurchase() {
		return myPurchase;
	}

	public void setMyPurchase(Purchase myPurchase) {
		this.myPurchase = myPurchase;
	}
}
