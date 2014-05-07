<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.industrika.shipping.i18n.ShippingMessages" %>
<%@page import="com.industrika.commons.utils.TextFormatter" %>
<%@page import="java.util.Vector" %>
<%@page import="com.industrika.shipping.dto.ShippingSchedule" %>
<jsp:useBean id="list" scope="request" class="java.util.Vector"/>
<jsp:useBean id="dto" scope="request" class="com.industrika.shipping.dto.ShippingSchedule"/>
<jsp:useBean id="message" scope="request" class="java.lang.String"/>
<jsp:useBean id="error" scope="request" class="java.lang.String"/>
<jsp:useBean id="action" scope="request" class="java.lang.String"/>
<script type="text/javascript">
$(function(){
	var items=$("form[name=shippingshedule] #itemsJson").val();
	var branches=$("form[name=shippingshedule] #branchJson").val();
	var warehouses=$("form[name=shippingshedule] #warehouseJson").val();
	var maxItems=$("form[name=shippingshedule] #productQuantityJson").val();
	var jsonRows= $("form[name=shippingshedule] #rowsAsJson").val();
	var routes=$("form[name=shippingshedule] #routesAccount").val();
	if (routes!="") {
		var selectedRoute = <%=(dto.getRoute() != null && dto.getRoute().getIdRoute() != null && dto.getRoute() != null && dto.getRoute().getIdRoute().intValue() >0) ? dto.getRoute().getIdRoute().intValue() : -1 %>;
		var JsonActs=jQuery.parseJSON(routes);
		$(JsonActs).each(function() {  
		    var ID = this.idRoute;
		    var NAME = this.routeName;
		    if (ID === selectedRoute){
		    	$('form[name=shippingshedule] #route').append(new Option(NAME,ID,false,true));
		    } else {
		    	$('form[name=shippingshedule] #route').append(new Option(NAME,ID));
		    }
		});
	}
	
	$(function () {
	        if(jsonRows==""){
	        	var initValues="";
	        }
	        else{
	         	var JsonRowObj=jQuery.parseJSON(jsonRows);
	         	var initValues=JsonRowObj;
	        }
	        
	     $("form[name=shippingshedule] #shippingscheduleTable").appendGrid('init', {
	        caption: 'Productos a enviar',
	        initRows: 1,					
	        columns: [
	                { name: 'itemName', display: 'Producto : Precio', type: 'select', ctrlOptions: items } ,
	                { name: 'itemQuantity', display: 'Cantidad de producto', type: 'select', ctrlOptions: maxItems } ,
	                { name: 'itemBranch', display: 'Sucursal', type: 'select', ctrlOptions: branches } ,
	                { name: 'itemWarehouse', display: 'Almacen', type: 'select', ctrlOptions: warehouses }
	            ],
	        initData: initValues
	    });

	    // Initialize validation plugin
	    $(document.policy).validate({
	        errorLabelContainer: 'form[name=shippingshedule] #ulError',
	        wrapper: 'li'
    	});

	});

});

function addSchedule(formId,message){
	var data =$("form[name=shippingshedule] #shippingscheduleTable").appendGrid('getAllValue');
		var myJsonString = JSON.stringify(data);
		$("form[name=shippingshedule] #rowsAsJson").val(myJsonString);
 		sendDataConfirm(formId,message);
}

</script>
<form name="shippingshedule" id="shippingshedule" class="normaltext">
	<input type="hidden" name="action" id="action"/>
	<input type="hidden" name="formName" id="formName" value="shippingshedule"/>
	<br>
	<table class="commandbuttons">
		<tr>
			<td class="separator">&nbsp;</td>
			<td><img class="resetbutton" src="images/reset.png" onclick="javascript:clearForm()"></td>
			<td class="separator">&nbsp;</td>
			<td class="td_add"><div id="addButton"><img class="addbutton" src="images/new.png" onclick="javascript:document.shippingshedule.action.value='add';addSchedule('shippingshedule','alta del env&iacute;o')"></div></td>
			<td class="separator">&nbsp;</td>
			<td><div id="updateButton"><img class="savebutton" src="images/save.png" onclick="javascript:document.shippingshedule.action.value='update';sendDataConfirm('shippingshedule','actualización del env&iacute;o')"></div></td>
			<td class="separator">&nbsp;</td>
			<td><div id="deleteButton"><img class="deletebutton" src="images/delete.png" onclick="javascript:document.shippingshedule.action.value='remove';sendDataConfirm('shippingshedule','eliminado del env&iacute;o')"></div></td>
			<td class="separator">&nbsp;</td>
			<td><div id="searchButton"><img class="searchbutton" src="images/search.png" onclick="javascript:document.shippingshedule.action.value='search';sendData('shippingshedule')"></td>
			<td class="separator">&nbsp;</td>
		</tr>
	</table>
	<br>
	<table align="center">
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext"><%=TextFormatter.formatWeb(ShippingMessages.getMessage("shippingschedule.Title")) %></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td><input type='text' name="itemsJson"  id="itemsJson" style="display:none;position: absolute;" value='<%= dto.getItemsJson() != null ? TextFormatter.formatWeb(dto.getItemsJson()) : "" %>'/></td>
			<td><input type='text' name="branchJson"  id="branchJson" style="display:none;position: absolute;" value='<%= dto.getBranchJson() != null ? TextFormatter.formatWeb(dto.getBranchJson()) : "" %>'/></td>
			<td><input type='text' name="warehouseJson"  id="warehouseJson" style="display:none;position: absolute;" value='<%= dto.getWarehouseJson() != null ? TextFormatter.formatWeb(dto.getWarehouseJson()) : "" %>'/></td>
			<td><input type='text' name="productQuantityJson"  id="productQuantityJson" style="display:none;position: absolute;" value='<%= dto.getProductQuantityJson() != null ? TextFormatter.formatWeb(dto.getProductQuantityJson()) : "" %>'/></td>
			<td><input type='text' name="rowsAsJson"  id="rowsAsJson" style="display:none;position: absolute;" value='<%= dto.getRowsAsJson() != null ? dto.getRowsAsJson() : "" %>'/></td>
			<td><input type='text' name="routesAccount"  id="routesAccount" style="display:none;position: absolute;" value='<%= dto.getRoutesAccount() != null ? TextFormatter.formatWeb(dto.getRoutesAccount()) : "" %>'/></td>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext">&nbsp;</td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment "><%=TextFormatter.formatWeb(ShippingMessages.getMessage("shippingschedule.Date"))%>:</td>
			<td><input type='text' name='date' id='date' onchange="javascript:activeAdd('date');" value='<%= dto.getDate() != null ? TextFormatter.getFormattedDate(dto.getDate(),null) : "" %>'/></td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td><input type='hidden' name='idShippingSchedule' id='idShippingSchedule' value='<%= dto.getIdShippingSchedule() != null ? ""+dto.getIdShippingSchedule().intValue() : "" %>'/></td>
			<td class="labelaligment"><%=TextFormatter.formatWeb(ShippingMessages.getMessage("route.Name")) %>:</td>
			<td><select id="route" name="route" style="width: 150px;"></select></td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext">&nbsp;</td>
			<td class="separator">&nbsp;</td>
		</tr>
	</table>
	<br/><br/>
	<table id="shippingscheduleTable" align="center"></table>
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

	<div id="resultsTable">
<% if (list != null && list.size()>0){ %>
		<table class="resultsTable">
			<tr>
				<th><%=TextFormatter.formatWeb(ShippingMessages.getMessage("shippingschedule.ID")) %></th>
				<th><%=TextFormatter.formatWeb(ShippingMessages.getMessage("shippingschedule.Date")) %></th>				
				<th><%=TextFormatter.formatWeb(ShippingMessages.getMessage("route.Name")) %></th>
			</tr>
<%		for (int a = 0;a < list.size();a++){ 
			ShippingSchedule schedule = (ShippingSchedule) list.get(a); %>
			<tr onclick="javascript:clearForm();document.shippingshedule.idShippingSchedule.value='<%=schedule.getIdShippingSchedule()%>';document.shippingshedule.action.value='search';sendData('shippingshedule');">
				<td><%=schedule.getIdShippingSchedule() != null ? schedule.getIdShippingSchedule() : ""%></td>
				<td><%=schedule.getDate() != null ? TextFormatter.getFormattedDate(schedule.getDate(),null) : ""%></td>
				<td><%=schedule.getRoute().getName() !=null ? TextFormatter.formatWeb(schedule.getRoute().getName()) : ""%></td>
			</tr>
<%		} %>
		</table>
<%	} %>
	</div>
	<br/>
</form>
<script>
	$("form[name=shippingshedule] #date").datepicker();

	function clearForm() {
		document.shippingshedule.idShippingSchedule.value = '';
		document.shippingshedule.date.value = '';
		document.shippingshedule.idShippingSchedule.focus();
		document.shippingshedule.route.selectedIndex = -1;
		document.getElementById("resultsTable").style.visibility = "hidden";
		document.getElementById("resultsTable").style.visibility = "hidden";
		document.getElementById("addButton").style.visibility = "hidden";
		document.getElementById("searchButton").style.visibility = "visible";
		activeAdd('date');
		hideUpdateDelete();
		
	}
	$.datepicker.regional['es'] = {
		closeText : 'Cerrar',
		prevText : '<Ant',
		 nextText: 'Sig>',
		currentText : 'Hoy',
		monthNames : [ 'Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio',
				'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre',
				'Diciembre' ],
		monthNamesShort : [ 'Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun', 'Jul',
				'Ago', 'Sep', 'Oct', 'Nov', 'Dic' ],
		dayNames : [ 'Domingo', 'Lunes', 'Martes', 'Mi&eacute;rcoles', 'Jueves',
				'Viernes', 'S&aacute;bado' ],
		dayNamesShort : [ 'Dom', 'Lun', 'Mar', 'Mi&eacute;', 'Juv', 'Vie', 'S&aacute;b' ],
		dayNamesMin : [ 'Do', 'Lu', 'Ma', 'Mi', 'Ju', 'Vi', 'S&aacute;' ],
		weekHeader : 'Sm',
		dateFormat : 'dd/mm/yy',
		firstDay : 1,
		isRTL : false,
		showMonthAfterYear : false,
		yearSuffix : ''
	};
	$.datepicker.setDefaults($.datepicker.regional['es']);
	$(function() {
		$("#fecha").datepicker();
	});

	<%	if (error != null && !error.equalsIgnoreCase("")){ %>
	showError('<%=error%>');
<% 	} %>
<%	if (dto == null || dto.getIdShippingSchedule() == null || dto.getIdShippingSchedule().intValue() <= 0) {
		if (list == null || list.size() <= 0){ 
			if (error == null || error.equalsIgnoreCase("")){
%>
	clearForm();
<%			}
		} else { %>
	activeAdd("date");
<%		} %>
		
	hideUpdateDelete();
<% 	} else { %>
	document.getElementById("addButton").style.visibility = "hidden";
	document.getElementById("searchButton").style.visibility = "hidden";
<%	} %>

	</script>
