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

import com.industrika.commons.dao.SystemActionDao;
import com.industrika.commons.dto.Action;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.commons.validation.IndustrikaValidator;

/**
 * @author jose.arellano
 */
@Repository("systemActionDaoHibernate")
public class SystemActionDaoHibernate extends JpaDao<Integer, Action> implements
		SystemActionDao {

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Action> find(Action action, String[] orderFields)
			throws IndustrikaObjectNotFoundException, IndustrikaPersistenceException {
		if(action.getId() != null && action.getId() > 0){
			action = findById(action.getId());
			List<Action> list = new ArrayList<Action>();
			list.add(action);
			return list;
		}
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Action.class);
		List<Action> results = null;
		try{
			if (action != null){
				if (!StringUtils.isEmpty(action.getType())){
					criteria.add(Restrictions.like("type", "%"+action.getType()+"%"));
				}
				if (!StringUtils.isEmpty(action.getDescription())){
					criteria.add(Restrictions.like("description", "%"+action.getDescription()+"%"));
				}
			}
			super.addOrderByFields(criteria, orderFields);
			
			results = criteria.list();
			
		}catch(Exception ex){
			throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_executequery")+
					action.getClass(), ex);
		}
		
		return results;
	}

	@Autowired
	@Qualifier("actionValidator")
	public void setValidator(IndustrikaValidator<Action> validator){
		this.validator = validator;
	}
}
