package com.industrika.productplanning.dto;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "product_lifecycle")
public class ProductLifeCycle implements Serializable {

	@Transient
	private static final long serialVersionUID = -6947795117931877592L;

	@Id
	@GeneratedValue
	private Integer idProductLifeCycle;

	private String name;
	
	public Integer getIdProductLifeCycle() {
		return idProductLifeCycle;
	}

	public void setIdProductLifeCycle(Integer idProductLifeCycle) {
		this.idProductLifeCycle = idProductLifeCycle;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
