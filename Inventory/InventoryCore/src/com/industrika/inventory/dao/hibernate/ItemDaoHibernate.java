package com.industrika.inventory.dao.hibernate;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.industrika.commons.dao.hibernate.RootDao;
import com.industrika.commons.exceptions.IndustrikaAlreadyExistException;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.inventory.dao.ItemDao;
import com.industrika.inventory.dto.Item;
import com.industrika.inventory.i18n.InventoryMessages;
import com.industrika.inventory.validation.ItemValidator;

@Repository("itemDao")
public class ItemDaoHibernate extends RootDao implements ItemDao {

	private static final String TITLE = InventoryMessages.getMessage("item.Title");
	private ItemValidator validator;

	@Autowired
	@Qualifier("itemValidator")
	public void setValidator(ItemValidator validator) {
		this.validator = validator;
	}
	
	@Transactional
	private void alreadyExist(Item item) 
			throws 	IndustrikaAlreadyExistException {
		Item toFind = new Item();
		Item found = new Item();
		try{
			toFind = new Item();
			toFind.setCode(item.getCode());
			found = get(toFind);
		}catch(Exception ex){
			if (!(ex instanceof IndustrikaObjectNotFoundException)){
				ex.printStackTrace();
			}
			found = null;
		}		
		if (found != null){
			if (item.getIdItem() == null || 
					item.getIdItem().intValue() != found.getIdItem().intValue()){
				found = null;
				throw new IndustrikaAlreadyExistException(
						"Ya existe un artículo con "+InventoryMessages.getMessage("item.Code")+ " igual.");
			}
		}
		found = null;
	}
	
	@Override
	@Transactional
	public Integer add(Item item) 
			throws 	IndustrikaValidationException,
					IndustrikaPersistenceException, 
					IndustrikaAlreadyExistException {
		validator.validate(item);
		Integer id = null;
		alreadyExist(item);
		try{
			id = (Integer) sessionFactory.getCurrentSession().save(item);
		}catch(Exception ex){
			throw new IndustrikaPersistenceException(
					CommonsMessages.getMessage("error_persistence_add")+TITLE,
					ex);
		}
		return id;
	}

	@Override
	@Transactional
	public void update(Item item) 
			throws 	IndustrikaValidationException,
					IndustrikaPersistenceException, 
					IndustrikaAlreadyExistException, 
					IndustrikaObjectNotFoundException {
		validator.validate(item);
		alreadyExist(item);
		try{
			if (item.getIdItem() == null || item.getIdItem().intValue() <= 0){
				throw new IndustrikaObjectNotFoundException(
						CommonsMessages.getMessage("error_persistence_objectnotfound"));
			} else {
				try{
					sessionFactory.getCurrentSession().saveOrUpdate(item);
				}catch(NonUniqueObjectException nu){
					sessionFactory.getCurrentSession().merge(item);
				}
			}
		}catch(IndustrikaObjectNotFoundException onfe){
			throw onfe;
		}catch(Exception ex){
			ex.printStackTrace();
			throw new IndustrikaPersistenceException(
					CommonsMessages.getMessage("error_persistence_update")+TITLE,
					ex);
		}

	}

	@Override
	@Transactional
	public void delete(Item item)
			throws 	IndustrikaPersistenceException,
					IndustrikaObjectNotFoundException {
		if (item.getIdItem() != null && item.getIdItem().intValue() > 0){
			delete(item.getIdItem());
		} else {
			Item temp = get(item);
			try{
				sessionFactory.getCurrentSession().delete(temp);
			}catch(Exception ex){
				throw new IndustrikaPersistenceException(
						CommonsMessages.getMessage("error_persistence_delete")+TITLE,
						ex);
			}
		}
	}

	@Override
	@Transactional
	public void delete(Integer idItem)
			throws 	IndustrikaPersistenceException,
					IndustrikaObjectNotFoundException {
		if (idItem != null && idItem.intValue() > 0){
			Item temp = (Item) sessionFactory.getCurrentSession().get(Item.class, idItem);
			try{
				sessionFactory.getCurrentSession().delete(temp);
			}catch(Exception ex){
				throw new IndustrikaPersistenceException(
						CommonsMessages.getMessage("error_persistence_delete")+TITLE,
						ex);
			}
		} else {
			throw new IndustrikaPersistenceException(
					CommonsMessages.getMessage("error_persistence_objectnotfound"));
		}
	}

	@Override
	@Transactional
	public Item get(Item item)
			throws 	IndustrikaPersistenceException,
					IndustrikaObjectNotFoundException {
		if (item.getIdItem() != null && item.getIdItem().intValue() > 0){
			return get(item.getIdItem());
		} else {
			List<Item> results=find(item,null);
			if (results.size() > 1){
				throw new IndustrikaObjectNotFoundException(
						CommonsMessages.getMessage("error_persistence_morethanoneobject"));
			} else {
				return results.get(0);
			}
		}
	}

	@Override
	@Transactional
	public Item get(Integer idItem)
			throws 	IndustrikaPersistenceException,
					IndustrikaObjectNotFoundException {
		Item dto = null;
		try{
			dto = (Item) sessionFactory.getCurrentSession().get(Item.class, idItem);
			if (dto == null){
				throw new IndustrikaObjectNotFoundException(
						CommonsMessages.getMessage("error_persistence_objectnotfound"));
			}
		}catch(IndustrikaObjectNotFoundException onfe){
			throw onfe;
		}catch(Exception ex){
			throw new IndustrikaPersistenceException(
					CommonsMessages.getMessage("error_persistence_executequery")+TITLE,
					ex);
		}		
		return dto;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Item> find(Item item, String[] order)
			throws 	IndustrikaPersistenceException,
					IndustrikaObjectNotFoundException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Item.class);
		try{
			if (item != null){
				if (item.getIdItem() != null && item.getIdItem().intValue() > 0){
					criteria.add(Restrictions.eq("idItem", item.getIdItem().intValue()));
				} else {
					if (!StringUtils.isEmpty(item.getName())){
						criteria.add(Restrictions.like("name", "%"+item.getName()+"%"));
					}
					if (!StringUtils.isEmpty(item.getCode())){
						criteria.add(Restrictions.like("code", "%"+item.getCode()+"%"));
					}		
					if (!StringUtils.isEmpty(item.getDescription())){
						criteria.add(Restrictions.like("description", "%"+item.getDescription()+"%"));
					}		
					if (item.getPrice()!=null && item.getPrice().doubleValue() > 0){
						criteria.add(Restrictions.eq("price", item.getPrice()));
					}		
					if (item.getCost()!=null && item.getCost().doubleValue() > 0){
						criteria.add(Restrictions.eq("cost", item.getCost()));
					}		
					if (item.getProvider() != null && item.getProvider().getIdPerson() != null && item.getProvider().getIdPerson() > 0){
						criteria.add(Restrictions.eq("provider.idPerson",item.getProvider().getIdPerson()));
					}
					if (item.getTrademark() != null && item.getTrademark().getIdTrademark() != null && item.getTrademark().getIdTrademark() > 0){
						criteria.add(Restrictions.eq("trademark.idTrademark",item.getTrademark().getIdTrademark()));
					}
				}
			}
			if (order != null && order.length > 0){
				for (int a=0;a<order.length;a++){
					if (order[a] != null && !order[a].trim().equalsIgnoreCase("")){
						criteria.addOrder(Order.asc(order[a]));
					}
				}
			}
		}catch(Exception ex){
			throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_executequery")+TITLE, ex);
		}
		List<Item> results = Collections.checkedList(criteria.list(),Item.class);
		if (results == null || results.size() <= 0){
			throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_objectnotfound"));
		}
		return results;
	}
}
