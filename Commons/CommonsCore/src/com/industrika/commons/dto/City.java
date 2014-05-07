package com.industrika.commons.dto;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity					
@Table(name="city")	
public class City implements Serializable{
	@Transient
	private static final long serialVersionUID = -3151709785161350537L;
	@Id					
    @GeneratedValue	
	private Integer idCity;
	private String name;
	private String shortName;
	@ManyToOne
	@JoinColumn(name="state")	
	private State state;
	
	
	public Integer getIdCity() {
		return idCity;
	}
	
	public void setIdCity(Integer idCity) {
		this.idCity = idCity;
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
	
	public State getState() {
		return state;
	}
	
	public void setState(State state) {
		this.state = state;
	}
	

}
