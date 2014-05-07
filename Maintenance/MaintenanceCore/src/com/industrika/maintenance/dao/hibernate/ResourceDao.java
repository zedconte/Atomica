package com.industrika.maintenance.dao.hibernate;

import org.hibernate.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.industrika.commons.validation.IndustrikaValidator;
import com.industrika.maintenance.dao.IResourceDao;
import com.industrika.maintenance.dto.Resource;

@Repository("ResourceDaoHibernate")
public class ResourceDao extends BaseDao<Resource> implements IResourceDao {

	@Override
	protected void setFilters(Resource dto, Criteria criteria) {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Autowired
	@Qualifier("resourceValidator")
	public void setValidator(IndustrikaValidator<Resource> validator) {
		// TODO Auto-generated method stub
		if (validator==null) throw new IllegalArgumentException("Validator should not be null");
		
		this.validator = validator;
	}

}
