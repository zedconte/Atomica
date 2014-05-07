package com.industrika.commons.view;

import java.util.ArrayList;
import java.util.List;

import com.industrika.commons.dto.Action;
import com.industrika.commons.dto.Option;
import com.industrika.commons.dto.Privilege;

/**
 * @author jose.arellano
 */
public class SystemPrivilegeDTO extends Privilege {
	private static final long serialVersionUID = 1L;

	private List<Action> listActions = new ArrayList<Action>();
	private List<Option> listOptions = new ArrayList<Option>();
	
	/**
	 * 
	 */
	public SystemPrivilegeDTO() {
	}

	/**
	 * @param id
	 * @param name
	 */
	public SystemPrivilegeDTO(Integer id, String name) {
		super(id, name);
	}

	/**
	 * @param name
	 */
	public SystemPrivilegeDTO(String name) {
		super(name);
	}
	
	/**
	 * @param pr
	 */
	public SystemPrivilegeDTO(Privilege pr) {
		this.setAction(pr.getAction());
		this.setId(pr.getId());
		this.setOption(pr.getOption());
		this.setName(pr.getName());
	}

	public void addAction(Action act){
		if(listActions == null){
			listActions = new ArrayList<Action>();
		}
		listActions.add(act);
	}
	
	public void addOption(Option opt){
		if(listOptions == null){
			listOptions = new ArrayList<Option>();
		}
		listOptions.add(opt);
	}
	
	public List<Action> getListActions() {
		return listActions;
	}

	public void setListActions(List<Action> listActions) {
		this.listActions = listActions;
	}

	public List<Option> getListOptions() {
		return listOptions;
	}

	public void setListOptions(List<Option> listOptions) {
		this.listOptions = listOptions;
	}
	
}
