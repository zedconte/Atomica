<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.industrika.humanresources.i18n.HRMessages" %>
<%@page import="com.industrika.humanresources.dto.Payroll" %>
<%@page import="com.industrika.humanresources.dto.PayrollDetail" %>
<%@page import="com.industrika.commons.utils.TextFormatter" %>
<%@page import="java.util.Vector" %>
<jsp:useBean id="dto" scope="request" class="com.industrika.humanresources.dto.Payroll"/>
<jsp:useBean id="list" scope="request" class="java.util.Vector"/>
<jsp:useBean id="message" scope="request" class="java.lang.String"/>
<jsp:useBean id="error" scope="request" class="java.lang.String"/>

<script>
	$(function(){
		$("form[name=payroll] #begin").datepicker();
		$("form[name=payroll] #end").datepicker();
		var payrollbegindate= $("form[name=payroll] #begin").val();
		var payrollenddate= $("form[name=payroll] #end").val();
		if(payrollbegindate.length===0){
			$("form[name=payroll] #begin").datepicker("setDate", new Date());
		}
		if(payrollenddate.length===0){
			$("form[name=payroll] #end").datepicker("setDate", new Date());
		}
	});
</script>
<form name="payroll" id="payroll" class="normaltext">
	<input type="hidden" name="action" id="action"/>
	<input type="hidden" name="formName" id="formName" value="payroll"/>
	<table class="commandbuttons">
		<tr>
			<td class="separator">&nbsp;</td>
			<td><img class="resetbutton" src="images/reset.png" onclick="javascript:clearForm()"></td>
			<td class="separator">&nbsp;</td>
			<td><div id="addButton"><img class="addbutton" src="images/new.png" onclick="javascript:document.payroll.action.value='add';sendDataConfirm('payroll','Alta para el departamento')"></div></td>
			<td class="separator">&nbsp;</td>
			<td><div id="updateButton"><img class="savebutton" src="images/save.png" onclick="javascript:document.payroll.action.value='update';sendDataConfirm('payroll','Actualizaci&oacute;n del departamento')"></div></td>
			<td class="separator">&nbsp;</td>
			<td><div id="deleteButton"><img class="deletebutton" src="images/delete.png" onclick="javascript:document.payroll.action.value='remove';sendDataConfirm('payroll','Eliminado del departamento')"></div></td>
			<td class="separator">&nbsp;</td>
			<td><img id="searchButton" class="searchbutton" src="images/search.png" onclick="javascript:document.payroll.action.value='search';sendData('payroll')"></td>
			<td class="separator">&nbsp;</td>
		</tr>
	</table>
	<br/>
	<table align="center">
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext"><%=TextFormatter.formatWeb(HRMessages.getMessage("payroll.Title")) %></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext">&nbsp;</td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=TextFormatter.formatWeb(HRMessages.getMessage("payroll.Begin")) %>:</td>
			<td>
				<input onchange="javascript:activeAdd('begin')" type='text' name='begin' id='begin' value='<%= dto.getBegin() != null ? TextFormatter.getFormattedDate(dto.getBegin(),null) : "" %>'/>
				<input type='hidden' name='idPayroll' id='idPayroll' value='<%= dto.getIdPayroll() != null ? ""+dto.getIdPayroll().intValue() : "" %>'/>
			</td>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=TextFormatter.formatWeb(HRMessages.getMessage("payroll.End")) %>:</td>
			<td>
				<input type='text' name='end' id='end' value='<%= dto.getEnd() != null ? TextFormatter.getFormattedDate(dto.getEnd(),null) : "" %>'/>
			</td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=TextFormatter.formatWeb(HRMessages.getMessage("payroll.Days")) %>:</td>
			<td>
				<input type='text' name='days' id='days' value='<%= dto.getDays() != null ? ""+dto.getDays().intValue() : "" %>'/>
			</td>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"></td>
			<td></td>
			<td class="separator">&nbsp;</td>
		</tr>
	</table>
	<div id="detailsTable">
		<table align="center">
			<tr>
				<td class="separator">&nbsp;</td>
				<td class="labelaligment"><%=TextFormatter.formatWeb(HRMessages.getMessage("payroll.Subtotal")) %>:</td>
				<td class="labelaligment"><%=dto.getSubtotal() != null ? TextFormatter.getCurrencyValue(dto.getSubtotal()) : "$0.00" %></td>
				<td class="separator">&nbsp;</td>
				<td class="labelaligment"><%=TextFormatter.formatWeb(HRMessages.getMessage("payroll.Discount")) %>:</td>
				<td class="labelaligment"><%=dto.getDiscount() != null ? TextFormatter.getCurrencyValue(dto.getDiscount()) : "$0.00" %></td>
				<td class="separator">&nbsp;</td>
			</tr>
			<tr>
				<td class="separator">&nbsp;</td>
				<td class="labelaligment"><%=TextFormatter.formatWeb(HRMessages.getMessage("payroll.Deductions")) %>:</td>
				<td class="labelaligment"><%=dto.getDecutions() != null ? TextFormatter.getCurrencyValue(dto.getDecutions()) : "$0.00" %></td>
				<td class="separator">&nbsp;</td>
				<td class="labelaligment"><%=TextFormatter.formatWeb(HRMessages.getMessage("payroll.Total")) %>:</td>
				<td class="labelaligment"><%=dto.getTotal() != null ? TextFormatter.getCurrencyValue(dto.getTotal()) : "$0.00" %></td>
				<td class="separator">&nbsp;</td>
			</tr>
			<tr>
				<td class="separator">&nbsp;</td>
				<td colspan="5" class="titletext">&nbsp;</td>
				<td class="separator">&nbsp;</td>
			</tr>
			<tr>
				<td class="separator">&nbsp;</td>
				<td colspan="5" class="titletext"><%=TextFormatter.formatWeb(HRMessages.getMessage("payroll.Details")) %></td>
				<td class="separator">&nbsp;</td>
			</tr>
			<tr>
				<td class="separator">&nbsp;</td>
				<td colspan="5" class="titletext">&nbsp;</td>
				<td class="separator">&nbsp;</td>
			</tr>
			<tr>
				<td class="separator">&nbsp;</td>
				<td colspan="5" class="titletext">
					<table width="100%" class="resultsTable">
						<tr>
							<th><%=TextFormatter.formatWeb(HRMessages.getMessage("payroll.Employee")) %></th>
							<th><%=TextFormatter.formatWeb(HRMessages.getMessage("payroll.Salary")) %></th>
							<th><%=TextFormatter.formatWeb(HRMessages.getMessage("payroll.Discount")) %></th>
							<th><%=TextFormatter.formatWeb(HRMessages.getMessage("payroll.Deductions")) %></th>
							<th><%=TextFormatter.formatWeb(HRMessages.getMessage("payroll.Total")) %></th>
						</tr>
<% 	if (dto.getDetail()!=null && dto.getDetail().size() > 0){
		for (PayrollDetail row : dto.getDetail()){
%>					
						<tr>
							<td><%=TextFormatter.formatWeb(row.getEmployee().getLastName() + (row.getEmployee().getMiddleName() != null ? " "+row.getEmployee().getMiddleName(): "") + " " + row.getEmployee().getFirstName()) %></td>
							<td style="text-align: right"><%=row.getSalary() != null ? TextFormatter.getCurrencyValue(row.getSalary()) : "$0.00" %></td>
							<td style="text-align: right"><%=row.getDiscount() != null ? TextFormatter.getCurrencyValue(row.getDiscount()) : "$0.00" %></td>
							<td style="text-align: right"><%=row.getDeductions() != null ? TextFormatter.getCurrencyValue(row.getDeductions()) : "$0.00" %></td>
							<td style="text-align: right"><%=row.getTotal() != null ? TextFormatter.getCurrencyValue(row.getTotal()) : "$0.00" %></td>
						</tr>
<%
		}
	}
%>
					</table>
				</td>
				<td class="separator">&nbsp;</td>
			</tr>
			
			<tr>
				<td class="separator">&nbsp;</td>
				<td colspan="5" class="titletext">&nbsp;</td>
				<td class="separator">&nbsp;</td>
			</tr>
		</table>
	</div>
	<table align="center">
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
				<th><%=TextFormatter.formatWeb(HRMessages.getMessage("payroll.Begin")) %></th>
				<th><%=TextFormatter.formatWeb(HRMessages.getMessage("payroll.End")) %></th>
				<th><%=TextFormatter.formatWeb(HRMessages.getMessage("payroll.Days")) %></th>
				<th><%=TextFormatter.formatWeb(HRMessages.getMessage("payroll.Discount")) %></th>
				<th><%=TextFormatter.formatWeb(HRMessages.getMessage("payroll.Deductions")) %></th>
				<th><%=TextFormatter.formatWeb(HRMessages.getMessage("payroll.Total")) %></th>
			</tr>
<%		for (int a = 0;a < list.size();a++){ 
				Payroll payroll = (Payroll) list.get(a); %>
			<tr onclick="javascript:clearForm();document.payroll.idPayroll.value='<%=payroll.getIdPayroll()%>';document.payroll.action.value='search';sendData('payroll');">
				<td><%=payroll.getBegin() != null ? TextFormatter.getFormattedDate(payroll.getBegin(),null) : ""%></td>
				<td><%=payroll.getEnd() != null ? TextFormatter.getFormattedDate(payroll.getEnd(),null) : ""%></td>
				<td style="text-align: right"><%=payroll.getDays() != null ? payroll.getDays().intValue() : "0"%></td>
				<td style="text-align: right"><%=payroll.getDiscount() != null ? TextFormatter.getCurrencyValue(payroll.getDiscount()) : "$0.00"%></td>
				<td style="text-align: right"><%=payroll.getDecutions() != null ? TextFormatter.getCurrencyValue(payroll.getDecutions()) : "$0.00"%></td>
				<td style="text-align: right"><%=payroll.getTotal() != null ? TextFormatter.getCurrencyValue(payroll.getTotal()) : "$0.00"%></td>
			</tr>
<%		} %>
		</table>
<%	} %>
	</div>
</form>
<script>
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
		$("#begin").datepicker();
	});
	$(function () {
		$("#end").datepicker();
	});

	function clearForm(){
		document.payroll.idPayroll.value='';
		document.payroll.begin.value='';
		document.payroll.end.value='';
		document.payroll.days.value='';
		document.getElementById("detailsTable").style.visibility = "hidden";
		document.getElementById("resultsTable").style.visibility = "hidden";
		document.getElementById("addButton").style.visibility = "hidden";
		document.getElementById("searchButton").style.visibility = "visible";
		activeAdd('begin');
		hideUpdateDelete();
		document.payroll.idPayroll.focus();
	}
	
<%	if (error != null && !error.equalsIgnoreCase("")){ %>
	showError('<%=error%>');
<% 	} %>
<%	if (dto == null || dto.getIdPayroll() == null || dto.getIdPayroll().intValue() <= 0) {
		if (list == null || list.size() <= 0){ 
			if (error == null || error.equalsIgnoreCase("")){
%>
	clearForm();
<%			}
		} else { %>
	activeAdd("begin");
<%		} %>
		
	hideUpdateDelete();
<% 	} else { %>
	document.getElementById("addButton").style.visibility = "hidden";
	document.getElementById("searchButton").style.visibility = "hidden";
<%	} %>

</script>