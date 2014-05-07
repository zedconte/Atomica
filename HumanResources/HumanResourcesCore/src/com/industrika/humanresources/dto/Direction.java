package com.industrika.humanresources.dto;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity					
@Table(name="direction")	
public class Direction implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3914988246991164150L;
	@Id					
    @GeneratedValue		
	private Integer idDirection;
	private String name;
	public Integer getIdDirection() {
		return idDirection;
	}
	public void setIdDirection(Integer idDirection) {
		this.idDirection = idDirection;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
