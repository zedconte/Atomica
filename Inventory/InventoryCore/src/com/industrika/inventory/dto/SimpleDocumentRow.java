package com.industrika.inventory.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SimpleDocumentRow implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6885605474366938840L;

	@SerializedName("itemName")
	@Expose
	String itemId;
	
	@SerializedName("itemQuantity")
	@Expose
	String itemQuantity;
	
	@SerializedName("itemBranch")
	@Expose
	String itemBranchID;

	@SerializedName("itemWarehouse")
	@Expose
	String itemWarehouseID;

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemQuantity() {
		return itemQuantity;
	}

	public void setItemQuantity(String itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	public String getItemBranchID() {
		return itemBranchID;
	}

	public void setItemBranchID(String itemBranchID) {
		this.itemBranchID = itemBranchID;
	}

	public String getItemWarehouseID() {
		return itemWarehouseID;
	}

	public void setItemWarehouseID(String itemWarehouseID) {
		this.itemWarehouseID = itemWarehouseID;
	}

	@Override
	public String toString() {
		return "SimpleDocumentRow [itemId=" + itemId + ", itemQuantity="
				+ itemQuantity + ", itemBranchID=" + itemBranchID
				+ ", itemWarehouseID=" + itemWarehouseID + "]";
	}
}
