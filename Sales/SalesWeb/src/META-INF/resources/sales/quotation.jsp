<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.industrika.sales.i18n.SalesMessages" %>
<%@page import="com.industrika.sales.dto.Quotation" %>
<%@page import="com.industrika.inventory.dto.Document" %>
<%@page import="com.industrika.inventory.dto.DocumentRow" %>
<%@page import="java.util.HashMap" %>
<%@page import="java.util.Map" %>
<%@page import="java.util.Vector" %>
<%@page import="com.industrika.commons.utils.TextFormatter" %>
<jsp:useBean id="dto" scope="request" class="com.industrika.sales.dto.Quotation"/>
<jsp:useBean id="row" scope="request" class="com.industrika.inventory.dto.DocumentRow"/>
<jsp:useBean id="list" scope="request" class="java.util.Vector"/>
<jsp:useBean id="message" scope="request" class="java.lang.String"/>
<jsp:useBean id="error" scope="request" class="java.lang.String"/>
<jsp:useBean id="policyList" scope="request" class="java.lang.String"/>

<script>
	$(function(){
		$("form[name=quotation] #date").datepicker();
		var policydate= $("form[name=quotation] #date").val();
		if(policydate.length===0){
			$("form[name=quotation] #date").datepicker("setDate", new Date());
		}
		
		$("form[name=quotation] #discount").val("0");
		
		activeSearch('idDocument');
		
		var items=$("form[name=quotation] #itemsJson").val();
		var branches=$("form[name=quotation] #branchJson").val();
		var warehouses=$("form[name=quotation] #warehouseJson").val();
		var maxItems=$("form[name=quotation] #productQuantityJson").val();
		
		var jsonRows= $("form[name=quotation] #rowsAsJson").val();
		
		var customersAccounts=$("form[name=quotation] #customersAccount").val();
		
		if (customersAccounts!="") {
			var selectedCustomer = <%=(dto.getCustomer() != null && dto.getCustomer().getIdPerson() != null && dto.getCustomer().getIdPerson().intValue() >0) ? dto.getCustomer().getIdPerson().intValue() : -1 %>;
			var JsonActs=jQuery.parseJSON(customersAccounts);			
			$(JsonActs).each(function() {  
			    var ID = this.idProvider;
			    var NAME = this.providerName;
			    if (ID === selectedCustomer){
			    	$('form[name=quotation] #customer').append(new Option(NAME,ID,false,true));
			    } else {
			    	$('form[name=quotation] #customer').append(new Option(NAME,ID));
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
 	        
		     $("form[name=quotation] #quotationTable").appendGrid('init', {
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
		        errorLabelContainer: 'form[name=quotation] #ulError',
		        wrapper: 'li'
	    	});
 
		});
	
	});
	
	function activeSearch(id){
		var value=$("form[name=quotation] #"+id).val();
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
<form name="quotation" id="quotation" class="normaltext">
	<div  id="tempDiv"></div>
	<input type="hidden" name="action" id="action"/>
	<input type="hidden" name="formName" id="formName" value="quotation"/>
	<table class="commandbuttons">
		<tr>
			<td class="separator">&nbsp;</td>
			<td><img class="resetbutton" src="images/reset.png" onclick="javascript:clearForm()"></td>
			<td class="separator">&nbsp;</td>
			<td class="td_add"><img class="addbutton" src="images/new.png" onclick="javascript:document.quotation.action.value='add';addQuotation('quotation','Agregar la cotización','add')"></td>
			<td class="separator">&nbsp;</td>
			<td><div id="updateButton"><img class="savebutton" src="images/save.png" onclick="javascript:document.quotation.action.value='update';addQuotation('quotation','Actualización de la cotización','update')"></div></td>
			<td class="separator">&nbsp;</td>
			<td><div id="deleteButton"><img class="deletebutton" src="images/delete.png" onclick="javascript:document.quotation.action.value='remove';sendDataConfirm('quotation','Eliminado de la cotización')"></div></td>
			<td class="separator">&nbsp;</td>
			<td><img class="searchbutton" src="images/search.png" onclick="javascript:document.quotation.action.value='search';sendData('quotation')"></td>
			<td class="separator">&nbsp;</td>
		</tr>
	</table>
	<br>
	<table align="center">
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="13" class="titletext"><%=TextFormatter.formatWeb(SalesMessages.getMessage("quotation.Title")) %></td>
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
			<td class="labelaligment "><%=TextFormatter.formatWeb(SalesMessages.getMessage("quotation.Folio"))%>:</td>
			<td><input type='text' name='folio' id='folio' value='<%= dto.getFolio() != null ? ""+dto.getFolio() : "" %>'/></td>
			<td class="separator">&nbsp;</td>
			<td><input type='text' name="itemsJson"  id="itemsJson" style="display:none;position: absolute;" value='<%= dto.getItemsJson() != null ? TextFormatter.formatWeb(dto.getItemsJson()) : "" %>'/></td>
			<td><input type='text' name="branchJson"  id="branchJson" style="display:none;position: absolute;" value='<%= dto.getBranchJson() != null ? TextFormatter.formatWeb(dto.getBranchJson()) : "" %>'/></td>
			<td><input type='text' name="warehouseJson"  id="warehouseJson" style="display:none;position: absolute;" value='<%= dto.getWarehouseJson() != null ? TextFormatter.formatWeb(dto.getWarehouseJson()) : "" %>'/></td>
			<td><input type='text' name="productQuantityJson"  id="productQuantityJson" style="display:none;position: absolute;" value='<%= dto.getProductQuantityJson() != null ? TextFormatter.formatWeb(dto.getProductQuantityJson()) : "" %>'/></td>
			<td><input type='text' name="rowsAsJson"  id="rowsAsJson" style="display:none;position: absolute;" value='<%= dto.getRowsAsJson() != null ? dto.getRowsAsJson() : "" %>'/></td>
			<td><input type='text' name="customersAccount"  id="customersAccount" style="display:none;position: absolute;" value='<%= dto.getCustomersAccount() != null ? TextFormatter.formatWeb(dto.getCustomersAccount()) : "" %>'/></td>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment "><%=TextFormatter.formatWeb(SalesMessages.getMessage("quotation.Date"))%>:</td>
			<td><input type='text' name='date' id='date'  value='<%= dto.getDate() != null ? TextFormatter.getFormattedDate(dto.getDate(),null) : "" %>'/></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td>&nbsp;</td>
			<td class="labelaligment "><%=TextFormatter.formatWeb(SalesMessages.getMessage("quotation.Customer")) %>:</td>
			<td colspan="11"><select id="customer" name="customer" style="width: 100%;"></select></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="13" class="titletext">&nbsp;</td>
			<td class="separator">&nbsp;</td>
		</tr>
	</table>
	<table id="quotationTable" align="center"></table>
	<table align="center">
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext">&nbsp;</td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment "><%=TextFormatter.formatWeb(SalesMessages.getMessage("quotation.Subtotal"))%>:</td>
			<td><input class="labelaligment " type='text' name='subtotal' id='subtotal' value='<%= dto.getSubtotal() != null ? ""+TextFormatter.getNumberValue(dto.getSubtotal()) : "0.00" %>' readonly/></td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment "><%=TextFormatter.formatWeb(SalesMessages.getMessage("quotation.Discount"))%>:</td>
			<td><input class="labelaligment " type='text' onblur="javascript:this.value=formatNumberWithoutSign(Number(this.value));" name='discount' id='discount' onchange="javascript:applyTotal();" value='<%= dto.getDiscount() != null ? TextFormatter.getNumberValue(dto.getDiscount()) : "0.00" %>'/></td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment "><%=TextFormatter.formatWeb(SalesMessages.getMessage("quotation.Taxes"))%>:</td>
			<td><input class="labelaligment " type='text' name='tax' id='tax' value='<%= dto.getTax() != null ? TextFormatter.getNumberValue(dto.getTax().doubleValue()) : "0.00" %>' readonly/></td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment "><%=TextFormatter.formatWeb(SalesMessages.getMessage("quotation.Total"))%>:</td>
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
				<th><%=TextFormatter.formatWeb(SalesMessages.getMessage("quotation.Folio")) %></th>
				<th><%=TextFormatter.formatWeb(SalesMessages.getMessage("quotation.Date")) %></th>
				<th><%=TextFormatter.formatWeb(SalesMessages.getMessage("quotation.Customer")) %></th>
				<th><%=TextFormatter.formatWeb(SalesMessages.getMessage("quotation.Total")) %></th>
			</tr>
<%		for (int a = 0;a < list.size();a++){ 
				Quotation quotation = (Quotation) list.get(a); %>
			<tr onclick="javascript:clearForm();document.quotation.idDocument.value='<%=quotation.getIdDocument()%>';document.quotation.action.value='search';sendData('quotation');">
				<td><%=quotation.getFolio() != null ? TextFormatter.formatWeb(quotation.getFolio()) : ""%></td>
				<td><%=quotation.getDate() != null ? TextFormatter.getFormattedDate(quotation.getDate(),null) : ""%></td>				
				<td><%=quotation.getCustomer().getBusinessName() !=null ? TextFormatter.formatWeb(quotation.getCustomer().getBusinessName()) : ""%></td>
				<td style="text-align: right"><%=quotation.getTotal() != null ? TextFormatter.getCurrencyValue(quotation.getTotal())  : ""%></td>
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
	function addQuotation(formId,message,action){
		if($("form[name=quotation]").valid()){
			var data =$("form[name=quotation] #quotationTable").appendGrid('getAllValue');
				document.quotation.action.value=action;
				var myJsonString = JSON.stringify(data);
				$("form[name=quotation] #rowsAsJson").val(myJsonString);
		 		sendDataConfirm(formId,message);
		}		
	}
	
	function fillTotal(){
		var data =$("form[name=quotation] #quotationTable").appendGrid('getAllValue');
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
		
		$("form[name=quotation] #subtotal").val(formatNumberWithoutSign(subTotal));
		$("form[name=quotation] #tax").val(formatNumberWithoutSign(taxes));
		applyTotal();
	}
	
	function applyTotal(){
		if ($("form[name=quotation] #discount").val() === ''){
			$("form[name=quotation] #discount").val('0.00');
		}
		if ($("form[name=quotation] #tax").val() === ''){
			$("form[name=quotation] #tax").val('0.00');
		}
		var discount = parseFloat(flatNumber($("form[name=quotation] #discount").val()));
		var tax = parseFloat(flatNumber($("form[name=quotation] #tax").val()));
		var subtotal = parseFloat(flatNumber($("form[name=quotation] #subtotal").val()));
		if(discount==0){
			$("form[name=quotation] #total").val(formatNumberWithoutSign(subtotal+tax));
		}
		else{
			var t=subtotal-((discount/parseFloat(100))*subtotal);
			$("form[name=quotation] #total").val(formatNumberWithoutSign(t+tax));
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
			document.quotation.idDocument.value='';
			document.quotation.subtotal.value='';
			document.quotation.total.value='';
			document.quotation.folio.value='';
			document.quotation.discount.value='';
			document.quotation.tax.value='';
			document.quotation.date.value='';
			document.quotation.customer.selectedIndex=-1;
			activeSearch('idDocument');
			//$("form[name=quotation] #date").datepicker("setDate", new Date());
			var initValues="";
			$("form[name=quotation] #quotationTable").appendGrid('load',initValues);
	    }

</script>
