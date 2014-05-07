package com.industrika.shipping.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


@Entity						
@Table(name="shippingroutes")
public class ShippingRoute implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -628832600493650277L;

	@Id					
    @GeneratedValue
    @SerializedName("idRoute")
	@Expose
	private Integer idRoute;
	@SerializedName("routeName")
	@Expose
	private String name;
	private String acronym;
	private Double distance;
	private Double time;
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getIdRoute() {
		return idRoute;
	}

	public void setIdRoute(Integer idRoute) {
		this.idRoute = idRoute;
	}

	public String getAcronym() {
		return acronym;
	}

	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public Double getTime() {
		return time;
	}

	public void setTime(Double time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "ShippingRoute [idRoute=" + idRoute + ", name=" + name
				+ ", acronym=" + acronym + ", distance=" + distance + ", time="
				+ time + "]";
	}
}
