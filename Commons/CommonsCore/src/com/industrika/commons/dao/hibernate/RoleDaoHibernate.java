package com.industrika.commons.dao.hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.industrika.commons.dao.RoleDao;
import com.industrika.commons.dto.Privilege;
import com.industrika.commons.dto.Role;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.commons.validation.IndustrikaValidator;

@Repository("roleDaoHibernate")
public class RoleDaoHibernate extends JpaDao<Integer, Role> implements RoleDao {

	private static final String TITLE = CommonsMessages.getMessage("role");
	
	@Override
	@Transactional
	public List<Role> find(Role dto, String[] orderFields) throws IndustrikaPersistenceException,
				IndustrikaObjectNotFoundException{
		if(dto.getId() != null && dto.getId()>0){
			dto = findById(dto.getId());
			List<Role> listRoles = new ArrayList<Role>();
			if(dto !=null){
				listRoles.add(dto);
			}
			return listRoles;
		}
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Role.class);
		try{
			if (dto != null){
				if (!StringUtils.isEmpty(dto.getName())){
					criteria.add(Restrictions.like("name", "%"+dto.getName()+"%"));
				}
				if (!StringUtils.isEmpty(dto.getInitials())){
					criteria.add(Restrictions.like("initials", "%"+dto.getInitials()+"%"));
				}
			}
			if (orderFields != null && orderFields.length > 0){
				for (int a=0;a<orderFields.length;a++){
					if (orderFields[a] != null && !orderFields[a].trim().equalsIgnoreCase("")){
						criteria.addOrder(Order.asc(orderFields[a]));
					}
				}
			}
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		}catch(Exception ex){
			throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_executequery")+TITLE, ex);
		}
		@SuppressWarnings("unchecked")
		List<Role> results = criteria.list();
		if (results == null || results.size() <= 0){
			throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_objectnotfound"));
		}
		return results;
	}

	@Autowired
	@Qualifier("roleValidator")
	public void setValidator(IndustrikaValidator<Role> validator){
		this.validator = validator;
	}

	@Override
	@Transactional
	public Role setPrivileges(Role role, Set<Privilege> newPrivileges)
			throws IndustrikaObjectNotFoundException,
			IndustrikaPersistenceException {
		role = findById(role.getId());
		
		role.getPrivileges().clear();
		
		role.getPrivileges().addAll(newPrivileges);
		return role;
	}

	@Override
	@Transactional
	public Role addPrivilege(Role role, Privilege privilege)
			throws IndustrikaObjectNotFoundException,
			IndustrikaPersistenceException, IndustrikaValidationException {
		role = findById(role.getId());
		
		role.getPrivileges().add(privilege);
		update(role);
		return role;
	}
}
