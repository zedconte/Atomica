package com.industrika.commons.dao.hibernate;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.industrika.commons.businesslogic.ApplicationContextProvider;
import com.industrika.commons.dao.CurrencyDao;
import com.industrika.commons.dao.PersonDao;
import com.industrika.commons.dto.Address;
import com.industrika.commons.dto.Currency;
import com.industrika.commons.dto.Person;
import com.industrika.commons.dto.Phone;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;

public class CurrencyDaoHibernateTest {
	@Before
	public void setUp() throws Exception {
		// Es bien importante cuando son pruebas unitarias que modifican la base de datos
		// eliminar todos los registros que usamos como pruebas
		CurrencyDao dao = ApplicationContextProvider.getCtx().getBean(CurrencyDao.class);
		List<Currency> list=new Vector<Currency>();
		try{
			Currency currency = new Currency();
			currency.setName("UNITTEST");
			list = dao.find(currency,null);
			if (list != null && list.size() > 0){
				for (Currency find: list){
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
		CurrencyDao dao = ApplicationContextProvider.getCtx().getBean(CurrencyDao.class);
		List<Currency> list=new Vector<Currency>();
		try{
			Currency currency = new Currency();
			currency.setName("UNITTEST");
			list = dao.find(currency,null);
			if (list != null && list.size() > 0){
				for (Currency find: list){
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
		CurrencyDao dao = ApplicationContextProvider.getCtx().getBean(CurrencyDao.class);
		Currency dto=new Currency();
		dto.setName("Dollar");
		dto.setShortName("Dll");
		dto.setSymbol("$");
		
		try{
			dto.setIdCurrency(dao.add(dto));
			assertTrue("El objeto tiene un id incorrecto",dto.getIdCurrency().intValue() > 0);
		}catch(Exception ex){
			ex.printStackTrace();
			fail(ex.getMessage());
		}
	}
	
	
}
