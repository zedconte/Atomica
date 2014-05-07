package com.industrika.humanresources.dto;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity					
@Table(name="management")	
public class Management implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 79083799916444207L;
	@Id					
    @GeneratedValue		
	private Integer idManagement;
	private String name;
	@ManyToOne
	@JoinColumn(name="direction")	
	private Direction direction;
	
	public Integer getIdManagement() {
		return idManagement;
	}
	public void setIdManagement(Integer idManagement) {
		this.idManagement = idManagement;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Direction getDirection() {
		return direction;
	}
	public void setDirection(Direction direction) {
		this.direction = direction;
	}

}
