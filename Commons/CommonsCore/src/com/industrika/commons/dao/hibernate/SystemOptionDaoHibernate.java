package com.industrika.commons.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.industrika.commons.dao.SystemOptionDao;
import com.industrika.commons.dto.Option;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.commons.validation.IndustrikaValidator;

/**
 * @author jose.arellano
 */
@Repository("systemOptionDaoHibernate")
public class SystemOptionDaoHibernate extends JpaDao<Integer, Option> implements
		SystemOptionDao {

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Option> find(Option option, String[] orderFields)
			throws IndustrikaObjectNotFoundException,
			IndustrikaPersistenceException {
		if(option.getId() != null && option.getId()>0){
			option = findById(option.getId());
			List<Option> res = new ArrayList<Option>();
			res.add(option);
			return res;
		}
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Option.class,"op");
		List<Option> results = null;
		try{
			if (option != null){
				criteria.createAlias("op.module", "mod");
				if (!StringUtils.isEmpty(option.getResourceName())){
					criteria.add(Restrictions.like("resourceName", "%"+option.getResourceName()+"%"));
				}
				if (!StringUtils.isEmpty(option.getText())){
					criteria.add(Restrictions.like("text", "%"+option.getText()+"%"));
				}
				if (option.getModule() != null && !StringUtils.isEmpty(option.getModule().getName())){
					criteria.add(Restrictions.like("mod.name", "%"+
							option.getModule().getName()+"%"));
				}
			}
			super.addOrderByFields(criteria, orderFields);
			
			results = criteria.list();
		}catch(Exception ex){
			throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_executequery")+
					option.getClass(), ex);
		}
		
		return results;
	}

	@Autowired
	@Qualifier("optionValidator")
	public void setValidator(IndustrikaValidator<Option> validator){
		this.validator = validator;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public String getFactory(String resourceName) throws IndustrikaObjectNotFoundException{
		String factory = null;
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Option.class,"op");
		List<Option> results = null;
		try{
			if (!StringUtils.isEmpty(resourceName)){
				criteria.add(Restrictions.eq("resourceName", resourceName));
			}
			results = criteria.list();
			if (results != null){
				if (results.size() >= 1){
					factory = results.get(0).getModule().getFactory();
				} else {
					throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_morethanoneobject"));				
				}
			} else {
				throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_objectnotfound"));
			}
		}catch(Exception ex){
			throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_executequery")+
					new Option().getClass(), ex);
		}		

		return factory;
	}
}
