package com.industrika.sales.dto;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.industrika.inventory.dto.Document;

@Entity
@Table(name="quotation")
public class Quotation extends Document {
	/**
	 * 
	 */
	private static final long serialVersionUID = -801523121553822382L;
	@ManyToOne
	@JoinColumn(name="customer")	
	private Customer customer;
	private String payType;
	private String reference;
	@Transient 
	private String itemsJson;
	
	@Transient
	//Sucursal
	private String branchJson;
	
	@Transient 
	//Almacen
	private String warehouseJson;
	
	@Transient 
	//Max elements
	private String productQuantityJson;
	
	@Transient 
	//Rows
	private String rowsAsJson;
	
	@Transient
	private String customersAccount;
	
	public String getRowsAsJson() {
		return rowsAsJson;
	}
	public void setRowsAsJson(String rowsAsJson) {
		this.rowsAsJson = rowsAsJson;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getItemsJson() {
		return itemsJson;
	}
	public void setItemsJson(String itemsJson) {
		this.itemsJson = itemsJson;
	}
	
	public String getBranchJson() {
		return branchJson;
	}
	public void setBranchJson(String branchJson) {
		this.branchJson = branchJson;
	}
	public String getWarehouseJson() {
		return warehouseJson;
	}
	public void setWarehouseJson(String warehouseJson) {
		this.warehouseJson = warehouseJson;
	}
	
	public String getProductQuantityJson() {
		return productQuantityJson;
	}
	public void setProductQuantityJson(String productQuantityJson) {
		this.productQuantityJson = productQuantityJson;
	}
	public String getCustomersAccount() {
		return customersAccount;
	}
	public void setCustomersAccount(String customersAccount) {
		this.customersAccount = customersAccount;
	}
}
