package com.industrika.administration.dto;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity						
@Table(name="deductions")
public class Deduction implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 825515699774949130L;
	@Id					
    @GeneratedValue
	private Integer idDeduction;
	private String name;
	private String initials;
	private Double value;
	public Integer getIdDeduction() {
		return idDeduction;
	}
	public void setIdDeduction(Integer idDeduction) {
		this.idDeduction = idDeduction;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInitials() {
		return initials;
	}
	public void setInitials(String initials) {
		this.initials = initials;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}

}
