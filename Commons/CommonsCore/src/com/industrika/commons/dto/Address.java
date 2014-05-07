package com.industrika.commons.dto;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity						// La anotacion se agrega para decirle a hibernate que este objeto sera mapeado a una tabla
@Table(name="addresses")	// Podemos tambien especificar como se llamara la tabla, si no agregamos el nombre sera el mismo de la clase
public class Address implements Serializable {
	@Transient
	private static final long serialVersionUID = -5473849964262931864L;
	@Id					// Se especifica a hibernate que este atributo sera el campo llave en la tabla
    @GeneratedValue		// Asi mismo, le especificamos que deseamos que el numero sea autogenerado
	private Integer idAddress;
	private String street;
	private String intNumber;
	private String extNumber;
	private String suburb;
	private String zipCode;
	@ManyToOne
	@JoinColumn(name="person")	
	private Person person;
	@ManyToOne
	@JoinColumn(name="city")		
	private City city;
		
	public Integer getIdAddress() {
		return idAddress;
	}
	public void setIdAddress(Integer idAddress) {
		this.idAddress = idAddress;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getIntNumber() {
		return intNumber;
	}
	public void setIntNumber(String intNumber) {
		this.intNumber = intNumber;
	}
	public String getExtNumber() {
		return extNumber;
	}
	public void setExtNumber(String extNumber) {
		this.extNumber = extNumber;
	}
	public String getSuburb() {
		return suburb;
	}
	public void setSuburb(String suburb) {
		this.suburb = suburb;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public City getCity() {
		return city;
	}
	public void setCity(City city) {
		this.city = city;
	}
	
}
