/**
 * 
 */
package com.industrika.commons.dao;

import java.util.Set;

import com.industrika.commons.dto.Role;
import com.industrika.commons.dto.User;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;
import com.industrika.commons.exceptions.IndustrikaPersistenceException;

/**
 * @author jose.arellano
 */
public interface UserDao extends GenericDao<Integer,User>{
	
	/**
	 * Searches a user and fetches all of its roles, since this inner
	 * list must be lazy
	 * @param idUser
	 * @return
	 * @throws IndustrikaPersistenceException
	 * @throws IndustrikaObjectNotFoundException
	 */
	public User findUserWithRoles(Integer idUser) throws IndustrikaPersistenceException, IndustrikaObjectNotFoundException;
	
	public User authenticateUser(String userName, String password);
	
	/**
	 * Updates the list of roles of this user, replacing the current list with the one recieved.
	 * If the list argument is empty, it will clear the roles.
	 * @param user
	 * @param newRoles
	 * @return
	 */
	User setRoles(User user,Set<Role> newRoles) throws IndustrikaObjectNotFoundException, IndustrikaPersistenceException;
	
	/**
	 * Adds a single role to the user, if the current list for roles for the user contains 
	 * <b>n</b> records, where the recieved role is not included, the result will be to have <b>n+1</b> roles registered.
	 * @param user
	 * @param role
	 * @return
	 */
	User addRole(User user, Role role) throws IndustrikaObjectNotFoundException, IndustrikaPersistenceException;
}
