package com.industrika.humanresources.dto;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity					
@Table(name="payroll")	
public class Payroll implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 116572306094217686L;
	@Id					
    @GeneratedValue		
	private Integer idPayroll;
	private Calendar begin;
	private Calendar end;
    @OneToMany(cascade = {CascadeType.ALL}) 
    @LazyCollection(LazyCollectionOption.FALSE)		
	private List<PayrollDetail> detail;
    private Double subtotal;
    private Double discount;
    private Double deductions;
    private Double total;
    private Integer days;
	public Integer getIdPayroll() {
		return idPayroll;
	}
	public void setIdPayroll(Integer idPayroll) {
		this.idPayroll = idPayroll;
	}
	public Calendar getBegin() {
		return begin;
	}
	public void setBegin(Calendar begin) {
		this.begin = begin;
	}
	public Calendar getEnd() {
		return end;
	}
	public void setEnd(Calendar end) {
		this.end = end;
	}
	public List<PayrollDetail> getDetail() {
		return detail;
	}
	public void setDetail(List<PayrollDetail> detail) {
		this.detail = detail;
	}
	public Double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	public Double getDecutions() {
		return deductions;
	}
	public void setDecutions(Double deductions) {
		this.deductions = deductions;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public Integer getDays() {
		return days;
	}
	public void setDays(Integer days) {
		this.days = days;
	}
}
