package com.industrika.commons.dto;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity					
@Table(name="country")	
public class Country implements Serializable {
	@Transient
	private static final long serialVersionUID = 6808186332359690026L;
	@Id					
    @GeneratedValue		
	private Integer idCountry;
	private String name;
	private String shortName;

	
    @OneToMany(mappedBy="country", cascade = {CascadeType.PERSIST,CascadeType.REFRESH})		 
    @LazyCollection(LazyCollectionOption.FALSE)		
	private List<State> states;


	public Integer getIdCountry() {
		return idCountry;
	}


	public void setIdCountry(Integer idCountry) {
		this.idCountry = idCountry;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getShortName() {
		return shortName;
	}


	public void setShortName(String shortName) {
		this.shortName = shortName;
	}


	public List<State> getStates() {
		return states;
	}


	public void setStates(List<State> states) {
		if (states != null && states.size() > 0){
			for (State state:states){
				state.setCountry(this);
			}
		}
		this.states = states;
	}
    


}
