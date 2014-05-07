package com.industrika.sales.dao.hibernate;

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
import com.industrika.inventory.dao.DocumentFolioDao;
import com.industrika.inventory.dto.DocumentFolio;
import com.industrika.sales.dao.QuotationDao;
import com.industrika.sales.dto.Quotation;
import com.industrika.sales.i18n.SalesMessages;
import com.industrika.sales.validation.QuotationValidator;

@Repository("quotationDao")
public class QuotationDaoHibernate extends RootDao implements QuotationDao {

	private static final String TITLE = SalesMessages.getMessage("quotation.Title");
	private static final int DOCUMENT_TYPE = 3;
	private QuotationValidator validator;
	@Autowired
	private DocumentFolioDao daoFolio;
	private DocumentFolio folio;
	
	@Autowired
	@Qualifier("quotationValidator")
	public void setValidator(QuotationValidator validator) {
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
	private void alreadyExist(Quotation quotation) 
			throws 	IndustrikaAlreadyExistException {
		Quotation toFind = new Quotation();
		Quotation found = new Quotation();
		try{
			toFind = new Quotation();
			toFind.setFolio(quotation.getFolio());
			found = get(toFind);
		}catch(Exception ex){
			if (!(ex instanceof IndustrikaObjectNotFoundException)){
				ex.printStackTrace();
			}
			found = null;
		}		
		if (found != null){
			if (quotation.getIdDocument() == null || 
					quotation.getIdDocument().intValue() != found.getIdDocument().intValue()){
				found = null;
				throw new IndustrikaAlreadyExistException(
						"Ese folio ya fue utilizado.");
			}
		}
	}
	
	@Override
	@Transactional
	public Integer add(Quotation quotation) 
			throws 	IndustrikaValidationException,
					IndustrikaPersistenceException, 
					IndustrikaAlreadyExistException {
		Integer id = null;
		addFolios();
		quotation.setFolio(daoFolio.getFolio(DOCUMENT_TYPE));
		validator.validate(quotation);
		alreadyExist(quotation);
		try{
			id = (Integer) sessionFactory.getCurrentSession().save(quotation);
		}catch(Exception ex){
			throw new IndustrikaPersistenceException(
					CommonsMessages.getMessage("error_persistence_add")+TITLE,
					ex);
		}
		return id;
	}

	@Override
	@Transactional
	public void update(Quotation quotation) 
			throws 	IndustrikaValidationException,
					IndustrikaPersistenceException, 
					IndustrikaAlreadyExistException, 
					IndustrikaObjectNotFoundException {
		validator.validate(quotation);
		alreadyExist(quotation);
		try{
			if (quotation.getIdDocument() == null || quotation.getIdDocument().intValue() <= 0){
				throw new IndustrikaObjectNotFoundException(
						CommonsMessages.getMessage("error_persistence_objectnotfound"));
			} else {
				try{
					sessionFactory.getCurrentSession().saveOrUpdate(quotation);
				}catch(NonUniqueObjectException nu){
					sessionFactory.getCurrentSession().merge(quotation);
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
	public void delete(Quotation quotation)
			throws 	IndustrikaPersistenceException,
					IndustrikaObjectNotFoundException {
		if (quotation.getIdDocument() != null && quotation.getIdDocument().intValue() > 0){
			delete(quotation.getIdDocument());
		} else {
			Quotation temp = get(quotation);
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
	public void delete(Integer idQuotation)
			throws 	IndustrikaPersistenceException,
					IndustrikaObjectNotFoundException {
		if (idQuotation != null && idQuotation.intValue() > 0){
			Quotation temp = (Quotation) sessionFactory.getCurrentSession().get(Quotation.class, idQuotation);
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
	public Quotation get(Quotation quotation)
			throws 	IndustrikaPersistenceException,
					IndustrikaObjectNotFoundException {
		if (quotation.getIdDocument() != null && quotation.getIdDocument().intValue() > 0){
			return get(quotation.getIdDocument());
		} else {
			List<Quotation> results=find(quotation,null);
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
	public Quotation get(Integer idQuotation)
			throws 	IndustrikaPersistenceException,
					IndustrikaObjectNotFoundException {
		Quotation dto = null;
		try{
			dto = (Quotation) sessionFactory.getCurrentSession().get(Quotation.class, idQuotation);
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
	public List<Quotation> find(Quotation quotation, String[] order)
			throws 	IndustrikaPersistenceException,
					IndustrikaObjectNotFoundException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Quotation.class);
		try{
			if (quotation != null){
				if (quotation.getIdDocument() != null && quotation.getIdDocument().intValue() > 0){
					criteria.add(Restrictions.eq("idDocument", quotation.getIdDocument().intValue()));
				} else {
					if (!StringUtils.isEmpty(quotation.getFolio())){
						criteria.add(Restrictions.like("folio", "%"+quotation.getFolio()+"%"));
					}
					if (!StringUtils.isEmpty(quotation.getPayType())){
						criteria.add(Restrictions.like("payType", "%"+quotation.getPayType()+"%"));
					}		
					if (!StringUtils.isEmpty(quotation.getReference())){
						criteria.add(Restrictions.like("reference", "%"+quotation.getReference()+"%"));
					}		
					if (quotation.getCustomer() != null && quotation.getCustomer().getIdPerson() != null && quotation.getCustomer().getIdPerson().intValue() > 0){
						criteria.add(Restrictions.eq("customer.idPerson", quotation.getCustomer().getIdPerson()));
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
		List<Quotation> results = Collections.checkedList(criteria.list(),Quotation.class);
		if (results == null || results.size() <= 0){
			throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_objectnotfound"));
		}
		return results;
	}

}
