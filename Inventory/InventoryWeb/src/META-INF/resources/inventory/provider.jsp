<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.industrika.commons.i18n.CommonsMessages" %>
<%@page import="com.industrika.inventory.i18n.InventoryMessages" %>
<%@page import="com.industrika.inventory.dto.Provider" %>
<%@page import="com.industrika.commons.dto.City" %>
<%@page import="com.industrika.commons.dao.CityDao" %>
<%@page import="com.industrika.commons.businesslogic.ApplicationContextProvider" %>
<%@page import="com.industrika.commons.utils.TextFormatter" %>
<%@page import="java.util.Vector" %>
<jsp:useBean id="dto" scope="request" class="com.industrika.inventory.dto.Provider"/>
<jsp:useBean id="list" scope="request" class="java.util.Vector"/>
<jsp:useBean id="message" scope="request" class="java.lang.String"/>
<jsp:useBean id="error" scope="request" class="java.lang.String"/>
<form name="provider" id="provider" class="normaltext">
	<input type="hidden" name="action" id="action"/>
	<input type="hidden" name="formName" id="formName" value="provider"/>
	<table class="commandbuttons">
		<tr>
			<td class="separator">&nbsp;</td>
			<td><img class="resetbutton" src="images/reset.png" onclick="javascript:clearForm()"></td>
			<td class="separator">&nbsp;</td>
			<td><div id="addButton"><img class="addbutton" src="images/new.png" onclick="javascript:document.provider.action.value='add';sendDataConfirm('provider','Alta para el proveedor')"></div></td>
			<td class="separator">&nbsp;</td>
			<td><div id="updateButton"><img class="savebutton" src="images/save.png" onclick="javascript:document.provider.action.value='update';sendDataConfirm('provider','Actualizaci&oacute;n para el proveedor')"></div></td>
			<td class="separator">&nbsp;</td>
			<td><div id="deleteButton"><img class="deletebutton" src="images/delete.png" onclick="javascript:document.provider.action.value='remove';sendDataConfirm('provider','Eliminado del proveedor')"></div></td>
			<td class="separator">&nbsp;</td>
			<td><img id="searchButton" class="searchbutton" src="images/search.png" onclick="javascript:document.provider.action.value='search';sendData('provider')"></td>
			<td class="separator">&nbsp;</td>
		</tr>
	</table>
	<br/>
	<table align="center">
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext"><%=TextFormatter.formatWeb(InventoryMessages.getMessage("provider.Title")) %></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext">&nbsp;</td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=TextFormatter.formatWeb(InventoryMessages.getMessage("provider.BusinessName")) %>:</td>
			<td>
				<input onkeyup="javascript:activeAdd('businessName')" type='text' size="50" name='businessName' id='businessName' value='<%=dto.getBusinessName() != null ? TextFormatter.formatWeb(dto.getBusinessName()) : ""%>'/>
				<input type='hidden' name='idPerson' id='idPerson' value='<%= dto.getIdPerson() != null ? ""+dto.getIdPerson().intValue() : "" %>'/>
			</td>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=TextFormatter.formatWeb(InventoryMessages.getMessage("provider.Rfc")) %>:</td>
			<td><input type='text' name='rfc' id='rfc' value='<%=dto.getRfc() != null ? TextFormatter.formatWeb(dto.getRfc()) : ""%>'/></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=TextFormatter.formatWeb(InventoryMessages.getMessage("provider.purchasesContactName")) %>:</td>
			<td>
				<input type='text' size="50" name='purchasesContactName' id='purchasesContactName' value='<%=dto.getPurchasesContactName() != null ? TextFormatter.formatWeb(dto.getPurchasesContactName()) : ""%>'/>
			</td>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=TextFormatter.formatWeb(InventoryMessages.getMessage("provider.Email")) %>:</td>
			<td><input type='text' name='email' id='email' value='<%=dto.getEmail() != null ? TextFormatter.formatWeb(dto.getEmail()) : ""%>'/></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=TextFormatter.formatWeb(InventoryMessages.getMessage("provider.Acumulated")) %>:</td>
			<td>
				<input type='text' class="labelaligment" name='acumulated' id='acumulated' value='<%=dto.getAcumulated() != null ? TextFormatter.getCurrencyValue(dto.getAcumulated()) : "0.00"%>' readonly="readonly"/>
			</td>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=TextFormatter.formatWeb(InventoryMessages.getMessage("provider.Balance")) %>:</td>
			<td><input type='text' class="labelaligment" name='balance' id='balance' value='<%=dto.getBalance() != null ? TextFormatter.getCurrencyValue(dto.getBalance()) : "0.00"%>' readonly="readonly"/></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=CommonsMessages.getMessage("street") %>:</td>
			<td>
				<input type='hidden' name='idAddress' id='idAddress' value='<%= (dto.getAddresses() != null && dto.getAddresses().size()>0 && dto.getAddresses().get(0) != null && dto.getAddresses().get(0).getIdAddress() != null) ? "" + dto.getAddresses().get(0).getIdAddress().intValue() : "" %>'/>
				<input type='text' size="50" name='street' id='street' value='<%=(dto.getAddresses() != null && dto.getAddresses().size()>0 && dto.getAddresses().get(0) != null && dto.getAddresses().get(0).getStreet() != null) ? TextFormatter.formatWeb(dto.getAddresses().get(0).getStreet()) : ""%>'/>
			</td>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=CommonsMessages.getMessage("extNumber") %>:</td>
			<td><input type='text' name='extNumber' id='extNumber' value='<%=(dto.getAddresses() != null && dto.getAddresses().size() > 0 && dto.getAddresses().get(0) != null && dto.getAddresses().get(0).getExtNumber() !=null) ? TextFormatter.formatWeb(dto.getAddresses().get(0).getExtNumber()) : ""%>'/></td>
			<td class="separator">&nbsp;</td>
		</tr>

		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=CommonsMessages.getMessage("intNumber") %>:</td>
			<td>
				<input type='text' name='intNumber' id='intNumber' value='<%=(dto.getAddresses() != null && dto.getAddresses().size()>0 && dto.getAddresses().get(0) != null && dto.getAddresses().get(0).getIntNumber() != null)? TextFormatter.formatWeb(dto.getAddresses().get(0).getIntNumber()) : ""%>'/>
			</td>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=CommonsMessages.getMessage("suburb") %>:</td>
			<td><input type='text' name='suburb' id='suburb' value='<%=(dto.getAddresses() != null && dto.getAddresses().size() > 0 && dto.getAddresses().get(0) != null && dto.getAddresses().get(0).getSuburb() !=null) ? TextFormatter.formatWeb(dto.getAddresses().get(0).getSuburb()) : ""%>'/></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=CommonsMessages.getMessage("city") %>:</td>
			<td>
				<select name="idCity" id="idCity">
<%
	String[] order = {"name"};
	try{
		Vector<City> cities = new Vector<City>(((CityDao)ApplicationContextProvider.getCtx().getBean("cityDao")).find(new City(),order));
		if (cities != null && cities.size() > 0){
			for (City city : cities){
%>			
			<option value="<%=city.getIdCity()%>" <%=(dto.getAddresses()!=null && dto.getAddresses().size() > 0 && dto.getAddresses().get(0) != null && dto.getAddresses().get(0).getCity()!=null && dto.getAddresses().get(0).getCity().getIdCity()!=null && dto.getAddresses().get(0).getCity().getIdCity().intValue()==city.getIdCity().intValue()) ? "selected='selected'":""%>><%= TextFormatter.formatWeb(city.getName()) %></option>
<%
			}
		}
	}catch(Exception ex){ex.getMessage();}
%>
				</select>
			</td>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=CommonsMessages.getMessage("zipCode") %>:</td>
			<td><input type='text' name='zipcode' id='zipcode' value='<%=(dto.getAddresses() != null && dto.getAddresses().size() > 0 && dto.getAddresses().get(0) != null && dto.getAddresses().get(0).getZipCode() !=null) ? TextFormatter.formatWeb(dto.getAddresses().get(0).getZipCode()) : ""%>'/></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=CommonsMessages.getMessage("phonetype") %>:</td>
			<td>
				<input type='hidden' name='idPhone' id='idPhone' value='<%= (dto.getPhones() != null && dto.getPhones().size()>0 && dto.getPhones().get(0) != null && dto.getPhones().get(0).getIdPhone() != null) ? "" + dto.getPhones().get(0).getIdPhone().intValue() : "" %>'/>
				<input type='text' name='phoneType' id='phoneType' value='<%= (dto.getPhones() != null && dto.getPhones().size()>0 && dto.getPhones().get(0) != null && dto.getPhones().get(0).getType() != null) ? dto.getPhones().get(0).getType() : "" %>'/>
				&nbsp;&nbsp;&nbsp;<%=CommonsMessages.getMessage("areaCode") %>:
				<input type='text' name='areaCode' id='areaCode' size = "4" value='<%=(dto.getPhones() != null && dto.getPhones().size()>0 && dto.getPhones().get(0) != null && dto.getPhones().get(0).getAreaCode() != null)? TextFormatter.formatWeb(dto.getPhones().get(0).getAreaCode()) : ""%>'/>
			</td>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=CommonsMessages.getMessage("number") %>:</td>
			<td><input type='text' name='phoneNumber' id='phoneNumber' value='<%=(dto.getPhones() != null && dto.getPhones().size() > 0 && dto.getPhones().get(0) != null && dto.getPhones().get(0).getNumber() !=null) ? TextFormatter.formatWeb(dto.getPhones().get(0).getNumber()) : ""%>'/></td>
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
				<th><%=TextFormatter.formatWeb(InventoryMessages.getMessage("provider.BusinessName")) %></th>
				<th><%=TextFormatter.formatWeb(InventoryMessages.getMessage("provider.Rfc")) %></th>
				<th><%=TextFormatter.formatWeb(InventoryMessages.getMessage("provider.purchasesContactName")) %></th>
				<th><%=TextFormatter.formatWeb(InventoryMessages.getMessage("provider.Email")) %></th>
				<th><%=CommonsMessages.getMessage("phones") %></th>
			</tr>
<%		for (int a = 0;a < list.size();a++){ 
				Provider provider = (Provider) list.get(a); %>
			<tr onclick="javascript:clearForm();document.provider.idPerson.value='<%=provider.getIdPerson()%>';document.provider.action.value='search';sendData('provider');">
				<td><%=TextFormatter.formatWeb(provider.getBusinessName())%></td>
				<td><%=TextFormatter.formatWeb(provider.getRfc())%></td>
				<td><%=TextFormatter.formatWeb(provider.getPurchasesContactName())%></td>
				<td><%=TextFormatter.formatWeb(provider.getEmail())%></td>
				<td><%=(provider.getPhones() != null && provider.getPhones().size() >= 1 && provider.getPhones().get(0) !=null && provider.getPhones().get(0).getNumber() != null) ? TextFormatter.formatWeb(provider.getPhones().get(0).getNumber()) : ""%></td>
			</tr>
<%		} %>
		</table>
<%	} %>
	</div>
</form>
<script>
	function clearForm(){
		document.provider.idPerson.value='';
		document.provider.businessName.value='';
		document.provider.rfc.value='';
		document.provider.purchasesContactName.value='';
		document.provider.email.value='';
		document.provider.acumulated.value='';
		document.provider.balance.value='';
		document.provider.idAddress.value='';
		document.provider.street.value='';
		document.provider.extNumber.value='';
		document.provider.intNumber.value='';
		document.provider.suburb.value='';
		document.provider.zipcode.value='';
		document.provider.idPhone.value='';
		document.provider.areaCode.value='';
		document.provider.phoneNumber.value='';
		document.provider.phoneType.value='';
		document.provider.idCity.selectedIndex=-1;
		document.getElementById("resultsTable").style.visibility = "hidden";
		document.getElementById("addButton").style.visibility = "hidden";
		document.getElementById("searchButton").style.visibility = "visible";
		activeAdd('businessName');
		hideUpdateDelete();
		document.provider.idPerson.focus();
	}
<% 	if (dto.getAddresses()==null || dto.getAddresses().size() <= 0 || dto.getAddresses().get(0) == null || dto.getAddresses().get(0).getCity() == null || dto.getAddresses().get(0).getCity().getIdCity() == null || dto.getAddresses().get(0).getCity().getIdCity().intValue() <= 0){%>
	document.provider.idCity.selectedIndex=-1;
<%	}%>
<%	if (error != null && !error.equalsIgnoreCase("")){ %>
	showError('<%=error%>');
<% 	} %>
<%	if (dto == null || dto.getIdPerson() == null || dto.getIdPerson().intValue() <= 0) {
		if (list == null || list.size() <= 0){ 
			if (error == null || error.equalsIgnoreCase("")){
%>
	clearForm();
<%			}
		} else { %>
	document.provider.idState.selectedIndex=-1;
	activeAdd("businessName");
<%		} %>
		
	hideUpdateDelete();
<% 	} else { %>
	document.getElementById("addButton").style.visibility = "hidden";
	document.getElementById("searchButton").style.visibility = "hidden";
<%	} %>

</script>