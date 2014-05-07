package com.industrika.inventory.dto;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.industrika.commons.dto.Branch;
import com.industrika.commons.dto.MovementConcept;
import com.industrika.commons.dto.Warehouse;

@Entity
@Table(name="inventorymovement")
public class InventoryMovement implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6388530209713312375L;
	@Id
	@GeneratedValue	
	private Integer idMovement;
	private Calendar date;
	private Double quantity;
	@ManyToOne
	@JoinColumn(name="item")	
	private Item item;
	private String reference;
	private String serial;
	private Integer type;
	@ManyToOne
	@JoinColumn(name="warehouse")	
	private Warehouse warehouse; 
	@ManyToOne
	@JoinColumn(name="branch")	
	private Branch branch;
	@ManyToOne
	@JoinColumn(name="concept")	
	private MovementConcept concept;

	public Integer getIdMovement() {
		return idMovement;
	}
	public void setIdMovement(Integer idMovement) {
		this.idMovement = idMovement;
	}
	public Calendar getDate() {
		return date;
	}
	public void setDate(Calendar date) {
		this.date = date;
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
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
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
	public MovementConcept getConcept() {
		return concept;
	}
	public void setConcept(MovementConcept concept) {
		this.concept = concept;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}

}
