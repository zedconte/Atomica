<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.industrika.humanresources.i18n.HRMessages" %>
<%@page import="com.industrika.humanresources.dto.Direction" %>
<%@page import="com.industrika.commons.utils.TextFormatter" %>
<%@page import="java.util.Vector" %>
<jsp:useBean id="dto" scope="request" class="com.industrika.humanresources.dto.Direction"/>
<jsp:useBean id="list" scope="request" class="java.util.Vector"/>
<jsp:useBean id="message" scope="request" class="java.lang.String"/>
<jsp:useBean id="error" scope="request" class="java.lang.String"/>

<form name="direction" id="direction" class="normaltext">
	<input type="hidden" name="action" id="action"/>
	<input type="hidden" name="formName" id="formName" value="direction"/>
	<table class="commandbuttons">
		<tr>
			<td class="separator">&nbsp;</td>
			<td><img class="resetbutton" src="images/reset.png" onclick="javascript:clearForm()"></td>
			<td class="separator">&nbsp;</td>
			<td><div id="addButton"><img class="addbutton" src="images/new.png" onclick="javascript:document.direction.action.value='add';sendDataConfirm('direction','Alta para la dirección')"></div></td>
			<td class="separator">&nbsp;</td>
			<td><div id="updateButton"><img class="savebutton" src="images/save.png" onclick="javascript:document.direction.action.value='update';sendDataConfirm('direction','Actualizaci&oacute;n de la dirección')"></div></td>
			<td class="separator">&nbsp;</td>
			<td><div id="deleteButton"><img class="deletebutton" src="images/delete.png" onclick="javascript:document.direction.action.value='remove';sendDataConfirm('direction','Eliminado de la dirección')"></div></td>
			<td class="separator">&nbsp;</td>
			<td><img id="searchButton" class="searchbutton" src="images/search.png" onclick="javascript:document.direction.action.value='search';sendData('direction')"></td>
			<td class="separator">&nbsp;</td>
		</tr>
	</table>
	<br/>
	<table align="center">
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext"><%=TextFormatter.formatWeb(HRMessages.getMessage("direction.Title")) %></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext">&nbsp;</td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=TextFormatter.formatWeb(HRMessages.getMessage("direction.Name")) %>:</td>
			<td colspan="4">
				<input onkeyup="javascript:activeAdd('name')" type='text' size="50" name='name' id='name' value='<%=dto.getName() != null ? TextFormatter.formatWeb(dto.getName()) : ""%>'/>
				<input type='hidden' name='idDirection' id='idDirection' value='<%= dto.getIdDirection() != null ? ""+dto.getIdDirection().intValue() : "" %>'/>
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
	<div id="resultsTable">
<% if (list != null && list.size()>0){ %>
		<table class="resultsTable">
			<tr>
				<th><%=TextFormatter.formatWeb(HRMessages.getMessage("direction.Name")) %></th>
			</tr>
<%		for (int a = 0;a < list.size();a++){ 
				Direction direction = (Direction) list.get(a); %>
			<tr onclick="javascript:clearForm();document.direction.idDirection.value='<%=direction.getIdDirection()%>';document.direction.action.value='search';sendData('direction');">
				<td><%=TextFormatter.formatWeb(direction.getName())%></td>
			</tr>
<%		} %>
		</table>
<%	} %>
	</div>
</form>
<script>
	function clearForm(){
		document.direction.idDirection.value='';
		document.direction.name.value='';
		document.getElementById("resultsTable").style.visibility = "hidden";
		document.getElementById("addButton").style.visibility = "hidden";
		document.getElementById("searchButton").style.visibility = "visible";
		activeAdd('name');
		hideUpdateDelete();
		document.direction.idDirection.focus();
	}
	
<%	if (error != null && !error.equalsIgnoreCase("")){ %>
	showError('<%=error%>');
<% 	} %>
<%	if (dto == null || dto.getIdDirection() == null || dto.getIdDirection().intValue() <= 0) {
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