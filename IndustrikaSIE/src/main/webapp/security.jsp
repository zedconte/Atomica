<%@page contentType="text/html;charset=ISO-8859-1"%>
<%@page import="java.util.List" %>
<%@page import="com.industrika.commons.utils.TextFormatter" %>
<%@page import="com.industrika.commons.dto.User" %>
<%@page import="com.industrika.commons.dto.Role" %>
<%@page import="com.industrika.commons.dto.Action" %>
<%@page import="com.industrika.commons.dto.Privilege" %>
<%@page import="com.industrika.commons.dto.Module" %>
<%@page import="com.industrika.commons.dto.Option" %>
<%@page import="com.industrika.commons.i18n.CommonsMessages" %>
<%@page import="com.industrika.maintenance.i18n.MaintenanceMessages" %>
<%
    User user = (User) request.getSession().getAttribute("signedUser");
	String form = (String) request.getSession().getAttribute("formcall");
	List<Module> modules = (List<Module>) request.getSession().getAttribute("menuModules");
%>
<script>
var addB = document.getElementById("addButton");
var updateB = document.getElementById("updateButton");
var deleteB = document.getElementById("deleteButton");
var searchB = document.getElementById("searchButton");

<%
	boolean add=false, delete=false, update=false, search=false;
	if (form != null && modules != null && modules.size() > 0){
		for (Module module: modules){
			if (module.getOptions() != null){
				for (Option option: module.getOptions()){
					if (option.getVisible() == 1){
						if (user.getRoles() != null && user.getRoles().size() > 0){
							for (Role role : user.getRoles()){
								if (role.getPrivileges() != null && role.getPrivileges().size() > 0){
									for (Privilege privilege : role.getPrivileges()){
										if (privilege.getOption().getResourceName().equalsIgnoreCase(form)){
											if (privilege.getAction().getType().equalsIgnoreCase("add")){
												add = true;
											} else
											if (privilege.getAction().getType().equalsIgnoreCase("update")){
												update = true;
											} else 
											if (privilege.getAction().getType().equalsIgnoreCase("delete")){
												delete = true;
											} else
											if (privilege.getAction().getType().equalsIgnoreCase("search")){
												search = true;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
%>
<% if (!add){ %>
try{
	addB.innerHTML="";
}catch(e){}
<% }
	if (!update){%>
try{
	updateB.innerHTML="";
}catch(e){}
<%	}
	if (!delete){%>
try{
	deleteB.innerHTML="";
}catch(e){}
<%	}
	if (!search){%>
try{
	searchB.innerHTML="";
}catch(e){}
<%	}%>

</script>