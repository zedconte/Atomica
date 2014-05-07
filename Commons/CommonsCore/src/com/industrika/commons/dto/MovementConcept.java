package com.industrika.commons.dto;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "movement_concept")
public class MovementConcept implements Serializable {

	@Transient
	private static final long serialVersionUID = -6947795117931877592L;

	@Id
	@GeneratedValue
	private Integer idMovementConcept;

	private String name;

	public Integer getIdMovementConcept() {
		return idMovementConcept;
	}

	public void setIdMovementConcept(Integer idMovementConcept) {
		this.idMovementConcept = idMovementConcept;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
