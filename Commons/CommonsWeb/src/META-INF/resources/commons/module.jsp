<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.industrika.commons.i18n.CommonsMessages" %>
<%@page import="com.industrika.commons.dto.Module" %>
<%@page import="java.util.List" %>
<jsp:useBean id="dto" scope="request" class="com.industrika.commons.dto.Module"/>
<jsp:useBean id="list" scope="request" class="java.util.ArrayList"/>
<jsp:useBean id="message" scope="request" class="java.lang.String"/>
<jsp:useBean id="error" scope="request" class="java.lang.String"/>

<form name="module" id="module" class="normaltext">
	<input type="hidden" name="action" id="action"/>
	<input type="hidden" name="formName" id="formName" value="module"/>
	<table class="commandbuttons">
		<td class="separator">&nbsp;</td>
		<td><img class="resetbutton" src="images/reset.png" onclick="javascript:clearForm()"></td>
		<td class="separator">&nbsp;</td>
		<td><img class="addbutton" src="images/new.png" onclick="javascript:document.module.action.value='add';sendDataConfirm('module','Alta para el M&oacute;dulo')"></td>
		<td class="separator">&nbsp;</td>
		<td><img class="savebutton" src="images/save.png" onclick="javascript:document.module.action.value='update';sendDataConfirm('module','Actualizaci&oacute;n para el M&oacute;dulo')"></td>
		<td class="separator">&nbsp;</td>
		<td><img class="deletebutton" src="images/delete.png" onclick="javascript:document.module.action.value='remove';sendDataConfirm('module','Eliminado para el M&oacute;dulo')"></td>
		<td class="separator">&nbsp;</td>
		<td><img class="searchbutton" src="images/search.png" onclick="javascript:document.module.action.value='search';sendData('module')"></td>
		<td class="separator">&nbsp;</td>
	</table>
	<br/>
	<table align="center">
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext"><%=CommonsMessages.getMessage("moduleTitle") %></td>
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
			<td><input type='text' name='id' id='id' value='<%= dto.getId() != null ? ""+dto.getId() : "" %>' readonly/></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=CommonsMessages.getMessage("name") %>:</td>
			<td><input type='text' name='name' id='name' size="50" value='<%=dto.getName() != null ? dto.getName() : ""%>'/></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=CommonsMessages.getMessage("description") %>:</td>
			<td><input type='text' name='description' id='description' value='<%=dto.getDescription() != null ? dto.getDescription() : ""%>'/></td>
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
<% if (list != null && list.size()>0){ %>	
	<table class="resultsTable">
		<tr>
			<th><%=CommonsMessages.getMessage("id") %></th>
			<th><%=CommonsMessages.getMessage("name") %></th>
			<th><%=CommonsMessages.getMessage("description") %></th>
		</tr>
<%		for (int a = 0;a < list.size();a++){ 
			Module module = (Module) list.get(a); %>
		<tr onclick="javascript:clearForm();document.module.id.value='<%=module.getId()%>';document.module.action.value='search';sendData('module');">
			<td><%=module.getId()%></td>
			<td><%=module.getName()%></td>
			<td><%=module.getDescription()%></td>
		</tr>
<%		} %>
	</table>
<%	} %>
</form>
<script>
	function clearForm(){
		document.module.id.value='';
		document.module.name.value='';
		document.module.description.value='';
		document.module.name.focus();
	}
	
<%	if (error != null && !error.equals("")){ %>
	showError('<%=error%>');
<% 	} %>
</script>