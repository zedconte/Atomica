package com.industrika.commons.view;

import java.util.List;

import com.industrika.commons.dto.Privilege;
import com.industrika.commons.dto.Role;

/**
 * @author jose.arellano
 */
public class RolePrivilegeDTO {

	private Role role;
	private Privilege privilege;

	private List<Privilege> availablePrivileges;
	
	public RolePrivilegeDTO() {
	}

	
	
	public RolePrivilegeDTO(Role role, Privilege privilege) {
		super();
		this.role = role;
		this.privilege = privilege;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Privilege getPrivilege() {
		return privilege;
	}

	public void setPrivilege(Privilege privilege) {
		this.privilege = privilege;
	}

	public List<Privilege> getAvailablePrivileges() {
		return availablePrivileges;
	}

	public void setAvailablePrivileges(List<Privilege> availablePrivileges) {
		this.availablePrivileges = availablePrivileges;
	}

}
