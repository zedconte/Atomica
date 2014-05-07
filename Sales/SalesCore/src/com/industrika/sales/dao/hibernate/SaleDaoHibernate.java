package com.industrika.sales.dao.hibernate;

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
import com.industrika.inventory.dao.DocumentFolioDao;
import com.industrika.inventory.dao.InventoryMovementDao;
import com.industrika.inventory.dto.DocumentFolio;
import com.industrika.inventory.dto.DocumentRow;
import com.industrika.inventory.dto.InventoryMovement;
import com.industrika.sales.dao.SaleDao;
import com.industrika.sales.dto.Sale;
import com.industrika.sales.i18n.SalesMessages;
import com.industrika.sales.validation.SaleValidator;

@Repository("saleDao")
public class SaleDaoHibernate extends RootDao implements SaleDao {

	private static final String TITLE = SalesMessages.getMessage("sale.Title");
	private static final int DOCUMENT_TYPE = 4;
	private SaleValidator validator;
	@Autowired
	private DocumentFolioDao daoFolio;
	@Autowired
	@Qualifier("movementConceptDaoHibernate")
	private MovementConteptDao daoConcept;
	@Autowired
	private InventoryMovementDao daoInventory;
	
	private DocumentFolio folio;
	
	@Autowired
	@Qualifier("saleValidator")
	public void setValidator(SaleValidator validator) {
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
	private void alreadyExist(Sale sale) 
			throws 	IndustrikaAlreadyExistException {
		Sale toFind = new Sale();
		Sale found = new Sale();
		try{
			toFind = new Sale();
			toFind.setFolio(sale.getFolio());
			found = get(toFind);
		}catch(Exception ex){
			if (!(ex instanceof IndustrikaObjectNotFoundException)){
				ex.printStackTrace();
			}
			found = null;
		}		
		if (found != null){
			if (sale.getIdDocument() == null || 
					sale.getIdDocument().intValue() != found.getIdDocument().intValue()){
				found = null;
				throw new IndustrikaAlreadyExistException(
						"Ese folio ya fue utilizado.");
			}
		}
	}
	
	private MovementConcept getSaleConcept(){
		MovementConcept mc=null;
		MovementConcept find = new MovementConcept();
		find.setName("Por venta realizada");
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
	
	private MovementConcept getSaleConceptDelete(){
		MovementConcept mc=null;
		MovementConcept find = new MovementConcept();
		find.setName("Por cancelación de venta");
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

	private MovementConcept getSaleConceptUpdate(){
		MovementConcept mc=null;
		MovementConcept find = new MovementConcept();
		find.setName("Por cancelación de venta");
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
	public Integer add(Sale sale) 
			throws 	IndustrikaValidationException,
					IndustrikaPersistenceException, 
					IndustrikaAlreadyExistException {
		Integer id = null;
		addFolios();
		sale.setFolio(daoFolio.getFolio(DOCUMENT_TYPE));
		validator.validate(sale);
		alreadyExist(sale);
		try{
			id = (Integer) sessionFactory.getCurrentSession().save(sale);
			if (sale.getRows() != null && sale.getRows().size() > 0){
				MovementConcept mc = getSaleConcept();
				for (DocumentRow row : sale.getRows()){
					InventoryMovement movement=new InventoryMovement();
					movement.setBranch(row.getBranch());
					movement.setDate(Calendar.getInstance());
					movement.setItem(row.getItem());
					movement.setQuantity(row.getQuantity());
					movement.setReference(TITLE + " - "+sale.getFolio());
					movement.setType(1);
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
	public void update(Sale sale) 
			throws 	IndustrikaValidationException,
					IndustrikaPersistenceException, 
					IndustrikaAlreadyExistException, 
					IndustrikaObjectNotFoundException {
		validator.validate(sale);
		alreadyExist(sale);
		try{
			if (sale.getIdDocument() == null || sale.getIdDocument().intValue() <= 0){
				throw new IndustrikaObjectNotFoundException(
						CommonsMessages.getMessage("error_persistence_objectnotfound"));
			} else {
				Sale old = get(sale.getIdDocument());
				try{
					sessionFactory.getCurrentSession().saveOrUpdate(sale);
				}catch(NonUniqueObjectException nu){
					sessionFactory.getCurrentSession().merge(sale);
				}
				if (old.getRows() != null && old.getRows().size() > 0){
					MovementConcept mc = getSaleConceptUpdate();
					for (DocumentRow row : old.getRows()){
						InventoryMovement movement=new InventoryMovement();
						movement.setBranch(row.getBranch());
						movement.setDate(Calendar.getInstance());
						movement.setItem(row.getItem());
						movement.setQuantity(row.getQuantity());
						movement.setReference(TITLE + " - "+sale.getFolio());
						movement.setType(0);
						movement.setWarehouse(row.getWarehouse());
						movement.setConcept(mc);
						daoInventory.add(movement);
					}
				}
				if (sale.getRows() != null && sale.getRows().size() > 0){
					MovementConcept mc = getSaleConceptUpdate();
					for (DocumentRow row : sale.getRows()){
						InventoryMovement movement=new InventoryMovement();
						movement.setBranch(row.getBranch());
						movement.setDate(Calendar.getInstance());
						movement.setItem(row.getItem());
						movement.setQuantity(row.getQuantity());
						movement.setReference(TITLE + " - "+sale.getFolio());
						movement.setType(1);
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
	public void delete(Sale sale)
			throws 	IndustrikaPersistenceException,
					IndustrikaObjectNotFoundException {
		if (sale.getIdDocument() != null && sale.getIdDocument().intValue() > 0){
			delete(sale.getIdDocument());
		} else {
			Sale temp = get(sale);
			try{
				sessionFactory.getCurrentSession().delete(temp);
				if (sale.getRows() != null && sale.getRows().size() > 0){
					MovementConcept mc = getSaleConceptDelete();
					for (DocumentRow row : sale.getRows()){
						InventoryMovement movement=new InventoryMovement();
						movement.setBranch(row.getBranch());
						movement.setDate(Calendar.getInstance());
						movement.setItem(row.getItem());
						movement.setQuantity(row.getQuantity());
						movement.setReference(TITLE + " - "+sale.getFolio());
						movement.setType(0);
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
	public void delete(Integer idSale)
			throws 	IndustrikaPersistenceException,
					IndustrikaObjectNotFoundException {
		if (idSale != null && idSale.intValue() > 0){
			Sale temp = (Sale) sessionFactory.getCurrentSession().get(Sale.class, idSale);
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
	public Sale get(Sale sale)
			throws 	IndustrikaPersistenceException,
					IndustrikaObjectNotFoundException {
		if (sale.getIdDocument() != null && sale.getIdDocument().intValue() > 0){
			return get(sale.getIdDocument());
		} else {
			List<Sale> results=find(sale,null);
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
	public Sale get(Integer idSale)
			throws 	IndustrikaPersistenceException,
					IndustrikaObjectNotFoundException {
		Sale dto = null;
		try{
			dto = (Sale) sessionFactory.getCurrentSession().get(Sale.class, idSale);
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
	public List<Sale> find(Sale sale, String[] order)
			throws 	IndustrikaPersistenceException,
					IndustrikaObjectNotFoundException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Sale.class);
		try{
			if (sale != null){
				if (sale.getIdDocument() != null && sale.getIdDocument().intValue() > 0){
					criteria.add(Restrictions.eq("idDocument", sale.getIdDocument().intValue()));
				} else {
					if (!StringUtils.isEmpty(sale.getFolio())){
						criteria.add(Restrictions.like("folio", "%"+sale.getFolio()+"%"));
					}
					if (!StringUtils.isEmpty(sale.getPayType())){
						criteria.add(Restrictions.like("payType", "%"+sale.getPayType()+"%"));
					}		
					if (!StringUtils.isEmpty(sale.getReference())){
						criteria.add(Restrictions.like("reference", "%"+sale.getReference()+"%"));
					}		
					if (sale.getCustomer() != null && sale.getCustomer().getIdPerson() != null && sale.getCustomer().getIdPerson().intValue() > 0){
						criteria.add(Restrictions.eq("customer.idPerson", sale.getCustomer().getIdPerson()));
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
		List<Sale> results = Collections.checkedList(criteria.list(),Sale.class);
		if (results == null || results.size() <= 0){
			throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_objectnotfound"));
		}
		return results;
	}

}
