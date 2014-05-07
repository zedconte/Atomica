/**
 * 
 */
package com.industrika.commons.dao;

import java.util.List;

import com.industrika.commons.dto.Privilege;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;

/**
 * @author jose.arellano
 */
public interface SystemPrivilegeDao extends GenericDao<Integer, Privilege> {

	List<Privilege> find(Privilege privilege, String[] orderFields) 
			throws IndustrikaObjectNotFoundException, IndustrikaPersistenceException;
}
