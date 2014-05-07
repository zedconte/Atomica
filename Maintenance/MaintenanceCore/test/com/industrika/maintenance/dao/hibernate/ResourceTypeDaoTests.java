package com.industrika.maintenance.dao.hibernate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.testng.annotations.*;
import static org.mockito.Mockito.*;

import org.mockito.ArgumentCaptor;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.maintenance.dto.ResourceType;


public class ResourceTypeDaoTests extends HibernateBaseTest {
	
	
	private ResourceTypeDao dao;
	private ResourceType dto;
	private ResourceType result;
	
	private final Integer valid_id = 12;
	private final Integer invalid_id = 0;
	
	private final String valid_name ="name";
	private final String invalid_name = "";
	
	private final String[] sortFieldsSingle = {"abc"};
	private final String[] sortFieldsMultiple = {"abc", "def"};
	
	@Override
	@BeforeMethod
	public void setUp() throws Exception {
		super.setUp();
		dao = spy(new ResourceTypeDao());
		ReflectionUtils.setField(dao, "sessionFactory", sf);
		dto = new ResourceType();
		result = new ResourceType();
		when(criteria.add(any(Criterion.class))).thenReturn(criteria);
		when(criteria.list()).thenReturn(Arrays.asList(result));
		dto.setId(valid_id);
		dto.setName(valid_name);
		result.setId(valid_id);
		result.setName(valid_name);
	}

	@Test(expectedExceptions=IllegalArgumentException.class)
	public void find_ShouldThrowIllegalArgumentIfNull() throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException {
		dao.find(null, null);
	}
	
	@Test
	public void find_ShouldGetByIdIfIdIsNotZero() throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException {
		doReturn(result).when((BaseDao<ResourceType>)dao).findById(dto.getId());
		
		List<ResourceType> results = dao.find(dto, null);
		
		verify(dao, times(1)).findById(dto.getId());
		assertEquals(result,  results.get(0));
	}
	
	@Test
	public void find_ShouldGetByNameIfIdIsZero() throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException {
		dto.setId(invalid_id);
				
		dao.find(dto, sortFieldsMultiple);
		
		ArgumentCaptor<Criterion> criterion = ArgumentCaptor.forClass(Criterion.class);
		verify(criteria, times(1)).add(criterion.capture());
		assertEquals("name like %name%", criterion.getValue().toString());
	}
	
	@Test
	public void find_ShouldReturnCriteriaListIfHasData() throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException {
		dto.setId(invalid_id);
		List<ResourceType> expected = new ArrayList<ResourceType>(Arrays.asList(result));
				
		List<ResourceType> actual = dao.find(dto, sortFieldsMultiple);
		
		assertNotNull(actual);
		assertEquals(expected.size(), actual.size());
	}
	
	@Test
	public void find_ShouldSetOrderFieldsOnCriteriaIfPassed() throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException {
		dto.setId(invalid_id);
		
		dao.find(dto, sortFieldsMultiple);
		
		ArgumentCaptor<Order> order = ArgumentCaptor.forClass(Order.class); 
		verify(criteria, times(2)).addOrder(order.capture());
		List<Order> orderList = order.getAllValues();
		assertEquals(sortFieldsMultiple.length, orderList.size());
		
	}
	
	@Test(expectedExceptions=IndustrikaPersistenceException.class)
	public void find_ShouldThrowIndustrikaPersistenceExceptionIfCriteriaThrows() throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException {
		dto.setId(invalid_id);
		doThrow(new HibernateException("message")).when(criteria).list();
				
		dao.find(dto, sortFieldsMultiple);

	}
	
	@Test
	public void find_ShouldNotOrderResultsOnCriteriaIfNotOrderFieldsNull() throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException {
		List<ResourceType> expected = new ArrayList<ResourceType>();
		when(criteria.list()).thenReturn(expected);
		
		dao.find(dto, null);
		
		verify(criteria, never()).addOrder(any(Order.class));
	}
	

}
