package com.industrika.commons.dto;

import java.io.Serializable;
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


@Entity					
@Table(name="state")	
public class State implements Serializable{
	@Transient
	private static final long serialVersionUID = -55048638025069511L;
	@Id					
    @GeneratedValue	
	private Integer idState;
	private String name;
	private String shortName;
	@ManyToOne
	@JoinColumn(name="country")	
	private Country country;
	
    @OneToMany(mappedBy="state", cascade = {CascadeType.PERSIST,CascadeType.REFRESH})			 
    @LazyCollection(LazyCollectionOption.FALSE)			
	private List<City> cities;
	
	public Integer getIdState() {
		return idState;
	}
	public void setIdState(Integer idState) {
		this.idState = idState;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getShortName() {
		return shortName;
	}
	
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
	public Country getCountry() {
		return country;
	}
	
	public void setCountry(Country country) {
		this.country = country;
	}
	
	
	public List<City> getCities() {
		return cities;
	}


	public void setCities(List<City> cities) {
		if (cities != null && cities.size() > 0){
			for (City city:cities){
				city.setState(this);
			}
		}
		this.cities = cities;
	}
	
}
