package com.industrika.commons.commands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.industrika.commons.dao.GenericDao;
import com.industrika.commons.dao.SystemActionDao;
import com.industrika.commons.dao.SystemOptionDao;
import com.industrika.commons.dto.Action;
import com.industrika.commons.dto.Option;
import com.industrika.commons.dto.Privilege;
import com.industrika.commons.view.SystemPrivilegeDTO;

@Component("systemPrivilegeCommand")
@Scope("prototype")
public class SystemPrivilegeCommand extends AbstractCatalogCommand<Integer, Privilege> {
	
	@Autowired
	@Qualifier("systemActionDaoHibernate")
	private SystemActionDao actionDao;
	
	@Autowired
	@Qualifier("systemOptionDaoHibernate")
	private SystemOptionDao optionDao;
	
	private List<Action> listActions;
	
	private List<Option> listOptions;
	
	public SystemPrivilegeCommand(){
		setSuccessView("/commons/systemPrivilege.jsp");
		setSortBy("opt.resourceName","act.type","name");
	}
	
	@Override
	public HashMap<String, Object> execute(Map<String, String[]> parameters) {
		super.execute(parameters);
		
		SystemPrivilegeDTO prDTO = null;
		
		try {
			
			if (results.get("dto") != null){
				Privilege pr = (Privilege) results.remove("dto");
				
				prDTO = new SystemPrivilegeDTO(pr);
				
				results.put("dto", prDTO);
			
				//add the lists of actions and options to the request, so it can be used in the formulary
				listActions = actionDao.findAll();
				listOptions = optionDao.findAll();
				
				prDTO.setListActions(listActions);
				prDTO.setListOptions(listOptions);
			}
		}catch(DataIntegrityViolationException e){
			results.put("error","No puede ser eliminado pues existe información que depende de ésta");
		} catch (Exception e) {e.printStackTrace();
			results.put("error",e.getMessage());
		}
		return results;
	}
	
	@Override
	@Autowired
	@Qualifier("systemPrivilegeDaoHibernate")
	public void setDao(GenericDao<Integer, Privilege> dao) {
		this.dao = dao;
	}

}
