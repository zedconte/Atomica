package com.industrika.commons.dto;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity						// La anotacion se agrega para decirle a hibernate que este objeto sera mapeado a una tabla
@Table(name="currency")	// Podemos tambien especificar como se llamara la tabla, si no agregamos el nombre sera el mismo de la clase
public class Currency implements Serializable {
	@Transient
	private static final long serialVersionUID = 6808186332359690023L;
	@Id					// Se especifica a hibernate que este atributo sera el campo llave en la tabla
    @GeneratedValue		// Asi mismo, le especificamos que deseamos que el numero sea autogenerado
    private Integer idCurrency;
	private String name;	
	private String shortName;
	private String symbol;
	
    public Integer getIdCurrency() {
		return idCurrency;
	}
	public void setIdCurrency(Integer idCurrency) {
		this.idCurrency = idCurrency;
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
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
}
