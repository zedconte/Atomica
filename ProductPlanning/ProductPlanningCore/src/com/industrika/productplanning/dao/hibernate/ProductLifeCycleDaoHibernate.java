package com.industrika.productplanning.dao.hibernate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.industrika.commons.dao.hibernate.JpaDao;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.commons.validation.IndustrikaValidator;
import com.industrika.productplanning.dto.ProductLifeCycle;
import com.industrika.productplanning.dao.ProductLifeCycleDao;

@Repository("productLifeCycleDaoHibernate")
public class ProductLifeCycleDaoHibernate extends JpaDao<Integer, ProductLifeCycle> implements ProductLifeCycleDao {

	@Override
	@Transactional
	public List<ProductLifeCycle> find(ProductLifeCycle productLifeCycle,
			String[] sortFields) throws IndustrikaObjectNotFoundException,
			IndustrikaPersistenceException {
     
		List<ProductLifeCycle> products = new ArrayList<ProductLifeCycle>();

		if (productLifeCycle.getIdProductLifeCycle() != null
				&& productLifeCycle.getIdProductLifeCycle() > 0) {
			try {
				productLifeCycle = findById(productLifeCycle.getIdProductLifeCycle());
			} catch (IndustrikaPersistenceException ex) {
				throw new RuntimeException(ex);
			}

			if (productLifeCycle != null)
				products.add(productLifeCycle);
			return products;
		}
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ProductLifeCycle.class);

			try {
				
				if (productLifeCycle != null) {
					if (productLifeCycle.getIdProductLifeCycle() != null && productLifeCycle.getIdProductLifeCycle().intValue() > 0){
						criteria.add(Restrictions.eq("idProductLifeCycle", productLifeCycle.getIdProductLifeCycle()));
					}
					if (!StringUtils.isEmpty(productLifeCycle.getName())) {
						criteria.add(Restrictions.like("name", "%" + productLifeCycle.getName() + "%"));
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
				throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_executequery") + productLifeCycle.getClass());
			}

			@SuppressWarnings("unchecked")
			List<ProductLifeCycle> list = criteria.list();
			return list;	
	}

	@Autowired
	@Qualifier("productLifeCycleValidator")
	public void setValidator(IndustrikaValidator<ProductLifeCycle> validator) {
		this.validator = validator;
	}
}
