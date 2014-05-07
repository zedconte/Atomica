package com.industrika.inventory.dao;

import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.inventory.dto.DocumentFolio;

public interface DocumentFolioDao {
	public DocumentFolio addDocumentFolio(DocumentFolio folio);
	public String getFolio(Integer documentType) throws IndustrikaPersistenceException;
}
