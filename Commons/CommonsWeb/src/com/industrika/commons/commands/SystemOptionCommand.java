package com.industrika.commons.commands;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.industrika.commons.dao.GenericDao;
import com.industrika.commons.dao.SystemModuleDao;
import com.industrika.commons.dto.Module;
import com.industrika.commons.dto.Option;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.commons.view.SystemOptionDTO;

/**
 * @author jose.arellano
 */
@Component("systemOptionCommand")
@Scope("prototype")
public class SystemOptionCommand extends AbstractCatalogCommand<Integer, Option> {

	private List<Module> listModule;
	
	@Autowired
	@Qualifier("systemModuleDaoHibernate")
	private SystemModuleDao moduleDao;
	
	public SystemOptionCommand(){
		setSuccessView("/commons/systemOption.jsp");
		setSortBy("resourceName","mod.name");
	}
	
	@Override
	public HashMap<String, Object> execute(Map<String, String[]> parameters) {
		super.execute(parameters);
		
		SystemOptionDTO sop = null;
		
		try {
			
			if (results.get("dto") != null){
				Option opt = (Option) results.remove("dto");
				
				sop = new SystemOptionDTO(opt);
				
				results.put("dto", sop);
			
				//add the list of modules to the request, so it can be used in the formulary
				listModule = moduleDao.findAll();
				sop.setListModule(listModule);
				
			}
			
		}catch(DataIntegrityViolationException e){
			results.put("error","No puede ser eliminado pues existe información que depende de ésta");
		} catch (Exception e) {e.printStackTrace();
			results.put("error",e.getMessage());
		}
		
		
		
		return results;
	}
	
	@Override
	protected void readDTOAttributes(Object record,Map<String, String[]> parameters){
		if(parameters.get("id") != null && parameters.get("id").length >0 
				&& !StringUtils.isBlank(parameters.get("id")[0]) && StringUtils.isNumeric(parameters.get("id")[0])){
			try {
				this.record = dao.findById(new Integer(parameters.get("id")[0]));
				record = this.record;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		super.readDTOAttributes(record, parameters);
	}
	
	@Override
	protected void saveRecord() throws IndustrikaValidationException, IndustrikaPersistenceException{
		record.setCreationDate(Calendar.getInstance());
		super.saveRecord();
	}
	
	@Override
	@Autowired
	@Qualifier("systemOptionDaoHibernate")
	public void setDao(GenericDao<Integer, Option> dao) {
		this.dao = dao;
	}

}
