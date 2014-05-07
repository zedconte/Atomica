/**
 * 
 */
package com.industrika.commons.dao;

import com.industrika.commons.dto.Option;
import com.industrika.commons.exceptions.IndustrikaObjectNotFoundException;

/**
 * @author jose.arellano
 */
public interface SystemOptionDao extends GenericDao<Integer, Option> {
	
	public String getFactory(String resourceName) throws IndustrikaObjectNotFoundException;
	
}
