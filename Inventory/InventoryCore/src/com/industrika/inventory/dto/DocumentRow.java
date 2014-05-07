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
@Table(name="documentrow")
public class DocumentRow implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7495624812124771821L;
	@Id
	@GeneratedValue
	private Integer idRow;
	private Double quantity;
	@ManyToOne
	@JoinColumn(name="item")	
	private Item item;
	@ManyToOne
	@JoinColumn(name="branch")	
	private Branch branch;
	@ManyToOne
	@JoinColumn(name="warehouse")	
	private Warehouse warehouse;
	private Double amount;
	private Double taxes;
	
	public Integer getIdRow() {
		return idRow;
	}
	public void setIdRow(Integer idRow) {
		this.idRow = idRow;
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
	public Branch getBranch() {
		return branch;
	}
	public void setBranch(Branch branch) {
		this.branch = branch;
	}
	public Warehouse getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getTaxes() {
		return taxes;
	}
	public void setTaxes(Double taxes) {
		this.taxes = taxes;
	}
}
