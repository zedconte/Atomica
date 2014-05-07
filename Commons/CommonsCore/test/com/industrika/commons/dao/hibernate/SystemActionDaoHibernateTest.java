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

import com.industrika.commons.dao.SystemActionDao;
import com.industrika.commons.dto.Action;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.exceptions.IndustrikaValidationException;

/**
 * @author jose.arellano
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/commons_context.xml"})
public class SystemActionDaoHibernateTest {

	private static final Logger LOGGER = Logger.getLogger(SystemActionDaoHibernateTest.class);
	private static final String MOCK_ACT_TYPE = "Mock Action type";
	private static final String MOCK_ACT_DESC = "Mock Action description";
	
	@Autowired
	private SystemActionDao actionDao;
	
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
		LOGGER.info("Testing save method in SystemActionDaoHibernate....");
		
		Action action = getMockAction();
		action = actionDao.save(action);
		Assert.assertTrue("Incorrect id", action.getId() > 0);
		
		LOGGER.info("Finished Testing save method in SystemActionDaoHibernate....");
		LOGGER.info("---------------------------------------------");
	}
	
	@Test
	public void testFindById() throws Exception{
		LOGGER.info("---------------------------------------------");
		LOGGER.info("Testing findById method in SystemActionDaoHibernate....");
		
		Action action = getMockAction();
		action = actionDao.save(action);
		
		Action action2 = actionDao.findById(action.getId());
		
		Assert.assertNotNull("Object not found, expected Module object with id: "+action.getId(),action2);
		Assert.assertEquals(action2.getType(), MOCK_ACT_TYPE);
		Assert.assertEquals(action2.getDescription(), MOCK_ACT_DESC);
		
		LOGGER.info("Finished Testing findById method in SystemActionDaoHibernate....");
		LOGGER.info("---------------------------------------------");
	}
	
	@Test
	public void testUpdate() throws IndustrikaValidationException, IndustrikaPersistenceException, IndustrikaObjectNotFoundException{
		LOGGER.info("---------------------------------------------");
		LOGGER.info("Testing update method in SystemActionDaoHibernate....");
		
		Action action = getMockAction();
		action = actionDao.save(action);
		
		Action action2 = actionDao.findById(action.getId());
		Assert.assertNotNull("Object not found, expected Module object with id: "+action.getId(),action2);
		
		action2.setType(MOCK_ACT_TYPE+" modified");
		action2.setDescription(MOCK_ACT_DESC+" modified");
		
		actionDao.update(action2);
		
		Action action3 = actionDao.findById(action.getId());
		
		Assert.assertEquals(action3.getType(), MOCK_ACT_TYPE+" modified");
		Assert.assertEquals(action3.getDescription(), MOCK_ACT_DESC+" modified");
		
		LOGGER.info("Finished Testing update method in SystemActionDaoHibernate....");
		LOGGER.info("---------------------------------------------");
	}

	@Test
	public void testFindAll() throws Exception{
		LOGGER.info("---------------------------------------------");
		LOGGER.info("Testing find method in SystemActionDaoHibernate....");
		
		List<Action> list = createMockActions(5);
		for(Action act: list)
			actionDao.save(act);
		
		list = actionDao.find(new Action(null, MOCK_ACT_TYPE, null), null);
		
		Assert.assertTrue("List size is incorrect, excepted 5, found: "+list.size(),list.size()==5);
		for(Action action:list){
			Assert.assertTrue("Incorrect attribute value: name="+action.getType(),
					action.getType().startsWith(MOCK_ACT_TYPE));
			Assert.assertTrue("Incorrect attribute value: description="+action.getDescription(),
					action.getDescription().startsWith(MOCK_ACT_DESC));
		}
		
		
		LOGGER.info("Finished Testing find method in SystemActionDaoHibernate....");
		LOGGER.info("---------------------------------------------");
	}
	
	@Test
	public void testRemove() throws Exception{
		LOGGER.info("---------------------------------------------");
		LOGGER.info("Testing remove method in SystemActionDaoHibernate....");
		
		Action action = getMockAction();
		action = actionDao.save(action);
		
		actionDao.remove(action);
		
		Action action2 = null;
		try{
			action2 = actionDao.findById(action.getId());
		}
		catch(IndustrikaObjectNotFoundException e){
			// record not found, correct
		}
		
		Assert.assertNull("Object found, supposed to be deleted",action2);
		
		LOGGER.info("Finished Testing remove method in SystemActionDaoHibernate....");
		LOGGER.info("---------------------------------------------");
	}
	
	public static Action getMockAction(){
		Action act= new Action();
		act.setDescription(MOCK_ACT_DESC);
		act.setType(MOCK_ACT_TYPE);
		return act;
	}
	
	public static List<Action> createMockActions(Integer amount){
		List<Action> listActions = new ArrayList<Action>(amount);
		for(int i=0; i<amount; i++){
			listActions.add(new Action(null,MOCK_ACT_TYPE+i, MOCK_ACT_DESC+i));
		}
		return listActions;
	}
	
	private void removeMockRecords(){
		try{
			Action action = new Action(null,MOCK_ACT_TYPE,null);
			
			removeRecords(
					actionDao.find(action,null));
			
		}catch(IndustrikaObjectNotFoundException onf){
			onf.printStackTrace();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void removeRecords(List<Action> list) throws IndustrikaObjectNotFoundException, IndustrikaPersistenceException{
		if(list!= null){
			for(Action act: list){
				actionDao.remove(act);
			}
		}
	}
}
