<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.industrika.commons.i18n.CommonsMessages" %>
<%@page import="com.industrika.commons.dto.Currency" %>
<%@page import="java.util.Vector" %>
<%@page import="com.industrika.commons.utils.TextFormatter" %>

<jsp:useBean id="dto" scope="request" class="com.industrika.commons.dto.Currency"/>
<jsp:useBean id="list" scope="request" class="java.util.Vector"/>
<jsp:useBean id="message" scope="request" class="java.lang.String"/>
<jsp:useBean id="error" scope="request" class="java.lang.String"/>

<form name="currency" id="currency" class="normaltext">
	<input type="hidden" name="action" id="action"/>
	<input type="hidden" name="formName" id="formName" value="currency"/>
	<table class="commandbuttons">
		<tr>
			<td class="separator">&nbsp;</td>
			<td><img class="resetbutton" src="images/reset.png" onclick="javascript:clearForm()"></td>
			<td class="separator">&nbsp;</td>
			<td><div id="addButton"><img class="addbutton" src="images/new.png" onclick="javascript:document.currency.action.value='add';sendDataConfirm('currency','Alta para la Divisa')"></div></td>
			<td class="separator">&nbsp;</td>
			<td><div id="updateButton"><img class="savebutton" src="images/save.png" onclick="javascript:document.currency.action.value='update';sendDataConfirm('currency','Actualizaci&oacute;n de la Divisa')"></div></td>
			<td class="separator">&nbsp;</td>
			<td><div id="deleteButton"><img class="deletebutton" src="images/delete.png" onclick="javascript:document.currency.action.value='remove';sendDataConfirm('currency','Eliminado de la Divisa')"></div></td>
			<td class="separator">&nbsp;</td>
			<td><div id="searchButton"><img class="searchbutton" src="images/search.png" onclick="javascript:document.currency.action.value='search';sendData('currency')"></div></td>
			<td class="separator">&nbsp;</td>
		</tr>
	</table>
	<br/>
	<table align="center">
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext"><%=CommonsMessages.getMessage("currency.Title") %></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext">&nbsp;</td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=CommonsMessages.getMessage("currency.Name") %>:</td>
			<td>
				<input type='hidden' name='idCurrency' id='idCurrency' value='<%= dto.getIdCurrency() != null ? ""+dto.getIdCurrency().intValue() : "" %>'/>
				<input type='text' name='name' id='name' size="50" onkeyup="javascript:activeAdd('name')" value='<%=dto.getName() != null ? TextFormatter.formatWeb(dto.getName()) : ""%>'/>
			</td>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=CommonsMessages.getMessage("currency.ShortName") %>:</td>
			<td><input type='text' name='shortName' id='shortName' value='<%=dto.getShortName() != null ? TextFormatter.formatWeb(dto.getShortName()) : ""%>'/></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=CommonsMessages.getMessage("currency.Symbol") %>:</td>
			<td><input type='text' name='symbol' id='symbol' value='<%=dto.getSymbol() != null ? TextFormatter.formatWeb(dto.getSymbol()) : ""%>'/></td>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment">&nbsp;</td>
			<td>&nbsp;</td>
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
				<th><%=CommonsMessages.getMessage("currency.Name") %></th>
				<th><%=CommonsMessages.getMessage("currency.ShortName") %></th>
				<th><%=CommonsMessages.getMessage("currency.Symbol") %></th>
			</tr>
<%		for (int a = 0;a < list.size();a++){ 
			Currency currency = (Currency) list.get(a); %>
			<tr onclick="javascript:clearForm();document.currency.idCurrency.value='<%=currency.getIdCurrency()%>';document.currency.action.value='search';sendData('currency');">
				<td><%=TextFormatter.formatWeb(currency.getName())%></td>
				<td><%=currency.getShortName() != null ? TextFormatter.formatWeb(currency.getShortName()) : ""%></td>
				<td><%=currency.getSymbol() != null ? TextFormatter.formatWeb(currency.getSymbol()) : ""%></td>
			</tr>
<%		} %>
		</table>
<%	} %>
	</div>
</form>
<script>
	function clearForm(){
		document.currency.idCurrency.value='';
		document.currency.name.value='';
		document.currency.shortName.value='';
		document.currency.symbol.value='';
		document.getElementById("resultsTable").style.visibility = "hidden";
		document.getElementById("addButton").style.visibility = "hidden";
		document.getElementById("searchButton").style.visibility = "visible";
		activeAdd('name');
		hideUpdateDelete();
		document.currency.idCurrency.focus();
	}
	

<%	if (error != null && !error.equalsIgnoreCase("")){ %>
	showError('<%=error%>');
<% 	} %>
<%	if (dto == null || dto.getIdCurrency() == null || dto.getIdCurrency().intValue() <= 0) {
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