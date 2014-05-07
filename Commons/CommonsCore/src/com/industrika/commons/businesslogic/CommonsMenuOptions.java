package com.industrika.commons.businesslogic;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.industrika.commons.dao.RoleDao;
import com.industrika.commons.dao.SystemActionDao;
import com.industrika.commons.dao.SystemModuleDao;
import com.industrika.commons.dao.SystemOptionDao;
import com.industrika.commons.dao.SystemPrivilegeDao;
import com.industrika.commons.dao.UserDao;
import com.industrika.commons.dto.Action;
import com.industrika.commons.dto.Module;
import com.industrika.commons.dto.Option;
import com.industrika.commons.dto.Privilege;
import com.industrika.commons.dto.Role;
import com.industrika.commons.dto.User;

@Component("commonsOptions")
public class CommonsMenuOptions implements MenuOptions{
	
	@Autowired
	private SystemModuleDao dao;
	@Autowired
	private SystemOptionDao optionDao;
	@Autowired
	private UserDao daoUser;
	@Autowired
	private RoleDao daoRole;
	@Autowired
	private SystemPrivilegeDao daoPrivilege;
	@Autowired
	private SystemActionDao daoAction;
	
	private final String FACTORY="com.industrika.commons.commands.CommonsCommandsFactory";
	
	@Override
	public Module getModule() {
		return preloadOptions();
	}
	
	public void addPrivilegesToAdmin(Option op){
		try{
			User admin = daoUser.findUserWithRoles(1);
			Role role = admin.getRoles().iterator().next();
			List<Action> actions = daoAction.find(new Action(), null);
			if (actions != null && actions.size() > 0){
				for (Action act : actions){
					Privilege pri = new Privilege();
					pri.setAction(act);
					pri.setOption(op);
					pri.setName(role.getName()+" - Acceso a "+act.getDescription()+" en "+op.getText());
					pri = daoPrivilege.save(pri);
					role.getPrivileges().add(pri);
					daoRole.update(role);
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void addAdminUser(){
		User user = null;
		try{
			user = daoUser.findById(1);			
		}catch(Exception ex){
			user = null;
		}
		if (user == null){
			try{
				Role role = new Role();
				role.setName("Administrador General");
				role.setInitials("AG");
				//role = daoRole.save(role);
				Action add=new Action();
				add.setDescription("Agregar nuevos registros");
				add.setType("add");
				add = daoAction.save(add);
				Action update=new Action();
				update.setDescription("Actualizar información de registros");
				update.setType("update");
				update = daoAction.save(update);
				Action delete=new Action();
				delete.setDescription("Eliminar registros");
				delete.setType("delete");
				delete = daoAction.save(delete);
				Action search=new Action();
				search.setDescription("Buscar registros");
				search.setType("search");
				search = daoAction.save(search);
				Action enter=new Action();
				enter.setDescription("Entrar a la pantalla");
				enter.setType("enter");
				enter = daoAction.save(enter);
				user = new User();
				user.setCode("admin");
				user.setPassword("admin");
				user.setName("Administrador");
				Set<Role> roles = new HashSet<Role>();
				roles.add(role);
				user.setRoles(roles);
				daoUser.save(user);
			}catch(Exception e){
				e.printStackTrace();
			}			
		}
	}
	/**
	 * This method will preload the module options, for that reason is very important  
	 * than when a new options is added to the module, that new options MUST be added 
	 * in this method too.
	 */
	private Module preloadOptions(){
		Module toFind = new Module();
		toFind.setName("Generales");
		Module module = null;
		try{
			module = dao.find(toFind, null).get(0);
		}catch(Exception ex){
			module = null;
			ex.getMessage();
		}
		// If the module doesn't exist then add it
		if (module == null || module.getId() == null){
			module = new Module();
			module.setDescription("Información General");
			module.setName("Generales");
			module.setFactory(FACTORY);
			try{
				dao.save(module);
				module = dao.find(module, null).get(0);
				addAdminUser();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		
		// Verify the country option, if the option doesn't exist then add it
		Option op = new Option();
		op.setResourceName("country");
		op.setCreationDate(Calendar.getInstance());
		op.setText("Catálogo de Paises");
		op.setVisible(1);
		module = addMenu(module, op);
		// Verify the city option, if the option doesn't exist then add it
		op = new Option();
		op.setResourceName("city");
		op.setCreationDate(Calendar.getInstance());
		op.setText("Catálogo de Ciudades");
		op.setVisible(1);
		module = addMenu(module, op);
		// Verify the currencies option, if the option doesn't exist then add it
		op = new Option();
		op.setResourceName("currency");
		op.setCreationDate(Calendar.getInstance());
		op.setText("Catálogo de Divisas");
		op.setVisible(1);
		module = addMenu(module, op);
		// Verify the movementConcept option, if the option doesn't exist then add it
		op = new Option();
		op.setResourceName("movementConcept");
		op.setCreationDate(Calendar.getInstance());
		op.setText("Catálogo de Conceptos de Movimientos");
		op.setVisible(1);
		module = addMenu(module, op);
		// Verify the system actions option, if the option doesn't exist then add it
		op = new Option();
		op.setResourceName("action");
		op.setCreationDate(Calendar.getInstance());
		op.setText("Catálogo de Acciones Permitidas");
		op.setVisible(1);
		module = addMenu(module, op);
		// Verify the systemprivilege option, if the option doesn't exist then add it
		op = new Option();
		op.setResourceName("systemprivilege");
		op.setCreationDate(Calendar.getInstance());
		op.setText("Privilegios de Acceso");
		op.setVisible(0);
		module = addMenu(module, op);
		// Verify the role option, if the option doesn't exist then add it
		op = new Option();
		op.setResourceName("role");
		op.setCreationDate(Calendar.getInstance());
		op.setText("Catálogo de Roles");
		module = addMenu(module, op);
		// Verify the role privilege option, if the option doesn't exist then add it
		op = new Option();
		op.setResourceName("roleprivilege");
		op.setCreationDate(Calendar.getInstance());
		op.setText("Privilegios de Rol");
		op.setVisible(0);
		module = addMenu(module, op);
		// Verify the user privilege option, if the option doesn't exist then add it
		op = new Option();
		op.setResourceName("userrole");
		op.setCreationDate(Calendar.getInstance());
		op.setText("Roles de Usuario");
		op.setVisible(0);
		module = addMenu(module, op);
		// Verify the state option, if the option doesn't exist then add it
		op = new Option();
		op.setResourceName("state");
		op.setCreationDate(Calendar.getInstance());
		op.setText("Catálogo de Estados");
		op.setVisible(1);
		module = addMenu(module, op);
		// Verify the trademark option, if the option doesn't exist then add it
		op = new Option();
		op.setResourceName("trademark");
		op.setCreationDate(Calendar.getInstance());
		op.setText("Catálogo de Marcas");
		op.setVisible(1);
		module = addMenu(module, op);
		// Verify the user option, if the option doesn't exist then add it
		op = new Option();
		op.setResourceName("user");
		op.setCreationDate(Calendar.getInstance());
		op.setText("Usuarios del Sistema");
		op.setVisible(1);
		module = addMenu(module, op);
		// Verify the warehouse option, if the option doesn't exist then add it
		op = new Option();
		op.setResourceName("warehouse");
		op.setCreationDate(Calendar.getInstance());
		op.setText("Catálogo de Almacenes");
		op.setVisible(1);
		module = addMenu(module, op);
		// Verify the branch option, if the option doesn't exist then add it
		op = new Option();
		op.setResourceName("branch");
		op.setCreationDate(Calendar.getInstance());
		op.setText("Catálogo de Sucursales");
		op.setVisible(1);
		module = addMenu(module, op);
		
		return module;
	}
	
	public Module addMenu(Module module, Option option){
		option.setModule(module);
		if (module != null){
			boolean add = true;
			if (module.getOptions() != null){
				for (Option op: module.getOptions()){
					if (op.getResourceName() != null && 
							op.getResourceName().equalsIgnoreCase(option.getResourceName())){
						add = false;
					}
				}
			}
			if (add){
				try{
					option = optionDao.save(option);
					module = dao.findById(module.getId());
					addPrivilegesToAdmin(option);
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		}
		return module;
	}
}
