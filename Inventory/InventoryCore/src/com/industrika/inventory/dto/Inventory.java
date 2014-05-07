package com.industrika.inventory.dto;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.industrika.commons.dto.Branch;
import com.industrika.commons.dto.Warehouse;

@Entity
@Table(name="inventory")
public class Inventory implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 357002727850740840L;
	@Id
	@GeneratedValue
	private Integer idInventory;
	private Double quantity;
	@ManyToOne
	@JoinColumn(name="item")	
	private Item item;
	private Double cost;
	@ManyToOne
	@JoinColumn(name="warehouse")	
	private Warehouse warehouse; 
	@ManyToOne
	@JoinColumn(name="branch")	
	private Branch branch;

	public Integer getIdInventory() {
		return idInventory;
	}
	public void setIdInventory(Integer idInventory) {
		this.idInventory = idInventory;
	}
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public Double getCost() {
		return cost;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}
	public Warehouse getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}
	public Branch getBranch() {
		return branch;
	}
	public void setBranch(Branch branch) {
		this.branch = branch;
	}
}
