package com.industrika.sales.dao;

import java.util.List;

import com.industrika.commons.exceptions.IndustrikaAlreadyExistException;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.exceptions.IndustrikaValidationException;
import com.industrika.sales.dto.Quotation;

public interface QuotationDao {
	public Integer add(Quotation dto) throws IndustrikaValidationException, IndustrikaPersistenceException, IndustrikaAlreadyExistException;
	public void update(Quotation dto) throws IndustrikaValidationException, IndustrikaPersistenceException, IndustrikaAlreadyExistException,IndustrikaObjectNotFoundException;
	public void delete(Quotation dto) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	public void delete(Integer idQuotation) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	public Quotation get(Quotation dto) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	public Quotation get(Integer idQuotation) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	public List<Quotation> find(Quotation dto,String[] order) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;

}
