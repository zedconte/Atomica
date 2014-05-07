/**
 * 
 */
package com.industrika.commons.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.industrika.commons.dao.SystemModuleDao;
import com.industrika.commons.dto.Module;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.exceptions.IndustrikaValidationException;

/**
 * @author jose.arellano
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/commons_context.xml"})
public class SystemModuleDaoHibernateTest {

	private static final Logger LOGGER = Logger.getLogger(SystemModuleDaoHibernateTest.class);
	private static final String MOCK_MOD_NAME = "Mock Module";
	private static final String MOCK_MOD_DESC = "Mock Module description";
	
	@Autowired
	private SystemModuleDao moduleDao;
	
	@Before
	public void prepare(){
		removeMockRecords();
	}
	
	@After
	public void tearDown(){
		removeMockRecords();
	}
	
	@Test
	public void testSave() throws IndustrikaValidationException, IndustrikaPersistenceException{
		LOGGER.info("---------------------------------------------");
		LOGGER.info("Testing save method in SystemModuleDaoHibernate....");
		
		Module module = getMockModule();
		module = moduleDao.save(module);
		Assert.assertTrue("Incorrect id", module.getId() > 0);
		
		LOGGER.info("Finished Testing save method in SystemModuleDaoHibernate....");
		LOGGER.info("---------------------------------------------");
	}
	
	@Test
	public void testFindById() throws Exception{
		LOGGER.info("---------------------------------------------");
		LOGGER.info("Testing findById method in SystemModuleDaoHibernate....");
		
		Module module = getMockModule();
		module = moduleDao.save(module);
		
		Module module2 = moduleDao.findById(module.getId());
		
		Assert.assertNotNull("Object not found, expected Module object with id: "+module.getId(),module2);
		Assert.assertEquals(module2.getName(), MOCK_MOD_NAME);
		Assert.assertEquals(module2.getDescription(), MOCK_MOD_DESC);
		
		LOGGER.info("Finished Testing findById method in SystemModuleDaoHibernate....");
		LOGGER.info("---------------------------------------------");
	}
	
	@Test
	public void testUpdate() throws IndustrikaValidationException, IndustrikaPersistenceException, IndustrikaObjectNotFoundException{
		LOGGER.info("---------------------------------------------");
		LOGGER.info("Testing update method in SystemModuleDaoHibernate....");
		
		Module module = getMockModule();
		module=(moduleDao.save(module));
		
		Module module2 = moduleDao.findById(module.getId());
		Assert.assertNotNull("Object not found, expected Module object with id: "+module.getId(),module2);
		
		module2.setName(MOCK_MOD_NAME+" modified");
		module2.setDescription(MOCK_MOD_DESC+" modified");
		
		moduleDao.update(module2);
		
		Module module3 = moduleDao.findById(module.getId());
		
		Assert.assertEquals(module3.getName(), MOCK_MOD_NAME+" modified");
		Assert.assertEquals(module3.getDescription(), MOCK_MOD_DESC+" modified");
		
		LOGGER.info("Finished Testing update method in SystemModuleDaoHibernate....");
		LOGGER.info("---------------------------------------------");
	}

	@Test
	public void testFindAll() throws Exception{
		LOGGER.info("---------------------------------------------");
		LOGGER.info("Testing find method in SystemModuleDaoHibernate....");
		
		List<Module> list = createMockModules(5);
		for(Module module: list)
			moduleDao.save(module);
		
		list = moduleDao.find(new Module(MOCK_MOD_NAME,null), null);
		
		Assert.assertTrue("List size is incorrect, excepted 5, found: "+list.size(),list.size()==5);
		for(Module module:list){
			Assert.assertTrue("Incorrect attribute value: name="+module.getName(),
					module.getName().startsWith(MOCK_MOD_NAME));
			Assert.assertTrue("Incorrect attribute value: description="+module.getDescription(),
					module.getDescription().startsWith(MOCK_MOD_DESC));
		}
		
		
		LOGGER.info("Finished Testing find method in SystemModuleDaoHibernate....");
		LOGGER.info("---------------------------------------------");
	}
	
	@Test
	public void testRemove() throws Exception{
		LOGGER.info("---------------------------------------------");
		LOGGER.info("Testing remove method in SystemModuleDaoHibernate....");
		
		Module module = getMockModule();
		module=(moduleDao.save(module));
		
		moduleDao.remove(module);
		
		Module module2 = null;
		try{
			module2 = moduleDao.findById(module.getId());
		}
		catch(IndustrikaObjectNotFoundException e){
			// record not found, correct
		}
		
		Assert.assertNull("Object found, supposed to be deleted",module2);
		
		LOGGER.info("Finished Testing remove method in SystemModuleDaoHibernate....");
		LOGGER.info("---------------------------------------------");
	}
	
	public static Module getMockModule(){
		Module module = new Module();
		module.setDescription(MOCK_MOD_DESC);
		module.setName(MOCK_MOD_NAME);
		return module;
	}
	
	public static List<Module> createMockModules(Integer amount){
		List<Module> listModules = new ArrayList<Module>(amount);
		for(int i=0; i<amount; i++){
			listModules.add(new Module(MOCK_MOD_NAME+i, MOCK_MOD_DESC+i));
		}
		return listModules;
	}
	
	private void removeMockRecords(){
		try{
			Module module = new Module(MOCK_MOD_NAME,null);
			
			removeRecords(
					moduleDao.find(module,null));
			
		}catch(IndustrikaObjectNotFoundException onf){
			onf.getMessage();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void removeRecords(List<Module> list) throws IndustrikaObjectNotFoundException, IndustrikaPersistenceException{
		if(list!= null){
			for(Module module : list){
				moduleDao.remove(module);
			}
		}
	}
}
