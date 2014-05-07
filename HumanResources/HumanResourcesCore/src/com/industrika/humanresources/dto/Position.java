package com.industrika.humanresources.dto;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity					
@Table(name="position")	
public class Position implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2493406706861825801L;
	@Id					
    @GeneratedValue		
	private Integer idPosition;
	private String name;
	public Integer getIdPosition() {
		return idPosition;
	}
	public void setIdPosition(Integer idPosition) {
		this.idPosition = idPosition;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
