<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.industrika.administration.i18n.AdministrationMessages" %>
<%@page import="com.industrika.administration.dto.BankOperation" %>
<%@page import="com.industrika.administration.dto.Bank" %>
<%@page import="java.util.Vector" %>
<%@page import="com.industrika.commons.utils.TextFormatter" %>
<jsp:useBean id="dto" scope="request" class="com.industrika.administration.dto.BankOperation"/>
<jsp:useBean id="list" scope="request" class="java.util.Vector"/>
<jsp:useBean id="message" scope="request" class="java.lang.String"/>
<jsp:useBean id="error" scope="request" class="java.lang.String"/>
<script>
  $(function() {
	activeSearch('idOperation','bankoperation');
	initRadio();
	
	var bankAct=$("form[name=bankoperation] #bankAccounts").val();
	<%Bank myBank=dto.getBank();
	String deftVal=""+dto.getBankId();
	 if(myBank!=null){
		 deftVal=""+ myBank.getIdBank();
	 }
	
	%>

	if (bankAct!="") {
		var JsonBankActs=jQuery.parseJSON(bankAct);
		$(JsonBankActs).each(function() {  
		    var ID = this.bankId;
		    var NAME = this.bankName;
		    $('form[name=bankoperation] #bankId').append(new Option(NAME,ID));
		});
	}
	
	var selectedValue=<%=deftVal%>
	
	 $("form[name=bankoperation] #bankId").val(selectedValue);
	
	    
 });
 
function activeSearch(id, formname){
	var value=$("form[name="+formname+"] #"+id).val();
	if (value !== ''){
		$(".addButton").css("visibility", "visible");
		$(".savebutton").css("visibility", "visible");
		$(".deletebutton").css("visibility", "visible");
	} else {
		$(".addButton").css("visibility", "hidden");
		$(".savebutton").css("visibility", "hidden");
		$(".deletebutton").css("visibility", "hidden");
	}
}

function initRadio(){
    var policyVal=$("form[name=bankoperation] #typeOperation").val();
    
    if(policyVal==="DEPOSITO"){
    	$('input:radio[name=operationType][value=DEPOSITO]').click();
    	}
    
     if(policyVal==="RETIRO"){
    	$('input:radio[name=operationType][value=RETIRO]').click();
    	}
    
     if(policyVal==="TRANSPASO"){
    	$('input:radio[name=operationType][value=TRANSPASO]').click();
    	}
 }
</script>
<form name="bankoperation" id="bankoperation" class="normaltext">
	<input type="hidden" name="action" id="action"/>
	<input type="hidden" name="formName" id="formName" value="bankoperation"/>
	<table class="commandbuttons">
		<tr>
			<td class="separator">&nbsp;</td>
			<td><img class="resetbutton" src="images/reset.png" onclick="javascript:clearForm()"></td>
			<td class="separator">&nbsp;</td>
			<td><img class="addbutton" src="images/new.png" onclick="javascript:document.bankoperation.action.value='add';sendDataConfirm('bankoperation','Agregar la operación')"></td>
			<td class="separator">&nbsp;</td>
			<td><img class="savebutton" src="images/save.png" onclick="javascript:document.bankoperation.action.value='update';sendDataConfirm('bankoperation','actualización de la operacion')"></td>
			<td class="separator">&nbsp;</td>
			<td><img class="deletebutton" src="images/delete.png" onclick="javascript:document.bankoperation.action.value='remove';sendDataConfirm('bankoperation','eliminado de de la operacion')"></td>
			<td class="separator">&nbsp;</td>
			<td><img class="searchbutton" src="images/search.png" onclick="javascript:document.bankoperation.action.value='search';sendData('bankoperation')"></td>
			<td class="separator">&nbsp;</td>
		</tr>
	</table>
	<br>
	<table align="center">
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext"><%=AdministrationMessages.getMessage("bank.operation.Title") %></td>
			<td class="separator">&nbsp;</td>
		</tr>
	</table>
	<br>
	<table align="center">
		<tr>
			<td class="separator">&nbsp;</td>
			<td><input type="radio" name='operationType' id='operationType' value="DEPOSITO" checked>Depósito</td>
			<td class="separator">&nbsp;</td>
			<td> <input type="radio" name='operationType' id='operationType' value="RETIRO">Retiro<br></td>
			<td class="separator">&nbsp;</td>
			<td> <input type="radio" name='operationType' id='operationType' value="TRANSPASO">Transpaso<br></td>
			<td class="separator">&nbsp;</td>
		</tr>
		</table>
	
	<br/>
	<table align="center">
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment "><%=AdministrationMessages.getMessage("bank.operation.Id") %>:</td>
			<td><input type='text' name='idOperation' id='idOperation' onkeyup="activeSearch('idOperation','bankoperation');" value='<%= dto.getIdOperation() != null ? ""+dto.getIdOperation().intValue() : "" %>'/></td>
			<td><input type='text' name="typeOperation"  id="typeOperation" style="display:none;position: absolute;" value='<%= dto.getOperationType() != null ? TextFormatter.formatWeb(dto.getOperationType()) : "DEPOSITO" %>'/></td>
			<td><input type='text' name="bankAccounts"  id="bankAccounts" style="display:none;position: absolute;" value='<%= dto.getBankAccounts() != null ? TextFormatter.formatWeb(dto.getBankAccounts()) : "" %>'/></td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext">&nbsp;</td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment "><%=AdministrationMessages.getMessage("bank.operation.description") %>:</td>
			<td><input type='text' name='description' id='description' value='<%= dto.getDescription()!= null ? ""+dto.getDescription() : "" %>'/></td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext">&nbsp;</td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment "><%=AdministrationMessages.getMessage("bank.operation.amount") %>:</td>
			<td><input type='text' name='amount' id='amount' value='<%= dto.getAmount()!= null ? ""+dto.getAmount() : "" %>'/></td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext">&nbsp;</td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment "><%=AdministrationMessages.getMessage("bank.operation.select") %>:</td>
			<td><select id="bankId" name="bankId" style="width: 150px;"></select></td>
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
			<th><%=AdministrationMessages.getMessage("bank.operation.type") %></th>
			<th><%=AdministrationMessages.getMessage("bank.operation.description") %></th>
			<th><%=AdministrationMessages.getMessage("bank.operation.amount") %></th>
			<th><%=AdministrationMessages.getMessage("bank.operation.bankName") %></th>
		</tr>
<%		for (int a = 0;a < list.size();a++){ 
		BankOperation bankOperation = (BankOperation) list.get(a);
		Bank bank = bankOperation.getBank();
		String bankName=bank.getName()!= null? bank.getName():"";
%>
		<tr onclick="javascript:clearForm();document.bankoperation.idOperation.value='<%=bankOperation.getIdOperation()%>';document.bankoperation.action.value='search';sendData('bankoperation');">
			<td><%=TextFormatter.formatWeb(bankOperation.getOperationType())%></td>
			<td><%=TextFormatter.formatWeb(bankOperation.getDescription())%></td>
			<td><%=TextFormatter.getCurrencyValue("es","MX",bankOperation.getAmount())%></td>
			<td><%=TextFormatter.formatWeb(bankName)%></td>
		</tr>
<%		} %>
	</table>
<%	} %>
</form>
<script>
	function clearForm(){
		document.bankoperation.idOperation.value='';
		document.bankoperation.description.value='';
		document.bankoperation.amount.value='';
		activeSearch('idOperation','bankoperation');
	}
	
<%	if (error != null && !error.equalsIgnoreCase("")){ %>
	showError('<%=error%>');
<% 	} %>
</script>