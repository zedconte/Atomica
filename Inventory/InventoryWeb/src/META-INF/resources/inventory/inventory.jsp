<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.industrika.inventory.i18n.InventoryMessages" %>
<%@page import="com.industrika.inventory.dto.Inventory" %>
<%@page import="com.industrika.inventory.dto.Item" %>
<%@page import="com.industrika.commons.dto.Branch" %>
<%@page import="com.industrika.commons.dto.Warehouse" %>
<%@page import="com.industrika.inventory.dao.ItemDao" %>
<%@page import="com.industrika.commons.dao.BranchDao" %>
<%@page import="com.industrika.commons.dao.WarehouseDao" %>
<%@page import="com.industrika.commons.businesslogic.ApplicationContextProvider" %>
<%@page import="com.industrika.commons.utils.TextFormatter" %>
<%@page import="java.util.Vector" %>
<jsp:useBean id="dto" scope="request" class="com.industrika.inventory.dto.Inventory"/>
<jsp:useBean id="list" scope="request" class="java.util.Vector"/>
<jsp:useBean id="message" scope="request" class="java.lang.String"/>
<jsp:useBean id="error" scope="request" class="java.lang.String"/>
<form name="inventory" id="inventory" class="normaltext">
	<input type="hidden" name="action" id="action"/>
	<input type="hidden" name="formName" id="formName" value="inventory"/>
	<table class="commandbuttons">
		<tr>
			<td class="separator">&nbsp;</td>
			<td><img class="resetbutton" src="images/reset.png" onclick="javascript:clearForm()"></td>
			<td class="separator">&nbsp;</td>
			<td><div id="addButton"></div></td>
			<td class="separator">&nbsp;</td>
			<td><div id="updateButton"></div></td>
			<td class="separator">&nbsp;</td>
			<td><div id="deleteButton"></div></td>
			<td class="separator">&nbsp;</td>
			<td><img id="searchButton" class="searchbutton" src="images/search.png" onclick="javascript:document.inventory.action.value='search';sendData('inventory')"></td>
			<td class="separator">&nbsp;</td>
		</tr>
	</table>
	<br/>
	<table align="center">
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext"><%=TextFormatter.formatWeb(InventoryMessages.getMessage("inventory.Title")) %></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext">&nbsp;</td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=TextFormatter.formatWeb(InventoryMessages.getMessage("inventory.Branch")) %>:</td>
			<td  colspan="4">
				<select name="idBranch" id="idBranch" >
<%
	String[] orderBranches = {"name"};
	try{
		Vector<Branch> branches = new Vector<Branch>(((BranchDao)ApplicationContextProvider.getCtx().getBean("branchDaoHibernate")).find(new Branch(), orderBranches));
		if (branches != null && branches.size() > 0){
			for (Branch branch : branches){
%>
					<option value="<%=branch.getIdBranch()%>" <%=(dto.getBranch()!=null && dto.getBranch().getIdBranch()!=null && dto.getBranch().getIdBranch().intValue()==branch.getIdBranch().intValue()) ? "selected='selected'":"" %>><%= TextFormatter.formatWeb(branch.getName()) %></option>
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
			<td class="labelaligment"><%=TextFormatter.formatWeb(InventoryMessages.getMessage("inventory.Warehouse")) %>:</td>
			<td  colspan="4">
				<select name="idWarehouse" id="idWarehouse" >
<%
	String[] orderWarehouse = {"name"};
	try{
		Vector<Warehouse> warehouses = new Vector<Warehouse>(((WarehouseDao)ApplicationContextProvider.getCtx().getBean("warehouseDaoHibernate")).find(new Warehouse(), orderWarehouse));
		if (warehouses != null && warehouses.size() > 0){
			for (Warehouse warehouse : warehouses){
%>
					<option value="<%=warehouse.getIdWarehouse()%>" <%=(dto.getWarehouse()!=null && dto.getWarehouse().getIdWarehouse()!=null && dto.getWarehouse().getIdWarehouse().intValue()==warehouse.getIdWarehouse().intValue()) ? "selected='selected'":"" %>><%= TextFormatter.formatWeb(warehouse.getName()) %></option>
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
			<td class="labelaligment"><%=TextFormatter.formatWeb(InventoryMessages.getMessage("inventory.Item")) %>:</td>
			<td  colspan="4">
				<select name="idItem" id="idItem">
<%
	String[] orderItems = {"name"};
	try{
		Vector<Item> items = new Vector<Item>(((ItemDao)ApplicationContextProvider.getCtx().getBean("itemDao")).find(new Item(), orderItems));
		if (items != null && items.size() > 0){
			for (Item item : items){
%>
					<option value="<%=item.getIdItem()%>" <%=(dto.getItem()!=null && dto.getItem().getIdItem()!=null && dto.getItem().getIdItem().intValue()==item.getIdItem().intValue()) ? "selected='selected'":"" %>><%= TextFormatter.formatWeb(item.getName()) %></option>
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
			<td class="labelaligment"><%=TextFormatter.formatWeb(InventoryMessages.getMessage("inventory.Quantity")) %>:</td>
			<td><input type='text' onkeyup="javascript:activeAdd('quantity')" class="labelaligment numeric" name='quantity' id='quantity' onblur="javascript:this.value=formatNumberWithoutSign(Number(this.value));" value='<%=dto.getQuantity() != null ? TextFormatter.getNumberValue(dto.getQuantity()) : "0.00"%>'/></td>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"></td>
			<td></td>
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
				<th><%=TextFormatter.formatWeb(InventoryMessages.getMessage("inventory.Branch")) %></th>
				<th><%=TextFormatter.formatWeb(InventoryMessages.getMessage("inventory.Warehouse")) %></th>
				<th><%=TextFormatter.formatWeb(InventoryMessages.getMessage("inventory.Item")) %></th>
				<th><%=TextFormatter.formatWeb(InventoryMessages.getMessage("inventory.Quantity")) %></th>
			</tr>
<%		for (int a = 0;a < list.size();a++){ 
				Inventory inventory = (Inventory) list.get(a); %>
			<tr >
				<td><%=inventory.getBranch() != null ? TextFormatter.formatWeb(inventory.getBranch().getName()) : ""%></td>
				<td><%=inventory.getWarehouse() != null ? TextFormatter.formatWeb(inventory.getWarehouse().getName()) : ""%></td>
				<td><%=inventory.getItem() != null ? TextFormatter.formatWeb(inventory.getItem().getName()) : ""%></td>
				<td style="text-align: right"><%=inventory.getQuantity() !=null ? TextFormatter.getNumberValue(inventory.getQuantity()) : "0.00"%></td>
			</tr>
<%		} %>
		</table>
<%	} %>
	</div>
</form>
<script>
	function clearForm(){
		document.inventory.quantity.value='';
		document.inventory.idItem.selectedIndex=-1;
		document.inventory.idBranch.selectedIndex=-1;
		document.inventory.idWarehouse.selectedIndex=-1;
		document.getElementById("resultsTable").style.visibility = "hidden";
		document.getElementById("addButton").style.visibility = "hidden";
		document.getElementById("searchButton").style.visibility = "visible";
	}
<% 	if (dto.getItem()==null || dto.getItem().getIdItem() <= 0){%>
	document.inventory.idItem.selectedIndex=-1;
<%	}%>
<% 	if (dto.getBranch()==null || dto.getBranch().getIdBranch() <= 0){%>
	document.inventory.idBranch.selectedIndex=-1;
<%	}%>
<% 	if (dto.getWarehouse()==null || dto.getWarehouse().getIdWarehouse() <= 0){%>
	document.inventory.idWarehouse.selectedIndex=-1;
<%	}%>
<%	if (error != null && !error.equalsIgnoreCase("")){ %>
	showError('<%=error%>');
<% 	} %>

</script>