/**
 * 
 */
package com.industrika.commons.dto;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author jose.arellano
 */
@Entity
@Table(name="privilege")
public class Privilege implements Serializable {
	@Transient
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(name="name",length=500)
	private String name;
	
	@ManyToOne(cascade={CascadeType.REFRESH},fetch=FetchType.EAGER)
	@JoinColumn(name="id_action",referencedColumnName="id")
	private Action action;
	
	@ManyToOne(cascade={CascadeType.REFRESH},fetch=FetchType.EAGER)
	@JoinColumn(name="id_option",referencedColumnName="id")
	private Option option;
	
	public Privilege() {
	}

	public Privilege(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public Privilege(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Transient
	public String getActionType(){
		String result = null;
		if(getAction() != null)
			result = getAction().getType();
		return result;
	}
	
	@Transient
	public String getResourceName(){
		String opt = null;
		if(getOption() != null)
			opt = getOption().getResourceName();
		return opt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public Option getOption() {
		return option;
	}

	public void setOption(Option option) {
		this.option = option;
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
		if (!(obj instanceof Privilege)) {
			return false;
		}
		Privilege other = (Privilege) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}
	
}
