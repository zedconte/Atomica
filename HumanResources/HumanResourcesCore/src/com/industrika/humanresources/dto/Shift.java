package com.industrika.humanresources.dto;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity					
@Table(name="shift")	
public class Shift implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3689094067030754125L;
	@Id					
    @GeneratedValue		
	private Integer idShift;
	private String name;
	public Integer getIdShift() {
		return idShift;
	}
	public void setIdShift(Integer idShift) {
		this.idShift = idShift;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
