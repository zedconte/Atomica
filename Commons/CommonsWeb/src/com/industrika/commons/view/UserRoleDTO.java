package com.industrika.commons.view;

import java.util.List;

import com.industrika.commons.dto.Role;
import com.industrika.commons.dto.User;

/**
 * @author jose.arellano
 */
public class UserRoleDTO {

	private User user;
	private Role role;
	
	private List<Role> availableRoles;
	
	/**
	 * 
	 */
	public UserRoleDTO() {
	}

	public UserRoleDTO(User user, Role role) {
		super();
		this.user = user;
		this.role = role;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<Role> getAvailableRoles() {
		return availableRoles;
	}

	public void setAvailableRoles(List<Role> availableRoles) {
		this.availableRoles = availableRoles;
	}

}
