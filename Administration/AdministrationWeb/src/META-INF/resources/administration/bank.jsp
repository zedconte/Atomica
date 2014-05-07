<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.industrika.administration.i18n.AdministrationMessages" %>
<%@page import="com.industrika.administration.dto.Bank" %>
<%@page import="com.industrika.commons.utils.TextFormatter" %>
<%@page import="java.util.Vector" %>
<jsp:useBean id="dto" scope="request" class="com.industrika.administration.dto.Bank"/>
<jsp:useBean id="list" scope="request" class="java.util.Vector"/>
<jsp:useBean id="message" scope="request" class="java.lang.String"/>
<jsp:useBean id="error" scope="request" class="java.lang.String"/>
<script>
	$(function() {
		activeSearch('idBank');
 	});
 
function activeSearch(id){
	var value=$("form[name=bank] #"+id).val();
	if (value !== ''){
		$(".addButton").css("visibility", "hidden");
		$(".savebutton").css("visibility", "visible");
		$(".deletebutton").css("visibility", "visible");
	} else {
		$(".addButton").css("visibility", "visible");
		$(".savebutton").css("visibility", "hidden");
		$(".deletebutton").css("visibility", "hidden");
	}
}
</script>
<form name="bank" id="bank" class="normaltext">
	<input type="hidden" name="action" id="action"/>
	<input type="hidden" name="formName" id="formName" value="bank"/>
	<table class="commandbuttons">
		<tr>
			<td class="separator">&nbsp;</td>
			<td><img class="resetbutton" src="images/reset.png" onclick="javascript:clearForm()"></td>
			<td class="separator">&nbsp;</td>
			<td><img class="addbutton" src="images/new.png" onclick="javascript:document.bank.action.value='add';sendDataConfirm('bank','alta del banco')"></td>
			<td class="separator">&nbsp;</td>
			<td><img class="savebutton" src="images/save.png" onclick="javascript:document.bank.action.value='update';sendDataConfirm('bank','actualización del banco')"></td>
			<td class="separator">&nbsp;</td>
			<td><img class="deletebutton" src="images/delete.png" onclick="javascript:document.bank.action.value='remove';sendDataConfirm('bank','eliminado del banco')"></td>
			<td class="separator">&nbsp;</td>
			<td><img class="searchbutton" src="images/search.png" onclick="javascript:document.bank.action.value='search';sendData('bank')"></td>
			<td class="separator">&nbsp;</td>
		</tr>
	</table>
	<br/>
	<table align="center">
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext"><%=AdministrationMessages.getMessage("bank.Title") %></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext">&nbsp;</td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td><input type='hidden' name='idBank' id='idBank' onkeyup="javascript:activeSearch('idBank');" value='<%= dto.getIdBank() != null ? ""+dto.getIdBank().intValue() : "" %>'/></td>
			<td class="labelaligment"><%=AdministrationMessages.getMessage("bank.Name") %>:</td>
			<td><input type='text' name='name' id='name' size="50" value='<%=dto.getName() != null ? TextFormatter.formatWeb(dto.getName()) : ""%>'/></td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=AdministrationMessages.getMessage("bank.Acronym") %>:</td>
			<td><input type='text' name='acronym' id='acronym' value='<%=dto.getAcronym() != null ? ""+TextFormatter.formatWeb(dto.getAcronym()) : ""%>'/></td>
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
			<th><%=AdministrationMessages.getMessage("bank.Name") %></th>
			<th><%=AdministrationMessages.getMessage("bank.Acronym") %></th>
		</tr>
<%		for (int a = 0;a < list.size();a++){ 
			Bank bank = (Bank) list.get(a); %>
		<tr onclick="javascript:clearForm();document.bank.idBank.value='<%=bank.getIdBank()%>';document.bank.action.value='search';sendData('bank');">
			<td><%=TextFormatter.formatWeb(bank.getName())%></td>
			<td><%=bank.getAcronym() != null ? TextFormatter.formatWeb(bank.getAcronym()) : ""%></td>
		</tr>
<%		} %>
	</table>
<%	} %>
</form>
<script>
	function clearForm(){
		document.bank.idBank.value='';
		document.bank.name.value='';
		document.bank.acronym.value='';
		document.bank.idBank.focus();
		activeSearch('idBank');
	}
	
<%	if (error != null && !error.equalsIgnoreCase("")){ %>
	showError('<%=error%>');
<% 	} %>
</script>