package com.industrika.shipping.dto;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.industrika.inventory.dto.DocumentRow;
import com.industrika.inventory.dto.Item;

@Entity						
@Table(name="shippingschedule")
public class ShippingSchedule implements Serializable {
	/**
	 * 
	 */
	@Transient
	private static final long serialVersionUID = -3447621951183762600L;
	@Id					
    @GeneratedValue
	private Integer idShippingSchedule;
	private Calendar date;
	@ManyToOne
	@JoinColumn(name="shippingroutes")
	private ShippingRoute route;
	
    @OneToMany(cascade = {CascadeType.ALL}) 
    @LazyCollection(LazyCollectionOption.FALSE)	
	private List<DocumentRow> itemsRows;
    
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
	private String routesAccount;
	
	@Transient 
	private String itemsJson;
	
	public Integer getIdShippingSchedule() {
		return idShippingSchedule;
	}
	public void setIdShippingSchedule(Integer idShippingSchedule) {
		this.idShippingSchedule = idShippingSchedule;
	}
	public Calendar getDate() {
		return date;
	}
	public void setDate(Calendar date) {
		this.date = date;
	}
	public ShippingRoute getRoute() {
		return route;
	}
	public void setRoute(ShippingRoute route) {
		this.route = route;
	}
	public List<DocumentRow> getItemsRows() {
		return itemsRows;
	}
	public void setItemsRows(List<DocumentRow> itemsRows) {
		this.itemsRows = itemsRows;
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
	public String getRowsAsJson() {
		return rowsAsJson;
	}
	public void setRowsAsJson(String rowsAsJson) {
		this.rowsAsJson = rowsAsJson;
	}
	public String getRoutesAccount() {
		return routesAccount;
	}
	public void setRoutesAccount(String routesAccount) {
		this.routesAccount = routesAccount;
	}
	public String getItemsJson() {
		return itemsJson;
	}
	public void setItemsJson(String itemsJson) {
		this.itemsJson = itemsJson;
	}
}
