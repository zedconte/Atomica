/**
 * 
 */
package com.industrika.commons.dao.hibernate;

import java.util.ArrayList;
import java.util.Calendar;
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
import com.industrika.commons.dao.SystemModuleDao;
import com.industrika.commons.dao.SystemOptionDao;
import com.industrika.commons.dto.Module;
import com.industrika.commons.dto.Option;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.exceptions.IndustrikaValidationException;

/**
 * @author jose.arellano
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/commons_context.xml"})
public class SystemOptionDaoHibernateTest {

	private static final String MOCK_OPT_NAME="Fake opt";
	private static final Calendar MOCK_OPT_CRDATE = Calendar.getInstance();
	private static final Logger LOGGER = Logger.getLogger(SystemOptionDaoHibernateTest.class);
	
	private static List<Module> mockModuleList = SystemModuleDaoHibernateTest.createMockModules(3);
	
	private SystemOptionDao optionDao;
	
	@BeforeClass
	public static void prepareExternalRecords() throws Exception{
		prepareMockModules();
	}
	
	@AfterClass
	public static void removeExternalRecords() throws Exception{
		removeMockModules();
	}
	
	@Before
	public void prepareTestUnit() throws Exception{
		removeMockOptions();
	}

	@After
	public void tearDown() throws Exception{
		removeMockOptions();
	}
	
	@Test
	public void testSave() throws IndustrikaValidationException, IndustrikaPersistenceException{
		LOGGER.info("---------------------------------------------");
		LOGGER.info("Testing save method in SystemModuleDaoHibernate....");
		
		Option option = getMockOption();
		option.setModule(mockModuleList.get(0));
		option=(optionDao.save(option));
		
		Assert.assertTrue("Invalid id, should be grather than 0",option.getId()!= null && option.getId()>0);
		
		LOGGER.info("Finished Testing save method in SystemModuleDaoHibernate....");
		LOGGER.info("---------------------------------------------");
	}
	
	@Test
	public void testFind() throws Exception{
		LOGGER.info("---------------------------------------------");
		LOGGER.info("Testing findById method in SystemModuleDaoHibernate....");
		
		Option option = getMockOption();
		option.setModule(mockModuleList.get(0));
		option=(optionDao.save(option));
		
		Option option1 = optionDao.findById(option.getId());
		Assert.assertTrue("Records don´t match, should match", 
				option.getResourceName().equals(option1.getResourceName()));
		Assert.assertTrue("Records don´t match, should match",
				option.getModule().equals(option1.getModule()));
		//because of DB integration, there is a small difference in the creation date, don´t validate them
		
		LOGGER.info("Finished Testing findById method in SystemModuleDaoHibernate....");
		LOGGER.info("---------------------------------------------");
	}
	
	@Test
	public void testUpdate() throws Exception{
		LOGGER.info("---------------------------------------------");
		LOGGER.info("Testing update method in SystemModuleDaoHibernate....");
		
		Option option = getMockOption();
		option.setModule(mockModuleList.get(0));
		option=(optionDao.save(option));
		
		Option option1 = optionDao.findById(option.getId());
		option1.setResourceName(MOCK_OPT_NAME+" modified");
		option1.setModule(mockModuleList.get(1));
		
		optionDao.update(option1);
		
		Assert.assertTrue("Objects should match after updating", 
				(MOCK_OPT_NAME+" modified").equals(option1.getResourceName()));
		Assert.assertTrue("Objects should match after updating", 
				mockModuleList.get(1).equals(option1.getModule()));
		
		LOGGER.info("Finished Testing update method in SystemModuleDaoHibernate....");
		LOGGER.info("---------------------------------------------");
	}
	
	@Test
	public void testFindAll() throws Exception{
		LOGGER.info("---------------------------------------------");
		LOGGER.info("Testing findAll method in SystemModuleDaoHibernate....");
		
		List<Option> listMockOpt = createMockOptions(5);
		
		for(Option opt : listMockOpt){
			optionDao.save(opt);
		}
		
		Option option = getMockOption();
		option.setModule(mockModuleList.get(0));
		option.setResourceName(MOCK_OPT_NAME);
		
		List<Option> listOpt = optionDao.find(option, null);
		
		Assert.assertTrue("5 records expected,  found: "+listOpt.size(), listOpt.size() == 5);
		
		String[] sortBy = {"resourceName"};
		listOpt = optionDao.find(option, sortBy);
		
		for(int i=1; i<=5; i++){
			Assert.assertTrue("Failed 'sort by' functionality at findAll method", 
					listOpt.get(i-1).getResourceName().equals(MOCK_OPT_NAME+" "+i));
		}
		
		LOGGER.info("Finished Testing findAll method in SystemModuleDaoHibernate....");
		LOGGER.info("---------------------------------------------");
	}
	
	public static List<Option> createMockOptions(Integer amount){
		List<Option> listOpt = new ArrayList<Option>();
		
		Option option = null;
		
		for(int i=1; i<=amount; i++){
			option = getMockOption();
			option.setResourceName(MOCK_OPT_NAME+" "+i);
			option.setModule(mockModuleList.get(0));
			listOpt.add(option);
		}
		return listOpt;
	}
	
	@Test
	public void testRemove() throws Exception{
		LOGGER.info("---------------------------------------------");
		LOGGER.info("Testing remove method in SystemModuleDaoHibernate....");
		
		Option option = getMockOption();
		option.setModule(mockModuleList.get(0));
		option=(optionDao.save(option));
		
		optionDao.remove(option);
		
		Option option2= null;
		try{
			option2 = optionDao.findById(option.getId());
		}
		catch(IndustrikaObjectNotFoundException e){
			// record not found, correct
		}
		
		Assert.assertNull("Object found, supposed to be deleted",option2);
		
		LOGGER.info("Finished Testing remove method in SystemModuleDaoHibernate....");
		LOGGER.info("---------------------------------------------");
	}
	
	public static Option getMockOption(){
		Option option = new Option();
		option.setCreationDate(MOCK_OPT_CRDATE);
		option.setResourceName(MOCK_OPT_NAME);
		option.setModule(new Module());
		return option;
	}

	@Autowired
	public void setOptionDao(SystemOptionDao optionDao) {
		this.optionDao = optionDao;
	}
	
	private void removeMockOptions() throws Exception{
		Option opt = new Option();
		opt.setResourceName(MOCK_OPT_NAME);
		List<Option> listOpt = optionDao.find(opt, null);
		
		removeRecords(listOpt);
	}
	
	private void removeRecords(List<Option> listOptions) throws Exception{
		if(listOptions != null)
			for(Option option : listOptions)
				optionDao.remove(option);
	}
	
	private static void prepareMockModules() throws Exception{
		//inserts the mock modules, so we can generate the mock options for them.
		SystemModuleDao moduleDao = ApplicationContextProvider.getCtx().getBean(SystemModuleDao.class);
		for(Module module : mockModuleList)
			moduleDao.save(module);
	}
	
	private static void removeMockModules() throws Exception{
		//inserts the mock modules, so we can generate the mock options for them.
		SystemModuleDao moduleDao = ApplicationContextProvider.getCtx().getBean(SystemModuleDao.class);
		for(Module module : mockModuleList)
			moduleDao.remove(module);
	}
}
