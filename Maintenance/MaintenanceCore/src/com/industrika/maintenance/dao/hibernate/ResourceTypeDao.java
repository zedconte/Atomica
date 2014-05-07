package com.industrika.maintenance.dao.hibernate;

import org.hibernate.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import com.industrika.commons.validation.IndustrikaValidator;
import com.industrika.maintenance.dao.IResourceTypeDao;
import com.industrika.maintenance.dto.ResourceType;

@Repository("ResourceTypeDaoHibernate")
public class ResourceTypeDao extends BaseDao<ResourceType> implements IResourceTypeDao {

	@Override
	protected void setFilters(ResourceType dto, Criteria criteria) {
		addLikeCriterion(criteria, "name", dto.getName());
	}

	
	@Autowired
	@Qualifier("resourceTypeValidator")
	public void setValidator(IndustrikaValidator<ResourceType> validator) {
		this.validator = validator;
	}

}
