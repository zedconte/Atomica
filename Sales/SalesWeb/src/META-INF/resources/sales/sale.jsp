<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.industrika.sales.i18n.SalesMessages" %>
<%@page import="com.industrika.sales.dto.Sale" %>
<%@page import="com.industrika.inventory.dto.Document" %>
<%@page import="com.industrika.inventory.dto.DocumentRow" %>
<%@page import="java.util.HashMap" %>
<%@page import="java.util.Map" %>
<%@page import="java.util.Vector" %>
<%@page import="com.industrika.commons.utils.TextFormatter" %>
<jsp:useBean id="dto" scope="request" class="com.industrika.sales.dto.Sale"/>
<jsp:useBean id="row" scope="request" class="com.industrika.inventory.dto.DocumentRow"/>
<jsp:useBean id="list" scope="request" class="java.util.Vector"/>
<jsp:useBean id="message" scope="request" class="java.lang.String"/>
<jsp:useBean id="error" scope="request" class="java.lang.String"/>
<jsp:useBean id="policyList" scope="request" class="java.lang.String"/>

<script>
	$(function(){
		$("form[name=sale] #date").datepicker();
		var policydate= $("form[name=sale] #date").val();
		if(policydate.length===0){
			$("form[name=sale] #date").datepicker("setDate", new Date());
		}
		
		$("form[name=sale] #discount").val("0");
		
		activeSearch('idDocument');
		
		var items=$("form[name=sale] #itemsJson").val();
		var branches=$("form[name=sale] #branchJson").val();
		var warehouses=$("form[name=sale] #warehouseJson").val();
		var maxItems=$("form[name=sale] #productQuantityJson").val();
		
		var jsonRows= $("form[name=sale] #rowsAsJson").val();
		
		var customersAccounts=$("form[name=sale] #customersAccount").val();
		
		if (customersAccounts!="") {
			var selectedCustomer = <%=(dto.getCustomer() != null && dto.getCustomer().getIdPerson() != null && dto.getCustomer() != null && dto.getCustomer().getIdPerson().intValue() >0) ? dto.getCustomer().getIdPerson().intValue() : -1 %>;
			var JsonActs=jQuery.parseJSON(customersAccounts);			
			$(JsonActs).each(function() {  
			    var ID = this.idProvider;
			    var NAME = this.providerName;
			    if (ID === selectedCustomer){
			    	$('form[name=sale] #customer').append(new Option(NAME,ID,false,true));
			    } else {
			    	$('form[name=sale] #customer').append(new Option(NAME,ID));
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
 	        
		     $("form[name=sale] #saleTable").appendGrid('init', {
		        caption: 'Orden',
		        initRows: 1,					
		        columns: [
		                { name: 'itemName', display: 'Producto : Precio', type: 'select', ctrlOptions: items } ,
		                { name: 'itemQuantity', display: 'Cantidad de producto', type: 'select', ctrlOptions: maxItems, onChange: function (evt, rowIndex) {
		                    fillTotal();
		                } } ,
		                { name: 'itemBranch', display: 'Sucursal', type: 'select', ctrlOptions: branches } ,
		                { name: 'itemWarehouse', display: 'Almacen', type: 'select', ctrlOptions: warehouses }
		            ],
		        initData: initValues
		    });
  
		    // Initialize validation plugin
		    $(document.policy).validate({
		        errorLabelContainer: 'form[name=sale] #ulError',
		        wrapper: 'li'
	    	});
 
		});
	
	});
	
	function activeSearch(id){
		var value=$("form[name=sale] #"+id).val();
		if (value !== ''){
			$(".addButton").css("visibility", "hidden");
			$(".savebutton").css("visibility", "visible");
			$(".deletebutton").css("visibility", "visible");
		} else {
			$(".addButton").css("visibility", "visible");
			$(".savebutton").css("visibility", "hidden");
			$(".deletebutton").css("visibility", "hidden");
		}
	}
	

</script>
<div style='visibility: hidden' id="tempDiv"></div>
<form name="sale" id="sale" class="normaltext">
	<div  id="tempDiv"></div>
	<input type="hidden" name="action" id="action"/>
	<input type="hidden" name="formName" id="formName" value="sale"/>
	<table class="commandbuttons">
		<tr>
			<td class="separator">&nbsp;</td>
			<td><img class="resetbutton" src="images/reset.png" onclick="javascript:clearForm()"></td>
			<td class="separator">&nbsp;</td>
			<td class="td_add"><img class="addbutton" src="images/new.png" onclick="javascript:document.sale.action.value='add';addSale('sale','Alta de la venta','add')"></td>
			<td class="separator">&nbsp;</td>
			<td><div id="updateButton"><img class="savebutton" src="images/save.png" onclick="javascript:document.sale.action.value='update';addSale('sale','Actualización de la venta','update')"></div></td>
			<td class="separator">&nbsp;</td>
			<td><div id="deleteButton"><img class="deletebutton" src="images/delete.png" onclick="javascript:document.sale.action.value='remove';sendDataConfirm('sale','Eliminado de la venta')"></div></td>
			<td class="separator">&nbsp;</td>
			<td><img class="searchbutton" src="images/search.png" onclick="javascript:document.sale.action.value='search';sendData('sale')"></td>
			<td class="separator">&nbsp;</td>
		</tr>
	</table>
	<br>
	<table align="center">
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="13" class="titletext"><%=TextFormatter.formatWeb(SalesMessages.getMessage("sale.Title")) %></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="13" class="titletext">&nbsp;</td>
			<td class="separator">&nbsp;</td>
		</tr>	
		<tr>
			<td class="separator">&nbsp;</td>
			<td><input type='hidden' name='idDocument' id='idDocument' onkeyup="javascript:activeSearch('idDocument');" value='<%= dto.getIdDocument() != null ? ""+dto.getIdDocument().intValue() : "" %>'/></td>
			<td class="labelaligment "><%=SalesMessages.getMessage("sale.Folio")%>:</td>
			<td><input type='text' name='folio' id='folio' value='<%= dto.getFolio() != null ? ""+dto.getFolio() : "" %>'/></td>
			<td class="separator">&nbsp;</td>
			<td><input type='text' name="itemsJson"  id="itemsJson" style="display:none;position: absolute;" value='<%= dto.getItemsJson() != null ? TextFormatter.formatWeb(dto.getItemsJson()) : "" %>'/></td>
			<td><input type='text' name="branchJson"  id="branchJson" style="display:none;position: absolute;" value='<%= dto.getBranchJson() != null ? TextFormatter.formatWeb(dto.getBranchJson()) : "" %>'/></td>
			<td><input type='text' name="warehouseJson"  id="warehouseJson" style="display:none;position: absolute;" value='<%= dto.getWarehouseJson() != null ? TextFormatter.formatWeb(dto.getWarehouseJson()) : "" %>'/></td>
			<td><input type='text' name="productQuantityJson"  id="productQuantityJson" style="display:none;position: absolute;" value='<%= dto.getProductQuantityJson() != null ? TextFormatter.formatWeb(dto.getProductQuantityJson()) : "" %>'/></td>
			<td><input type='text' name="rowsAsJson"  id="rowsAsJson" style="display:none;position: absolute;" value='<%= dto.getRowsAsJson() != null ? dto.getRowsAsJson() : "" %>'/></td>
			<td><input type='text' name="customersAccount"  id="customersAccount" style="display:none;position: absolute;" value='<%= dto.getCustomersAccount() != null ? TextFormatter.formatWeb(dto.getCustomersAccount()) : "" %>'/></td>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment "><%=SalesMessages.getMessage("sale.Date")%>:</td>
			<td><input type='text' name='date' id='date'  value='<%= dto.getDate() != null ? TextFormatter.getFormattedDate(dto.getDate(),null) : "" %>'/></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td>&nbsp;</td>
			<td class="labelaligment "><%=SalesMessages.getMessage("sale.Customer") %>:</td>
			<td colspan="11"><select id="customer" name="customer" style="width: 100%;"></select></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="13" class="titletext">&nbsp;</td>
			<td class="separator">&nbsp;</td>
		</tr>
	</table>
	<table id="saleTable" align="center"></table>
	<table align="center">
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext">&nbsp;</td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment "><%=SalesMessages.getMessage("sale.Subtotal")%>:</td>
			<td><input class="labelaligment " type='text' name='subtotal' id='subtotal' value='<%= dto.getSubtotal() != null ? ""+TextFormatter.getNumberValue(dto.getSubtotal()) : "0.00" %>' readonly/></td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment "><%=SalesMessages.getMessage("sale.Discount")%>:</td>
			<td><input class="labelaligment " type='text' onblur="javascript:this.value=formatNumberWithoutSign(Number(this.value));" name='discount' id='discount' onchange="javascript:applyTotal();" value='<%= dto.getDiscount() != null ? TextFormatter.getNumberValue(dto.getDiscount()) : "0.00" %>'/></td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment "><%=SalesMessages.getMessage("sale.Taxes")%>:</td>
			<td><input class="labelaligment " type='text' name='tax' id='tax' value='<%= dto.getTax() != null ? TextFormatter.getNumberValue(dto.getTax().doubleValue()) : "0.00" %>' readonly/></td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment "><%=SalesMessages.getMessage("sale.Total")%>:</td>
			<td><input class="labelaligment " type='text' name='total' id='total' value='<%= dto.getTotal() != null ? TextFormatter.getNumberValue(dto.getTotal()) : "0.00" %>' readonly/></td>
		</tr>
	<table>
	<br>
	<%	if (message != null && !message.equalsIgnoreCase("")){ %>
	<table align="center">
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="messagetext"><%=message %></td>
			<td class="separator">&nbsp;</td>
		</tr>
	 </table>
	 <br>
	<%	} %>	
	<div id="resultsTable">
<% if (list != null && list.size()>0){ %>
		<table class="resultsTable">
			<tr>
				<th><%=TextFormatter.formatWeb(SalesMessages.getMessage("sale.Folio")) %></th>
				<th><%=TextFormatter.formatWeb(SalesMessages.getMessage("sale.Date")) %></th>
				<th><%=TextFormatter.formatWeb(SalesMessages.getMessage("sale.Customer")) %></th>
				<th><%=TextFormatter.formatWeb(SalesMessages.getMessage("sale.Total")) %></th>
			</tr>
<%		for (int a = 0;a < list.size();a++){ 
				Sale sale = (Sale) list.get(a); %>
			<tr onclick="javascript:clearForm();document.sale.idDocument.value='<%=sale.getIdDocument()%>';document.sale.action.value='search';sendData('sale');">
				<td><%=sale.getFolio() != null ? sale.getFolio() : ""%></td>
				<td><%=sale.getDate() != null ? TextFormatter.getFormattedDate(sale.getDate(),null) : ""%></td>
				<td><%=sale.getCustomer().getBusinessName() !=null ? TextFormatter.formatWeb(sale.getCustomer().getBusinessName()) : ""%></td>
				<td style="text-align: right"><%=sale.getTotal() != null ? TextFormatter.getCurrencyValue(sale.getTotal())  : ""%></td>
			</tr>
<%		} %>
		</table>
<%	} %>
	</div>
	<div id="divErrorPlacement" style="min-height: 10px;">
    	<ul id="ulError"></ul>
	</div>
	
</form>
<script>
<%	if (error != null && !error.equalsIgnoreCase("")){ %>
	showError('<%=error%>');
<% 	} %>
	function addSale(formId,message,action){
		if($("form[name=sale]").valid()){
			var data =$("form[name=sale] #saleTable").appendGrid('getAllValue');
				document.sale.action.value=action;
				var myJsonString = JSON.stringify(data);
				$("form[name=sale] #rowsAsJson").val(myJsonString);
		 		sendDataConfirm(formId,message);
		}		
	}
	
	function fillTotal(){
		var data =$("form[name=sale] #saleTable").appendGrid('getAllValue');
		 var subTotal=parseFloat("0.0");
		 var taxes=parseFloat("0.0");
		for (var i=0;i<data.length;i++)
		{ 
			var quantity=parseFloat(data[i].itemQuantity);	
			var priceVal=data[i].itemName;
			var element=priceVal.split("_");
			var price = parseFloat(element[1]);
			var sub=quantity*price;
			var tax = parseFloat(element[2])*quantity;
			subTotal=subTotal+sub;	
			taxes=taxes+tax;
		}
		
		$("form[name=sale] #subtotal").val(formatNumberWithoutSign(subTotal));
		$("form[name=sale] #tax").val(formatNumberWithoutSign(taxes));
		applyTotal();
	}
	
	function applyTotal(){
		if ($("form[name=sale] #discount").val() === ''){
			$("form[name=sale] #discount").val('0.00');
		}
		if ($("form[name=sale] #tax").val() === ''){
			$("form[name=sale] #tax").val('0.00');
		}
		var discount = parseFloat(flatNumber($("form[name=sale] #discount").val()));
		var tax = parseFloat(flatNumber($("form[name=sale] #tax").val()));
		var subtotal = parseFloat(flatNumber($("form[name=sale] #subtotal").val()));
		if(discount==0){
			$("form[name=sale] #total").val(formatNumberWithoutSign(subtotal+tax));
		}
		else{
			var t=subtotal-((discount/parseFloat(100))*subtotal);
			$("form[name=sale] #total").val(formatNumberWithoutSign(t+tax));
		}
			
	}
	
	$.datepicker.regional['es'] = {
		 closeText: 'Cerrar',
		 prevText: '<Ant',
		 nextText: 'Sig>',
		 currentText: 'Hoy',
		 monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
		 monthNamesShort: ['Ene','Feb','Mar','Abr', 'May','Jun','Jul','Ago','Sep', 'Oct','Nov','Dic'],
		 dayNames: ['Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado'],
		 dayNamesShort: ['Dom','Lun','Mar','Mié','Juv','Vie','Sáb'],
		 dayNamesMin: ['Do','Lu','Ma','Mi','Ju','Vi','Sá'],
		 weekHeader: 'Sm',
		 dateFormat: 'dd/mm/yy',
		 firstDay: 1,
		 isRTL: false,
		 showMonthAfterYear: false,
		 yearSuffix: ''
		 };
		 $.datepicker.setDefaults($.datepicker.regional['es']);
		$(function () {
		$("#fecha").datepicker();
		});
		
		function clearForm(){
			document.sale.idDocument.value='';
			document.sale.subtotal.value='';
			document.sale.total.value='';
			document.sale.folio.value='';
			document.sale.discount.value='';
			document.sale.tax.value='';
			document.sale.date.value='';
			document.sale.customer.selectedIndex=-1;
			activeSearch('idDocument');
			//$("form[name=sale] #date").datepicker("setDate", new Date());
			var initValues="";
			$("form[name=sale] #saleTable").appendGrid('load',initValues);
	    }

</script>
