package com.industrika.sie.delegate;

import java.io.InvalidClassException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.industrika.commons.businesslogic.ApplicationContextProvider;
import com.industrika.commons.commands.IndustrikaCommand;
import com.industrika.commons.commands.IndustrikaCommandsFactory;
import com.industrika.commons.dao.SystemOptionDao;
import com.industrika.commons.utils.TextFormatter;

public class BusinessDelegate {
	
	
	private static final ConcurrentMap<String, IndustrikaCommandsFactory> factories = new ConcurrentHashMap<String, IndustrikaCommandsFactory>();
		
	@SuppressWarnings("unchecked")
	public static String delegate(String formName, HttpServletRequest request){
		
		if (StringUtils.isBlank(formName)) throw new IllegalArgumentException("Invalid form name");
		if (request==null) throw new IllegalArgumentException("Invalid request parameter");
		
		IndustrikaCommand command = null;
		try {
			command = getCommand(formName);
		} catch (InvalidClassException | ClassNotFoundException e) {
			e.printStackTrace();
			
		} 
		
		String response = "index.jsp";
		
				
		Map<String, String[]> params = new HashMap<String, String[]>();
		params.putAll(request.getParameterMap());
		
		params.put("returnType", getReturnType(request));
		Map<String, Object> commandResponse = command.execute(params);
		response = (String) commandResponse.get("form");
		request.setAttribute("message", commandResponse.get("message") != null ? TextFormatter.formatWeb(commandResponse.get("message").toString()) : "");
		request.setAttribute("error", commandResponse.get("error") != null ? TextFormatter.formatWeb(commandResponse.get("error").toString()) : "");
		request.setAttribute("dto", commandResponse.get("dto"));
		request.setAttribute("list", commandResponse.get("list"));
			
		return response;
	}
	
	
	
	private static String[] getReturnType(HttpServletRequest request){
		//String requestedWith = request.getHeader("X-Requested-With");
		//if (!StringUtils.isBlank(requestedWith) && requestedWith.contains("XMLHttpRequest"))
		//	return new String[]{"json"};
		return new String[] {"jsp"};
	}
		
	@SuppressWarnings("unchecked")
	private static <T> T createNewInstance(String className, Class<T> assignableFrom) throws ClassNotFoundException, InvalidClassException{
		Class<?> classToInstantiate;
		try {
			classToInstantiate = Class.forName(className);
		} catch (ClassNotFoundException e) {
				e.printStackTrace();
				throw e;
		}
		if (assignableFrom.isAssignableFrom(classToInstantiate))
			try {
				return (T) classToInstantiate.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		throw new InvalidClassException(className, "The class is not assignable from " + assignableFrom.getCanonicalName());
	}
	
	private static IndustrikaCommandsFactory getFactory(String formName) throws InvalidClassException, ClassNotFoundException{
		String className = null;
		try{
			SystemOptionDao dao = (SystemOptionDao) ApplicationContextProvider.getCtx().getBean("systemOptionDaoHibernate");
			className = dao.getFactory(formName);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		if (!StringUtils.isEmpty(className))
		{
			if (factories.containsKey(className))
				return factories.get(className);
			IndustrikaCommandsFactory factory = createNewInstance(className, IndustrikaCommandsFactory.class);
			IndustrikaCommandsFactory oldValue = factories.putIfAbsent(className, factory);
			if (oldValue!=null) factory = oldValue;
			return factory;
		}
		throw new ClassNotFoundException("No factory class found for " + formName);
	}
		
	private static IndustrikaCommand getCommand(String formName) throws InvalidClassException, ClassNotFoundException{
		IndustrikaCommandsFactory factory = getFactory(formName);
		return factory.getCommand(formName);
	}
}
