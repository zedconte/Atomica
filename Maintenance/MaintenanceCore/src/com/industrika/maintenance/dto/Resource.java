package com.industrika.maintenance.dto;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity						
@Table(name="resource")
public class Resource extends BaseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6842791111921272917L;
	
	private String name;
	
	@ManyToOne
	@JoinColumn(name="resourceTypeId")
	private ResourceType resourceType;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public static long getSerialVersionuid() {
		return serialVersionUID;
	}
	
	public void setResourceType(ResourceType type) {
		this.resourceType = type;
		
	}
	public ResourceType getResourceType() {
		
		return this.resourceType;
	}

	
}
