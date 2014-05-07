package com.industrika.commons.dto;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
@Table(name = "warehouse")
public class Warehouse implements Serializable {

	@Transient
	private static final long serialVersionUID = 4323256310871015167L;

	@Id
	@GeneratedValue
	@SerializedName("idWarehouse")
	@Expose
	private Integer idWarehouse;

	@SerializedName("warehouseName")
	@Expose
	private String name;

	@ManyToOne
	@JoinColumn(name = "branch")
	private Branch branch;

	public Integer getIdWarehouse() {
		return idWarehouse;
	}

	public void setIdWarehouse(Integer idWarehouse) {
		this.idWarehouse = idWarehouse;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}
}
