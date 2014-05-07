package com.industrika.humanresources.dto;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity					
@Table(name="payrolldetail")	
public class PayrollDetail implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -938299862292437010L;
	@Id					
    @GeneratedValue		
	private Integer idDetail;
	@ManyToOne
	@JoinColumn(name="employee")	
	private Employee employee;
	private Double salary;
	private Double discount;
	private Double deductions;
	private Double total;
	public Integer getIdDetail() {
		return idDetail;
	}
	public void setIdDetail(Integer idDetail) {
		this.idDetail = idDetail;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public Double getSalary() {
		return salary;
	}
	public void setSalary(Double salary) {
		this.salary = salary;
	}
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	public Double getDeductions() {
		return deductions;
	}
	public void setDeductions(Double deductions) {
		this.deductions = deductions;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	
}
