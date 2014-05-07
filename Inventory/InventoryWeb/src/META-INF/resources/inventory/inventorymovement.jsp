<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.industrika.inventory.i18n.InventoryMessages" %>
<%@page import="com.industrika.inventory.dto.InventoryMovement" %>
<%@page import="com.industrika.inventory.dto.Item" %>
<%@page import="com.industrika.commons.dto.Branch" %>
<%@page import="com.industrika.commons.dto.Warehouse" %>
<%@page import="com.industrika.commons.dto.MovementConcept" %>
<%@page import="com.industrika.inventory.dao.ItemDao" %>
<%@page import="com.industrika.commons.dao.BranchDao" %>
<%@page import="com.industrika.commons.dao.WarehouseDao" %>
<%@page import="com.industrika.commons.dao.MovementConteptDao" %>
<%@page import="com.industrika.commons.businesslogic.ApplicationContextProvider" %>
<%@page import="com.industrika.commons.utils.TextFormatter" %>
<%@page import="java.util.Vector" %>
<%@page import="java.text.SimpleDateFormat" %>
<jsp:useBean id="dto" scope="request" class="com.industrika.inventory.dto.InventoryMovement"/>
<jsp:useBean id="list" scope="request" class="java.util.Vector"/>
<jsp:useBean id="message" scope="request" class="java.lang.String"/>
<jsp:useBean id="error" scope="request" class="java.lang.String"/>
<%
	SimpleDateFormat sdf=new SimpleDateFormat("mm/dd/yy");
%>
<form name="inventorymovement" id="inventorymovement" class="normaltext">
	<input type="hidden" name="action" id="action"/>
	<input type="hidden" name="formName" id="formName" value="inventorymovement"/>
	<table class="commandbuttons">
		<tr>
			<td class="separator">&nbsp;</td>
			<td><img class="resetbutton" src="images/reset.png" onclick="javascript:clearForm()"></td>
			<td class="separator">&nbsp;</td>
			<td><div id="addButton"><img class="addbutton" src="images/new.png" onclick="javascript:document.inventorymovement.action.value='add';sendDataConfirm('inventorymovement','Alta para el movimiento al inventario')"></div></td>
			<td class="separator">&nbsp;</td>
			<td><div id="updateButton"></div></td>
			<td class="separator">&nbsp;</td>
			<td><div id="deleteButton"></div></td>
			<td class="separator">&nbsp;</td>
			<td><img id="searchButton" class="searchbutton" src="images/search.png" onclick="javascript:document.inventorymovement.action.value='search';sendData('inventorymovement')"></td>
			<td class="separator">&nbsp;</td>
		</tr>
	</table>
	<br/>
	<table align="center">
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext"><%=TextFormatter.formatWeb(InventoryMessages.getMessage("inventorymovement.Title")) %></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext">&nbsp;</td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=TextFormatter.formatWeb(InventoryMessages.getMessage("inventorymovement.Date")) %>:</td>
			<td>
				<input type='text' name='date' id='date' value='<%=dto.getDate() != null ? sdf.format(dto.getDate().getTime()) : ""%>'/>
				<input type='hidden' name='idMovement' id='idMovement' value='<%= dto.getIdMovement() != null ? ""+dto.getIdMovement().intValue() : "" %>'/>
			</td>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=TextFormatter.formatWeb(InventoryMessages.getMessage("inventorymovement.Quantity")) %>:</td>
			<td><input type='text' onkeyup="javascript:activeAdd('quantity')" class="labelaligment numeric" name='quantity' id='quantity' onblur="javascript:this.value=formatNumberWithoutSign(Number(this.value));" value='<%=dto.getQuantity() != null ? TextFormatter.getNumberValue(dto.getQuantity()) : "0.00"%>'/></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=TextFormatter.formatWeb(InventoryMessages.getMessage("inventorymovement.Branch")) %>:</td>
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
			<td class="labelaligment"><%=TextFormatter.formatWeb(InventoryMessages.getMessage("inventorymovement.Warehouse")) %>:</td>
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
			<td class="labelaligment"><%=TextFormatter.formatWeb(InventoryMessages.getMessage("inventorymovement.Item")) %>:</td>
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
			<td class="labelaligment"><%=TextFormatter.formatWeb(InventoryMessages.getMessage("inventorymovement.Concept")) %>:</td>
			<td>
				<select name="idConcept" id="idConcept">
<%
	String[] orderConcepts = {"name"};
	try{
		Vector<MovementConcept> concepts = new Vector<MovementConcept>(((MovementConteptDao)ApplicationContextProvider.getCtx().getBean("movementConceptDaoHibernate")).find(new MovementConcept(), orderConcepts));
		if (concepts != null && concepts.size() > 0){
			for (MovementConcept concept : concepts){
%>
					<option value="<%=concept.getIdMovementConcept()%>" <%=(dto.getConcept()!=null && dto.getConcept().getIdMovementConcept()!=null && dto.getConcept().getIdMovementConcept().intValue()==concept.getIdMovementConcept().intValue()) ? "selected='selected'":"" %>><%= TextFormatter.formatWeb(concept.getName()) %></option>
<%			}
		}
	}catch(Exception ex){}
%>
				</select>
			</td>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=TextFormatter.formatWeb(InventoryMessages.getMessage("inventorymovement.Type")) %>:</td>
			<td>
				<select name="type" id="type">
					<option value="0" <%=(dto.getType() != null && dto.getType().intValue() == 0) ? "selected='selected'":"" %>>Entrada</option>
					<option value="1" <%=(dto.getType() != null && dto.getType().intValue() == 1) ? "selected='selected'":"" %>>Salida</option>
				</select>
			</td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=TextFormatter.formatWeb(InventoryMessages.getMessage("inventorymovement.Serial")) %>:</td>
			<td>
				<input type='text' name='serial' id='serial' value='<%=dto.getSerial() != null ? TextFormatter.formatWeb(dto.getSerial()) : ""%>'/>
			</td>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=TextFormatter.formatWeb(InventoryMessages.getMessage("inventorymovement.Reference")) %>:</td>
			<td><input type='text' name='reference' id='reference' value='<%=dto.getReference() != null ? TextFormatter.formatWeb(dto.getReference()) : ""%>'/></td>
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
				<th><%=TextFormatter.formatWeb(InventoryMessages.getMessage("inventorymovement.Date")) %></th>
				<th><%=TextFormatter.formatWeb(InventoryMessages.getMessage("inventorymovement.Branch")) %></th>
				<th><%=TextFormatter.formatWeb(InventoryMessages.getMessage("inventorymovement.Warehouse")) %></th>
				<th><%=TextFormatter.formatWeb(InventoryMessages.getMessage("inventorymovement.Item")) %></th>
				<th><%=TextFormatter.formatWeb(InventoryMessages.getMessage("inventorymovement.Quantity")) %></th>
				<th><%=TextFormatter.formatWeb(InventoryMessages.getMessage("inventorymovement.Type")) %></th>
				<th><%=TextFormatter.formatWeb(InventoryMessages.getMessage("inventorymovement.Concept")) %></th>
			</tr>
<%		for (int a = 0;a < list.size();a++){ 
				InventoryMovement inventorymovement = (InventoryMovement) list.get(a); %>
			<tr onclick="javascript:clearForm();document.inventorymovement.idMovement.value='<%=inventorymovement.getIdMovement()%>';document.inventorymovement.action.value='search';sendData('inventorymovement');">
				<td><%=inventorymovement.getDate() != null ? sdf.format(inventorymovement.getDate().getTime()) : ""%></td>
				<td><%=inventorymovement.getBranch() != null ? TextFormatter.formatWeb(inventorymovement.getBranch().getName()) : ""%></td>
				<td><%=inventorymovement.getWarehouse() != null ? TextFormatter.formatWeb(inventorymovement.getWarehouse().getName()) : ""%></td>
				<td><%=inventorymovement.getItem() != null ? TextFormatter.formatWeb(inventorymovement.getItem().getName()) : ""%></td>
				<td style="text-align: right"><%=inventorymovement.getQuantity() !=null ? TextFormatter.getNumberValue(inventorymovement.getQuantity()) : "0.00"%></td>
				<td><%=inventorymovement.getType() !=null ? (inventorymovement.getType().intValue() == 1 ? "Salida" : "Entrada") : ""%></td>
				<td><%=inventorymovement.getConcept() != null ? TextFormatter.formatWeb(inventorymovement.getConcept().getName()) : ""%></td>
			</tr>
<%		} %>
		</table>
<%	} %>
	</div>
</form>
<script>
	function clearForm(){
		document.inventorymovement.idMovement.value='';
		document.inventorymovement.quantity.value='';
		document.inventorymovement.reference.value='';
		document.inventorymovement.serial.value='';
		document.inventorymovement.date.value='';
		document.inventorymovement.idItem.selectedIndex=-1;
		document.inventorymovement.idBranch.selectedIndex=-1;
		document.inventorymovement.idWarehouse.selectedIndex=-1;
		document.inventorymovement.idConcept.selectedIndex=-1;
		document.inventorymovement.type.selectedIndex=-1;
		document.getElementById("resultsTable").style.visibility = "hidden";
		document.getElementById("addButton").style.visibility = "hidden";
		document.getElementById("searchButton").style.visibility = "visible";
	}
<% 	if (dto.getItem()==null || dto.getItem().getIdItem() <= 0){%>
	document.inventorymovement.idItem.selectedIndex=-1;
<%	}%>
<% 	if (dto.getBranch()==null || dto.getBranch().getIdBranch() <= 0){%>
	document.inventorymovement.idBranch.selectedIndex=-1;
<%	}%>
<% 	if (dto.getWarehouse()==null || dto.getWarehouse().getIdWarehouse() <= 0){%>
	document.inventorymovement.idWarehouse.selectedIndex=-1;
<%	}%>
<% 	if (dto.getConcept()==null || dto.getConcept().getIdMovementConcept() <= 0){%>
	document.inventorymovement.idConcept.selectedIndex=-1;
<%	}%>
<% 	if (dto.getType()==null){%>
	document.inventorymovement.type.selectedIndex=-1;
<%	}%>
<%	if (error != null && !error.equalsIgnoreCase("")){ %>
	showError('<%=error%>');
<% 	} %>
<%	if (dto == null || dto.getIdMovement() == null || dto.getIdMovement().intValue() <= 0) {
		if (list == null || list.size() <= 0){ 
			if (error == null || error.equalsIgnoreCase("")){
%>
	clearForm();
<%			}
		} else { %>
		activeAdd("quantity");
<%		} %>
		
	hideUpdateDelete();
<% 	} else { %>
	document.getElementById("addButton").style.visibility = "hidden";
	document.getElementById("searchButton").style.visibility = "hidden";
<%	} %>

$(function() {
    $("#date").datepicker();
});

</script>