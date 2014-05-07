package com.industrika.commons.dao;

import java.util.Set;

import com.industrika.commons.dto.Privilege;
import com.industrika.commons.dto.Role;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;
import com.industrika.commons.exceptions.IndustrikaValidationException;


public interface RoleDao extends GenericDao<Integer, Role>{

	/**
	 * Updates the list of privileges of this role, replacing the current list with the one recieved.
	 * If the list argument is empty, it will clear the roles.
	 * @param role
	 * @param newPrivileges
	 * @return
	 */
	Role setPrivileges(Role role,Set<Privilege> newPrivileges) throws IndustrikaObjectNotFoundException, IndustrikaPersistenceException;
	
	/**
	 * Adds a single privilege to the role, if the current list for privileges for the role contains 
	 * <b>n</b> records, where the recieved privilege is not included, the result will be to have <b>n+1</b> privileges registered.
	 * @param role
	 * @param privilege
	 * @return
	 */
	Role addPrivilege(Role role, Privilege privilege) throws IndustrikaObjectNotFoundException,
				IndustrikaPersistenceException, IndustrikaValidationException;	 
}
