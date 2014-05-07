package com.industrika.commons.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.industrika.commons.dao.BranchDao;
import com.industrika.commons.dto.Branch;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.commons.validation.IndustrikaValidator;

@Repository("branchDaoHibernate")
public class BranchDaoHibernate extends JpaDao<Integer, Branch> implements BranchDao {

	@Override
	@Transactional
	public List<Branch> find(Branch branch, String[] sortFields) throws IndustrikaObjectNotFoundException, IndustrikaPersistenceException {
		List<Branch> branches = new ArrayList<Branch>();

		if (branch.getIdBranch() != null && branch.getIdBranch() > 0) {
			try {
				branch = findById(branch.getIdBranch());
			} catch (IndustrikaPersistenceException ex) {
				throw new RuntimeException(ex);
			}

			if (branch != null)
				branches.add(branch);
			return branches;
		}
			Criteria criteria = sessionFactory.getCurrentSession()
					.createCriteria(Branch.class);

			try {
				
				if (branch != null) {
					if (!StringUtils.isEmpty(branch.getName())) {
						criteria.add(Restrictions.like("name", "%" + branch.getName() + "%"));
					}
					
					if(!StringUtils.isEmpty(branch.getResponsibleName())){
						criteria.add(Restrictions.like("responsibleName", "%" + branch.getResponsibleName()+"%"));
					}
				}

				criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
				if (sortFields != null && sortFields.length > 0) {
					for (String field : sortFields) {
						if (field != null && StringUtils.isEmpty(field.trim())) {
							criteria.addOrder(Order.asc(field));
						}
					}
				}

			} catch (Exception ex) {
				throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_executequery") + branch.getClass());
			}

			@SuppressWarnings("unchecked")
			List<Branch> list = criteria.list();
			return list;
		
	}

	@Autowired
	@Qualifier("branchValidator")
	public void setValidator(IndustrikaValidator<Branch> validator) {
		this.validator = validator;
	}

}
