package com.industrika.inventory.dto;

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
@Table(name="document")
public class Document implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4505478129679407736L;
	@Id
	@GeneratedValue
	protected Integer idDocument;
	protected String folio;
	protected Calendar date;
	protected String[][] taxes;
	protected Double subtotal;
	protected Double discount;
	protected Double tax;
	protected Double total;
    @OneToMany(cascade = {CascadeType.ALL}) 
    @LazyCollection(LazyCollectionOption.FALSE)	
    protected List<DocumentRow> rows;
	public Integer getIdDocument() {
		return idDocument;
	}
	public void setIdDocument(Integer idDocument) {
		this.idDocument = idDocument;
	}
	public String getFolio() {
		return folio;
	}
	public void setFolio(String folio) {
		this.folio = folio;
	}
	public Calendar getDate() {
		return date;
	}
	public void setDate(Calendar date) {
		this.date = date;
	}
	public String[][] getTaxes() {
		return taxes;
	}
	public void setTaxes(String[][] taxes) {
		this.taxes = taxes;
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
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public List<DocumentRow> getRows() {
		return rows;
	}
	public void setRows(List<DocumentRow> rows) {
		this.rows = rows;
	}
	public Double getTax() {
		return tax;
	}
	public void setTax(Double tax) {
		this.tax = tax;
	}
}
