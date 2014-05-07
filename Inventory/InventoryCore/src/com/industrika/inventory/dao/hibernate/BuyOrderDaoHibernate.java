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
import com.industrika.inventory.dao.BuyOrderDao;
import com.industrika.inventory.dao.DocumentFolioDao;
import com.industrika.inventory.dto.BuyOrder;
import com.industrika.inventory.dto.DocumentFolio;
import com.industrika.inventory.i18n.InventoryMessages;
import com.industrika.inventory.validation.BuyOrderValidator;

@Repository("buyorderDao")
public class BuyOrderDaoHibernate extends RootDao implements BuyOrderDao {

	private static final String TITLE = InventoryMessages.getMessage("buyorder.Title");
	private static final int DOCUMENT_TYPE = 1;
	private BuyOrderValidator validator;
	@Autowired
	private DocumentFolioDao daoFolio;
	private DocumentFolio folio;
	
	@Autowired
	@Qualifier("buyorderValidator")
	public void setValidator(BuyOrderValidator validator) {
		this.validator = validator;
	}
	
	private void addFolios(){
		if (folio == null){
			DocumentFolio temp=new DocumentFolio();
			temp.setDocumentType(DOCUMENT_TYPE);
			temp.setDocumentName(TITLE);
			folio = daoFolio.addDocumentFolio(temp);
		}
	}
	
	@Transactional
	private void alreadyExist(BuyOrder buyorder) 
			throws 	IndustrikaAlreadyExistException {
		BuyOrder toFind = new BuyOrder();
		BuyOrder found = new BuyOrder();
		try{
			toFind = new BuyOrder();
			toFind.setFolio(buyorder.getFolio());
			found = get(toFind);
		}catch(Exception ex){
			if (!(ex instanceof IndustrikaObjectNotFoundException)){
				ex.printStackTrace();
			}
			found = null;
		}		
		if (found != null){
			if (buyorder.getIdDocument() == null || 
					buyorder.getIdDocument().intValue() != found.getIdDocument().intValue()){
				found = null;
				throw new IndustrikaAlreadyExistException(
						"Ese folio ya fue utilizado.");
			}
		}
	}
	
	@Override
	@Transactional
	public Integer add(BuyOrder buyorder) 
			throws 	IndustrikaValidationException,
					IndustrikaPersistenceException, 
					IndustrikaAlreadyExistException {
		Integer id = null;
		addFolios();
		buyorder.setFolio(daoFolio.getFolio(DOCUMENT_TYPE));
		validator.validate(buyorder);
		alreadyExist(buyorder);
		try{
			id = (Integer) sessionFactory.getCurrentSession().save(buyorder);
		}catch(Exception ex){
			throw new IndustrikaPersistenceException(
					CommonsMessages.getMessage("error_persistence_add")+TITLE,
					ex);
		}
		return id;
	}

	@Override
	@Transactional
	public void update(BuyOrder buyorder) 
			throws 	IndustrikaValidationException,
					IndustrikaPersistenceException, 
					IndustrikaAlreadyExistException, 
					IndustrikaObjectNotFoundException {
		validator.validate(buyorder);
		alreadyExist(buyorder);
		try{
			if (buyorder.getIdDocument() == null || buyorder.getIdDocument().intValue() <= 0){
				throw new IndustrikaObjectNotFoundException(
						CommonsMessages.getMessage("error_persistence_objectnotfound"));
			} else {
				try{
					sessionFactory.getCurrentSession().saveOrUpdate(buyorder);
				}catch(NonUniqueObjectException nu){
					sessionFactory.getCurrentSession().merge(buyorder);
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
	public void delete(BuyOrder buyorder)
			throws 	IndustrikaPersistenceException,
					IndustrikaObjectNotFoundException {
		if (buyorder.getIdDocument() != null && buyorder.getIdDocument().intValue() > 0){
			delete(buyorder.getIdDocument());
		} else {
			BuyOrder temp = get(buyorder);
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
	public void delete(Integer idBuyOrder)
			throws 	IndustrikaPersistenceException,
					IndustrikaObjectNotFoundException {
		if (idBuyOrder != null && idBuyOrder.intValue() > 0){
			BuyOrder temp = (BuyOrder) sessionFactory.getCurrentSession().get(BuyOrder.class, idBuyOrder);
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
	public BuyOrder get(BuyOrder buyorder)
			throws 	IndustrikaPersistenceException,
					IndustrikaObjectNotFoundException {
		if (buyorder.getIdDocument() != null && buyorder.getIdDocument().intValue() > 0){
			return get(buyorder.getIdDocument());
		} else {
			List<BuyOrder> results=find(buyorder,null);
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
	public BuyOrder get(Integer idBuyOrder)
			throws 	IndustrikaPersistenceException,
					IndustrikaObjectNotFoundException {
		BuyOrder dto = null;
		try{
			dto = (BuyOrder) sessionFactory.getCurrentSession().get(BuyOrder.class, idBuyOrder);
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
	public List<BuyOrder> find(BuyOrder buyorder, String[] order)
			throws 	IndustrikaPersistenceException,
					IndustrikaObjectNotFoundException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(BuyOrder.class);
		try{
			if (buyorder != null){
				if (buyorder.getIdDocument() != null && buyorder.getIdDocument().intValue() > 0){
					criteria.add(Restrictions.eq("idDocument", buyorder.getIdDocument().intValue()));
				} else {
					if (!StringUtils.isEmpty(buyorder.getFolio())){
						criteria.add(Restrictions.like("folio", "%"+buyorder.getFolio()+"%"));
					}
					if (!StringUtils.isEmpty(buyorder.getPayType())){
						criteria.add(Restrictions.like("payType", "%"+buyorder.getPayType()+"%"));
					}		
					if (!StringUtils.isEmpty(buyorder.getReference())){
						criteria.add(Restrictions.like("reference", "%"+buyorder.getReference()+"%"));
					}		
					if (buyorder.getProvider() != null && buyorder.getProvider().getIdPerson() != null && buyorder.getProvider().getIdPerson().intValue() > 0){
						criteria.add(Restrictions.eq("provider.idPerson", buyorder.getProvider().getIdPerson()));
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
		List<BuyOrder> results = Collections.checkedList(criteria.list(),BuyOrder.class);
		if (results == null || results.size() <= 0){
			throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_objectnotfound"));
		}
		return results;
	}

}
