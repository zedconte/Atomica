<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.industrika.commons.i18n.CommonsMessages" %>
<%@page import="com.industrika.commons.dto.Role" %>
<%@page import="com.industrika.commons.dto.User" %>
<%@page import="java.util.List" %>
<jsp:useBean id="dto" scope="request" class="com.industrika.commons.view.UserRoleDTO"/>
<jsp:useBean id="list" scope="request" class="java.util.ArrayList"/>
<jsp:useBean id="message" scope="request" class="java.lang.String"/>
<jsp:useBean id="error" scope="request" class="java.lang.String"/>

<form name="userrole" id="userrole" class="normaltext">
	<input type="hidden" name="action" id="action"/>
	<input type="hidden" name="formName" id="formName" value="userrole"/>
	<input type="hidden" name="user_id" id="user_id" value="<%=dto.getUser().getId()%>"/>
	<table class="commandbuttons">
		<td class="separator">&nbsp;</td>
		<td><img class="resetbutton" src="images/reset.png" onclick="javascript:clearForm()"></td>
		<td class="separator">&nbsp;</td>
		<td><img class="addbutton" src="images/new.png" onclick="javascript:document.userrole.action.value='add';sendDataConfirm('userrole','Alta para el Rol')"></td>
		<td class="separator">&nbsp;</td>
		<td><img class="deletebutton" src="images/delete.png" onclick="javascript:document.userrole.action.value='remove';sendDataConfirm('userrole','Eliminado para el Rol')"></td>
		<td class="separator">&nbsp;</td>
	</table>
	<br/>
	<table align="center">
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext"><%=CommonsMessages.getMessage("userRoles") %></td>
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
			<td><input type='text' name='id' id='id' value='<%= dto.getUser().getId()%>' disabled/></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=CommonsMessages.getMessage("userName") %>:</td>
			<td><input type='text' name='code' id='code' value='<%=dto.getUser().getCode()%>' disabled/></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=CommonsMessages.getMessage("role") %>:</td>
			<td>
				<select name="role_id" id="role_id">
					<option value="" > <%=CommonsMessages.getMessage("select") %>
					</option>
<% for (Role role : dto.getAvailableRoles()){
		String selected= ((dto.getRole() != null && dto.getRole().equals(role))?"selected":"");%>
					<option value="<%=role.getId()%>" <%=selected%> >
						<%=role.getName()%>
					</option>
<% }%>
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
<% if (dto.getUser().getRoles() != null && !dto.getUser().getRoles().isEmpty()){ %>	
	<table class="resultsTable">
		<thead>
			<tr>
				<th colspan="3"><%=CommonsMessages.getMessage("userRoles") %></th>
			</tr>
			<tr>
				<th><%=CommonsMessages.getMessage("id") %></th>
				<th><%=CommonsMessages.getMessage("initials") %></th>
				<th><%=CommonsMessages.getMessage("role") %></th>
			</tr>
		</thead>
<%		for (Role role : dto.getUser().getRoles()){ %>
		<tr onclick="javascript:clearForm();document.userrole.role_id.value='<%=role.getId()%>';">
			<td><%=role.getId()%></td>
			<td><%=role.getInitials()%></td>
			<td><%=role.getName()%></td>
		</tr>
<%		} %>
	</table>
<%	} %>
</form>
	<br/>
<form name="user" id="user" class="normaltext">
	<input type="hidden" name="action" id="action" value="search"/>
	<input type="hidden" name="formName" id="formName" value="user"/>
	<input type="hidden" name="id" id="id" value="<%=dto.getUser().getId()%>"/>
	<table class="commandbuttons">
		<tr>
			<td class="separator">&nbsp;</td>
			<td>
				<input type="button" value="Regresar" onclick="javascript:sendData('user');"/></td>
		</tr>
	</table>
</form>

<script>
	function clearForm(){
		document.userrole.role_id.value='';
		document.userrole.role_id.focus();
	}
	
<%	if (error != null && !error.equals("")){ %>
	showError('<%=error%>');
<% 	} %>
</script>