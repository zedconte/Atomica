package com.industrika.humanresources.dto;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.industrika.commons.dto.Person;

@Entity					
@Table(name="employee")	
public class Employee extends Person {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1467542155486661703L;
	private String rfc;
	private String nss;
	private Double salary;
	@ManyToOne
	@JoinColumn(name="department")		
	private Department department;
	@ManyToOne
	@JoinColumn(name="position")		
	private Position position;
	@ManyToOne
	@JoinColumn(name="shift")		
	private Shift shift;
	
	public String getRfc() {
		return rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	public String getNss() {
		return nss;
	}
	public void setNss(String nss) {
		this.nss = nss;
	}
	public Double getSalary() {
		return salary;
	}
	public void setSalary(Double salary) {
		this.salary = salary;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public Position getPosition() {
		return position;
	}
	public void setPosition(Position position) {
		this.position = position;
	}
	public Shift getShift() {
		return shift;
	}
	public void setShift(Shift shift) {
		this.shift = shift;
	}
	public String toString(){
		return (lastName != null ? lastName+" " : "")+(middleName != null ? middleName+" " : "")+(firstName != null ? firstName : "");
	}
}
