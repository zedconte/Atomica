<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.industrika.inventory.i18n.InventoryMessages" %>
<%@page import="com.industrika.inventory.dto.Provider" %>
<%@page import="com.industrika.inventory.dto.Item" %>
<%@page import="com.industrika.administration.dto.Tax" %>
<%@page import="com.industrika.commons.dto.Trademark" %>
<%@page import="com.industrika.commons.dao.TrademarkDao" %>
<%@page import="com.industrika.administration.dao.TaxDao" %>
<%@page import="com.industrika.inventory.dao.ProviderDao" %>
<%@page import="com.industrika.commons.businesslogic.ApplicationContextProvider" %>
<%@page import="com.industrika.commons.utils.TextFormatter" %>
<%@page import="java.util.Vector" %>
<jsp:useBean id="dto" scope="request" class="com.industrika.inventory.dto.Item"/>
<jsp:useBean id="list" scope="request" class="java.util.Vector"/>
<jsp:useBean id="message" scope="request" class="java.lang.String"/>
<jsp:useBean id="error" scope="request" class="java.lang.String"/>
<form name="item" id="item" class="normaltext">
	<input type="hidden" name="action" id="action"/>
	<input type="hidden" name="formName" id="formName" value="item"/>
	<table class="commandbuttons">
		<tr>
			<td class="separator">&nbsp;</td>
			<td><img class="resetbutton" src="images/reset.png" onclick="javascript:clearForm()"></td>
			<td class="separator">&nbsp;</td>
			<td><div id="addButton"><img class="addbutton" src="images/new.png" onclick="javascript:document.item.action.value='add';sendDataConfirm('item','Alta para el art&iacute;lo')"></div></td>
			<td class="separator">&nbsp;</td>
			<td><div id="updateButton"><img class="savebutton" src="images/save.png" onclick="javascript:document.item.action.value='update';sendDataConfirm('item','Actualizaci&oacute;n para el art&iacute;culo')"></div></td>
			<td class="separator">&nbsp;</td>
			<td><div id="deleteButton"><img class="deletebutton" src="images/delete.png" onclick="javascript:document.item.action.value='remove';sendDataConfirm('item','Eliminado del art&iacute;culo')"></div></td>
			<td class="separator">&nbsp;</td>
			<td><img id="searchButton" class="searchbutton" src="images/search.png" onclick="javascript:document.item.action.value='search';sendData('item')"></td>
			<td class="separator">&nbsp;</td>
		</tr>
	</table>
	<br/>
	<table align="center">
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext"><%=TextFormatter.formatWeb(InventoryMessages.getMessage("item.Title")) %></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext">&nbsp;</td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=TextFormatter.formatWeb(InventoryMessages.getMessage("item.Code")) %>:</td>
			<td>
				<input onkeyup="javascript:activeAdd('code')" type='text' name='code' id='code' value='<%=dto.getCode() != null ? TextFormatter.formatWeb(dto.getCode()) : ""%>'/>
				<input type='hidden' name='idItem' id='idItem' value='<%= dto.getIdItem() != null ? ""+dto.getIdItem().intValue() : "" %>'/>
			</td>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=TextFormatter.formatWeb(InventoryMessages.getMessage("item.Name")) %>:</td>
			<td><input type='text' name='name' id='name' value='<%=dto.getName() != null ? TextFormatter.formatWeb(dto.getName()) : ""%>'/></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=TextFormatter.formatWeb(InventoryMessages.getMessage("item.Description")) %>:</td>
			<td  colspan="4">
				<input type='text' size="100%" name='description' id='description' value='<%=dto.getDescription() != null ? TextFormatter.formatWeb(dto.getDescription()) : ""%>'/>
			</td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=TextFormatter.formatWeb(InventoryMessages.getMessage("item.Price")) %>:</td>
			<td>
				<input type='text' onblur="javascript:this.value=formatNumber(Number(this.value));" class="numeric labelaligment" name='price' id='price' value='<%=dto.getPrice() != null ? TextFormatter.getCurrencyValue(dto.getPrice()) : "0.00"%>'/>
			</td>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=TextFormatter.formatWeb(InventoryMessages.getMessage("item.Cost")) %>:</td>
			<td><input type='text' onblur="javascript:this.value=formatNumber(Number(this.value));" class="numeric labelaligment" name='cost' id='cost' value='<%=dto.getCost() != null ? TextFormatter.getCurrencyValue(dto.getCost()) : "0.00"%>'/></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=InventoryMessages.getMessage("item.Provider") %>:</td>
			<td colspan="4">
				<select name="idProvider" id="idProvider">
<%
	String[] order = {"businessName"};
	try{
		Vector<Provider> providers = new Vector<Provider>(((ProviderDao)ApplicationContextProvider.getCtx().getBean("providerDao")).find(new Provider(), order));
		if (providers != null && providers.size() > 0){
			for (Provider provider : providers){
%>
					<option value="<%=provider.getIdPerson()%>" <%=(dto.getProvider()!=null && dto.getProvider().getIdPerson()!=null && dto.getProvider().getIdPerson().intValue()==provider.getIdPerson().intValue()) ? "selected='selected'":"" %>><%= TextFormatter.formatWeb(provider.getBusinessName()) %></option>
<%			}
		}
	}catch(Exception ex){}
%>
				</select>
			</td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=InventoryMessages.getMessage("item.Trademark") %>:</td>
			<td colspan="4">
				<select name="idTrademark" id="idTrademark">
<%
	String[] order2 = {"name"};
	try{
		Vector<Trademark> tms = new Vector<Trademark>(((TrademarkDao)ApplicationContextProvider.getCtx().getBean("trademarkDaoHibernate")).find(new Trademark(), order2));
		if (tms != null && tms.size() > 0){
			for (Trademark tm : tms){
%>
					<option value="<%=tm.getIdTrademark()%>" <%=(dto.getTrademark()!=null && dto.getTrademark().getIdTrademark()!=null && dto.getTrademark().getIdTrademark().intValue()==tm.getIdTrademark().intValue()) ? "selected='selected'":"" %>><%= TextFormatter.formatWeb(tm.getName()) %></option>
<%			}
		}
	}catch(Exception ex){}
%>
				</select>
			</td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=InventoryMessages.getMessage("item.Taxes") %>:</td>
			<td colspan="4">
				<select name="idTax" id="idTax" multiple>
<%
	String[] order1 = {"name"};
	try{
		Vector<Tax> taxes = new Vector<Tax>(((TaxDao)ApplicationContextProvider.getCtx().getBean("taxDao")).find(new Tax(), order1));
		if (taxes != null && taxes.size() > 0){
			for (Tax tax : taxes){
%>
					<option value="<%=tax.getIdTax()%>"
<%
				if (dto.getTaxes() != null && dto.getTaxes().size() > 0){
					for (Tax dtoT : dto.getTaxes()){
%>
					 <%=(dtoT.getIdTax().intValue() == tax.getIdTax().intValue()) ? "selected='selected'":"" %>
<%
					}
				}
%>
					 ><%= TextFormatter.formatWeb(tax.getName()) %></option>
<%			}
		}
	}catch(Exception ex){}
%>
				</select>
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
				<th><%=TextFormatter.formatWeb(InventoryMessages.getMessage("item.Code")) %></th>
				<th><%=TextFormatter.formatWeb(InventoryMessages.getMessage("item.Name")) %></th>
				<th><%=TextFormatter.formatWeb(InventoryMessages.getMessage("item.Trademark")) %></th>
				<th><%=TextFormatter.formatWeb(InventoryMessages.getMessage("item.Price")) %></th>
			</tr>
<%		for (int a = 0;a < list.size();a++){ 
				Item item = (Item) list.get(a); %>
			<tr onclick="javascript:clearForm();document.item.idItem.value='<%=item.getIdItem()%>';document.item.action.value='search';sendData('item');">
				<td><%=TextFormatter.formatWeb(item.getCode())%></td>
				<td><%=TextFormatter.formatWeb(item.getName())%></td>
				<td><%=item.getTrademark() != null ? TextFormatter.formatWeb(item.getTrademark().getName()) : ""%></td>
				<td style="text-align: right"><%=TextFormatter.getCurrencyValue(item.getPrice())%></td>
			</tr>
<%		} %>
		</table>
<%	} %>
	</div>
</form>
<script>
	function clearForm(){
		document.item.idItem.value='';
		document.item.name.value='';
		document.item.code.value='';
		document.item.description.value='';
		document.item.price.value='';
		document.item.cost.value='';
		document.item.idProvider.selectedIndex=-1;
		document.item.idTrademark.selectedIndex=-1;
		document.item.idTax.selectedIndex=-1;
		document.getElementById("resultsTable").style.visibility = "hidden";
		document.getElementById("addButton").style.visibility = "hidden";
		document.getElementById("searchButton").style.visibility = "visible";
		activeAdd('code');
		hideUpdateDelete();
		document.item.code.focus();
	}
<% 	if (dto.getTaxes()==null || dto.getTaxes().size() <= 0){%>
	document.item.idTax.selectedIndex=-1;
<%	}%>
<% 	if (dto.getProvider()==null || dto.getProvider().getIdPerson() <= 0){%>
	document.item.idProvider.selectedIndex=-1;
<%	}%>
<% 	if (dto.getTrademark()==null || dto.getTrademark().getIdTrademark() <= 0){%>
	document.item.idTrademark.selectedIndex=-1;
<%	}%>
<%	if (error != null && !error.equalsIgnoreCase("")){ %>
	showError('<%=error%>');
<% 	} %>
<%	if (dto == null || dto.getIdItem() == null || dto.getIdItem().intValue() <= 0) {
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

$('.numeric').on('input', function (event) { 
    this.value = this.value.replace(/[^0-9]\./g, '');
});

</script>