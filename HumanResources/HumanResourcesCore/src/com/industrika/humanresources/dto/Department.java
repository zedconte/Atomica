package com.industrika.humanresources.dto;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity					
@Table(name="department")	
public class Department implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8114001349195489553L;
	@Id					
    @GeneratedValue		
	private Integer idDepartment;
	private String name;
	@ManyToOne
	@JoinColumn(name="management")	
	private Management management;
	
	public Integer getIdDepartment() {
		return idDepartment;
	}
	public void setIdDepartment(Integer idDepartment) {
		this.idDepartment = idDepartment;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Management getManagement() {
		return management;
	}
	public void setManagement(Management management) {
		this.management = management;
	}
}
