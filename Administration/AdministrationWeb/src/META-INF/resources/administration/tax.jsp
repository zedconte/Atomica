<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.industrika.administration.i18n.AdministrationMessages" %>
<%@page import="com.industrika.administration.dto.Tax" %>
<%@page import="java.util.Vector" %>
<%@page import="com.industrika.commons.utils.TextFormatter" %>
<jsp:useBean id="dto" scope="request" class="com.industrika.administration.dto.Tax"/>
<jsp:useBean id="list" scope="request" class="java.util.Vector"/>
<jsp:useBean id="message" scope="request" class="java.lang.String"/>
<jsp:useBean id="error" scope="request" class="java.lang.String"/>
<script>
function initRadio(){
    var isPercentage=$("form[name=taxescatalogue] #isPercentage").val();
    
    if(isPercentage==='true'){
    	$('input:radio[name=taxType][value=PERCENTAGE]').click();
    	}
    
     if(isPercentage==='false'){
    	$('input:radio[name=taxType][value=AMOUNT]').click();
    	}
 }

</script>
<form name="taxescatalogue" id="taxescatalogue" class="normaltext">
	<input type="hidden" name="action" id="action"/>
	<input type="hidden" name="formName" id="formName" value="taxescatalogue"/>
	<input type="hidden" name="isPercentage" id="isPercentage" value='<%= dto.isPercentage() != null ? dto.isPercentage() : true %>'/>
	<table class="commandbuttons">
		<tr>
			<td class="separator">&nbsp;</td>
			<td><img class="resetbutton" src="images/reset.png" onclick="javascript:clearForm()"></td>
			<td class="separator">&nbsp;</td>
			<td><div id="addButton"><img class="addbutton" src="images/new.png" onclick="javascript:document.taxescatalogue.action.value='add';sendDataConfirm('taxescatalogue','alta del Impuesto')"></div></td>
			<td class="separator">&nbsp;</td>
			<td><div id="updateButton"><img class="savebutton" src="images/save.png" onclick="javascript:document.taxescatalogue.action.value='update';sendDataConfirm('taxescatalogue','actualización del Impuesto')"></div></td>
			<td class="separator">&nbsp;</td>
			<td><div id="deleteButton"><img class="deletebutton" src="images/delete.png" onclick="javascript:document.taxescatalogue.action.value='remove';sendDataConfirm('taxescatalogue','eliminado del Impuesto')"></div></td>
			<td class="separator">&nbsp;</td>
			<td><div id="searchButton"><img class="searchbutton" src="images/search.png" onclick="javascript:document.taxescatalogue.action.value='search';sendData('taxescatalogue')"></div></td>
			<td class="separator">&nbsp;</td>
		</tr>
	</table>
	<br/>
	<table align="center">
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext"><%=AdministrationMessages.getMessage("tax.Title") %></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext">&nbsp;</td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=AdministrationMessages.getMessage("tax.name") %>:</td>
			<td>
				<input type='hidden' name='idTax' id='idTax'  value='<%= dto.getIdTax() != null ? ""+dto.getIdTax().intValue() : "" %>'/>
				<input type='text' name='name' id='name' onkeyup="javascript:activeAdd('name')" value='<%=dto.getName() != null ? TextFormatter.formatWeb(dto.getName()) : ""%>'/>
			</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=AdministrationMessages.getMessage("tax.initials") %>:</td>
			<td><input type='text' name='initials' id='initials' value='<%=dto.getInitials() != null ? ""+TextFormatter.formatWeb(dto.getInitials()):""%>'/></td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=AdministrationMessages.getMessage("tax.select.type") %>:</td>
			<td><input type="radio" name='taxType' id='taxType' value="PERCENTAGE" checked>Porcentaje</td>
			<td class="separator">&nbsp;</td>
			<td> <input type="radio" name='taxType' id='taxType' value="AMOUNT">Cantidad<br></td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=AdministrationMessages.getMessage("tax.amount") %>:</td>
			<td><input type='text' name='taxValue' id='taxValue' value='<%=dto.getTaxValue() != null ? ""+dto.getTaxValue() : ""%>'/></td>
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
			<th><%=AdministrationMessages.getMessage("tax.name") %></th>
			<th><%=AdministrationMessages.getMessage("tax.initials") %></th>
			<th><%=AdministrationMessages.getMessage("bank.operation.amount") %></th>
		</tr>
<%		for (int a = 0;a < list.size();a++){ 
			Tax tax = (Tax) list.get(a);
			Double amount = tax.getTaxValue();
			String formatAmt= "";
			if(tax.isPercentage()){
				/*Format %*/
				formatAmt=TextFormatter.getPercentage(amount, true);
			}
			else{
				/*Format amount $*/
				formatAmt=TextFormatter.getCurrencyValue("es","MX",amount);
			}
%>
		<tr onclick="javascript:clearForm();document.taxescatalogue.idTax.value='<%=tax.getIdTax()%>';document.taxescatalogue.action.value='search';sendData('taxescatalogue');">
			<td><%=TextFormatter.formatWeb(tax.getName())%></td>
			<td><%=TextFormatter.formatWeb(tax.getInitials())%></td>
			<td><%=formatAmt%></td>
		</tr>
<%		} %>
	</table>
<%	} %>
	</div>
</form>
<script>
	function clearForm(){
		document.taxescatalogue.idTax.value='';
		document.taxescatalogue.name.value='';
		document.taxescatalogue.initials.value='';
		document.taxescatalogue.taxValue.value='';
		initRadio();
		activeAdd("name");
		document.getElementById("resultsTable").style.visibility = "hidden";
		document.getElementById("addButton").style.visibility = "hidden";
		document.getElementById("updateButton").style.visibility = "hidden";
		document.getElementById("deleteButton").style.visibility = "hidden";
		document.getElementById("searchButton").style.visibility = "visible";
		document.taxescatalogue.name.focus();
	}
	
<%	if (error != null && !error.equalsIgnoreCase("")){ %>
	showError('<%=error%>');
<% 	} %>
<%	if (dto == null || dto.getIdTax() == null || dto.getIdTax().intValue() <= 0) {
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