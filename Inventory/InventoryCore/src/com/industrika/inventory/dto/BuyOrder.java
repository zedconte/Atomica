package com.industrika.inventory.dto;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="buyorder")
public class BuyOrder extends Document {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4946322430338813345L;
	@ManyToOne
	@JoinColumn(name="provider")	
	private Provider provider;
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
	String providersAccount;
	
	public String getRowsAsJson() {
		return rowsAsJson;
	}
	public void setRowsAsJson(String rowsAsJson) {
		this.rowsAsJson = rowsAsJson;
	}
	public Provider getProvider() {
		return provider;
	}
	public void setProvider(Provider provider) {
		this.provider = provider;
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
	public String getProvidersAccount() {
		return providersAccount;
	}
	public void setProvidersAccount(String providersAccount) {
		this.providersAccount = providersAccount;
	}
}
