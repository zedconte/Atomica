package com.industrika.inventory.dao.hibernate;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.industrika.commons.dao.hibernate.RootDao;
import com.industrika.commons.exceptions.IndustrikaAlreadyExistException;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.inventory.dao.InventoryDao;
import com.industrika.inventory.dao.InventoryMovementDao;
import com.industrika.inventory.dto.InventoryMovement;
import com.industrika.inventory.i18n.InventoryMessages;
import com.industrika.inventory.validation.InventoryMovementValidator;

@Repository("inventorymovementDao")
public class InventoryMovementDaoHibernate extends RootDao implements InventoryMovementDao {

	private static final String TITLE = InventoryMessages.getMessage("inventorymovement.Title");
	private InventoryMovementValidator validator;
	@Autowired
	private InventoryDao inventoryDao;
	
	@Autowired
	public void setValidator(InventoryMovementValidator validator) {
		this.validator = validator;
	}
		
	@Override
	@Transactional
	public Integer add(InventoryMovement dto) 
			throws 	IndustrikaValidationException,
					IndustrikaPersistenceException, 
					IndustrikaAlreadyExistException {
		validator.validate(dto);
		Integer id = null;
		try{
			id = (Integer) sessionFactory.getCurrentSession().save(dto);
			inventoryDao.updateInventory(dto.getItem(),dto.getBranch(),dto.getWarehouse(),dto.getQuantity(),dto.getType());
		}catch(Exception ex){
			throw new IndustrikaPersistenceException(
					CommonsMessages.getMessage("error_persistence_add")+TITLE,
					ex);
		}
		return id;
	}

	@Override
	@Transactional
	public void update(InventoryMovement dto) 
			throws 	IndustrikaValidationException,
					IndustrikaPersistenceException, 
					IndustrikaAlreadyExistException, 
					IndustrikaObjectNotFoundException {
	}

	@Override
	@Transactional
	public void delete(InventoryMovement dto)
			throws 	IndustrikaPersistenceException,
					IndustrikaObjectNotFoundException {
	}

	@Override
	@Transactional
	public void delete(Integer idInventoryMovement)
			throws 	IndustrikaPersistenceException,
					IndustrikaObjectNotFoundException {
	}

	@Override
	@Transactional
	public InventoryMovement get(InventoryMovement dto)
			throws 	IndustrikaPersistenceException,
					IndustrikaObjectNotFoundException {
		if (dto.getIdMovement() != null && dto.getIdMovement().intValue() > 0){
			return get(dto.getIdMovement());
		} else {
			List<InventoryMovement> results=find(dto,null);
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
	public InventoryMovement get(Integer idInventoryMovement)
			throws 	IndustrikaPersistenceException,
					IndustrikaObjectNotFoundException {
		InventoryMovement dto = null;
		try{
			dto = (InventoryMovement) sessionFactory.getCurrentSession().get(InventoryMovement.class, idInventoryMovement);
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
	public List<InventoryMovement> find(InventoryMovement dto, String[] order)
			throws 	IndustrikaPersistenceException,
					IndustrikaObjectNotFoundException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(InventoryMovement.class);
		try{
			if (dto != null){
				if (dto.getIdMovement() != null && dto.getIdMovement().intValue() > 0){
					criteria.add(Restrictions.eq("idMovement", dto.getIdMovement().intValue()));
				} else {
					if (dto.getBranch() != null && dto.getBranch().getIdBranch() != null && dto.getBranch().getIdBranch().intValue() > 0){
						criteria.add(Restrictions.eq("branch.idBranch",dto.getBranch().getIdBranch()));
					}
					if (dto.getWarehouse() != null && dto.getWarehouse().getIdWarehouse() != null && dto.getWarehouse().getIdWarehouse().intValue() > 0){
						criteria.add(Restrictions.eq("warehouse.idWarehouse",dto.getWarehouse().getIdWarehouse()));
					}
					if (dto.getItem() != null && dto.getItem().getIdItem() != null && dto.getItem().getIdItem().intValue() > 0){
						criteria.add(Restrictions.eq("item.idItem",dto.getItem().getIdItem()));
					}
					if (dto.getConcept() != null && dto.getConcept().getIdMovementConcept() != null && dto.getConcept().getIdMovementConcept().intValue() > 0){
						criteria.add(Restrictions.eq("concept.idMovementConcept",dto.getConcept().getIdMovementConcept()));
					}
					if (dto.getDate() != null){
						Calendar begin = Calendar.getInstance();
						begin.set(dto.getDate().get(Calendar.YEAR), dto.getDate().get(Calendar.MONTH), dto.getDate().get(Calendar.DAY_OF_MONTH));
						begin.set(Calendar.HOUR, 0);
						begin.set(Calendar.MINUTE, 0);
						begin.set(Calendar.SECOND, 0);
						begin.set(Calendar.MILLISECOND, 0);
						Calendar end = Calendar.getInstance();
						end.set(dto.getDate().get(Calendar.YEAR), dto.getDate().get(Calendar.MONTH), dto.getDate().get(Calendar.DAY_OF_MONTH));
						end.set(Calendar.HOUR, 23);
						end.set(Calendar.MINUTE, 59);
						end.set(Calendar.SECOND, 50);
						end.set(Calendar.MILLISECOND, 999);
						criteria.add(Restrictions.between("date",begin, end));
					}
					if (!StringUtils.isEmpty(dto.getReference())){
						criteria.add(Restrictions.like("reference","%"+dto.getReference()+"%"));
					}
					if (!StringUtils.isEmpty(dto.getSerial())){
						criteria.add(Restrictions.like("serial","%"+dto.getSerial()+"%"));
					}
					if (dto.getType() != null){
						criteria.add(Restrictions.eq("type",dto.getType().intValue()));
					}
				}
			}
			if (order != null && order.length > 0){
				for (int a=0;a<order.length;a++){
					if (order[a] != null && !order[a].trim().equalsIgnoreCase("")){
						if (order[a].indexOf("item") > -1){
							criteria.createAlias("item", "item");
						}
						if (order[a].indexOf("warehouse") > -1){
							criteria.createAlias("warehouse", "warehouse");
						}
						if (order[a].indexOf("branch") > -1){
							criteria.createAlias("branch", "branch");
						}
						if (order[a].indexOf("concept") > -1){
							criteria.createAlias("concept", "concept");
						}
						criteria.addOrder(Order.asc(order[a]));
					}
				}
			}
		}catch(Exception ex){
			throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_executequery")+TITLE, ex);
		}
		List<InventoryMovement> results = Collections.checkedList(criteria.list(),InventoryMovement.class);
		if (results == null || results.size() <= 0){
			throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_objectnotfound"));
		}
		return results;
	}
}
