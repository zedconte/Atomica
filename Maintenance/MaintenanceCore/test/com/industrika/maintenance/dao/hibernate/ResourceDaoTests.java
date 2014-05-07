package com.industrika.maintenance.dao.hibernate;

import org.testng.annotations.*;

import static org.mockito.Mockito.*;

import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.maintenance.dao.IResourceDao;
import com.industrika.maintenance.dto.Resource;
import com.industrika.maintenance.dto.ResourceType;
import com.industrika.maintenance.validation.predefined.ResourceValidator;

public class ResourceDaoTests extends HibernateBaseTest {
		private ResourceDao dao;
		private ResourceValidator validator;
		private Resource dto;
		private final Integer id = 10;
		private final String name = "name";
		private final ResourceType type = new ResourceType();
		
		
		@BeforeMethod
		public void setUp() throws Exception{
			super.setUp();
			dao = spy(new ResourceDao());
			validator = spy(new ResourceValidator());
			dto = new Resource();
		}
		
		@Test
		public void ShouldBeInstanceOfBaseDao(){
			assertTrue(dao instanceof BaseDao);
		}
		
		@Test
		public void ShouldImplementIResourceDao(){
			assertTrue(dao instanceof IResourceDao);
		}
		
		@Test(expectedExceptions=IllegalArgumentException.class)
		public void setValidator_ShouldThrowIllegalArgumentIfValidatorIsNull(){
			
			dao.setValidator(null);
		}
		@Test
		public void setValidator_ShouldsetIniternalValidator() throws IndustrikaValidationException, IndustrikaPersistenceException{
			dao.setValidator(validator);
			
			dao.save(dto);
			
			verify(validator, times(1)).validate(dto);
		}
		 
}
