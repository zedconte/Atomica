package com.industrika.productplanning.dto;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "life_cycle")
public class LifeCycle implements Serializable {
	@Transient
	private static final long serialVersionUID = -6947795117931877592L;

	@Id
	@GeneratedValue
	private Integer idLifeCycle;

	private String name;
	
	private String lifeCycleName;
	
	private Integer lifeOrder;

	public Integer getIdLifeCycle() {
		return idLifeCycle;
	}

	public void setIdLifeCycle(Integer idLifeCycle) {
		this.idLifeCycle = idLifeCycle;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLifeCycleName() {
		return lifeCycleName;
	}

	public void setLifeCycleName(String lifeCycleName) {
		this.lifeCycleName = lifeCycleName;
	}

	public Integer getLifeOrder() {
		return lifeOrder;
	}

	public void setLifeOrder(Integer lifeOrder) {
		this.lifeOrder = lifeOrder;
	}

	@Override
	public String toString() {
		return "LifeCycle [idLifeCycle=" + idLifeCycle + ", name=" + name
				+ ", lifeCycleName=" + lifeCycleName + ", lifeOrder=" + lifeOrder + "]";
	}
}
