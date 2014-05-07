package com.industrika.inventory.dto;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.industrika.administration.dto.Tax;
import com.industrika.commons.dto.Trademark;

@Entity
@Table(name="item")
public class Item implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5089200304384893638L;
	@Id
	@GeneratedValue
	@SerializedName("idItem")
	@Expose
	private Integer idItem;
	private String code;
	@SerializedName("idItemName")
	@Expose
	private String name;
	private String description;
	
	@ManyToOne
	@JoinColumn(name="provider")		
	private Provider provider;
	
	@ManyToOne
	@JoinColumn(name="trademark")		
	private Trademark trademark;
	public Trademark getTrademark() {
		return trademark;
	}
	public void setTrademark(Trademark trademark) {
		this.trademark = trademark;
	}
	private Double price;
	private Double cost;
	@ManyToMany(cascade={CascadeType.REFRESH,CascadeType.PERSIST})
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Tax> taxes;
	
	public Integer getIdItem() {
		return idItem;
	}
	public void setIdItem(Integer idItem) {
		this.idItem = idItem;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Provider getProvider() {
		return provider;
	}
	public void setProvider(Provider provider) {
		this.provider = provider;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getCost() {
		return cost;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}
	public List<Tax> getTaxes() {
		return taxes;
	}
	public void setTaxes(List<Tax> taxes) {
		this.taxes = taxes;
	}
}
