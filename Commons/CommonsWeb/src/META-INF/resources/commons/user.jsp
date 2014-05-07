<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="com.industrika.commons.dto.Role"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.industrika.commons.i18n.CommonsMessages" %>
<%@page import="com.industrika.commons.dto.User" %>
<%@page import="com.industrika.commons.dto.Role" %>
<%@page import="com.industrika.commons.utils.TextFormatter" %>
<%@page import="java.util.List" %>
<jsp:useBean id="dto" scope="request" class="com.industrika.commons.dto.User"/>
<jsp:useBean id="list" scope="request" class="java.util.ArrayList"/>
<jsp:useBean id="message" scope="request" class="java.lang.String"/>
<jsp:useBean id="error" scope="request" class="java.lang.String"/>

<form name="user" id="user" class="normaltext">
	<input type="hidden" name="action" id="action"/>
	<input type="hidden" name="formName" id="formName" value="user"/>
	<table class="commandbuttons">
		<td class="separator">&nbsp;</td>
		<td><img class="resetbutton" src="images/reset.png" onclick="javascript:clearForm()"></td>
		<td class="separator">&nbsp;</td>
		<td><div id="addButton"><img class="addbutton" src="images/new.png" onclick="javascript:executeSave()"></div></td>
		<td class="separator">&nbsp;</td>
		<td><div id="updateButton"><img class="savebutton" src="images/save.png" onclick="javascript:executeUpdate()"></div></td>
		<td class="separator">&nbsp;</td>
		<td><div id="deleteButton"><img class="deletebutton" src="images/delete.png" onclick="javascript:executeDelete()"></div></td>
		<td class="separator">&nbsp;</td>
		<td><div id="searchButton"><img class="searchbutton" src="images/search.png" onclick="javascript:executeSearch()"></div></td>
		<td class="separator">&nbsp;</td>
	</table>
	<br/>
	<table align="center">
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext"><%=CommonsMessages.getMessage("userTitle") %></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext"><input type='hidden' name='id' id='id' value='<%= dto.getId() != null ? ""+dto.getId() : "" %>'/></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=CommonsMessages.getMessage("name") %>:</td>
			<td><input type='text' name='name' id='name' size="50" onkeyup="javascript:activeAdd('name')" value='<%=StringUtils.trimToEmpty(TextFormatter.formatWeb(dto.getName()))%>'/></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=CommonsMessages.getMessage("userName") %>:</td>
			<td><input type='text' name='code' id='code' value='<%=StringUtils.trimToEmpty(TextFormatter.formatWeb(dto.getCode()))%>'/></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=CommonsMessages.getMessage("email") %>:</td>
			<td><input type='text' name='email' id='email' value='<%=StringUtils.trimToEmpty(TextFormatter.formatWeb(dto.getEmail()))%>'/></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=CommonsMessages.getMessage("password") %>:</td>
			<td><input type='password' name='password' id='password' value='<%=StringUtils.trimToEmpty(TextFormatter.formatWeb(dto.getPassword()))%>'/></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=CommonsMessages.getMessage("confirmPassword") %>:</td>
			<td><input type='password' name='confirmPassword' id='confirmPassword' value='<%=StringUtils.trimToEmpty(TextFormatter.formatWeb(dto.getPassword()))%>'/></td>
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
<% if (dto.getRoles() != null && !dto.getRoles().isEmpty()){ %>	
	<table class="resultsTable" id="selectedUserRoles">
		<thead>
			<tr>
				<th colspan="3"><%=CommonsMessages.getMessage("userRoles") %></th>
			</tr>
			<tr>
				<th><%=CommonsMessages.getMessage("name") %></th>
				<th><%=CommonsMessages.getMessage("initials") %></th>
			</tr>
		</thead>
<%		for (Role role: dto.getRoles()){ %>
		<tr>
			<td><%=role.getName()%></td>
			<td><%=role.getInitials()%></td>
		</tr>
<%		} %>
	</table>
<%	} %>
</form>
<%if (dto.getId() != null) {%>
<form name="userrole" id="userrole" class="normaltext">
	<input type="hidden" name="action" id="action"/>
	<input type="hidden" name="formName" id="formName" value="userrole"/>
	<input type="hidden" name="user_id" id="user_id" value="<%=dto.getId()%>"/>
	<table class="commandbuttons">
		<tr>
			<td class="separator">&nbsp;</td>
			<td>
				<input type="button" value="Editar roles" onclick="javascript:document.userrole.action.value='search';sendData('userrole');"/></td>
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
			<th><%=CommonsMessages.getMessage("userName") %></th>
			<th><%=CommonsMessages.getMessage("email") %></th>
		</tr>
<%		for (int a = 0;a < list.size();a++){ 
			User user = (User) list.get(a); %>
		<tr onclick="javascript:clearForm();document.user.id.value='<%=user.getId()%>';document.user.action.value='search';sendData('user');">
			<td><%=TextFormatter.formatWeb(user.getName())%></td>
			<td><%=TextFormatter.formatWeb(user.getCode())%></td>
			<td><%=TextFormatter.formatWeb(user.getEmail())%></td>
		</tr>
<%		} %>
	</table>
<%	} %>
	</div>
<script>
	function clearForm(){
		document.user.id.value='';
		document.user.code.value='';
		document.user.email.value='';
		document.user.password.value='';
		document.user.confirmPassword.value='';
		$('#selectedUserRoles').remove();
		document.getElementById("resultsTable").style.visibility = "hidden";
		document.getElementById("addButton").style.visibility = "hidden";
		document.getElementById("searchButton").style.visibility = "visible";
		activeAdd('code');
		hideUpdateDelete();		
		document.user.code.focus();
	}
	
	function validatePasswords(){
		var pass = $('#password');
		var confPass = $('#confirmPassword');
		
		pass.val(pass.val().trim());
		confPass.val(confPass.val().trim());
		
		return (pass.val() !== '' && 
				(pass.val() === confPass.val()) )
	}
	
	function executeSave(){
		executeAction('add', true, true, 'Alta para el Usuario');
	}
	
	function executeUpdate(){
		executeAction('update', true, true, 'Actualizaci&oacute;n para el Usuario');
	}
	
	function executeDelete(){
		executeAction('remove', false, true, 'Eliminado para el Usuario');
	}
	
	function executeSearch(){
		executeAction('search', false, false);
	}
	
	function executeAction(action, validatePassword, showConfirm, tittle){
		$('#action').val(action);
		
		var confFunction = showConfirm ===true ? sendDataConfirm : sendData;
		
		if(validatePassword){
			if (validatePasswords()){
				confFunction('user',tittle);
			}
			else{
				showError("Las contrase&ntilde;as no coinciden");
			}
		}
		else{
			confFunction('user',tittle);
		}
		
	}
/*
	<td><img class="deletebutton" src="images/delete.png" onclick="javascript:"></td>
	<td class="separator">&nbsp;</td>
	<td><img class="searchbutton" src="images/search.png" onclick="javascript:"></td>
 */
	
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
	activeAdd("code");
<%		} %>
	
	hideUpdateDelete();
<% 	} else { %>
	document.getElementById("addButton").style.visibility = "hidden";
	document.getElementById("searchButton").style.visibility = "hidden";
<%	} %>

</script>