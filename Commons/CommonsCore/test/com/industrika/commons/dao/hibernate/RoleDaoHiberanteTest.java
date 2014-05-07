package com.industrika.commons.dao.hibernate;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.industrika.commons.dao.RoleDao;
import com.industrika.commons.dto.Role;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(/*classes={ApplicationContextProvider.class}*/{"/commons_context.xml"})
public class RoleDaoHiberanteTest {

	private static final String MOCK_ROLE_NAME = "Mock role";
	private static final String MOCK_ROLE_INITIALS = "MR";
	
	private RoleDao roleDao;

	@Before
	public void setUp() throws Exception {
		// Es bien importante cuando son pruebas unitarias que modifican la base de datos
		// eliminar todos los registros que usamos como pruebas
		try{
			Role role = new Role();
			role.setName(MOCK_ROLE_NAME);
			
			removeRecords(
					roleDao.find(role,null));
			
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
		
		try{
			Role role = new Role();
			role.setName(MOCK_ROLE_NAME);
			
			removeRecords(
					roleDao.find(role,null));
		}catch(IndustrikaObjectNotFoundException onf){
			onf.getMessage();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void removeRecords(List<Role> list) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException{
		if (list != null && !list.isEmpty()){
			for (Role role: list){
				roleDao.remove(role);
			}
		}
	}
	
	public static Role getMockRole(){
		Role dto=new Role();
		dto.setName(MOCK_ROLE_NAME);
		dto.setInitials(MOCK_ROLE_INITIALS);
		
		return dto;
	}

	@Test
	public void testAdd() {
		Role dto = getMockRole();
		
		try{
			dto = roleDao.save(dto);
			assertTrue("Incorrect id",dto.getId().intValue() > 0);
		}catch(Exception ex){
			ex.printStackTrace();
			fail(ex.getMessage());
		}
	}

	@Test
	public void testUpdate() {
		Role dto= getMockRole();
		
		try{
			dto = roleDao.save(dto);
			assertTrue("Incorrect id",dto.getId().intValue() > 0);
			
			dto.setInitials("MMR");
			roleDao.update(dto);
			Role updated = roleDao.findById(dto.getId());
			assertTrue("Names mismatch",updated.getName().equals(dto.getName()));
			assertTrue("Initials mismatch",updated.getInitials().equals(dto.getInitials()));
		}catch(Exception ex){
			ex.printStackTrace();
			fail(ex.getMessage());
		}
	}

	@Test
	public void testRemoveRole() {
		Role dto= getMockRole();
		
		try{
			dto = roleDao.save(dto);
			
			assertTrue("Incorrect id",dto.getId().intValue() > 0);
			roleDao.remove(dto);
			Role temp = roleDao.findById(dto.getId());
			if (temp != null){
				fail("Object found, after it was removed");
			}
		}catch(IndustrikaObjectNotFoundException onf){
			assertTrue("Object found, after it was removed",onf != null);
		}catch(Exception ex){
			ex.printStackTrace();
			fail(ex.getMessage());
		}		
	}

	@Test
	public void testRemoveInteger() {
		Role dto = getMockRole();
		
		try{
			dto = roleDao.save(dto);
			assertTrue("Incorrect id",dto.getId().intValue() > 0);
			roleDao.remove(dto);
			Role temp = roleDao.findById(dto.getId());
			if (temp != null){
				fail("Object found after it was removed");
			}
		}catch(IndustrikaObjectNotFoundException onf){
			assertTrue("Exception was incorrect (null)",onf != null);
		}catch(Exception ex){
			ex.printStackTrace();
			fail(ex.getMessage());
		}
	}

	@Test
	public void testGetRole() {
		Role dto = getMockRole();
		
		try{
			dto = roleDao.save(dto);
			assertTrue("Incorrect id",dto.getId().intValue() > 0);
			Role temp = roleDao.findById(dto.getId());
			assertTrue("Object found is not the same",temp.getId().intValue()==dto.getId().intValue());
		}catch(IndustrikaObjectNotFoundException onf){
			assertTrue("Object not found",onf != null);
		}catch(Exception ex){
			ex.printStackTrace();
			fail(ex.getMessage());
		}
	}

	@Test
	public void testGetInteger() {
		Role dto = getMockRole();
		
		try{
			dto = roleDao.save(dto);
			assertTrue("Incorrect id",dto.getId().intValue() > 0);
			Role temp = roleDao.findById(dto.getId());
			assertTrue("Object found is not the same",temp.getId().intValue()==dto.getId().intValue());
		}catch(IndustrikaObjectNotFoundException onf){
			assertTrue("Object not found",onf != null);
		}catch(Exception ex){
			ex.printStackTrace();
			fail(ex.getMessage());
		}
	}

	@Test
	public void testFind() {
		Role dto = getMockRole();
		for (int i = 0;i < 10 ;i++){
			
			try{
				dto = roleDao.save(dto);
				assertTrue("Incorrect id",dto.getId().intValue() > 0);
				
				dto.setId(null);
			}catch(Exception ex){
				ex.printStackTrace();
				fail(ex.getMessage());
			}
		}
		
		List<Role> list=new ArrayList<Role>();
		try{
			Role role = new Role();
			role.setName(MOCK_ROLE_NAME);
			list = roleDao.find(role,null);
			if (list != null){
				assertTrue("Incorrect amount of objects found, expected 10, found: "+list.size(),list.size() == 10);
			} else {
				fail("La lista esta en nulo");
			}
			role.setName(null);
			role.setInitials(MOCK_ROLE_INITIALS);
			list = roleDao.find(role,null);
			if (list != null){
				assertTrue("Incorrect amount of objects found, expected 10, found: "+list.size(),list.size() == 10);
			} else {
				fail("La lista esta en nulo");
			}
			
		}catch(IndustrikaObjectNotFoundException onf){
			fail("Objects not found, excepted to find 10 records");
		}catch(Exception ex){
			ex.printStackTrace();
			fail("Exception occurred while searching roles.");
		}

	}

	public RoleDao getRoleDao() {
		return roleDao;
	}

	@Autowired
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}
}
