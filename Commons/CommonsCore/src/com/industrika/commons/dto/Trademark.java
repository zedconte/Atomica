package com.industrika.commons.dto;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "trademark")
public class Trademark implements Serializable {

	@Transient
	private static final long serialVersionUID = -8092603879759276853L;

	@Id
	@GeneratedValue
	private Integer idTrademark;
	
	private String name;
	
	public Integer getIdTrademark() {
		return idTrademark;
	}

	public void setIdTrademark(Integer idTrademark) {
		this.idTrademark = idTrademark;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
