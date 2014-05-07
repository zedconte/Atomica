/**
 * 
 */
package com.industrika.commons.commands;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;

import com.industrika.commons.dao.GenericDao;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.commons.i18n.CommonsMessages;

/**
 * @author jose.arellano
 */
public abstract class AbstractCatalogCommand<K,E> implements IndustrikaCommand {

	private static final Logger LOGGER = Logger.getLogger(AbstractCatalogCommand.class);

	public static final String[] ACTIONS = {"add", "update", "remove", "search", "show"};
	
	@SuppressWarnings("rawtypes")
	protected Class entityClass;
	
	protected BasicParameters basicParameters;
	protected E record;
	protected List<E> listRecords;
	protected HashMap<String, Object> results;
	
	protected String successView;
	protected String errorView;
	protected String[] sortBy;
	
	private String defaultAction = ACTIONS[4];
	protected GenericDao<K,E> dao;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public AbstractCatalogCommand() {
		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
		this.entityClass = (Class) genericSuperclass.getActualTypeArguments()[1];
		
		try {
			record = (E)entityClass.newInstance();
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		listRecords = new ArrayList<E>();
	}

	/* (non-Javadoc)
	 * @see com.industrika.commons.commands.IndustrikaCommand#execute(java.util.Map)
	 */
	@Override
	public HashMap<String, Object> execute(Map<String, String[]> parameters) {
		results = new HashMap<String, Object>();
		
		initBasicParameters(parameters);
		readDTOAttributes(record, parameters);
		
		try{
			executeAction();
		}
		catch(Exception e){
			if (e instanceof DataIntegrityViolationException){
				results.put("error","No puede ser eliminado pues existe informaci&oacute;n que depende de &eacute;sta");
			} else {
				results.put("error",e.getMessage());
			}
		}
		
		//results.put("form", getClass().getResourceAsStream(successView));
		results.put("form", successView);
		
		return results;
	}

	public abstract void setDao(GenericDao<K,E> dao);
	
	public void setSuccessView(String successView){
		this.successView = successView;
	}
	
	public void setErrorView(String errorView){
		this.errorView = errorView;
	}
	
	public void setSortBy(String ... sortBy){
		this.sortBy = sortBy;
	}
	
	protected void executeAction() throws IndustrikaValidationException, IndustrikaPersistenceException, 
			IndustrikaObjectNotFoundException, InstantiationException, IllegalAccessException{
		try{
			if (basicParameters.action.equals(ACTIONS[0])){
				//add
				results.put("dto", record);
				saveRecord();
			}
			else if (basicParameters.action.equals(ACTIONS[1])){
				//update
				results.put("dto", record);
				updateRecord();
			}
			else if (basicParameters.action.equals(ACTIONS[2])){
				//remove
				removeRecord();
			}
			else if (basicParameters.action.equals(ACTIONS[3])){
				//search
				searchRecord();
			}
			else if (basicParameters.action.equals(ACTIONS[4])){
				//show main screen of this catalog
				showLandingCatalogScreen();
			}
			else{
				throw new RuntimeException("Action not supported: "+basicParameters.action);
			}
		}
		catch (IndustrikaValidationException e){
			results.put("error",e.getMessage());
		}
		catch (IndustrikaPersistenceException e){
			results.put("error",e.getMessage());
		}
		catch (IndustrikaObjectNotFoundException e){
			results.put("error",e.getMessage());
		}
		catch(DataIntegrityViolationException e){
			throw new IndustrikaPersistenceException("No puede ser eliminado pues existe información que depende de ésta",e);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	protected void saveRecord() throws IndustrikaValidationException, IndustrikaPersistenceException{
		dao.save(record);
		try {
			record = (E)entityClass.newInstance();			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}	
		results.put("message", CommonsMessages.getMessage("sucess_persistence"));
		results.put("dto", record);
	}
	
	@SuppressWarnings("unchecked")
	protected void updateRecord() throws IndustrikaValidationException, IndustrikaPersistenceException, IndustrikaObjectNotFoundException {
		dao.update(record);
		try {
			record = (E)entityClass.newInstance();			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		results.put("message", CommonsMessages.getMessage("sucess_persistence"));
		results.put("dto", record);
	}
	
	@SuppressWarnings("unchecked")
	protected void removeRecord() throws IndustrikaValidationException, IndustrikaPersistenceException, IndustrikaObjectNotFoundException,
			InstantiationException, IllegalAccessException {
		dao.remove(record);
		record = (E)entityClass.newInstance();
		results.put("message", CommonsMessages.getMessage("sucess_persistence"));
		results.put("dto", record);
	}
	
	protected void searchRecord() throws IndustrikaObjectNotFoundException, IndustrikaPersistenceException{
		listRecords = dao.find(record, sortBy);
		LOGGER.info("Recourds found: "+listRecords.size());
		if (listRecords.size() == 1){
			record = listRecords.get(0);
		}
		results.put("list", listRecords);
		results.put("dto", record);
	}
	
	protected void showLandingCatalogScreen() throws IndustrikaObjectNotFoundException, IndustrikaPersistenceException{
		listRecords = new ArrayList<E>();
		results.put("list", listRecords);
		results.put("dto", record);
	}
	
	protected void initBasicParameters(Map<String, String[]> parameters){
		basicParameters = new BasicParameters();
		basicParameters.returnType = (parameters.get("returnType") != null && parameters.get("returnType").length >0) ? 
				parameters.get("returnType")[0] : "jsp";
		
		basicParameters.action = (parameters.get("action") != null && parameters.get("action").length >0) ?
				parameters.get("action")[0] : this.defaultAction;
		
		parameters.remove("returnType");
		parameters.remove("action");
		parameters.remove("formName");
		
	}

	protected class BasicParameters{
		String returnType;
		String action;
	};
	
	protected void readDTOAttributes(Object record,Map<String, String[]> parameters){
		if(record != null){
			for(String attributeName : parameters.keySet()){
				setValue(record, attributeName, parameters.get(attributeName)[0]);
			}
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void setValue(Object object,String attributeName, String attributeValue){
		//module.option.name
		if(attributeName.contains(".")){
			String tmpAttrName = attributeName.substring(0,attributeName.indexOf("."));
			Class<?> clazz = object.getClass();
			//module
			try {
				Method getter = clazz.getMethod("get"+StringUtils.capitalize(tmpAttrName));
				
				Method setter = clazz.getMethod("set"+StringUtils.capitalize(tmpAttrName),getter.getReturnType());
				
				Object argument = setter.getParameterTypes()[0].newInstance();
				setter.invoke(object, argument);
				
				attributeName = attributeName.substring(attributeName.indexOf(".")+1);
				object = argument;
				
				setValue(object,
						attributeName,
						attributeValue);
				
			} catch (SecurityException | NoSuchMethodException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				LOGGER.warn("unable to set attribute: "+attributeName+", value: "+attributeValue, e);
			}
		}
		else{
			//get the data type
			try {
				Class<?> clazz = object.getClass();
				Method getter = clazz.getMethod("get"+StringUtils.capitalize(attributeName));
				Method setter = clazz.getMethod("set"+StringUtils.capitalize(attributeName),getter.getReturnType());
				Class clazzArgument = setter.getParameterTypes()[0];
				Object actualArgument = clazzArgument.getConstructor(String.class).newInstance(attributeValue);//;getActualObject(attributeValue);
	//			Object argument
				
				setter.invoke(object, actualArgument);
				
			} catch (SecurityException e) {
				LOGGER.warn("unable to set attribute: "+attributeName+", value: "+attributeValue, e);
			} catch (NoSuchMethodException e) {
				LOGGER.warn("unable to set attribute: "+attributeName+", value: "+attributeValue, e);
			} catch (IllegalArgumentException e) {
				LOGGER.warn("unable to set attribute: "+attributeName+", value: "+attributeValue, e);
			} catch (InstantiationException e) {
				LOGGER.warn("unable to set attribute: "+attributeName+", value: "+attributeValue, e);
			} catch (IllegalAccessException e) {
				LOGGER.warn("unable to set attribute: "+attributeName+", value: "+attributeValue, e);
			} catch (InvocationTargetException e) {
				LOGGER.warn("unable to set attribute: "+attributeName+", value: "+attributeValue, e);
			}
		}
	}
}
