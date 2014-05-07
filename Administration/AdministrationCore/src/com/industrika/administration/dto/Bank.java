package com.industrika.administration.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity						
@Table(name="banks")
public class Bank implements Serializable {
	@Transient
	private static final long serialVersionUID = -5473849964262931864L;
	@Id					
    @GeneratedValue
    @SerializedName("bankId")
	@Expose
	private Integer idBank;
	
	@SerializedName("bankName")
	@Expose
	private String name;
	private String acronym;
	
	@OneToMany(mappedBy="bank",cascade={CascadeType.ALL})
	@LazyCollection(LazyCollectionOption.FALSE)
	private Set<BankOperation> operations= new HashSet<BankOperation>();
	
	public Integer getIdBank() {
		return idBank;
	}
	public void setIdBank(Integer idBank) {
		this.idBank = idBank;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAcronym() {
		return acronym;
	}
	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public String toString() {
		return "Bank [idBank=" + idBank + ", name=" + name + ", acronym="
				+ acronym + "]";
	}
	public Set<BankOperation> getOperations() {
		return operations;
	}
	public void setOperations(Set<BankOperation> operations) {
		this.operations = operations;
	}
}
