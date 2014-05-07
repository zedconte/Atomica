package com.industrika.commons.dto;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity					// La anotacion se agrega para decirle a hibernate que este objeto sera mapeado a una tabla
@Table(name="phones")	// Podemos tambien especificar como se llamara la tabla, si no agregamos el nombre sera el mismo de la clase
public class Phone implements Serializable {
	@Transient
	private static final long serialVersionUID = -2706753168947032971L;
	@Id					// Se especifica a hibernate que este atributo sera el campo llave en la tabla
    @GeneratedValue		// Asi mismo, le especificamos que deseamos que el numero sea autogenerado
	private Integer idPhone;
	private String type;
	private String areaCode;
	private String number;
	@ManyToOne
	@JoinColumn(name="person")	
	private Person person;

	public Integer getIdPhone() {
		return idPhone;
	}
	public void setIdPhone(Integer idPhone) {
		this.idPhone = idPhone;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
}
