package com.industrika.maintenance.dto;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1909665450753900822L;
	
	@Id					
    @GeneratedValue		
	private Integer id;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
}
