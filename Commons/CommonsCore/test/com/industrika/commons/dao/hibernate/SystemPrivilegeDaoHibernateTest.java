/**
 * 
 */
package com.industrika.commons.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.industrika.commons.businesslogic.ApplicationContextProvider;
import com.industrika.commons.dao.SystemActionDao;
import com.industrika.commons.dao.SystemModuleDao;
import com.industrika.commons.dao.SystemOptionDao;
import com.industrika.commons.dao.SystemPrivilegeDao;
import com.industrika.commons.dto.Action;
import com.industrika.commons.dto.Module;
import com.industrika.commons.dto.Option;
import com.industrika.commons.dto.Privilege;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.exceptions.IndustrikaValidationException;

/**
 * @author jose.arellano
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/commons_context.xml"})
public class SystemPrivilegeDaoHibernateTest {

	private static final String MOCK_PR_NAME="Mock privilege";
	private static final Logger LOGGER = Logger.getLogger(SystemPrivilegeDaoHibernateTest.class);
	
	private static List<Action> mockActionList = SystemActionDaoHibernateTest.createMockActions(4);
	private static List<Option> mockOptionList = SystemOptionDaoHibernateTest.createMockOptions(3);
	
	private SystemOptionDao optionDao;
	private SystemActionDao actionDao;
	private SystemPrivilegeDao privilegeDao;
	
	@BeforeClass
	public static void prepareExternalRecords() throws Exception{
		prepareMockActions();
		prepareMockOptions();
	}
	
	@AfterClass
	public static void removeExternalRecords() throws Exception{
		removeMockActions();
		removeMockOptions();
	}
	
	@Before
	public void prepareTestUnit() throws Exception{
		removeMockPrivileges();
	}

	@After
	public void tearDown() throws Exception{
		removeMockPrivileges();
	}
	
	@Test
	public void testSave() throws IndustrikaValidationException, IndustrikaPersistenceException{
		LOGGER.info("---------------------------------------------");
		LOGGER.info("Testing save method in SystemPrivilegeDaoHibernate....");
		
		Privilege privilege = getMockPrivilege();
		privilege=(privilegeDao.save(privilege));
		
		Assert.assertTrue("Invalid id, should be grather than 0",privilege.getId() != null && privilege.getId()>0);
		
		LOGGER.info("Finished Testing save method in SystemPrivilegeDaoHibernate....");
		LOGGER.info("---------------------------------------------");
	}
	
	@Test
	public void testFind() throws Exception{
		LOGGER.info("---------------------------------------------");
		LOGGER.info("Testing findById method in SystemPrivilegeDaoHibernate....");
		
		Privilege privilege = getMockPrivilege();
		privilege=(privilegeDao.save(privilege));
		
		Privilege privilege1 = privilegeDao.findById(privilege.getId());
		Assert.assertTrue("Records don´t match, should match", 
				privilege.getName().equals(privilege1.getName()));
		Assert.assertTrue("Records don´t match, should match", 
				privilege.getResourceName().equals(privilege1.getResourceName()));
		Assert.assertTrue("Records don´t match, should match", 
				privilege.getActionType().equals(privilege1.getActionType()));
		Assert.assertTrue("Records don´t match, should match",
				privilege.getAction().equals(privilege1.getAction()));
		Assert.assertTrue("Records don´t match, should match",
				privilege.getOption().equals(privilege1.getOption()));
		//because of DB integration, there is a small difference in the creation date, don´t validate them
		
		LOGGER.info("Finished Testing findById method in SystemPrivilegeDaoHibernate....");
		LOGGER.info("---------------------------------------------");
	}
	
	@Test
	public void testUpdate() throws Exception{
		LOGGER.info("---------------------------------------------");
		LOGGER.info("Testing update method in SystemPrivilegeDaoHibernate....");
		
		Privilege privilege = getMockPrivilege();
		privilege=(privilegeDao.save(privilege));
		
		Privilege privilege1 = privilegeDao.findById(privilege.getId());
		privilege1.setName(MOCK_PR_NAME+" modified");
		privilege1.setOption(mockOptionList.get(1));
		privilege1.setAction(mockActionList.get(1));
		
		privilegeDao.update(privilege1);
		
		Assert.assertTrue("Objects should match after updating", 
				(MOCK_PR_NAME+" modified").equals(privilege1.getName()));
		Assert.assertTrue("Objects should match after updating", 
				mockOptionList.get(1).equals(privilege1.getOption()));
		Assert.assertTrue("Objects should match after updating", 
				mockActionList.get(1).equals(privilege1.getAction()));
		
		LOGGER.info("Finished Testing update method in SystemPrivilegeDaoHibernate....");
		LOGGER.info("---------------------------------------------");
	}
	
	@Test
	public void testFindAll() throws Exception{
		LOGGER.info("---------------------------------------------");
		LOGGER.info("Testing findAll method in SystemPrivilegeDaoHibernate....");
		
		List<Privilege> listPrs = createMockPrivileges(5);
		
		for (Privilege pr: listPrs) {
			pr.setId(null);
			privilegeDao.save(pr);
		}
		
		Privilege privilege = getMockPrivilege();
		
		//find using attributes: name, action type and resource name
		listPrs = privilegeDao.find(privilege, null);
		Assert.assertTrue("5 records expected,  found: "+listPrs.size(), listPrs.size() == 5);
		
		//find using attributes: name
		privilege.setAction(null);
		privilege.setOption(null);
		listPrs = privilegeDao.find(privilege, null);
		Assert.assertTrue("5 records expected,  found: "+listPrs.size(), listPrs.size() == 5);
		
		//find using attributes: Action type
		privilege.setAction(mockActionList.get(0));
		privilege.setName(null);
		listPrs = privilegeDao.find(privilege, null);
		Assert.assertTrue("5 records expected,  found: "+listPrs.size(), listPrs.size() == 5);
		
		//find using attributes: Action type
		privilege.setOption(mockOptionList.get(0));
		privilege.setAction(null);
		listPrs = privilegeDao.find(privilege, null);
		Assert.assertTrue("5 records expected,  found: "+listPrs.size(), listPrs.size() == 5);
		
		String[] sortBy = {"name"};
		listPrs = privilegeDao.find(privilege, sortBy);
		
		for(int i=0; i<5; i++){
			Assert.assertTrue("Failed 'sort by' functionality at findAll method", 
					listPrs.get(i).getName().equals(MOCK_PR_NAME+i));
		}
		
		LOGGER.info("Finished Testing findAll method in SystemPrivilegeDaoHibernate....");
		LOGGER.info("---------------------------------------------");
	}
	
	@Test
	public void testRemove() throws Exception{
		LOGGER.info("---------------------------------------------");
		LOGGER.info("Testing remove method in SystemPrivilegeDaoHibernate....");
		
		Privilege privilege = getMockPrivilege();
		privilege=(privilegeDao.save(privilege));
		
		privilegeDao.remove(privilege);
		
		Privilege privilege2= null;
		try{
			privilege2 = privilegeDao.findById(privilege.getId());
		}
		catch(IndustrikaObjectNotFoundException e){
			// record not found, correct
		}
		
		Assert.assertNull("Object found, supposed to be deleted",privilege2);
		
		//find the corresponding action and option, they should still exist in database
		Option option = null;
		try{
			option = optionDao.findById(mockOptionList.get(0).getId());
		}
		catch (Exception e){
			// if record not found an exception is thrown
		}
		finally{
			Assert.assertNotNull("Record should exist in the database...",option);
		}
		
		Action action = null;
		try{
			action = actionDao.findById(mockActionList.get(0).getId());
		}
		catch (Exception e){
			// if record not found an exception is thrown
		}
		finally{
			Assert.assertNotNull("Record should exist in the database...",action);
		}
		
		LOGGER.info("Finished Testing remove method in SystemPrivilegeDaoHibernate....");
		LOGGER.info("---------------------------------------------");
	}
	
	public static List<Privilege> createMockPrivileges(Integer amount){
		List<Privilege> listPr = new ArrayList<Privilege>(amount);
		Privilege tmp = null;
		for(int i=0; i<amount; i++){
			tmp = getMockPrivilege();
			tmp.setName(MOCK_PR_NAME+i);
			listPr.add(tmp);
		}
		return listPr;
	}
	
	public static Privilege getMockPrivilege(){
		Privilege pr = new Privilege();
		pr.setName(MOCK_PR_NAME);
		pr.setAction(mockActionList.get(0));
		pr.setOption(mockOptionList.get(0));
		return pr;
	}

	@Autowired
	public void setOptionDao(SystemOptionDao optionDao) {
		this.optionDao = optionDao;
	}
	
	@Autowired
	public void setActionDao(SystemActionDao actionDao) {
		this.actionDao = actionDao;
	}
	
	@Autowired
	public void setPrivilegeDao(SystemPrivilegeDao privilegeDao) {
		this.privilegeDao = privilegeDao;
	}
	
	private void removeMockPrivileges() throws Exception{
		Privilege pr = new Privilege();
		pr.setName(MOCK_PR_NAME);
		List<Privilege> listPr = privilegeDao.find(pr, null);
		
		removeRecords(listPr);
	}
	
	private void removeRecords(List<Privilege> listPrivileges) throws Exception{
		if(listPrivileges != null)
			for(Privilege pr: listPrivileges)
				privilegeDao.remove(pr);
	}
	
	private static void prepareMockActions() throws Exception{
		//inserts the mock action, so we can generate the mock privileges for them.
		SystemActionDao actionDao = ApplicationContextProvider.getCtx().getBean(SystemActionDao.class);
		for(Action action : mockActionList)
			actionDao.save(action);
	}
	
	private static void prepareMockOptions() throws Exception{
		//inserts the mock options, so we can generate the mock privileges for them.
		
		SystemModuleDao moduleDao = ApplicationContextProvider.getCtx().getBean(SystemModuleDao.class);
		Module module = SystemModuleDaoHibernateTest.getMockModule();
		module=(moduleDao.save(module));
		
		SystemOptionDao optionDao = ApplicationContextProvider.getCtx().getBean(SystemOptionDao.class);
		for(Option option : mockOptionList){
			option.setModule(module);
			optionDao.save(option);
		}
	}
	
	private static void removeMockActions() throws Exception{
		//deletes the mock actions
		SystemActionDao actionDao = ApplicationContextProvider.getCtx().getBean(SystemActionDao.class);
		for(Action action : mockActionList)
			actionDao.remove(action);
	}
	
	private static void removeMockOptions() throws Exception{
		//deletes the mock options
		SystemOptionDao optionDao = ApplicationContextProvider.getCtx().getBean(SystemOptionDao.class);
		
		Module mod = mockOptionList.get(0).getModule();
		
		for(Option option : mockOptionList){
			optionDao.remove(option);
		}
		
//		Module module = SystemModuleDaoHibernateTest.getMockModule();
//		module.setDescription(null);
		
		SystemModuleDao moduleDao = ApplicationContextProvider.getCtx().getBean(SystemModuleDao.class);
//		List<Module> listModules = moduleDao.find(module, null);
		
//		for(Module mod : listModules){
			moduleDao.remove(mod);
//		}
	}
}
