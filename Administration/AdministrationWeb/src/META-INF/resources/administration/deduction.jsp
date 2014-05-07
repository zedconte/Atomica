<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.industrika.administration.i18n.AdministrationMessages" %>
<%@page import="com.industrika.administration.dto.Deduction" %>
<%@page import="com.industrika.commons.utils.TextFormatter" %>
<%@page import="java.util.Vector" %>
<jsp:useBean id="dto" scope="request" class="com.industrika.administration.dto.Deduction"/>
<jsp:useBean id="list" scope="request" class="java.util.Vector"/>
<jsp:useBean id="message" scope="request" class="java.lang.String"/>
<jsp:useBean id="error" scope="request" class="java.lang.String"/>

<form name="deduction" id="deduction" class="normaltext">
	<input type="hidden" name="action" id="action"/>
	<input type="hidden" name="formName" id="formName" value="deduction"/>
	<table class="commandbuttons">
		<tr>
			<td class="separator">&nbsp;</td>
			<td><img class="resetbutton" src="images/reset.png" onclick="javascript:clearForm()"></td>
			<td class="separator">&nbsp;</td>
			<td><div id="addButton"><img class="addbutton" src="images/new.png" onclick="javascript:document.deduction.action.value='add';sendDataConfirm('deduction','alta de la deducci&oacute;n')"></div></td>
			<td class="separator">&nbsp;</td>
			<td><div id="updateButton"><img class="savebutton" src="images/save.png" onclick="javascript:document.deduction.action.value='update';sendDataConfirm('deduction','actualización de la deducci&oacute;n')"></div></td>
			<td class="separator">&nbsp;</td>
			<td><div id="deleteButton"><img class="deletebutton" src="images/delete.png" onclick="javascript:document.deduction.action.value='remove';sendDataConfirm('deduction','eliminado de la deducci&oacute;n')"></div></td>
			<td class="separator">&nbsp;</td>
			<td><div id="searchButton"><img class="searchbutton" src="images/search.png" onclick="javascript:document.deduction.action.value='search';sendData('deduction')"></div></td>
			<td class="separator">&nbsp;</td>
		</tr>
	</table>
	<br/>
	<table align="center">
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext"><%=TextFormatter.formatWeb(AdministrationMessages.getMessage("deduction.Title")) %></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext">&nbsp;</td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td><input type='hidden' name='idDeduction' id='idDeduction' value='<%= dto.getIdDeduction() != null ? ""+dto.getIdDeduction().intValue() : "" %>'/></td>
			<td class="labelaligment"><%=AdministrationMessages.getMessage("deduction.Name") %>:</td>
			<td><input type='text' name='name' id='name' onkeyup="javascript:activeAdd('name')" size="50" value='<%=dto.getName() != null ? TextFormatter.formatWeb(dto.getName()) : ""%>'/></td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=AdministrationMessages.getMessage("deduction.Initials") %>:</td>
			<td><input type='text' name='initials' id='initials' value='<%=dto.getInitials() != null ? ""+TextFormatter.formatWeb(dto.getInitials()) : ""%>'/></td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=AdministrationMessages.getMessage("deduction.Value") %>:</td>
			<td><input type='text' name='value' id='value' onblur="javascript:this.value=formatNumberWithoutSign(Number(this.value));" value='<%=dto.getValue() != null ? ""+TextFormatter.getNumberValue(dto.getValue()) : ""%>'/></td>
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
				<th><%=AdministrationMessages.getMessage("deduction.Name") %></th>
				<th><%=AdministrationMessages.getMessage("deduction.Initials") %></th>
				<th><%=AdministrationMessages.getMessage("deduction.Value") %></th>
			</tr>
<%		for (int a = 0;a < list.size();a++){ 
			Deduction deduction = (Deduction) list.get(a); %>
			<tr onclick="javascript:clearForm();document.deduction.idDeduction.value='<%=deduction.getIdDeduction()%>';document.deduction.action.value='search';sendData('deduction');">
				<td><%=TextFormatter.formatWeb(deduction.getName())%></td>
				<td><%=deduction.getInitials() != null ? TextFormatter.formatWeb(deduction.getInitials()) : ""%></td>
				<td><%=deduction.getValue() != null ? TextFormatter.getNumberValue(deduction.getValue()) : ""%></td>
			</tr>
<%		} %>
		</table>
<%	} %>
	</div>
</form>
<script>
	function clearForm(){
		document.deduction.idDeduction.value='';
		document.deduction.name.value='';
		document.deduction.initials.value='';
		document.deduction.value.value='';
		document.deduction.idDeduction.focus();
		document.getElementById("resultsTable").style.visibility = "hidden";
		document.getElementById("addButton").style.visibility = "hidden";
		document.getElementById("searchButton").style.visibility = "visible";		
		activeAdd('name');
		hideUpdateDelete();
	}
	
<%	if (error != null && !error.equalsIgnoreCase("")){ %>
	showError('<%=error%>');
<% 	} %>
<%	if (error != null && !error.equalsIgnoreCase("")){ %>
showError('<%=error%>');
<% 	} %>
<%	if (dto == null || dto.getIdDeduction() == null || dto.getIdDeduction().intValue() <= 0) {
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