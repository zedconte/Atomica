<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.industrika.business.i18n.BusinessMessages" %>
<%@page import="com.industrika.commons.utils.TextFormatter" %>
<%@page import="java.util.Vector" %>
<%@page import="com.industrika.business.dto.SaleReport" %>
<%@page import="com.industrika.business.dto.Report" %>
<%@page import="com.industrika.business.dto.Report.ReportType" %>
<%@page import="com.industrika.inventory.i18n.InventoryMessages" %>
<%@page import="com.industrika.inventory.dto.DocumentRow" %>
<%@page import="com.industrika.sales.dto.Sale" %>
<jsp:useBean id="list" scope="request" class="java.util.Vector"/>
<jsp:useBean id="dto" scope="request" class="com.industrika.business.dto.SaleReport"/>
<jsp:useBean id="message" scope="request" class="java.lang.String"/>
<jsp:useBean id="error" scope="request" class="java.lang.String"/>
<jsp:useBean id="action" scope="request" class="java.lang.String"/>
<script type="text/javascript">
$(function(){
	
	var items=$("form[name=salesreport] #itemsJson").val();
	var branches=$("form[name=salesreport] #branchJson").val();
	var warehouses=$("form[name=salesreport] #warehouseJson").val();
	
	//idItem
	//idItemName
	if (items!="") {
		var selectedItem = <%=(dto != null && dto.getItem() != null && dto.getItem().getIdItem() != null && dto.getItem().getIdItem().intValue() >0) ? dto.getItem().getIdItem().intValue() : -1 %>;
		var JsonActs=jQuery.parseJSON(items);
		$(JsonActs).each(function() {  
		    var ID = this.idItem;
		    var NAME = this.idItemName;
		    if (ID === selectedItem){
		    	$('form[name=salesreport] #item').append(new Option(NAME,ID,false,true));
		    } else {
		    	$('form[name=salesreport] #item').append(new Option(NAME,ID));
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
		    	$('form[name=salesreport] #branch').append(new Option(NAME,ID,false,true));
		    } else {
		    	$('form[name=salesreport] #branch').append(new Option(NAME,ID));
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
		    	$('form[name=salesreport] #warehouse').append(new Option(NAME,ID,false,true));
		    } else {
		    	$('form[name=salesreport] #warehouse').append(new Option(NAME,ID));
		    }
		});
	}

});

</script>
<form name="salesreport" id="salesreport" class="normaltext">
	<input type="hidden" name="action" id="action"/>
	<input type="hidden" name="formName" id="formName" value="salesreport"/>
	<br>
	<table class="commandbuttons">
		<tr>
			<td class="separator">&nbsp;</td>
			<td><img class="resetbutton" src="images/reset.png" onclick="javascript:clearForm()"></td>
			<td class="separator">&nbsp;</td>
			<td><img class="searchbutton" src="images/search.png" onclick="javascript:document.salesreport.action.value='search';sendData('salesreport')"></td>
			<td class="separator">&nbsp;</td>
		</tr>
	</table>
	<br>
	<table align="center">
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext"><%=TextFormatter.formatWeb(BusinessMessages.getMessage("saleReport.Title")) %></td>
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
						<th><%=TextFormatter.formatWeb(InventoryMessages.getMessage("item.Name"))%></th>
						<th><%=TextFormatter.formatWeb(InventoryMessages.getMessage("buyorder.id"))%></th>
						<th><%=TextFormatter.formatWeb(InventoryMessages.getMessage("buyorder.Folio"))%></th>
						<th><%=TextFormatter.formatWeb(InventoryMessages.getMessage("buyorder.Date"))%></th>
						<th><%=TextFormatter.formatWeb(InventoryMessages.getMessage("buyorder.Total"))%></th>
					</tr>
					<%
						for (int a = 0; a < list.size(); a++) {
							SaleReport saleReportRow = (SaleReport) list.get(a);
							Sale mySale = saleReportRow.getMySale();
					%>
					<tr>
						<td><%=mySale.getCustomer() != null ? TextFormatter.formatWeb(mySale.getCustomer().getBusinessName()) : ""%></td>
						<td><%=mySale.getIdDocument() != null ? mySale.getIdDocument() : ""%></td>
						<td><%=mySale.getFolio() != null ? mySale.getFolio() : ""%></td>
						<td><%=mySale.getDate() != null ? TextFormatter.getFormattedDate(mySale.getDate(), null) : ""%></td>
						<td><%=mySale.getTotal() != null ? TextFormatter.getCurrencyValue(mySale.getTotal()) : ""%></td>
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
						<th><%=TextFormatter.formatWeb(InventoryMessages.getMessage("item.Name"))%></th>
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
							SaleReport saleReportRow = (SaleReport) list.get(a);
							Sale mySale = saleReportRow.getMySale();
							Double total=0.0;
					%>
					<tr>
						<td><%=mySale.getCustomer() != null ? TextFormatter.formatWeb(mySale.getCustomer().getBusinessName()) : ""%></td>
						<td><%=mySale.getIdDocument() != null ? mySale.getIdDocument() : ""%></td>
						
						<%if(dto.getBranch()!=null && saleReportRow.isHasBranchVals()) {
								total=total+saleReportRow.getBranchTotal();
						%>
							<td><%=dto.getBranch() != null ? TextFormatter.formatWeb(dto.getBranch().getName()) : ""%></td>
							<td><%=saleReportRow.getBranchTotal() != null ? TextFormatter.getCurrencyValue(saleReportRow.getBranchTotal()) : ""%></td>
						<%
							}
							else if(dto.getBranch()!=null){%>
								<td>-</td>
								<td>-</td>
							<%}
						%>
						<%if(dto.getWarehouse()!=null && saleReportRow.isHasWarehouseVals() ) {
							total=total+saleReportRow.getWarehouseTotal();
						%>
							<td><%=dto.getWarehouse() != null ? TextFormatter.formatWeb(dto.getWarehouse().getName()) : ""%></td>
							<td><%=saleReportRow.getWarehouseTotal() != null ? TextFormatter.getCurrencyValue(saleReportRow.getWarehouseTotal()) : ""%></td>
						<%
							}
							else if(dto.getWarehouse()!=null){%>
								<td>-</td>
								<td>-</td>
							<%}
						%>
						
						
						<%if(dto.getItem()!=null && saleReportRow.isHasItemVals()) {
							total=total+saleReportRow.getItemTotal();
						%>
							<td><%=dto.getItem() != null ? TextFormatter.formatWeb(dto.getItem().getName()) : ""%></td>
							<td><%=saleReportRow.getItemTotal() != null ? TextFormatter.getCurrencyValue(saleReportRow.getItemTotal()) : ""%></td>
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
		document.salesreport.warehouse.selectedIndex = -1;
		document.salesreport.item.selectedIndex = -1;
		document.salesreport.branch.selectedIndex = -1;
		document.getElementById("resultsTable").style.visibility = "hidden";
	}

</script>
