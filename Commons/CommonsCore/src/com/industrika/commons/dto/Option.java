package com.industrika.commons.dto;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * @author jose.arellano
 */
@Entity
@Table(name="systemoption")
public class Option implements Serializable, Identifiable{
	@Transient
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(name="resource",nullable=false,length=50)
	private String resourceName;
	
	@Column(name="text",nullable=false,length=50)
	private String text;
	
	private int visibleInMenu;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Column(name="creation_date",nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar creationDate;
	
	@ManyToOne(cascade=CascadeType.REFRESH)
	@JoinColumn(name="id_module",referencedColumnName="id")
	private Module module;
	
	public Option() {
	}

	public Option(Integer id, String resourceName, Calendar creationDate,
			Module module) {
		super();
		this.id = id;
		this.resourceName = resourceName;
		this.creationDate = creationDate;
		this.module = module;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public Calendar getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Calendar creationDate) {
		this.creationDate = creationDate;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Option)) {
			return false;
		}
		Option other = (Option) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	@Transient
	@Override
	public String getDescription() {
		String desc = resourceName;
		if(getModule() != null && getModule().getName() != null){
			desc += "("+getModule().getName()+")";
		}
		return desc;
	}

	public int getVisible() {
		return visibleInMenu;
	}

	public void setVisible(int visible) {
		this.visibleInMenu = visible;
	}

}
