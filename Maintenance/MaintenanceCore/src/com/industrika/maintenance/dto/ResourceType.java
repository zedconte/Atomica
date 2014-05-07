package com.industrika.maintenance.dto;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity						
@Table(name="resource_type")
public class ResourceType extends BaseDto implements Serializable {

	/**
	 * 
	 */
	@Transient
	private static final long serialVersionUID = -6811467368738805150L;
	
	
	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public static long getSerialVersionuid() {
		return serialVersionUID;
	}

}
