package com.industrika.commons.dto;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
@Table(name = "branch")
public class Branch implements Serializable {

	@Transient
	private static final long serialVersionUID = -7341490630451633193L;

	@Id
	@GeneratedValue
	@SerializedName("idIBranch")
	@Expose
	private Integer idBranch;

	@SerializedName("branchName")
	@Expose
	private String name;

	private String responsibleName;

	@ManyToMany(fetch=FetchType.LAZY, cascade={CascadeType.PERSIST,CascadeType.REMOVE,CascadeType.REFRESH})
	@JoinTable(name="branch_phone",
	joinColumns={@JoinColumn(name="idBranch", nullable=false, updatable= false,referencedColumnName="idBranch")},
	inverseJoinColumns={@JoinColumn(name="idPhone", nullable=false, updatable=false,referencedColumnName="idPhone")})
	private List<Phone> phones;

    @ManyToOne(cascade = {CascadeType.ALL})
    @LazyCollection(LazyCollectionOption.FALSE)	
	private Address address;

	@ManyToMany(fetch=FetchType.LAZY, cascade={CascadeType.PERSIST,CascadeType.REMOVE,CascadeType.REFRESH})
	@JoinTable(name="branch_warehouse",
	joinColumns={@JoinColumn(name="idBranch", nullable=false, updatable= false,referencedColumnName="idBranch")},
	inverseJoinColumns={@JoinColumn(name="idWarehouse", nullable=false, updatable=false,referencedColumnName="idWarehouse")})
	private List<Warehouse> warehouses;

	public Integer getIdBranch() {
		return idBranch;
	}

	public void setIdBranch(Integer idBranch) {
		this.idBranch = idBranch;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Phone> getPhones() {
		return phones;
	}

	public void setPhones(List<Phone> phones) {		
		this.phones = phones;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<Warehouse> getWarehouses() {
		return warehouses;
	}

	public void setWarehouses(List<Warehouse> warehouses) {		
		this.warehouses = warehouses;
	}

	public String getResponsibleName() {
		return responsibleName;
	}

	public void setResponsibleName(String responsibleName) {
		this.responsibleName = responsibleName;
	}

}
