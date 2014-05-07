<%@page import="com.industrika.commons.dto.Role"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.industrika.commons.i18n.CommonsMessages" %>
<%@page import="com.industrika.commons.dto.Module" %>
<%@page import="com.industrika.commons.dto.Privilege" %>
<%@page import="com.industrika.commons.utils.TextFormatter" %>
<%@page import="java.util.List" %>
<jsp:useBean id="dto" scope="request" class="com.industrika.commons.view.RolePrivilegeDTO"/>
<jsp:useBean id="list" scope="request" class="java.util.ArrayList"/>
<jsp:useBean id="message" scope="request" class="java.lang.String"/>
<jsp:useBean id="error" scope="request" class="java.lang.String"/>

<form name="roleprivilege" id="roleprivilege" class="normaltext">
	<input type="hidden" name="action" id="action"/>
	<input type="hidden" name="formName" id="formName" value="roleprivilege"/>
	<input type="hidden" name="role_id" id="role_id" value="<%=dto.getRole() != null ? dto.getRole().getId() : ""%>"/>
	<table class="commandbuttons">
		<td class="separator">&nbsp;</td>
		<td><img class="resetbutton" src="images/reset.png" onclick="javascript:clearForm()"></td>
		<td class="separator">&nbsp;</td>
		<td><img class="addbutton" src="images/new.png" onclick="javascript:document.roleprivilege.action.value='add';sendDataConfirm('roleprivilege','Alta para el Privilegio')"></td>
		<td class="separator">&nbsp;</td>
		<td><img class="deletebutton" src="images/delete.png" onclick="javascript:document.roleprivilege.action.value='remove';sendDataConfirm('roleprivilege','Eliminado para el Privilegio')"></td>
		<td class="separator">&nbsp;</td>
		
	</table>
	<br/>
	<table align="center">
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext"><%=CommonsMessages.getMessage("rolePrivileges") %></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext">&nbsp;</td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment "><%=CommonsMessages.getMessage("id") %>:</td>
			<td><input type='text' name='id' id='id' value='<%= dto.getRole() != null ? dto.getRole().getId(): ""%>' disabled/></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=CommonsMessages.getMessage("role") %>:</td>
			<td><input type='text' name='name' id='name' value='<%=dto.getRole() !=null ? TextFormatter.formatWeb(dto.getRole().getName()) : ""%>' disabled/></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=CommonsMessages.getMessage("privilegeName") %>:</td>
			<td>
				<select name="privilege_id" id="privilege_id">
					<option value="" > <%=CommonsMessages.getMessage("select") %>
					</option>
<%	if (dto.getAvailablePrivileges() != null){
		for (Privilege pr : dto.getAvailablePrivileges()){
			String selected= ((dto.getPrivilege() != null && dto.getPrivilege().equals(pr))?"selected":"");%>
					<option value="<%=pr.getId()%>" <%=selected%> >
						<%=TextFormatter.formatWeb(pr.getActionType())%> - <%=TextFormatter.formatWeb(pr.getOption().getText())%>
					</option>
<% 		}
	}%>
				</select>
			</td>
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
<% if (dto.getRole() != null && dto.getRole().getPrivileges() != null && !dto.getRole().getPrivileges().isEmpty()){ %>	
	<table class="resultsTable">
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
<%		if (dto.getRole() != null && dto.getRole().getPrivileges() != null){
			for (Privilege pr : dto.getRole().getPrivileges()){ %>
		<tr onclick="javascript:clearForm();document.roleprivilege.privilege_id.value='<%=pr.getId()%>';">
			<td><%=pr.getName()%></td>
			<td><%=pr.getOption().getText()%></td>
			<td><%=pr.getActionType()%></td>
		</tr>
<%			} 
		}%>
	</table>
<%	} %>
</form>
	<br/>
<form name="role" id="role" class="normaltext">
	<input type="hidden" name="action" id="action" value="search"/>
	<input type="hidden" name="formName" id="formName" value="role"/>
	<input type="hidden" name="id" id="id" value="<%=dto.getRole() !=null ? dto.getRole().getId() : ""%>"/>
	<table class="commandbuttons">
		<tr>
			<td class="separator">&nbsp;</td>
			<td>
				<input type="button" value="Regresar" onclick="javascript:sendData('role');"/></td>
		</tr>
	</table>
</form>

<script>
	function clearForm(){
		document.roleprivilege.privilege_id.value='';
		document.roleprivilege.privilege_id.focus();
	}
	
<%	if (error != null && !error.equals("")){ %>
	showError('<%=error%>');
<% 	} %>
</script>