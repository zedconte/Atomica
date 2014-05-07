package com.industrika.commons.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.industrika.commons.dao.SystemModuleDao;
import com.industrika.commons.dto.Module;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.commons.validation.IndustrikaValidator;

/**
 * @author jose.arellano
 */
@Repository("systemModuleDaoHibernate")
public class SystemModuleDaoHibernate extends JpaDao<Integer, Module> implements
		SystemModuleDao{

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Module> find(Module module, String[] orderFields)
			throws IndustrikaObjectNotFoundException, IndustrikaPersistenceException {
		if(module.getId() != null){
			module = findById(module.getId());
			List<Module> res = new ArrayList<Module>();
			res.add(module);
			return res;
		}
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Module.class);
		List<Module> results = null;
		try{
			if (module != null){
				if (!StringUtils.isBlank(module.getName())){
					criteria.add(Restrictions.like("name", "%"+module.getName()+"%"));
				}
				if (!StringUtils.isBlank(module.getDescription())){
					criteria.add(Restrictions.like("description", "%"+module.getDescription()+"%"));
				}
			}
			super.addOrderByFields(criteria, orderFields);
			
			results = criteria.list();
			
		}catch(Exception ex){
			throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_executequery")+
					module.getClass(), ex);
		}
		
		return results;
	}

	@Autowired
	@Qualifier("moduleValidator")
	public void setValidator(IndustrikaValidator<Module> validator){
		this.validator = validator;
	}
}
