<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.industrika.administration.i18n.AdministrationMessages" %>
<%@page import="com.industrika.administration.dto.Account" %>
<%@page import="com.industrika.commons.utils.TextFormatter" %>
<%@page import="java.util.Vector" %>
<jsp:useBean id="dto" scope="request" class="com.industrika.administration.dto.Account"/>
<jsp:useBean id="list" scope="request" class="java.util.Vector"/>
<jsp:useBean id="message" scope="request" class="java.lang.String"/>
<jsp:useBean id="error" scope="request" class="java.lang.String"/>
<script>
	$(function() {
		activeSearch('refNumber','account');
	 });
 
function activeSearch(id, form){
	var value=$("form[name="+form+"] #"+id).val();
	
	if (value !== ''){
		$(".addButton").css("visibility", "visible");
		$(".savebutton").css("visibility", "visible");
	} else {
		$(".addButton").css("visibility", "hidden");
		$(".savebutton").css("visibility", "hidden");
	}
}
</script>
<form name="account" id="account" class="normaltext">
	<input type="hidden" name="action" id="action"/>
	<input type="hidden" name="formName" id="formName" value="account"/>
	<table class="commandbuttons">
		<tr>
			<td class="separator">&nbsp;</td>
			<td><img class="resetbutton" src="images/reset.png" onclick="javascript:clearForm()"></td>
			<td class="separator">&nbsp;</td>
			<td><img class="addbutton" src="images/new.png" onclick="javascript:document.account.action.value='add';sendDataConfirm('account','alta de cuenta')"></td>
			<td class="separator">&nbsp;</td>
			<td><img class="savebutton" src="images/save.png" onclick="javascript:document.account.action.value='update';sendDataConfirm('account','actualización del cuenta')"></td>
			<td class="separator">&nbsp;</td>
			<td><img class="searchbutton" src="images/search.png" onclick="javascript:document.account.action.value='search';sendData('account')"></td>
			<td class="separator">&nbsp;</td>
		</tr>
	</table>
	<br/>
	<table align="center">
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext"><%=AdministrationMessages.getMessage("account.Title") %></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext">&nbsp;</td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment "><%=AdministrationMessages.getMessage("account.RefNumber") %>:</td>
			<td><input type='text' name='refNumber' id='refNumber'  onkeyup="javascript:activeSearch('refNumber','account');" value='<%= dto.getRefNumber() != null ? ""+dto.getRefNumber() : "" %>'/></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=AdministrationMessages.getMessage("account.AccountName") %>:</td>
			<td><input type='text' name='accountName' id='accountName' size="50" value='<%=dto.getAccountName() != null ? TextFormatter.formatWeb(dto.getAccountName()) : ""%>'/></td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=AdministrationMessages.getMessage("account.Level") %>:</td>
			<td>
				<select name='level' id='level' onchange="selectVal(this.value)" value='<%=dto.getLevel() != null ? ""+dto.getLevel() : "1"%>'>
					<option value="1" onchange="selectVal(1)">Nivel 1</option>
					<option value="2" onchange="selectVal(2)">Nivel 2</option>
					<option value="3" onchange="selectVal(3)">Nivel 3</option>
				</select>
			</td>
			
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
			<th><%=AdministrationMessages.getMessage("account.RefNumber") %></th>
			<th><%=AdministrationMessages.getMessage("account.AccountName") %></th>
			<th><%=AdministrationMessages.getMessage("account.Level") %></th>
		</tr>
<%		for (int a = 0;a < list.size();a++){ 
			Account account = (Account) list.get(a); %>
		<tr onclick="javascript:clearForm();document.account.refNumber.value='<%=account.getRefNumber()%>';document.account.action.value='search';sendData('account');">
			<td><%=TextFormatter.formatWeb(account.getRefNumber())%></td>
			<td><%=TextFormatter.formatWeb(account.getAccountName())%></td>
			<td><%=account.getLevel()%></td>
		</tr>
<%		} %>
	</table>
<%	} %>
</form>
<script>
	function clearForm(){
		document.account.refNumber.value='';
		document.account.accountName.value='';
		document.account.level.value='';
		document.account.refNumber.focus();
		activeSearch('refNumber','account');
	}
	function selectVal(value)
    {
     document.account.level.value=value;
	}
	
<%	if (error != null && !error.equalsIgnoreCase("")){ %>
	showError('<%=error%>');
<% 	} %>
</script>