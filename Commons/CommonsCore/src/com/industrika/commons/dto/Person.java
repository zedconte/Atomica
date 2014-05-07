package com.industrika.commons.dto;

import java.io.Serializable;
import java.util.Calendar;
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

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity					// La anotacion se agrega para decirle a hibernate que este objeto sera mapeado a una tabla
@Table(name="person")	// Podemos tambien especificar como se llamara la tabla, si no agregamos el nombre sera el mismo de la clase
public class Person implements Serializable {
	@Transient
	private static final long serialVersionUID = 6808186332359690023L;
	@Id					// Se especifica a hibernate que este atributo sera el campo llave en la tabla
    @GeneratedValue		// Asi mismo, le especificamos que deseamos que el numero sea autogenerado
    @SerializedName("idProvider")
	@Expose
	protected Integer idPerson;
	protected String firstName;
	protected String lastName;
	protected String middleName;
	protected Calendar birthday;
	protected String gender;
	protected String email;
	
	
    @OneToMany(mappedBy="person", cascade = {CascadeType.ALL})			// Aqui encontramos una relacion uno a muchos, por lo cual se define y se agrega que deseamos que el cascade sea completo, es decir, un telefono no debe existir sin una persona 
    @LazyCollection(LazyCollectionOption.FALSE)		// Especificamos que cada que consultemos a una persona deseamos que lea tambien sus telefono para poder consultarlos	
    protected List<Phone> phones;
    
    @OneToMany(mappedBy="person", cascade = {CascadeType.ALL})			// Aqui encontramos una relacion uno a muchos, por lo cual se define y se agrega que deseamos que el cascade sea completo, es decir, un domicilio no debe existir sin una persona 
    @LazyCollection(LazyCollectionOption.FALSE)		// Especificamos que cada que consultemos a una persona deseamos que lea tambien sus domicilios para poder consultarlos	
    protected List<Address> addresses;

    public Integer getIdPerson() {
		return idPerson;
	}
	public void setIdPerson(Integer idPerson) {
		this.idPerson = idPerson;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public Calendar getBirthday() {
		return birthday;
	}
	public void setBirthday(Calendar brithday) {
		this.birthday = brithday;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<Phone> getPhones() {
		return phones;
	}
	public void setPhones(List<Phone> phones) {
		if (phones != null && phones.size() > 0){
			for (Phone phone:phones){
				phone.setPerson(this);
			}
		}
		this.phones = phones;
	}
	public List<Address> getAddresses() {
		return addresses;
	}
	public void setAddresses(List<Address> addresses) {
		if (addresses != null && addresses.size() > 0){
			for (Address address:addresses){
				address.setPerson(this);
			}
		}
		this.addresses = addresses;
	}
}
