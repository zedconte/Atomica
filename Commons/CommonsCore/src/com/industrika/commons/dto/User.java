/**
 * 
 */
package com.industrika.commons.dto;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * @author jose.arellano
 */
@Entity
@Table(name="user")
public class User implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name="id")
	private Integer id;
	
	/*
	 * This attribute corresponds to the user's unique id, which he will use to log in the application
	 */
	@Column(name="code",length=20,unique=true)
	private String code;
	
	@Column(name="password")
	private String password;
	
	@Column(name="email",length=50)
	private String email;
	
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="last_transaction_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar lastTransactionDate;
	
	@ManyToMany(fetch=FetchType.LAZY, cascade={CascadeType.PERSIST,CascadeType.REMOVE,CascadeType.REFRESH})
	@JoinTable(name="user_role",
		joinColumns={@JoinColumn(name="user_id", nullable=false, updatable= false,referencedColumnName="id")},
		inverseJoinColumns={@JoinColumn(name="role_id", nullable=false, updatable=false,referencedColumnName="id")}	)
	private Set<Role> roles = new HashSet<Role>();
	
	/**
	 * 
	 */
	public User() {
	}
	
	public User(String code, String password, String email) {
		super();
		this.code = code;
		this.password = password;
		this.email = email;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Calendar getLastTransactionDate() {
		return lastTransactionDate;
	}

	public void setLastTransactionDate(Calendar lastTransactionDate) {
		this.lastTransactionDate = lastTransactionDate;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

}
