package com.industrika.inventory.dao.hibernate;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.industrika.commons.dao.hibernate.RootDao;
import com.industrika.commons.exceptions.IndustrikaAlreadyExistException;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.i18n.CommonsMessages;
import com.industrika.inventory.dao.DocumentFolioDao;
import com.industrika.inventory.dto.DocumentFolio;
import com.industrika.inventory.i18n.InventoryMessages;

@Repository("documentfolioDao")
public class DocumentFolioDaoHibernate extends RootDao implements DocumentFolioDao {

	private static final String TITLE = InventoryMessages.getMessage("buyorder.Folio");
	
	@Transactional
	private void alreadyExist(DocumentFolio provider) 
			throws 	IndustrikaAlreadyExistException {
		DocumentFolio toFind = new DocumentFolio();
		DocumentFolio found = new DocumentFolio();
		try{
			toFind = new DocumentFolio();
			toFind.setDocumentType(provider.getDocumentType());
			found = get(toFind);
		}catch(Exception ex){
			if (!(ex instanceof IndustrikaObjectNotFoundException)){
				ex.printStackTrace();
			}
			found = null;
		}		
		if (found != null){
			if (provider.getIdFolio() == null || 
					provider.getIdFolio().intValue() != found.getIdFolio().intValue()){
				found = null;
				throw new IndustrikaAlreadyExistException(
						"Ya existe los folios para el tipo de documento");
			}
		}
		found = null;
	}

	@Transactional
	public DocumentFolio get(DocumentFolio idProvider)
			throws 	IndustrikaPersistenceException,
					IndustrikaObjectNotFoundException {
		DocumentFolio dto = null;
		try{
			if (idProvider.getIdFolio() != null && idProvider.getIdFolio().intValue() > 0){
				dto = (DocumentFolio) sessionFactory.getCurrentSession().get(DocumentFolio.class, idProvider);
			}else{
				List<DocumentFolio> results=find(idProvider);
				if (results.size() > 1){
					throw new IndustrikaObjectNotFoundException(
							CommonsMessages.getMessage("error_persistence_morethanoneobject"));
				} else {
					return results.get(0);
				}
			}
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
	@Transactional
	public List<DocumentFolio> find(DocumentFolio provider)
			throws 	IndustrikaPersistenceException,
					IndustrikaObjectNotFoundException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DocumentFolio.class);
		try{
			if (provider != null){
				if (provider.getIdFolio() != null && provider.getIdFolio().intValue() > 0){
					criteria.add(Restrictions.eq("idFolio", provider.getIdFolio().intValue()));
				} else {
					if (provider.getDocumentType() !=null && provider.getDocumentType().intValue() > 0){
						criteria.add(Restrictions.eq("documentType", provider.getDocumentType()));
					} else {
						if (!StringUtils.isEmpty(provider.getDocumentName())){
							criteria.add(Restrictions.like("documentName", "%"+provider.getDocumentName()+"%"));
						}
					}
				}
			}
		}catch(Exception ex){
			throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_executequery")+TITLE, ex);
		}
		List<DocumentFolio> results = Collections.checkedList(criteria.list(),DocumentFolio.class);
		if (results == null || results.size() <= 0){
			throw new IndustrikaObjectNotFoundException(CommonsMessages.getMessage("error_persistence_objectnotfound"));
		}
		return results;
	}

	@Override
	public String getFolio(Integer documentType) throws IndustrikaPersistenceException {
		String folio="";
		DocumentFolio dto = new DocumentFolio();
		dto.setDocumentType(documentType);
		DocumentFolio found=null;
		try{
			found = get(dto);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		if (found != null){
			int newfolio = found.getFolio().intValue()+1;
			try{
				found.setFolio(newfolio);
				sessionFactory.getCurrentSession().merge(found);
				folio = !StringUtils.isEmpty(found.getSerial()) ? found.getSerial()+"-" : "";
				folio += newfolio;
			}catch(Exception ex){
				ex.printStackTrace();
				throw new IndustrikaPersistenceException("No se puede adquirir el folio para el tipo de documento",ex);
			}
		}
		return folio;
	}

	@Override
	public DocumentFolio addDocumentFolio(DocumentFolio folio) {
		DocumentFolio found=null;
		try{
			found = get(folio);
		}catch(Exception ex){
			found = null;
			ex.printStackTrace();
		}
		if (found == null){
			try{
				folio.setFolio(0);
				folio.setIdFolio((Integer)sessionFactory.getCurrentSession().save(folio));
			}catch(Exception ex){
				ex.printStackTrace();
			}			
		} else {
			folio = found;
		}
		return folio;
	}
}
