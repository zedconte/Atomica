<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.industrika.administration.i18n.AdministrationMessages" %>
<%@page import="com.industrika.administration.dto.Account" %>
<%@page import="com.industrika.commons.utils.TextFormatter" %>
<%@page import="java.util.Vector" %>
<jsp:useBean id="list" scope="request" class="java.util.Vector"/>
<jsp:useBean id="message" scope="request" class="java.lang.String"/>
<jsp:useBean id="error" scope="request" class="java.lang.String"/>
<jsp:useBean id="action" scope="request" class="java.lang.String"/>

<script>
	$(function() {
	    initRadio();
	 });
	 
	 function initRadio(){
	    var value=$("form[name=reportescontables] #reportT").val();
	    if(value==="comprobacion"){
	    	$('input:radio[name=reportType][value=comprobacion]').click();
	    }
	    else if(value==="resultados"){
	   		$('input:radio[name=reportType][value=resultados]').click();
	    } 
	}

</script>

<form name="reportescontables" id="reportescontables" class="normaltext">
	<input type="hidden" name="action" id="action"/>
	<input type="hidden" name="formName" id="formName" value="reportescontables"/>
	<input type="hidden" name="reportT" id="reportT" value="<%=message %>"/>
	<br>
	<table class="commandbuttons">
		<tr>
			<td class="separator">&nbsp;</td>
			<td><img class="searchbutton" src="images/search.png" onclick="sendData('reportescontables')"></td>
			<td class="separator">&nbsp;</td>
		</tr>
	</table>
	<br>
	<table align="center">
		<tr>
			<td class="separator">&nbsp;</td>
			<td><input type="radio" name='reportType' id='reportType' value="resultados" onclick="javascript:document.reportescontables.action.value='resultados';" checked>Estado de resultados</td>
			<td class="separator">&nbsp;</td>
			<td> <input type="radio" name='reportType' id='reportType' value="comprobacion" onclick="javascript:document.reportescontables.action.value='comprobacion';">Balanza de comprobación<br></td>
			<td class="separator">&nbsp;</td>
		</tr>
	</table>
	 <br/>
<% if (list != null && list.size()>0){ %>	
	<table class="resultsTable">
		<tr>
			<th><%=AdministrationMessages.getMessage("account.RefNumber") %></th>
			<th><%=AdministrationMessages.getMessage("account.AccountName") %></th>
			<th><%=AdministrationMessages.getMessage("account.Debe") %></th>
			<th><%=AdministrationMessages.getMessage("account.Haber") %></th>
		</tr>
<%		for (int a = 0;a < list.size();a++){ 
			Account account = (Account) list.get(a);
			double debe=0;
			double haber=0;
			if(account.getBalance()>0){
				haber=account.getBalance();
			}
			if(account.getBalance()<0){
				debe=account.getBalance();
			}
%>
		<tr>
			<td><%=TextFormatter.formatWeb(account.getRefNumber())%></td>
			<td><%=TextFormatter.formatWeb(account.getAccountName())%></td>
			<td><%=TextFormatter.getCurrencyValue("es","MX",debe)%></td>
			<td><%=TextFormatter.getCurrencyValue("es","MX",haber)%></td>
		</tr>
<%		} %>
	</table>
<%	} %>
	
</form>
<script>
<%	if (error != null && !error.equalsIgnoreCase("")){ %>
	showError('<%=error%>');
<% 	} %>
</script>
