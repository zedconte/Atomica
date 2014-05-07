/**
 * 
 */
package com.industrika.commons.dao.hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.industrika.commons.dao.RoleDao;
import com.industrika.commons.dao.UserDao;
import com.industrika.commons.dto.Role;
import com.industrika.commons.dto.User;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.commons.i18n.CommonsMessages;

/**
 * @author jose.arellano
 */
@Repository("userDaoHibernate")
public class UserDaoHibernate extends JpaDao<Integer, User> implements UserDao {

	@Autowired
	private RoleDao roleDao;
	
	@Transactional
	public User findUserWithRoles(Integer idUser) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException{
		User u = findById(idUser);
		u.getRoles().size();
		
		return u;
	}
	
	/** (non-Javadoc)
	 * @throws IndustrikaPersistenceException 
	 * @throws IndustrikaObjectNotFoundException 
	 * @see com.industrika.commons.dao.UserDao#setRoles(User,Set<Roles>)
	 */
	@Override
	@Transactional
	public User setRoles(User user,Set<Role> newRoles) throws 
			IndustrikaObjectNotFoundException, IndustrikaPersistenceException{
		
		user = findById(user.getId());
		
		user.getRoles().clear();
		
		user.getRoles().addAll(newRoles);
		return user;
	}
	
	/** (non-Javadoc)
	 * @throws IndustrikaPersistenceException 
	 * @throws IndustrikaObjectNotFoundException 
	 * @see com.industrika.commons.dao.UserDao#setRoles(User,Set<Roles>)
	 */
	@Override
	@Transactional
	public User addRole(User user, Role role) throws 
			IndustrikaObjectNotFoundException, IndustrikaPersistenceException{
		
		user = findById(user.getId());
		
		user.getRoles().add(role);
		
		try {
			update(user);
			return user;
		} catch (IndustrikaValidationException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	@Transactional
	public List<User> find(User user, String[] orderFields) throws IndustrikaObjectNotFoundException {
		if(user.getId() != null && user.getId()>0){
			try {
				user = findById(user.getId());
			} catch (IndustrikaPersistenceException e) {
				throw new RuntimeException(e);
			}
			List<User> result = new ArrayList<User>();
			if ( user != null)
				result.add(user);
			return result;
		}
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
		try{
			if (user != null){
				if (!StringUtils.isEmpty(user.getCode())){
					criteria.add(Restrictions.like("code", "%"+user.getCode()+"%"));
				}
				if (!StringUtils.isEmpty(user.getEmail())){
					criteria.add(Restrictions.like("email", "%"+user.getEmail()+"%"));
				}
				if (!StringUtils.isEmpty(user.getLastTransactionDate())){
					criteria.add(Restrictions.eq("lastTransactionDate", user.getLastTransactionDate()));
				}
			}
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			if (orderFields != null && orderFields.length > 0){
				for (int a=0;a<orderFields.length;a++){
					if (orderFields[a] != null && !orderFields[a].trim().equalsIgnoreCase("")){
						criteria.addOrder(Order.asc(orderFields[a]));
					}
				}
			}
		}catch(Exception ex){
			throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_executequery")+user.getClass(), ex);
		}
		
		@SuppressWarnings("unchecked")
		List<User> results = criteria.list();
		return results;
	}

	@Override
	@Transactional
	public User authenticateUser(String userName, String password) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
		User user = null;
		try{
			if (!StringUtils.isEmpty(userName) && !StringUtils.isEmpty(password)){
				criteria.add(Restrictions.eq("code", userName));
				criteria.add(Restrictions.eq("password", password));
				user = (User) criteria.list().get(0);
				user.getRoles().size();
			}
		}catch(Exception ex){
			user = null;
			ex.getMessage();
		}
		return user;
	}

}
