package com.industrika.commons.dao.hibernate;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.industrika.commons.businesslogic.ApplicationContextProvider;
import com.industrika.commons.dao.PersonDao;
import com.industrika.commons.dto.Address;
import com.industrika.commons.dto.Person;
import com.industrika.commons.dto.Phone;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;

public class PersonDaoHiberanteTest {
	
	@Before
	public void setUp() throws Exception {
		// Es bien importante cuando son pruebas unitarias que modifican la base de datos
		// eliminar todos los registros que usamos como pruebas
		PersonDao dao = ApplicationContextProvider.getCtx().getBean(PersonDao.class);
		List<Person> list=new Vector<Person>();
		try{
			Person person = new Person();
			person.setFirstName("UNITTEST");
			list = dao.find(person,null);
			if (list != null && list.size() > 0){
				for (Person find: list){
					dao.remove(find);
				}
			}
		}catch(IndustrikaObjectNotFoundException onf){
			onf.getMessage();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	@After
	public void tearDown() throws Exception {
		// Es bien importante cuando son pruebas unitarias que modifican la base de datos
		// eliminar todos los registros que usamos como pruebas
		PersonDao dao = ApplicationContextProvider.getCtx().getBean(PersonDao.class);
		List<Person> list=new Vector<Person>();
		try{
			Person person = new Person();
			person.setFirstName("UNITTEST");
			list = dao.find(person,null);
			if (list != null && list.size() > 0){
				for (Person find: list){
					dao.remove(find);
				}
			}
		}catch(IndustrikaObjectNotFoundException onf){
			onf.getMessage();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	@Test
	public void testAdd() {
		PersonDao dao = ApplicationContextProvider.getCtx().getBean(PersonDao.class);
		Person dto=new Person();
		dto.setFirstName("Juan-UNITTEST");
		dto.setLastName("Perez-UNITTEST");
		dto.setGender("M");
		Address ad=new Address();
		ad.setStreet("Calle 1");
		ad.setExtNumber("1");
		ad.setIntNumber("2");
		ad.setSuburb("La colonia");
		ad.setZipCode("12345");
		List<Address> ads=new Vector<Address>();
		ads.add(ad);
		Phone fix=new Phone();
		fix.setAreaCode("52");
		fix.setNumber("123654987");
		fix.setType("F");
		Phone cel=new Phone();
		cel.setAreaCode("52");
		cel.setNumber("789456123");
		cel.setType("M");
		List<Phone> phones=new Vector<Phone>();
		phones.add(fix);
		phones.add(cel);
		dto.setAddresses(ads);
		dto.setPhones(phones);
		try{
			dto.setIdPerson(dao.add(dto));
			assertTrue("El objeto tiene un id incorrecto",dto.getIdPerson().intValue() > 0);
		}catch(Exception ex){
			ex.printStackTrace();
			fail(ex.getMessage());
		}
	}

	@Test
	public void testUpdate() {
		PersonDao dao = ApplicationContextProvider.getCtx().getBean(PersonDao.class);
		Person dto=new Person();
		dto.setFirstName("Juan-UNITTEST-UPDATE");
		dto.setLastName("Perez-UNITTEST-UPDATE");
		dto.setGender("M");
		Address ad=new Address();
		ad.setStreet("Calle 2");
		ad.setExtNumber("1");
		ad.setIntNumber("2");
		ad.setSuburb("La colonia");
		ad.setZipCode("12345");
		List<Address> ads=new Vector<Address>();
		ads.add(ad);
		Phone fix=new Phone();
		fix.setAreaCode("52");
		fix.setNumber("74185293");
		fix.setType("F");
		Phone cel=new Phone();
		cel.setAreaCode("52");
		cel.setNumber("963852741");
		cel.setType("M");
		List<Phone> phones=new Vector<Phone>();
		phones.add(fix);
		phones.add(cel);
		dto.setAddresses(ads);
		dto.setPhones(phones);
		try{
			dto.setIdPerson(dao.add(dto));
			assertTrue("El objeto tiene un id incorrecto",dto.getIdPerson().intValue() > 0);
			dto.setMiddleName("Padron-TEST-UPDATE");
			dao.update(dto);
			Person find = dao.get(dto.getIdPerson());
			assertTrue("El objeto encontrado no es el mismo",find.getMiddleName().equalsIgnoreCase(dto.getMiddleName()));
			assertTrue("El telefono al parecer no es el mismo",find.getPhones().get(0).getNumber().equalsIgnoreCase("74185293") || find.getPhones().get(0).getNumber().equalsIgnoreCase("963852741"));
		}catch(Exception ex){
			ex.printStackTrace();
			fail(ex.getMessage());
		}
	}

	@Test
	public void testRemovePerson() {
		PersonDao dao = ApplicationContextProvider.getCtx().getBean(PersonDao.class);
		Person dto=new Person();
		dto.setFirstName("Juan-UNITTEST");
		dto.setLastName("Perez-UNITTEST");
		dto.setGender("M");
		Address ad=new Address();
		ad.setStreet("Calle 1");
		ad.setExtNumber("1");
		ad.setIntNumber("2");
		ad.setSuburb("La colonia");
		ad.setZipCode("12345");
		List<Address> ads=new Vector<Address>();
		ads.add(ad);
		Phone fix=new Phone();
		fix.setAreaCode("52");
		fix.setNumber("123654987");
		fix.setType("F");
		Phone cel=new Phone();
		cel.setAreaCode("52");
		cel.setNumber("789456123");
		cel.setType("M");
		List<Phone> phones=new Vector<Phone>();
		phones.add(fix);
		phones.add(cel);
		dto.setAddresses(ads);
		dto.setPhones(phones);
		try{
			dto.setIdPerson(dao.add(dto));
			assertTrue("El objeto tiene un id incorrecto",dto.getIdPerson().intValue() > 0);
			dao.remove(dto);
			Person temp = dao.get(dto.getIdPerson());
			if (temp != null){
				fail("Se encontro un objeto que se supone se habia borrado");
			}
		}catch(IndustrikaObjectNotFoundException onf){
			assertTrue("La excepcion en la busqueda no fue la correcta",onf != null);
		}catch(Exception ex){
			ex.printStackTrace();
			fail(ex.getMessage());
		}		
	}

	@Test
	public void testRemoveInteger() {
		PersonDao dao = ApplicationContextProvider.getCtx().getBean(PersonDao.class);
		Person dto=new Person();
		dto.setFirstName("Juan-UNITTEST");
		dto.setLastName("Perez-UNITTEST");
		dto.setGender("M");
		Address ad=new Address();
		ad.setStreet("Calle 1");
		ad.setExtNumber("1");
		ad.setIntNumber("2");
		ad.setSuburb("La colonia");
		ad.setZipCode("12345");
		List<Address> ads=new Vector<Address>();
		ads.add(ad);
		Phone fix=new Phone();
		fix.setAreaCode("52");
		fix.setNumber("123654987");
		fix.setType("F");
		Phone cel=new Phone();
		cel.setAreaCode("52");
		cel.setNumber("789456123");
		cel.setType("M");
		List<Phone> phones=new Vector<Phone>();
		phones.add(fix);
		phones.add(cel);
		dto.setAddresses(ads);
		dto.setPhones(phones);
		try{
			dto.setIdPerson(dao.add(dto));
			assertTrue("El objeto tiene un id incorrecto",dto.getIdPerson().intValue() > 0);
			dao.remove(dto.getIdPerson());
			Person temp = dao.get(dto.getIdPerson());
			if (temp != null){
				fail("Se encontro un objeto que se supone se habia borrado");
			}
		}catch(IndustrikaObjectNotFoundException onf){
			assertTrue("La excepcion en la busqueda no fue la correcta",onf != null);
		}catch(Exception ex){
			ex.printStackTrace();
			fail(ex.getMessage());
		}
	}

	@Test
	public void testGetPerson() {
		PersonDao dao = ApplicationContextProvider.getCtx().getBean(PersonDao.class);
		Person dto=new Person();
		dto.setFirstName("Juan-UNITTEST");
		dto.setLastName("Perez-UNITTEST");
		dto.setGender("M");
		Address ad=new Address();
		ad.setStreet("Calle 1");
		ad.setExtNumber("1");
		ad.setIntNumber("2");
		ad.setSuburb("La colonia");
		ad.setZipCode("12345");
		List<Address> ads=new Vector<Address>();
		ads.add(ad);
		Phone fix=new Phone();
		fix.setAreaCode("52");
		fix.setNumber("123654987");
		fix.setType("F");
		Phone cel=new Phone();
		cel.setAreaCode("52");
		cel.setNumber("789456123");
		cel.setType("M");
		List<Phone> phones=new Vector<Phone>();
		phones.add(fix);
		phones.add(cel);
		dto.setAddresses(ads);
		dto.setPhones(phones);
		try{
			dto.setIdPerson(dao.add(dto));
			assertTrue("El objeto tiene un id incorrecto",dto.getIdPerson().intValue() > 0);
			Person temp = dao.get(dto);
			assertTrue("El objeto encontrao no es el mismo",temp.getIdPerson().intValue()==dto.getIdPerson().intValue());
		}catch(IndustrikaObjectNotFoundException onf){
			assertTrue("No se encontro el objeto que se deseaba buscar",onf != null);
		}catch(Exception ex){
			ex.printStackTrace();
			fail(ex.getMessage());
		}
	}

	@Test
	public void testGetInteger() {
		PersonDao dao = ApplicationContextProvider.getCtx().getBean(PersonDao.class);
		Person dto=new Person();
		dto.setFirstName("Juan-UNITTEST");
		dto.setLastName("Perez-UNITTEST");
		dto.setGender("M");
		Address ad=new Address();
		ad.setStreet("Calle 1");
		ad.setExtNumber("1");
		ad.setIntNumber("2");
		ad.setSuburb("La colonia");
		ad.setZipCode("12345");
		List<Address> ads=new Vector<Address>();
		ads.add(ad);
		Phone fix=new Phone();
		fix.setAreaCode("52");
		fix.setNumber("123654987");
		fix.setType("F");
		Phone cel=new Phone();
		cel.setAreaCode("52");
		cel.setNumber("789456123");
		cel.setType("M");
		List<Phone> phones=new Vector<Phone>();
		phones.add(fix);
		phones.add(cel);
		dto.setAddresses(ads);
		dto.setPhones(phones);
		try{
			dto.setIdPerson(dao.add(dto));
			assertTrue("El objeto tiene un id incorrecto",dto.getIdPerson().intValue() > 0);
			Person temp = dao.get(dto.getIdPerson());
			assertTrue("El objeto encontrao no es el mismo",temp.getIdPerson().intValue()==dto.getIdPerson().intValue());
		}catch(IndustrikaObjectNotFoundException onf){
			assertTrue("No se encontro el objeto que se deseaba buscar",onf != null);
		}catch(Exception ex){
			ex.printStackTrace();
			fail(ex.getMessage());
		}
	}

	@Test
	public void testFind() {
		PersonDao dao = ApplicationContextProvider.getCtx().getBean(PersonDao.class);
		for (int a = 0;a < 10 ;a++){
			Person dto=new Person();
			dto.setFirstName("Juan-UNITTEST-"+a);
			dto.setLastName("Perez-UNITTEST");
			dto.setGender("M");
			Address ad=new Address();
			ad.setStreet("Calle 1");
			ad.setExtNumber("1");
			ad.setIntNumber("2");
			ad.setSuburb("La colonia");
			ad.setZipCode("12345");
			List<Address> ads=new Vector<Address>();
			ads.add(ad);
			Phone fix=new Phone();
			fix.setAreaCode("52");
			fix.setNumber("123654987");
			fix.setType("F");
			Phone cel=new Phone();
			cel.setAreaCode("52");
			cel.setNumber("789456123");
			cel.setType("M");
			List<Phone> phones=new Vector<Phone>();
			phones.add(fix);
			phones.add(cel);
			dto.setAddresses(ads);
			dto.setPhones(phones);
			try{
				dto.setIdPerson(dao.add(dto));
				assertTrue("El objeto tiene un id incorrecto",dto.getIdPerson().intValue() > 0);
			}catch(Exception ex){
				ex.printStackTrace();
				fail(ex.getMessage());
			}
		}
		Person dto=new Person();
		dto.setFirstName("Pedro-UNITTEST");
		dto.setLastName("Perez-UNITTEST");
		dto.setGender("M");
		Address ad=new Address();
		ad.setStreet("Calle 1");
		ad.setExtNumber("1");
		ad.setIntNumber("2");
		ad.setSuburb("La colonia");
		ad.setZipCode("12345");
		List<Address> ads=new Vector<Address>();
		ads.add(ad);
		Phone fix=new Phone();
		fix.setAreaCode("52");
		fix.setNumber("123654987");
		fix.setType("F");
		Phone cel=new Phone();
		cel.setAreaCode("52");
		cel.setNumber("789456123");
		cel.setType("M");
		List<Phone> phones=new Vector<Phone>();
		phones.add(fix);
		phones.add(cel);
		dto.setAddresses(ads);
		dto.setPhones(phones);
		try{
			dto.setIdPerson(dao.add(dto));
			assertTrue("El objeto tiene un id incorrecto",dto.getIdPerson().intValue() > 0);
		}catch(Exception ex){
			ex.printStackTrace();
			fail(ex.getMessage());
		}

		List<Person> list=new Vector<Person>();
		try{
			Person person = new Person();
			person.setFirstName("Juan-UNITTEST");
			list = dao.find(person,null);
			if (list != null){
				assertTrue("La lista encontrada tiene mas elementos, se esperaban 10 y se encontraron: "+list.size(),list.size() == 10);
			} else {
				fail("La lista esta en nulo");
			}
		}catch(IndustrikaObjectNotFoundException onf){
			fail("No se encontraron objetos en la consulta");
		}catch(Exception ex){
			ex.printStackTrace();
			fail("No se encontraron objetos en la consulta");
		}

	}

}
