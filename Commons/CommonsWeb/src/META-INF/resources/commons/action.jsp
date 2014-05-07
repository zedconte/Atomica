<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.industrika.commons.i18n.CommonsMessages" %>
<%@page import="com.industrika.commons.dto.Action" %>
<%@page import="com.industrika.commons.utils.TextFormatter" %>
<%@page import="java.util.List" %>
<jsp:useBean id="dto" scope="request" class="com.industrika.commons.dto.Action"/>
<jsp:useBean id="list" scope="request" class="java.util.ArrayList"/>
<jsp:useBean id="message" scope="request" class="java.lang.String"/>
<jsp:useBean id="error" scope="request" class="java.lang.String"/>
 
<form name="action" id="action" class="normaltext">
	<input type="hidden" name="action" id="action"/>
	<input type="hidden" name="formName" id="formName" value="action"/>
	<table class="commandbuttons">
		<td class="separator">&nbsp;</td>
		<td><img class="resetbutton" src="images/reset.png" onclick="javascript:clearForm()"></td>
		<td class="separator">&nbsp;</td>
		<td><div id="addButton"><img class="addbutton" src="images/new.png" onclick="javascript:document.action.action.value='add';sendDataConfirm('action','Alta para la Acci&oacute;n')"></div></td>
		<td class="separator">&nbsp;</td>
		<td><div id="updateButton"><img class="savebutton" src="images/save.png" onclick="javascript:document.action.action.value='update';sendDataConfirm('action','Actualizaci&oacute;n para la Acci&oacute;n')"></div></td>
		<td class="separator">&nbsp;</td>
		<td><div id="deleteButton"><img class="deletebutton" src="images/delete.png" onclick="javascript:document.action.action.value='remove';sendDataConfirm('action','Eliminado para la Acci&oacute;n')"></div></td>
		<td class="separator">&nbsp;</td>
		<td><div id="searchButton"><img class="searchbutton" src="images/search.png" onclick="javascript:document.action.action.value='search';sendData('action')"></div></td>
		<td class="separator">&nbsp;</td>
	</table>
	<br/>
	<table align="center">
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext"><%=CommonsMessages.getMessage("actionTitle") %></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext"><div style="visibility: hidden"><input type='text' name='id' id='id' value='<%= dto.getId() != null ? ""+dto.getId() : "" %>'/></div></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=CommonsMessages.getMessage("type") %>:</td>
			<td><input type='text' name='type' id='type' size="50" onkeyup="javascript:activeAdd('type')" value='<%=dto.getType() != null ? TextFormatter.formatWeb(dto.getType()) : ""%>'/></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=CommonsMessages.getMessage("description") %>:</td>
			<td><input type='text' name='description' size="50" id='description' value='<%=dto.getDescription() != null ? TextFormatter.formatWeb(dto.getDescription()) : ""%>'/></td>
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
	<div id="resultsTable">
<% if (list != null && list.size()>0){ %>	
		<table class="resultsTable">
			<tr>
				<th><%=CommonsMessages.getMessage("type") %></th>
				<th><%=CommonsMessages.getMessage("description") %></th>
			</tr>
<%		for (int a = 0;a < list.size();a++){ 
			Action action= (Action) list.get(a); %>
			<tr onclick="javascript:clearForm();document.action.id.value='<%=action.getId()%>';document.action.action.value='search';sendData('action');">
				<td><%=TextFormatter.formatWeb(action.getType())%></td>
				<td><%=TextFormatter.formatWeb(action.getDescription())%></td>
			</tr>
<%		} %>
		</table>
<%	} %>
	</div>
</form>
<script>
	function clearForm(){
		document.action.id.value='';
		document.action.type.value='';
		document.action.description.value='';
		document.getElementById("resultsTable").style.visibility = "hidden";
		document.getElementById("addButton").style.visibility = "hidden";
		document.getElementById("searchButton").style.visibility = "visible";
		activeAdd('type');
		hideUpdateDelete();		
		document.action.type.focus();
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
	activeAdd("type");
<%		} %>
	
	hideUpdateDelete();
<% 		} else { %>
	document.getElementById("addButton").style.visibility = "hidden";
	document.getElementById("searchButton").style.visibility = "hidden";
<%	} %>

</script>