<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.industrika.administration.i18n.AdministrationMessages" %>
<%@page import="com.industrika.administration.dto.Policy" %>
<%@page import="com.industrika.administration.dto.PolicyRow" %>
<%@page import="com.industrika.commons.utils.TextFormatter" %>
<%@page import="java.util.Vector" %>
<jsp:useBean id="dto" scope="request" class="com.industrika.administration.dto.Policy"/>
<jsp:useBean id="row" scope="request" class="com.industrika.administration.dto.PolicyRow"/>
<jsp:useBean id="list" scope="request" class="java.util.Vector"/>
<jsp:useBean id="message" scope="request" class="java.lang.String"/>
<jsp:useBean id="error" scope="request" class="java.lang.String"/>
<jsp:useBean id="policyList" scope="request" class="java.lang.String"/>

<script>
	$(function(){
		$("form[name=policy] #policyDate").datepicker();
		var policydate= $("form[name=policy] #policyDate").val();
		if(policydate.length===0){
			$("form[name=policy] #policyDate").datepicker("setDate", new Date());
		}
		
	    var dtoAccounts=fixAccents($("form[name=policy] #accountsDB").val());
	    
	    var jsonRows= $("form[name=policy] #rowsAsJson").val();
	   
	    initRadio();
	 	$(function () {
	 	        if(jsonRows==""){
	 	        	var initValues= [{"accountNumber":"001-001-001","name":"account","checkNumber":"044072324",'debit': 100.00, 'credit':  0.0}];
	 	        }
	 	        else{
	 	         	var JsonRowObj=jQuery.parseJSON(jsonRows);
	 	         	var initValues=JsonRowObj;
	 	        }
	 	       
			    if(document.policy.policyList.value != null && !document.policy.policyList.value.length>0){
			    	initValues=initValues;
			    }
			    
			     $("form[name=policy] #policyTable").appendGrid('init', {
			        caption: 'Pólizas',
			        initRows: 1,					
			        columns: [
			                { name: 'accountNumber', display: 'Número de cuenta', type: 'select', ctrlOptions: dtoAccounts } ,
			                { name: 'name', display: 'Nombre', type: 'text', ctrlAttr: { maxlength: 150, title: 'Agrega el nombre de cuenta' }, ctrlCss: { width: '100px' }, uiTooltip: { show: true} },
			                { name: 'details', display: 'Detalle', type: 'text', ctrlAttr: { maxlength: 150, title: 'Agrega detalles' }, ctrlCss: { width: '160px'} },
			                { name: 'checkNumber', display: 'Número de cheque / Depósito', type: 'text', ctrlAttr: { maxlength: 150, title: 'Agrega el número de cheques o déposito' }, ctrlCss: { width: '160px'} },
			                { name: 'debit', display: 'Debe', type: 'text', ctrlAttr: { maxlength: 20 }, ctrlCss: { width: '50px', 'text-align': 'right' }, value: 0, ctrlClass: 'debitClass' },
			                { name: 'credit', display: 'Haber', type: 'text', ctrlAttr: { maxlength: 20 }, ctrlCss: { width: '50px', 'text-align': 'right' }, value: 0, ctrlClass: 'creditClass' }
			            ],
			        initData: initValues
			    });
	 
			    $.validator.addMethod('creditClass', function (value, element) {
						 return (value && -1 != value.search(/^[+-]?(?=.)(?:\d+,)*\d*(?:\.\d+)?$/));
			    }, 'Favor de usar valores numéricos');
			    
			    $.validator.addMethod('debitClass', function (value, element) {
			         return (value && -1 != value.search(/^[+-]?(?=.)(?:\d+,)*\d*(?:\.\d+)?$/));
			    }, 'Favor de usar valores numéricos');
		    
		    
			    // Initialize validation plugin
			    $(document.policy).validate({
			        errorLabelContainer: 'form[name=policy] #ulError',
			        wrapper: 'li'
		    	});
	 
			});
	 
	});
	  
	function initRadio(){
	    var policyVal=$("form[name=policy] #typePolicy").val();
	    
	    if(policyVal==="INGRESO"){
	    	$('input:radio[name=policyType][value=INGRESO]').click();
	    	}
	    
	     if(policyVal==="EGRESO"){
	    	$('input:radio[name=policyType][value=EGRESO]').click();
	    	}
	    
	     if(policyVal==="DIARIO"){
	    	$('input:radio[name=policyType][value=DIARIO]').click();
	    	}
	 }

</script>

<form name="policy" id="policy" class="normaltext">
	<input type="hidden" name="action" id="action"/>
	<input type="hidden" name="formName" id="formName" value="policy"/>
	<input type="hidden" name="policyList" id="policyList" value=""/>
	<table class="commandbuttons">
		<tr>
			<td class="separator">&nbsp;</td>
			<td><img class="resetbutton" src="images/reset.png" onclick="javascript:clearForm()"></td>
			<td class="separator">&nbsp;</td>
			<td class="td_add"><img class="addbutton" src="images/new.png" onclick="javascript:document.policy.action.value='add';addPolicy('policy','agregar poliza')"></td>
			<td class="separator,td_add">&nbsp;</td>
			<td class="separator">&nbsp;</td>
			<td><img class="searchbutton" src="images/search.png" onclick="javascript:document.policy.action.value='search';sendData('policy')"></td>
			<td class="separator">&nbsp;</td>
		</tr>
	</table>
	<br>
	<table align="center">
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment "><%=AdministrationMessages.getMessage("policy.IdPolicy")%>:</td>
			<td><input type='text' name='idPolicy' id='idPolicy' value='<%= dto.getIdPolicy() != null ? ""+dto.getIdPolicy() : "" %>'/></td>
			<td class="separator">&nbsp;</td>
			<td class="separator">&nbsp;</td>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment "><%=AdministrationMessages.getMessage("policy.Date")%>:</td>
			<td><input type='text' name='policyDate' id='policyDate'  value='<%= dto.getPolicyDate() != null ? ""+dto.getPolicyDate() : "" %>'/></td>
			<td><input type='text' name="accountsDB"  id="accountsDB" style="display:none;position: absolute;" value='<%= dto.getAccountsDB() != null ? dto.getAccountsDB() : "" %>'/></td>
			<td><input type='text' name="rowsAsJson"  id="rowsAsJson" style="display:none;position: absolute;" value='<%= dto.getRowsAsJson() != null ? dto.getRowsAsJson() : "" %>'/></td>
			<td><input type='text' name="typePolicy"  id="typePolicy" style="display:none;position: absolute;" value='<%= dto.getPolicyType() != null ? dto.getPolicyType() : "INGRESO" %>'/></td>
		</tr>
	<table>
	<br>
	<table align="center">
		<tr>
			<td class="separator">&nbsp;</td>
			<td><input type="radio" name='policyType' id='policyType' value="INGRESO" checked>Ingreso</td>
			<td class="separator">&nbsp;</td>
			<td> <input type="radio" name='policyType' id='policyType' value="EGRESO">Egreso<br></td>
			<td class="separator">&nbsp;</td>
			<td> <input type="radio" name='policyType' id='policyType' value="DIARIO">Diario<br></td>
			<td class="separator">&nbsp;</td>
		</tr>
	</table>
	<br/>
	<table id="policyTable" align="center"></table>
	<br>
	<%	if (message != null && !message.equalsIgnoreCase("")){ %>
	<table>
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="messagetext"><%=message %></td>
			<td class="separator">&nbsp;</td>
		</tr>
	 </table>
	 <br>
	<%	} %>
	<div id="divErrorPlacement" style="min-height: 100px;">
    	<ul id="ulError"></ul>
	</div>
	<div id="resultsTable">
<% if (list != null && list.size()>0){ %>
		<table class="resultsTable">
			<tr>
				<th><%=AdministrationMessages.getMessage("policy.IdPolicy") %></th>
				<th><%=AdministrationMessages.getMessage("policy.Date") %></th>				
				<th><%=AdministrationMessages.getMessage("policy.Type") %></th>
			</tr>
<%		for (int a = 0;a < list.size();a++){ 
				Policy policy = (Policy) list.get(a); %>
			<tr onclick="javascript:clearForm();document.policy.idPolicy.value='<%=policy.getIdPolicy()%>';document.policy.action.value='search';sendData('policy');">
				<td><%=policy.getIdPolicy() != null ? policy.getIdPolicy() : ""%></td>
				<td><%=policy.getPolicyDate() != null ? policy.getPolicyDate() : ""%></td>
				<td><%=policy.getPolicyType() != null ? policy.getPolicyType() : ""%></td>
			</tr>
<%		} %>
		</table>
<%	} %>
	</div>
	
</form>
<script>
<%	if (error != null && !error.equalsIgnoreCase("")){ %>
	showError('<%=error%>');
<% 	} %>
	function addPolicy(formId,message){
		if($("form[name=policy]").valid()){
			var data =$("form[name=policy] #policyTable").appendGrid('getAllValue');
			if(validateDebeHaber(data)){
				document.policy.action.value='add'
				var myJsonString = JSON.stringify(data);
				$("form[name=policy] #policyList").val(myJsonString);
		 		sendDataConfirm(formId,message);
			}
			else{
			 	 $("form[name=policy] #divErrorPlacement ul #myliDebeHaber").remove();
				 $("form[name=policy] #divErrorPlacement ul").append("<li id=\"myliDebeHaber\"><label class=\"error\" style=\"display: inline;\">El Haber debe cuadrar con el Debe</label></li>");
				 $("form[name=policy] #divErrorPlacement ul").show();
				 $("form[name=policy] #divErrorPlacement ul").css({ display: "block" });
			}
		}		
	}
	
	function validateDebeHaber(data){
	    var debe=parseFloat("0.0");
	    var haber=parseFloat("0.0");
		for (var i=0;i<data.length;i++)
		{ 
			debe=parseFloat(debe)+parseFloat(data[i].debit);
			haber=parseFloat(haber)+parseFloat(data[i].credit);
		}
		return (debe==haber)
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
			document.policy.idPolicy.value='';
			document.policy.policyDate.value='';
			document.policy.policyType.value='';
			var initValues= '';			
			$("form[name=policy] #policyTable").appendGrid('load',initValues);
	    }

</script>
