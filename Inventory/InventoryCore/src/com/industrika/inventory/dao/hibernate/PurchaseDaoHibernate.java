package com.industrika.inventory.dao.hibernate;

import java.util.Calendar;
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

import com.industrika.commons.dao.MovementConteptDao;
import com.industrika.commons.dao.hibernate.RootDao;
import com.industrika.commons.dto.MovementConcept;
import com.industrika.commons.exceptions.IndustrikaAlreadyExistException;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.inventory.dao.InventoryMovementDao;
import com.industrika.inventory.dao.PurchaseDao;
import com.industrika.inventory.dao.DocumentFolioDao;
import com.industrika.inventory.dto.DocumentRow;
import com.industrika.inventory.dto.InventoryMovement;
import com.industrika.inventory.dto.Purchase;
import com.industrika.inventory.dto.DocumentFolio;
import com.industrika.inventory.i18n.InventoryMessages;
import com.industrika.inventory.validation.PurchaseValidator;

@Repository("purchaseDao")
public class PurchaseDaoHibernate extends RootDao implements PurchaseDao {

	private static final String TITLE = InventoryMessages.getMessage("purchase.Title");
	private static final int DOCUMENT_TYPE = 2;
	private PurchaseValidator validator;
	@Autowired
	private DocumentFolioDao daoFolio;
	@Autowired
	@Qualifier("movementConceptDaoHibernate")
	private MovementConteptDao daoConcept;
	@Autowired
	private InventoryMovementDao daoInventory;
	
	private DocumentFolio folio;
	
	@Autowired
	@Qualifier("purchaseValidator")
	public void setValidator(PurchaseValidator validator) {
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
	private void alreadyExist(Purchase purchase) 
			throws 	IndustrikaAlreadyExistException {
		Purchase toFind = new Purchase();
		Purchase found = new Purchase();
		try{
			toFind = new Purchase();
			toFind.setFolio(purchase.getFolio());
			found = get(toFind);
		}catch(Exception ex){
			if (!(ex instanceof IndustrikaObjectNotFoundException)){
				ex.printStackTrace();
			}
			found = null;
		}		
		if (found != null){
			if (purchase.getIdDocument() == null || 
					purchase.getIdDocument().intValue() != found.getIdDocument().intValue()){
				found = null;
				throw new IndustrikaAlreadyExistException(
						"Ese folio ya fue utilizado.");
			}
		}
	}
	
	private MovementConcept getPurchaseConcept(){
		MovementConcept mc=null;
		MovementConcept find = new MovementConcept();
		find.setName("Por compra realizada");
		try{
			mc = daoConcept.find(find, null).get(0);
		}catch(Exception ex){
			ex.getMessage();
			mc = null;
		}
		if (mc == null || !mc.getName().equalsIgnoreCase(find.getName())){
			try{
				mc = new MovementConcept();
				mc.setName(find.getName());
				mc = daoConcept.save(mc);
			}catch(Exception ex){
				mc = null;
			}
		}
		return mc;
	}
	
	private MovementConcept getPurchaseConceptDelete(){
		MovementConcept mc=null;
		MovementConcept find = new MovementConcept();
		find.setName("Por cancelación de compra");
		try{
			mc = daoConcept.find(find, null).get(0);
		}catch(Exception ex){
			ex.getMessage();
			mc = null;
		}
		if (mc == null || !mc.getName().equalsIgnoreCase(find.getName())){
			try{
				mc = new MovementConcept();
				mc.setName(find.getName());
				mc = daoConcept.save(mc);
			}catch(Exception ex){
				mc = null;
			}
		}
		return mc;
	}

	private MovementConcept getPurchaseConceptUpdate(){
		MovementConcept mc=null;
		MovementConcept find = new MovementConcept();
		find.setName("Por cancelación de compra");
		try{
			mc = daoConcept.find(find, null).get(0);
		}catch(Exception ex){
			ex.getMessage();
			mc = null;
		}
		if (mc == null || !mc.getName().equalsIgnoreCase(find.getName())){
			try{
				mc = new MovementConcept();
				mc.setName(find.getName());
				mc = daoConcept.save(mc);
			}catch(Exception ex){
				mc = null;
			}
		}
		return mc;
	}

	@Override
	@Transactional
	public Integer add(Purchase purchase) 
			throws 	IndustrikaValidationException,
					IndustrikaPersistenceException, 
					IndustrikaAlreadyExistException {
		Integer id = null;
		addFolios();
		purchase.setFolio(daoFolio.getFolio(DOCUMENT_TYPE));
		validator.validate(purchase);
		alreadyExist(purchase);
		try{
			id = (Integer) sessionFactory.getCurrentSession().save(purchase);
			if (purchase.getRows() != null && purchase.getRows().size() > 0){
				MovementConcept mc = getPurchaseConcept();
				for (DocumentRow row : purchase.getRows()){
					InventoryMovement movement=new InventoryMovement();
					movement.setBranch(row.getBranch());
					movement.setDate(Calendar.getInstance());
					movement.setItem(row.getItem());
					movement.setQuantity(row.getQuantity());
					movement.setReference(TITLE + " - "+purchase.getFolio());
					movement.setType(0);
					movement.setWarehouse(row.getWarehouse());
					movement.setConcept(mc);
					daoInventory.add(movement);
				}
			}
		}catch(Exception ex){
			throw new IndustrikaPersistenceException(
					CommonsMessages.getMessage("error_persistence_add")+TITLE,
					ex);
		}
		return id;
	}

	@Override
	@Transactional
	public void update(Purchase purchase) 
			throws 	IndustrikaValidationException,
					IndustrikaPersistenceException, 
					IndustrikaAlreadyExistException, 
					IndustrikaObjectNotFoundException {
		validator.validate(purchase);
		alreadyExist(purchase);
		try{
			if (purchase.getIdDocument() == null || purchase.getIdDocument().intValue() <= 0){
				throw new IndustrikaObjectNotFoundException(
						CommonsMessages.getMessage("error_persistence_objectnotfound"));
			} else {
				Purchase old = get(purchase.getIdDocument());
				try{
					sessionFactory.getCurrentSession().saveOrUpdate(purchase);
				}catch(NonUniqueObjectException nu){
					sessionFactory.getCurrentSession().merge(purchase);
				}
				if (old.getRows() != null && old.getRows().size() > 0){
					MovementConcept mc = getPurchaseConceptUpdate();
					for (DocumentRow row : old.getRows()){
						InventoryMovement movement=new InventoryMovement();
						movement.setBranch(row.getBranch());
						movement.setDate(Calendar.getInstance());
						movement.setItem(row.getItem());
						movement.setQuantity(row.getQuantity());
						movement.setReference(TITLE + " - "+purchase.getFolio());
						movement.setType(1);
						movement.setWarehouse(row.getWarehouse());
						movement.setConcept(mc);
						daoInventory.add(movement);
					}
				}
				if (purchase.getRows() != null && purchase.getRows().size() > 0){
					MovementConcept mc = getPurchaseConceptUpdate();
					for (DocumentRow row : purchase.getRows()){
						InventoryMovement movement=new InventoryMovement();
						movement.setBranch(row.getBranch());
						movement.setDate(Calendar.getInstance());
						movement.setItem(row.getItem());
						movement.setQuantity(row.getQuantity());
						movement.setReference(TITLE + " - "+purchase.getFolio());
						movement.setType(0);
						movement.setWarehouse(row.getWarehouse());
						movement.setConcept(mc);
						daoInventory.add(movement);
					}
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
	public void delete(Purchase purchase)
			throws 	IndustrikaPersistenceException,
					IndustrikaObjectNotFoundException {
		if (purchase.getIdDocument() != null && purchase.getIdDocument().intValue() > 0){
			delete(purchase.getIdDocument());
		} else {
			Purchase temp = get(purchase);
			try{
				sessionFactory.getCurrentSession().delete(temp);
				if (purchase.getRows() != null && purchase.getRows().size() > 0){
					MovementConcept mc = getPurchaseConceptDelete();
					for (DocumentRow row : purchase.getRows()){
						InventoryMovement movement=new InventoryMovement();
						movement.setBranch(row.getBranch());
						movement.setDate(Calendar.getInstance());
						movement.setItem(row.getItem());
						movement.setQuantity(row.getQuantity());
						movement.setReference(TITLE + " - "+purchase.getFolio());
						movement.setType(1);
						movement.setWarehouse(row.getWarehouse());
						movement.setConcept(mc);
						daoInventory.add(movement);
					}
				}
			}catch(Exception ex){
				throw new IndustrikaPersistenceException(
						CommonsMessages.getMessage("error_persistence_delete")+TITLE,
						ex);
			}
		}
	}

	@Override
	@Transactional
	public void delete(Integer idPurchase)
			throws 	IndustrikaPersistenceException,
					IndustrikaObjectNotFoundException {
		if (idPurchase != null && idPurchase.intValue() > 0){
			Purchase temp = (Purchase) sessionFactory.getCurrentSession().get(Purchase.class, idPurchase);
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
	public Purchase get(Purchase purchase)
			throws 	IndustrikaPersistenceException,
					IndustrikaObjectNotFoundException {
		if (purchase.getIdDocument() != null && purchase.getIdDocument().intValue() > 0){
			return get(purchase.getIdDocument());
		} else {
			List<Purchase> results=find(purchase,null);
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
	public Purchase get(Integer idPurchase)
			throws 	IndustrikaPersistenceException,
					IndustrikaObjectNotFoundException {
		Purchase dto = null;
		try{
			dto = (Purchase) sessionFactory.getCurrentSession().get(Purchase.class, idPurchase);
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
	public List<Purchase> find(Purchase purchase, String[] order)
			throws 	IndustrikaPersistenceException,
					IndustrikaObjectNotFoundException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Purchase.class);
		try{
			if (purchase != null){
				if (purchase.getIdDocument() != null && purchase.getIdDocument().intValue() > 0){
					criteria.add(Restrictions.eq("idDocument", purchase.getIdDocument().intValue()));
				} else {
					if (!StringUtils.isEmpty(purchase.getFolio())){
						criteria.add(Restrictions.like("folio", "%"+purchase.getFolio()+"%"));
					}
					if (!StringUtils.isEmpty(purchase.getPayType())){
						criteria.add(Restrictions.like("payType", "%"+purchase.getPayType()+"%"));
					}		
					if (!StringUtils.isEmpty(purchase.getReference())){
						criteria.add(Restrictions.like("reference", "%"+purchase.getReference()+"%"));
					}		
					if (purchase.getProvider() != null && purchase.getProvider().getIdPerson() != null && purchase.getProvider().getIdPerson().intValue() > 0){
						criteria.add(Restrictions.eq("provider.idPerson", purchase.getProvider().getIdPerson()));
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
		List<Purchase> results = Collections.checkedList(criteria.list(),Purchase.class);
		if (results == null || results.size() <= 0){
			throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_objectnotfound"));
		}
		return results;
	}

}
