package com.industrika.inventory.dto;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="documentFolio")
public class DocumentFolio implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7801272480779252380L;
	@Id
	@GeneratedValue
	private Integer idFolio;
	private Integer documentType;
	private String documentName;
	private Integer folio;
	private String serial;
	public Integer getIdFolio() {
		return idFolio;
	}
	public void setIdFolio(Integer idFolio) {
		this.idFolio = idFolio;
	}
	public Integer getDocumentType() {
		return documentType;
	}
	public void setDocumentType(Integer documentType) {
		this.documentType = documentType;
	}
	public String getDocumentName() {
		return documentName;
	}
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	public Integer getFolio() {
		return folio;
	}
	public void setFolio(Integer folio) {
		this.folio = folio;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	
}
