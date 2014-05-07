<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.industrika.commons.i18n.CommonsMessages" %>
<%@page import="com.industrika.sales.i18n.SalesMessages" %>
<%@page import="com.industrika.sales.dto.Customer" %>
<%@page import="com.industrika.commons.dto.City" %>
<%@page import="com.industrika.commons.dao.CityDao" %>
<%@page import="com.industrika.commons.businesslogic.ApplicationContextProvider" %>
<%@page import="com.industrika.commons.utils.TextFormatter" %>
<%@page import="java.util.Vector" %>
<jsp:useBean id="dto" scope="request" class="com.industrika.sales.dto.Customer"/>
<jsp:useBean id="list" scope="request" class="java.util.Vector"/>
<jsp:useBean id="message" scope="request" class="java.lang.String"/>
<jsp:useBean id="error" scope="request" class="java.lang.String"/>
<form name="customer" id="customer" class="normaltext">
	<input type="hidden" name="action" id="action"/>
	<input type="hidden" name="formName" id="formName" value="customer"/>
	<table class="commandbuttons">
		<tr>
			<td class="separator">&nbsp;</td>
			<td><img class="resetbutton" src="images/reset.png" onclick="javascript:clearForm()"></td>
			<td class="separator">&nbsp;</td>
			<td><div id="addButton"><img class="addbutton" src="images/new.png" onclick="javascript:document.customer.action.value='add';sendDataConfirm('customer','Alta para el cliente')"></div></td>
			<td class="separator">&nbsp;</td>
			<td><div id="updateButton"><img class="savebutton" src="images/save.png" onclick="javascript:document.customer.action.value='update';sendDataConfirm('customer','Actualizaci&oacute;n para el cliente')"></div></td>
			<td class="separator">&nbsp;</td>
			<td><div id="deleteButton"><img class="deletebutton" src="images/delete.png" onclick="javascript:document.customer.action.value='remove';sendDataConfirm('customer','Eliminado del cliente')"></div></td>
			<td class="separator">&nbsp;</td>
			<td><img id="searchButton" class="searchbutton" src="images/search.png" onclick="javascript:document.customer.action.value='search';sendData('customer')"></td>
			<td class="separator">&nbsp;</td>
		</tr>
	</table>
	<br/>
	<table align="center">
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext"><%=TextFormatter.formatWeb(SalesMessages.getMessage("customer.Title")) %></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext">&nbsp;</td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=TextFormatter.formatWeb(SalesMessages.getMessage("customer.BusinessName")) %>:</td>
			<td>
				<input onkeyup="javascript:activeAdd('businessName')" type='text' size="50" name='businessName' id='businessName' value='<%=dto.getBusinessName() != null ? TextFormatter.formatWeb(dto.getBusinessName()) : ""%>'/>
				<input type='hidden' name='idPerson' id='idPerson' value='<%= dto.getIdPerson() != null ? ""+dto.getIdPerson().intValue() : "" %>'/>
			</td>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=TextFormatter.formatWeb(SalesMessages.getMessage("customer.Rfc")) %>:</td>
			<td><input type='text' name='rfc' id='rfc' value='<%=dto.getRfc() != null ? TextFormatter.formatWeb(dto.getRfc()) : ""%>'/></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=TextFormatter.formatWeb(SalesMessages.getMessage("customer.salesContactName")) %>:</td>
			<td>
				<input type='text' size="50" name='salesContactName' id='salesContactName' value='<%=dto.getSalesContactName() != null ? TextFormatter.formatWeb(dto.getSalesContactName()) : ""%>'/>
			</td>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=TextFormatter.formatWeb(SalesMessages.getMessage("customer.Email")) %>:</td>
			<td><input type='text' name='email' id='email' value='<%=dto.getEmail() != null ? TextFormatter.formatWeb(dto.getEmail()) : ""%>'/></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=TextFormatter.formatWeb(SalesMessages.getMessage("customer.Acumulated")) %>:</td>
			<td>
				<input type='text' class="labelaligment" name='acumulated' id='acumulated' value='<%=dto.getAcumulated() != null ? TextFormatter.getCurrencyValue("sp","MX",dto.getAcumulated()) : "0.00"%>' readonly="readonly"/>
			</td>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=TextFormatter.formatWeb(SalesMessages.getMessage("customer.Balance")) %>:</td>
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
				<th><%=TextFormatter.formatWeb(SalesMessages.getMessage("customer.BusinessName")) %></th>
				<th><%=TextFormatter.formatWeb(SalesMessages.getMessage("customer.Rfc")) %></th>
				<th><%=TextFormatter.formatWeb(SalesMessages.getMessage("customer.salesContactName")) %></th>
				<th><%=TextFormatter.formatWeb(SalesMessages.getMessage("customer.Email")) %></th>
				<th><%=CommonsMessages.getMessage("phones") %></th>
			</tr>
<%		for (int a = 0;a < list.size();a++){ 
				Customer customer = (Customer) list.get(a); %>
			<tr onclick="javascript:clearForm();document.customer.idPerson.value='<%=customer.getIdPerson()%>';document.customer.action.value='search';sendData('customer');">
				<td><%=TextFormatter.formatWeb(customer.getBusinessName())%></td>
				<td><%=TextFormatter.formatWeb(customer.getRfc())%></td>
				<td><%=TextFormatter.formatWeb(customer.getSalesContactName())%></td>
				<td><%=TextFormatter.formatWeb(customer.getEmail())%></td>
				<td><%=(customer.getPhones() != null && customer.getPhones().size() >= 1 && customer.getPhones().get(0) !=null && customer.getPhones().get(0).getNumber() != null) ? TextFormatter.formatWeb(customer.getPhones().get(0).getNumber()) : ""%></td>
			</tr>
<%		} %>
		</table>
<%	} %>
	</div>
</form>
<script>
	function clearForm(){
		document.customer.idPerson.value='';
		document.customer.businessName.value='';
		document.customer.rfc.value='';
		document.customer.salesContactName.value='';
		document.customer.email.value='';
		document.customer.acumulated.value='';
		document.customer.balance.value='';
		document.customer.idAddress.value='';
		document.customer.street.value='';
		document.customer.extNumber.value='';
		document.customer.intNumber.value='';
		document.customer.suburb.value='';
		document.customer.zipcode.value='';
		document.customer.idPhone.value='';
		document.customer.areaCode.value='';
		document.customer.phoneNumber.value='';
		document.customer.phoneType.value='';
		document.customer.idCity.selectedIndex=-1;
		document.getElementById("resultsTable").style.visibility = "hidden";
		document.getElementById("addButton").style.visibility = "hidden";
		document.getElementById("searchButton").style.visibility = "visible";
		activeAdd('businessName');
		hideUpdateDelete();
		document.customer.idPerson.focus();
	}
<% 	if (dto.getAddresses()==null || dto.getAddresses().size() <= 0 || dto.getAddresses().get(0) == null || dto.getAddresses().get(0).getCity() == null || dto.getAddresses().get(0).getCity().getIdCity() == null || dto.getAddresses().get(0).getCity().getIdCity().intValue() <= 0){%>
	document.customer.idCity.selectedIndex=-1;
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
	document.customer.idCity.selectedIndex=-1;
	activeAdd("businessName");
<%		} %>
		
	hideUpdateDelete();
<% 	} else { %>
	document.getElementById("addButton").style.visibility = "hidden";
	document.getElementById("searchButton").style.visibility = "hidden";
<%	} %>

</script>