<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.industrika.business.i18n.BusinessMessages" %>
<%@page import="com.industrika.commons.utils.TextFormatter" %>
<%@page import="java.util.Vector" %>
<%@page import="com.industrika.business.dto.PurchaseReport" %>
<%@page import="com.industrika.business.dto.Report" %>
<%@page import="com.industrika.business.dto.Report.ReportType" %>
<%@page import="com.industrika.inventory.i18n.InventoryMessages" %>
<%@page import="com.industrika.inventory.dto.DocumentRow" %>
<%@page import="com.industrika.inventory.dto.Purchase" %>
<jsp:useBean id="list" scope="request" class="java.util.Vector"/>
<jsp:useBean id="dto" scope="request" class="com.industrika.business.dto.PurchaseReport"/>
<jsp:useBean id="message" scope="request" class="java.lang.String"/>
<jsp:useBean id="error" scope="request" class="java.lang.String"/>
<jsp:useBean id="action" scope="request" class="java.lang.String"/>
<script type="text/javascript">
$(function(){
	
	var items=$("form[name=purchasereport] #itemsJson").val();
	var branches=$("form[name=purchasereport] #branchJson").val();
	var warehouses=$("form[name=purchasereport] #warehouseJson").val();
	
	//idItem
	//idItemName
	if (items!="") {
		var selectedItem = <%=(dto != null && dto.getItem() != null && dto.getItem().getIdItem() != null && dto.getItem().getIdItem().intValue() >0) ? dto.getItem().getIdItem().intValue() : -1 %>;
		var JsonActs=jQuery.parseJSON(items);
		$(JsonActs).each(function() {  
		    var ID = this.idItem;
		    var NAME = this.idItemName;
		    if (ID === selectedItem){
		    	$('form[name=purchasereport] #item').append(new Option(NAME,ID,false,true));
		    } else {
		    	$('form[name=purchasereport] #item').append(new Option(NAME,ID));
		    }
		});
	}
	
	//idIBranch
	//branchName
	if (branches!="") {
		var selectedItem = <%=(dto != null && dto.getBranch() != null && dto.getBranch().getIdBranch() != null && dto.getBranch().getIdBranch().intValue() >0) ? dto.getBranch().getIdBranch().intValue() : -1 %>;
		var JsonActs=jQuery.parseJSON(branches);
		$(JsonActs).each(function() {  
		    var ID = this.idIBranch;
		    var NAME = this.branchName;
		    if (ID === selectedItem){
		    	$('form[name=purchasereport] #branch').append(new Option(NAME,ID,false,true));
		    } else {
		    	$('form[name=purchasereport] #branch').append(new Option(NAME,ID));
		    }
		});
	}
	
	//idWarehouse
	//warehouseName
	if (warehouses!="") {
		var selectedItem = <%=(dto != null && dto.getWarehouse() != null && dto.getWarehouse().getIdWarehouse() != null && dto.getWarehouse().getIdWarehouse().intValue() >0) ? dto.getWarehouse().getIdWarehouse().intValue() : -1 %>;
		var JsonActs=jQuery.parseJSON(warehouses);
		$(JsonActs).each(function() {  
		    var ID = this.idWarehouse;
		    var NAME = this.warehouseName;
		    if (ID === selectedItem){
		    	$('form[name=purchasereport] #warehouse').append(new Option(NAME,ID,false,true));
		    } else {
		    	$('form[name=purchasereport] #warehouse').append(new Option(NAME,ID));
		    }
		});
	}
});

</script>
<form name="purchasereport" id="purchasereport" class="normaltext">
	<input type="hidden" name="action" id="action"/>
	<input type="hidden" name="formName" id="formName" value="purchasereport"/>
	<br>
	<table class="commandbuttons">
		<tr>
			<td class="separator">&nbsp;</td>
			<td><img class="resetbutton" src="images/reset.png" onclick="javascript:clearForm()"></td>
			<td class="separator">&nbsp;</td>
			<td><img class="searchbutton" src="images/search.png" onclick="javascript:document.purchasereport.action.value='search';sendData('purchasereport')"></td>
			<td class="separator">&nbsp;</td>
		</tr>
	</table>
	<br>
	<table align="center">
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext"><%=TextFormatter.formatWeb(BusinessMessages.getMessage("purchaseReport.Title")) %></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td><input type='text' name="itemsJson"  id="itemsJson" style="display:none;position: absolute;" value='<%= dto.getItemsJson() != null ? TextFormatter.formatWeb(dto.getItemsJson()) : "" %>'/></td>
			<td><input type='text' name="branchJson"  id="branchJson" style="display:none;position: absolute;" value='<%= dto.getBranchJson() != null ? TextFormatter.formatWeb(dto.getBranchJson()) : "" %>'/></td>
			<td><input type='text' name="warehouseJson"  id="warehouseJson" style="display:none;position: absolute;" value='<%= dto.getWarehouseJson() != null ? TextFormatter.formatWeb(dto.getWarehouseJson()) : "" %>'/></td>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext">&nbsp;</td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=TextFormatter.formatWeb(BusinessMessages.getMessage("saleReport.Branches")) %>:</td>
			<td><select id="branch" name="branch" style="width: 150px;"></select></td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext">&nbsp;</td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=TextFormatter.formatWeb(BusinessMessages.getMessage("saleReport.Warehouses")) %>:</td>
			<td><select id="warehouse" name="warehouse" style="width: 150px;"></select></td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext">&nbsp;</td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=TextFormatter.formatWeb(BusinessMessages.getMessage("saleReport.Items")) %>:</td>
			<td><select id="item" name="item" style="width: 150px;"></select></td>
		</tr>
	</table>
	<br>
	<br>
	<div id="resultsTable">
		<%
		if(dto.getType().equals(ReportType.GENERIC)){
				if (list != null && list.size() > 0) {
				%>
				<table class="resultsTable">
					<tr>
						<th><%=TextFormatter.formatWeb(BusinessMessages.getMessage("purchaseReport.Provider"))%></th>
						<th><%=TextFormatter.formatWeb(InventoryMessages.getMessage("buyorder.id"))%></th>
						<th><%=TextFormatter.formatWeb(InventoryMessages.getMessage("buyorder.Folio"))%></th>
						<th><%=TextFormatter.formatWeb(InventoryMessages.getMessage("buyorder.Date"))%></th>
						<th><%=TextFormatter.formatWeb(InventoryMessages.getMessage("buyorder.Total"))%></th>
					</tr>
					<%
						for (int a = 0; a < list.size(); a++) {
							PurchaseReport purchaseReportRow = (PurchaseReport) list.get(a);
							Purchase myPurchase = purchaseReportRow.getMyPurchase();
					%>
					<tr>
						<td><%=myPurchase.getProvider() != null ? TextFormatter.formatWeb(myPurchase.getProvider().getBusinessName()) : ""%></td>
						<td><%=myPurchase.getIdDocument() != null ? myPurchase.getIdDocument() : ""%></td>
						<td><%=myPurchase.getFolio() != null ? myPurchase.getFolio() : ""%></td>
						<td><%=myPurchase.getDate() != null ? TextFormatter.getFormattedDate(myPurchase.getDate(), null) : ""%></td>
						<td><%=myPurchase.getTotal() != null ? TextFormatter.getCurrencyValue(myPurchase.getTotal()) : ""%></td>
					</tr>
					<%
						}
					%>
				</table>
			<%} 
		}%>
		<%
		if(dto.getType().equals(ReportType.BYFILTER)){
				if (list != null && list.size() > 0) {
				%>
				<table class="resultsTable">
					<tr>
						<th><%=TextFormatter.formatWeb(BusinessMessages.getMessage("purchaseReport.Provider"))%></th>
						<th><%=TextFormatter.formatWeb(InventoryMessages.getMessage("buyorder.Folio"))%></th>
						<%if(dto.getBranch()!=null) {%>
							<th><%=TextFormatter.formatWeb(InventoryMessages.getMessage("inventorymovement.Branch"))%></th>
							<th><%=TextFormatter.formatWeb(BusinessMessages.getMessage("saleReport.totalBranch"))%></th>
						<%
							}
						%>
						<%if(dto.getWarehouse()!=null) {%>
							<th><%=TextFormatter.formatWeb(InventoryMessages.getMessage("inventorymovement.Warehouse"))%></th>
							<th><%=TextFormatter.formatWeb(BusinessMessages.getMessage("saleReport.totalWarehouse"))%></th>
						<%
							}
						%>
						<%if(dto.getItem()!=null) {%>
							<th><%=TextFormatter.formatWeb(InventoryMessages.getMessage("inventorymovement.Item"))%></th>
							<th><%=TextFormatter.formatWeb(BusinessMessages.getMessage("saleReport.totalItem"))%></th>
						<%
							}
						%>
						<th><%=TextFormatter.formatWeb(BusinessMessages.getMessage("saleReport.totalFilter"))%></th>
					</tr>
					<%
						for (int a = 0; a < list.size(); a++) {
							PurchaseReport purchaseReportRow = (PurchaseReport) list.get(a);
							Purchase purchase = purchaseReportRow.getMyPurchase();
							Double total=0.0;
					%>
					<tr>
						<td><%=purchase.getProvider() != null ? TextFormatter.formatWeb(purchase.getProvider().getBusinessName()) : ""%></td>
						<td><%=purchase.getIdDocument() != null ? purchase.getIdDocument() : ""%></td>
						
						<%if(dto.getBranch()!=null && purchaseReportRow.isHasBranchVals()) {
								total=total+purchaseReportRow.getBranchTotal();
						%>
							<td><%=dto.getBranch() != null ? TextFormatter.formatWeb(dto.getBranch().getName()) : ""%></td>
							<td><%=purchaseReportRow.getBranchTotal() != null ? TextFormatter.getCurrencyValue(purchaseReportRow.getBranchTotal()) : ""%></td>
						<%
							}
							else if(dto.getBranch()!=null){%>
								<td>-</td>
								<td>-</td>
							<%}
						%>
						<%if(dto.getWarehouse()!=null && purchaseReportRow.isHasWarehouseVals() ) {
							total=total+purchaseReportRow.getWarehouseTotal();
						%>
							<td><%=dto.getWarehouse() != null ? TextFormatter.formatWeb(dto.getWarehouse().getName()) : ""%></td>
							<td><%=purchaseReportRow.getWarehouseTotal() != null ? TextFormatter.getCurrencyValue(purchaseReportRow.getWarehouseTotal()) : ""%></td>
						<%
							}
							else if(dto.getWarehouse()!=null){%>
								<td>-</td>
								<td>-</td>
							<%}
						%>
						
						
						<%if(dto.getItem()!=null && purchaseReportRow.isHasItemVals()) {
							total=total+purchaseReportRow.getItemTotal();
						%>
							<td><%=dto.getItem() != null ? TextFormatter.formatWeb(dto.getItem().getName()) : ""%></td>
							<td><%=purchaseReportRow.getItemTotal() != null ? TextFormatter.getCurrencyValue(purchaseReportRow.getItemTotal()) : ""%></td>
						<%
							}
							else if(dto.getItem()!=null){%>
								<td>-</td>
								<td>-</td>
							<%}
						%>
						<td><%=total != null ? TextFormatter.getCurrencyValue(total) : ""%></td>
					</tr>
					<%
						}
					%>
				</table>
			<%} 
		}%>
	</div>
	
	<br/>
	<br/>
	<%	if (message != null && !message.equalsIgnoreCase("")){ %>
		<table align="center">
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="messagetext"><%=message %></td>
			<td class="separator">&nbsp;</td>
		</tr>
		</table>
<%	} %>
	<br/>
</form>
<script>

	<%	if (error != null && !error.equalsIgnoreCase("")){ %>
	showError('<%=error%>');
	<% 	} %>
	
	function clearForm() {
		document.purchasereport.warehouse.selectedIndex = -1;
		document.purchasereport.item.selectedIndex = -1;
		document.purchasereport.branch.selectedIndex = -1;
		document.getElementById("resultsTable").style.visibility = "hidden";
	}

</script>
