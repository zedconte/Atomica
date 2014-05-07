package com.industrika.humanresources.dto;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity					
@Table(name="absence")	
public class Absence implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3830181492298575536L;
	@Id					
    @GeneratedValue		
	private Integer idAbsence;
	private Calendar date;
	@ManyToOne
	@JoinColumn(name="employee")	
	private Employee employee;
	private Integer justified;
	private String reason;
	private Integer applyDiscount;
	public Integer getIdAbsence() {
		return idAbsence;
	}
	public void setIdAbsence(Integer idAbsence) {
		this.idAbsence = idAbsence;
	}
	public Calendar getDate() {
		return date;
	}
	public void setDate(Calendar date) {
		this.date = date;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public Integer getJustified() {
		return justified;
	}
	public void setJustified(Integer justified) {
		this.justified = justified;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Integer getApplyDiscount() {
		return applyDiscount;
	}
	public void setApplyDiscount(Integer applyDiscount) {
		this.applyDiscount = applyDiscount;
	}
	
}
