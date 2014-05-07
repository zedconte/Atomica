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

import com.industrika.commons.dao.SystemPrivilegeDao;
import com.industrika.commons.dto.Privilege;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.commons.validation.IndustrikaValidator;

/**
 * @author jose.arellano
 */
@Repository("systemPrivilegeDaoHibernate")
public class SystemPrivilegeDaoHibernate extends JpaDao<Integer, Privilege> implements
		SystemPrivilegeDao {

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Privilege> find(Privilege pr, String[] orderFields)
			throws IndustrikaObjectNotFoundException,
			IndustrikaPersistenceException {
		if(pr.getId() != null && pr.getId()>0){
			pr = findById(pr.getId());
			List<Privilege> res = new ArrayList<Privilege>();
			if(pr != null){
				res.add(pr);
			}
			return res;
		}
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Privilege.class,"pr");
		List<Privilege> results = null;
		try{
			if (pr != null){
				criteria.createAlias("pr.action", "act");
				criteria.createAlias("pr.option", "opt");
				if (!StringUtils.isEmpty(pr.getName())){
					criteria.add(Restrictions.like("name", "%"+pr.getName()+"%"));
				}
				if (pr.getAction() != null && !StringUtils.isEmpty(pr.getAction().getType())){
					criteria.add(Restrictions.like("act.type", "%"+
							pr.getAction().getType()+"%"));
				}
				if (pr.getOption() != null && !StringUtils.isEmpty(pr.getOption().getResourceName())){
					criteria.add(Restrictions.like("opt.resourceName", "%"+
							pr.getOption().getResourceName()+"%"));
				}
			}
			super.addOrderByFields(criteria, orderFields);
			
			results = criteria.list();
		}catch(Exception ex){
			throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_executequery")+
					pr.getClass(), ex);
		}
		
		return results;
	}
	
	@Autowired
	@Qualifier("privilegeValidator")
	public void setValidator(IndustrikaValidator<Privilege> validator){
		this.validator = validator;
	}
}
