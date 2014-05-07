/**
 * 
 */
package com.industrika.commons.view;

import java.util.Calendar;
import java.util.List;

import com.industrika.commons.dto.Module;
import com.industrika.commons.dto.Option;

/**
 * @author jose.arellano
 *
 */
public class SystemOptionDTO extends Option {
	private static final long serialVersionUID = 1L;

	private List<Module> listModule;
	
	/**
	 * 
	 */
	public SystemOptionDTO() {
	}

	/**
	 * @param id
	 * @param resourceName
	 * @param creationDate
	 * @param module
	 */
	public SystemOptionDTO(Integer id, String resourceName,
			Calendar creationDate, Module module) {
		super(id, resourceName, creationDate, module);
	}
	
	public SystemOptionDTO(Option option) {
		super(option.getId(), option.getResourceName(), option.getCreationDate(), 
				option.getModule());
	}

	public List<Module> getListModule() {
		return listModule;
	}

	public void setListModule(List<Module> listModule) {
		this.listModule = listModule;
	}

	
}
