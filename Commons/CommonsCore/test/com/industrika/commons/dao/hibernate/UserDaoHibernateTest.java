package com.industrika.commons.dao.hibernate;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.industrika.commons.dao.RoleDao;
import com.industrika.commons.dao.UserDao;
import com.industrika.commons.dto.Role;
import com.industrika.commons.dto.User;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/commons_context.xml"})
public class UserDaoHibernateTest {

	private static final String MOCK_USER_CODE = "testuser";
	private static final String MOCK_USER_EMAIL = "testemail@company.com";
	private static final String MOCK_USER_PASSWORD = "password123";
	private static final Calendar MOCK_LT_DATE= Calendar.getInstance();
	
	private static final Logger LOGGER = Logger.getLogger(UserDaoHibernateTest.class); 
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RoleDao roleDao;
	
	public UserDaoHibernateTest() {

	}

	@Before
	public void setUp() throws Exception {
		removeMockRecords();
	}

	@After
	public void tearDown() throws Exception {
		removeMockRecords();
	}
	
	@Test
	public void testSave() throws Exception{
		LOGGER.info("---------------------------------------------");
		LOGGER.info("Testing save method in UserDaoHibernate....");
		
		User user = getMockUser();
		user=(userDao.save(user));
		Assert.assertTrue("Incorrect id", user.getId() > 0);
		
		LOGGER.info("Finished Testing save method in UserDaoHibernate....");
		LOGGER.info("---------------------------------------------");
	}
	
	@Test
	public void testFind() throws Exception{
		LOGGER.info("---------------------------------------------");
		LOGGER.info("Testing find method in UserDaoHibernate....");
		User user = getMockUser();
		user=(userDao.save(user));
		
		User user2 = userDao.findById(user.getId());
		Assert.assertNotNull(user2);
		Assert.assertEquals(user.getCode(), user2.getCode());
		Assert.assertEquals(user.getEmail(), user2.getEmail());
		Assert.assertEquals(user.getPassword(), user2.getPassword());
		
		LOGGER.info("Finished Testing find method in UserDaoHibernate....");
		LOGGER.info("---------------------------------------------");
	}
	
	@Test
	public void testUpdate() throws Exception{
		LOGGER.info("---------------------------------------------");
		LOGGER.info("Testing update method in UserDaoHibernate....");
		
		User user = getMockUser();
		user=(userDao.save(user));
		
		User user2 = userDao.findById(user.getId());
		
		user2.setCode(MOCK_USER_CODE+"M");
		user2.setEmail(MOCK_USER_EMAIL+".mx");
		user2.setLastTransactionDate(Calendar.getInstance());
		user2.setPassword(MOCK_USER_PASSWORD+"M");
		
		userDao.update(user2);
		
		User user3 = userDao.findById(user.getId());
		//compare user3 and user, they must mismatch
		Assert.assertTrue("Attributes should have different values",
				!user.getCode().equals(user3.getCode()));
		Assert.assertTrue("Attributes should have different values",
				!user.getEmail().equals(user3.getEmail()));
		Assert.assertTrue("Attributes should have different values",
				user.getLastTransactionDate().compareTo(user3.getLastTransactionDate()) != 0);
		Assert.assertTrue("Attributes should have different values",
				!user.getPassword().equals(user3.getPassword()));
		//id has to be the same
		Assert.assertEquals(user.getId(),user3.getId());
		
		LOGGER.info("Finished Testing update method in UserDaoHibernate....");
		LOGGER.info("---------------------------------------------");
	}
	
	@Test
	public void testRemove() throws Exception{
		LOGGER.info("---------------------------------------------");
		LOGGER.info("Testing remove method in UserDaoHibernate....");
		
		User user = getMockUser();
		user=(userDao.save(user));
		
		User user2 = userDao.findById(user.getId());
		userDao.remove(user2);
		
		try{
			user2 = userDao.findById(user.getId());
		}
		catch(Exception e){
			//Error occurred because the object is not found, correct
			user2 = null;
		}
		Assert.assertNull("Object found, supposed to be removed",user2);
		
		LOGGER.info("Finished Testing remove method in UserDaoHibernate....");
		LOGGER.info("---------------------------------------------");
	}
	
	@Test
	public void testSetRoles() throws Exception{
		LOGGER.info("---------------------------------------------");
		LOGGER.info("Testing setRoles method in UserDaoHibernate....");
		
		RoleDaoHiberanteTest roleTest = new RoleDaoHiberanteTest();
		roleTest.setRoleDao(roleDao);
		
		try{
			//creation of mock roles
			
			Role role1 = RoleDaoHiberanteTest.getMockRole();
			Role role2 = RoleDaoHiberanteTest.getMockRole();
			Role role3 = RoleDaoHiberanteTest.getMockRole();
			
			role2.setName(role2.getName()+"2");
			role2.setInitials(role2.getInitials()+"2");
			
			role3.setName(role3.getName()+"3");
			role3.setInitials(role3.getInitials()+"3");
			
			role1=(roleDao.save(role1));
			role2=(roleDao.save(role2));
			role3=(roleDao.save(role3));
			
			//creation of mock user
			User user = getMockUser();
			user=(userDao.save(user));
			
			//adding the new roles
			Set<Role> setRoles = new HashSet<Role>();
			setRoles.add(role1);
			setRoles.add(role2);
			setRoles.add(role3);
			
			user = userDao.setRoles(user, setRoles);
			
			//compare roles set size
			Assert.assertTrue("List of roles is not the same, it is supposed to be the same",setRoles.size() == user.getRoles().size());
			Assert.assertTrue("List of roles does not contain the same objects",setRoles.containsAll(user.getRoles()));
			
			setRoles.clear();
			user = userDao.setRoles(user, setRoles);
			
			Assert.assertTrue("List of roles should be empty",user.getRoles().isEmpty());
			
			setRoles = new HashSet<Role>();
			setRoles.add(role1);
			setRoles.add(role2);
			setRoles.add(role3);
			
			user = userDao.setRoles(user, setRoles);
			
			//compare roles set size
			Assert.assertTrue("List of roles is not the same, it is supposed to be the same",setRoles.size() == user.getRoles().size());
			Assert.assertTrue("List of roles does not contain the same objects",setRoles.containsAll(user.getRoles()));
			
			setRoles.clear();
			userDao.setRoles(user, setRoles);
		}
		catch(Exception e){
			//an error occurred, trigger roleTest tearDown anyway
			throw e;
		}
		finally{
			roleTest.tearDown();
		}
		
		
		
		LOGGER.info("Finished Testing setRoles method in UserDaoHibernate....");
		LOGGER.info("---------------------------------------------");
	}
	
	@Test
	public void testAddRole() throws Exception{
		LOGGER.info("---------------------------------------------");
		LOGGER.info("Testing addRole method in UserDaoHibernate....");
		
		//creation of mock roles
		RoleDaoHiberanteTest roleTest = new RoleDaoHiberanteTest();
		roleTest.setRoleDao(roleDao);
		
		try{
		
			Role role1 = RoleDaoHiberanteTest.getMockRole();
			Role role2 = RoleDaoHiberanteTest.getMockRole();
			Role role3 = RoleDaoHiberanteTest.getMockRole();
			
			role2.setName(role2.getName()+"2");
			role2.setInitials(role2.getInitials()+"2");
			
			role3.setName(role3.getName()+"3");
			role3.setInitials(role3.getInitials()+"3");
			
			role1=(roleDao.save(role1));
			role2=(roleDao.save(role2));
			role3=(roleDao.save(role3));
			
			//creation of mock user
			User user = getMockUser();
			user=(userDao.save(user));
			
			user = userDao.addRole(user, role1);
			Assert.assertTrue("Only one role expected in the list",user.getRoles().size() == 1);
			
			user = userDao.addRole(user, role2);
			Assert.assertTrue("Only two roles expected in the list",user.getRoles().size() == 2);
			
			user = userDao.addRole(user, role3);
			Assert.assertTrue("Only three roles expected in the list",user.getRoles().size() == 3);
			
			//if adding an already existing role, list shouldn´t change
			user = userDao.addRole(user, role3);
			Assert.assertTrue("Only three roles expected in the list",user.getRoles().size() == 3);
			
			userDao.setRoles(user, new HashSet<Role>());
			
		}
		catch(Exception e){
			//exception occurred, failure
			throw e;
		}
		finally{
			roleTest.tearDown();
		}
		
		LOGGER.info("---------------------------------------------");
		LOGGER.info("Finished Testing addRole method in UserDaoHibernate....");
	}
	
	private void removeMockRecords(){
		try{
			User user = new User(MOCK_USER_CODE,null,null);
			
			removeRecords(
					userDao.find(user,null));
			
		}catch(IndustrikaObjectNotFoundException onf){
			onf.getMessage();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void removeRecords(List<User> listUser) throws IndustrikaObjectNotFoundException, IndustrikaPersistenceException{
		if(listUser != null){
			for(User user : listUser){
				userDao.remove(user);
			}
		}
	}
	
	private User getMockUser(){
		User user = new User(MOCK_USER_CODE,MOCK_USER_PASSWORD,MOCK_USER_EMAIL);
		user.setLastTransactionDate(MOCK_LT_DATE);
		return user;
	}
}
