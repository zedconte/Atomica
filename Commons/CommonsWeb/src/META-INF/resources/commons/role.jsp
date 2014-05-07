<%@page import="com.industrika.commons.dto.Role"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.industrika.commons.i18n.CommonsMessages" %>
<%@page import="com.industrika.commons.dto.Module" %>
<%@page import="com.industrika.commons.dto.Privilege" %>
<%@page import="com.industrika.commons.utils.TextFormatter" %>
<%@page import="java.util.List" %>
<jsp:useBean id="dto" scope="request" class="com.industrika.commons.dto.Role"/>
<jsp:useBean id="list" scope="request" class="java.util.ArrayList"/>
<jsp:useBean id="message" scope="request" class="java.lang.String"/>
<jsp:useBean id="error" scope="request" class="java.lang.String"/>

<form name="role" id="role" class="normaltext">
	<input type="hidden" name="action" id="action"/>
	<input type="hidden" name="formName" id="formName" value="role"/>
	<table class="commandbuttons">
		<td class="separator">&nbsp;</td>
		<td><img class="resetbutton" src="images/reset.png" onclick="javascript:clearForm()"></td>
		<td class="separator">&nbsp;</td>
		<td><div id="addButton"><img class="addbutton" src="images/new.png" onclick="javascript:document.role.action.value='add';sendDataConfirm('role','Alta para el Rol')"></div></td>
		<td class="separator">&nbsp;</td>
		<td><div id="updateButton"><img class="savebutton" src="images/save.png" onclick="javascript:document.role.action.value='update';sendDataConfirm('role','Actualizaci&oacute;n para el Rol')"></div></td>
		<td class="separator">&nbsp;</td>
		<td><div id="deleteButton"><img class="deletebutton" src="images/delete.png" onclick="javascript:document.role.action.value='remove';sendDataConfirm('role','Eliminado para el Rol')"></div></td>
		<td class="separator">&nbsp;</td>
		<td><div id="searchButton"><img class="searchbutton" src="images/search.png" onclick="javascript:document.role.action.value='search';sendData('role')"></div></td>
		<td class="separator">&nbsp;</td>
	</table>
	<br/>
	<table align="center">
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext"><%=CommonsMessages.getMessage("roleTitle") %></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext">&nbsp;</td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=CommonsMessages.getMessage("name") %>:</td>
			<td>
				<input type='hidden' name='id' id='id' value='<%= dto.getId() != null ? ""+dto.getId() : "" %>'/>
				<input type='text' name='name' id='name' size="50" onkeyup="javascript:activeAdd('name')" value='<%=dto.getName() != null ? TextFormatter.formatWeb(dto.getName()) : ""%>'/>
			</td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=CommonsMessages.getMessage("initials") %>:</td>
			<td><input type='text' name='initials' id='initials' value='<%=dto.getInitials() != null ? TextFormatter.formatWeb(dto.getInitials()) : ""%>'/></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext">&nbsp;</td>
			<td class="separator">&nbsp;</td>
		</tr>
<%	if (message != null && !message.equalsIgnoreCase("")){ %>
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="messagetext"><%=message %></td>
			<td class="separator">&nbsp;</td>
		</tr>
<%	} %>
	</table>

	<br/>
<% if (dto.getPrivileges() != null && !dto.getPrivileges().isEmpty()){ %>	
	<table class="resultsTable" id="selectedRolePrivileges">
		<thead>
			<tr>
				<th colspan="4"><%=CommonsMessages.getMessage("rolePrivileges") %></th>
			</tr>
			<tr>
				<th><%=CommonsMessages.getMessage("name") %></th>
				<th><%=CommonsMessages.getMessage("resourcename") %></th>
				<th><%=CommonsMessages.getMessage("action") %></th>
			</tr>
		</thead>
<%		for (Privilege pr : dto.getPrivileges()){ %>
		<tr>
			<td><%=TextFormatter.formatWeb(pr.getName())%></td>
			<td><%=TextFormatter.formatWeb(pr.getOption().getText())%></td>
			<td><%=TextFormatter.formatWeb(pr.getActionType())%></td>
		</tr>
<%		} %>
	</table>
<%	} %>
</form>

<%if (dto.getId() != null) {%>
<form name="roleprivilege" id="roleprivilege" class="normaltext">
	<input type="hidden" name="action" id="action"/>
	<input type="hidden" name="formName" id="formName" value="roleprivilege"/>
	<input type="hidden" name="role_id" id="role_id" value="<%=dto.getId()%>"/>
	<table class="commandbuttons">
		<tr>
			<td class="separator">&nbsp;</td>
			<td>
				<input type="button" value="Editar privilegios" onclick="javascript:document.roleprivilege.action.value='search';sendData('roleprivilege');"/></td>
		</tr>
	</table>
</form>

<%} %>
	<br/>
	<hr/>
	<div id="resultsTable">
<% if (list != null && list.size()>0){ %>	
	<table class="resultsTable">
		<tr>
			<th><%=CommonsMessages.getMessage("name") %></th>
			<th><%=CommonsMessages.getMessage("initials") %></th>
		</tr>
<%		for (int a = 0;a < list.size();a++){ 
			Role role = (Role) list.get(a); %>
		<tr onclick="javascript:clearForm();document.role.id.value='<%=role.getId()%>';document.role.action.value='search';sendData('role');">
			<td><%=role.getName()%></td>
			<td><%=role.getInitials()%></td>
		</tr>
<%		} %>
	</table>
<%	} %>
	</div>

<script>
	function clearForm(){
		document.role.id.value='';
		document.role.name.value='';
		document.role.initials.value='';
		$('#selectedRolePrivileges').remove();
		document.getElementById("resultsTable").style.visibility = "hidden";
		document.getElementById("addButton").style.visibility = "hidden";
		document.getElementById("searchButton").style.visibility = "visible";
		activeAdd('name');
		hideUpdateDelete();		
		document.role.name.focus();
	}
	
<%	if (error != null && !error.equals("")){ %>
	showError('<%=error%>');
<% 	} %>
<%	if (dto == null || dto.getId() == null || dto.getId().intValue() <= 0) {
		if (list == null || list.size() <= 0){ 
			if (error == null || error.equalsIgnoreCase("")){
%>
	clearForm();
<%			}
		} else { %>
	activeAdd("name");
<%		} %>
	
	hideUpdateDelete();
<% 	} else { %>
	document.getElementById("addButton").style.visibility = "hidden";
	document.getElementById("searchButton").style.visibility = "hidden";
<%	} %>

</script>