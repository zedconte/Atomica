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

import com.industrika.commons.dao.WarehouseDao;
import com.industrika.commons.dto.Warehouse;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.commons.validation.IndustrikaValidator;

@Repository("warehouseDaoHibernate")
public class WarehouseDaoHibernate extends JpaDao<Integer, Warehouse> implements WarehouseDao {

	@Override
	@Transactional
	public List<Warehouse> find(Warehouse warehouse, String[] sortFields) throws IndustrikaObjectNotFoundException,
			IndustrikaPersistenceException {

		List<Warehouse> warehouses = new ArrayList<Warehouse>();

		if (warehouse.getIdWarehouse() != null
				&& warehouse.getIdWarehouse() > 0) {
			try {
				warehouse = findById(warehouse.getIdWarehouse());
			} catch (IndustrikaPersistenceException ex) {
				throw new RuntimeException(ex);
			}

			if (warehouse != null)
				warehouses.add(warehouse);
			return warehouses;
		}
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Warehouse.class);

		try {

			if (warehouse != null) {
				if (!StringUtils.isEmpty(warehouse.getName())) {
					criteria.add(Restrictions.like("name", "%" + warehouse.getName() + "%"));
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
			throw new IndustrikaObjectNotFoundException(
					CommonsMessages
							.getMessage("error_persistence_executequery")
							+ warehouse.getClass());
		}

		@SuppressWarnings("unchecked")
		List<Warehouse> list = criteria.list();
		return list;
	}

	@Autowired
	@Qualifier("warehouseValidator")
	public void setValidator(IndustrikaValidator<Warehouse> validator) {
		this.validator = validator;
	}
}
