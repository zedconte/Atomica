<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.industrika.humanresources.i18n.HRMessages" %>
<%@page import="com.industrika.humanresources.dto.Absence" %>
<%@page import="com.industrika.humanresources.dto.Employee" %>
<%@page import="com.industrika.humanresources.dao.EmployeeDao" %>
<%@page import="com.industrika.commons.businesslogic.ApplicationContextProvider" %>
<%@page import="com.industrika.commons.utils.TextFormatter" %>
<%@page import="java.util.Vector" %>
<jsp:useBean id="dto" scope="request" class="com.industrika.humanresources.dto.Absence"/>
<jsp:useBean id="list" scope="request" class="java.util.Vector"/>
<jsp:useBean id="message" scope="request" class="java.lang.String"/>
<jsp:useBean id="error" scope="request" class="java.lang.String"/>

<script>
	$(function(){
		$("form[name=absence] #date").datepicker();
		var absencedate= $("form[name=absence] #date").val();
		if(absencedate.length===0){
			$("form[name=absence] #date").datepicker("setDate", new Date());
		}
	});
</script>
<form name="absence" id="absence" class="normaltext">
	<input type="hidden" name="action" id="action"/>
	<input type="hidden" name="formName" id="formName" value="absence"/>
	<table class="commandbuttons">
		<tr>
			<td class="separator">&nbsp;</td>
			<td><img class="resetbutton" src="images/reset.png" onclick="javascript:clearForm()"></td>
			<td class="separator">&nbsp;</td>
			<td><div id="addButton"><img class="addbutton" src="images/new.png" onclick="javascript:document.absence.action.value='add';sendDataConfirm('absence','Alta para el departamento')"></div></td>
			<td class="separator">&nbsp;</td>
			<td><div id="updateButton"><img class="savebutton" src="images/save.png" onclick="javascript:document.absence.action.value='update';sendDataConfirm('absence','Actualizaci&oacute;n del departamento')"></div></td>
			<td class="separator">&nbsp;</td>
			<td><div id="deleteButton"><img class="deletebutton" src="images/delete.png" onclick="javascript:document.absence.action.value='remove';sendDataConfirm('absence','Eliminado del departamento')"></div></td>
			<td class="separator">&nbsp;</td>
			<td><img id="searchButton" class="searchbutton" src="images/search.png" onclick="javascript:document.absence.action.value='search';sendData('absence')"></td>
			<td class="separator">&nbsp;</td>
		</tr>
	</table>
	<br/>
	<table align="center">
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext"><%=TextFormatter.formatWeb(HRMessages.getMessage("absence.Title")) %></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext">&nbsp;</td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=TextFormatter.formatWeb(HRMessages.getMessage("absence.Date")) %>:</td>
			<td>
				<input onchange="javascript:activeAdd('date')" type='text' name='date' id='date' value='<%= dto.getDate() != null ? TextFormatter.getFormattedDate(dto.getDate(),null) : "" %>'/>
				<input type='hidden' name='idAbsence' id='idAbsence' value='<%= dto.getIdAbsence() != null ? ""+dto.getIdAbsence().intValue() : "" %>'/>
			</td>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=TextFormatter.formatWeb(HRMessages.getMessage("absence.Employee")) %>:</td>
			<td>
				<select name="idEmployee" id="idEmployee" style="width: 100%">
<%
	String[] order = {"lastName","middleName","firstName"};
	try{
		Vector<Employee> employees = new Vector<Employee>(((EmployeeDao)ApplicationContextProvider.getCtx().getBean("employeeDao")).find(new Employee(),order));
		if (employees != null && employees.size() > 0){
			for (Employee employee : employees){
%>			
			<option value="<%=employee.getIdPerson()%>" <%=(dto.getEmployee()!=null && dto.getEmployee().getIdPerson()!=null && dto.getEmployee().getIdPerson().intValue()==employee.getIdPerson().intValue()) ? "selected='selected'":""%>><%= TextFormatter.formatWeb(employee.getLastName() + (employee.getMiddleName()!=null ? " "+employee.getMiddleName(): "") +" "+ employee.getFirstName()) %></option>
<%
			}
		}
	}catch(Exception ex){ex.getMessage();}
%>
				</select>
			</td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=TextFormatter.formatWeb(HRMessages.getMessage("absence.Justified")) %>:</td>
			<td>
				<select name="justified" id="justified" style="width: 100%">
					<option value="1" <%=(dto.getJustified() != null && dto.getJustified().intValue() == 1) ? "selected='selected'" : "" %> >S&iacute;</option>
					<option value="2" <%=(dto.getJustified() != null && dto.getJustified().intValue() == 2) ? "selected='selected'" : "" %>>No</option>
				</select>
			</td>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=TextFormatter.formatWeb(HRMessages.getMessage("absence.ApplyDiscount")) %>:</td>
			<td>
				<select name="applyDiscount" id="applyDiscount" style="width: 100%">
					<option value="1" <%=(dto.getApplyDiscount() != null && dto.getApplyDiscount().intValue() == 1) ? "selected='selected'" : "" %> >S&iacute;</option>
					<option value="2" <%=(dto.getApplyDiscount() != null && dto.getApplyDiscount().intValue() == 2) ? "selected='selected'" : "" %>>No</option>
				</select>
			</td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=TextFormatter.formatWeb(HRMessages.getMessage("absence.Reason")) %>:</td>
			<td colspan="4">
				<input type='text' size="80" name='reason' id='reason' value='<%= TextFormatter.formatWeb(dto.getReason()) %>'/>
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
				<th><%=TextFormatter.formatWeb(HRMessages.getMessage("absence.Date")) %></th>
				<th><%=TextFormatter.formatWeb(HRMessages.getMessage("absence.Employee")) %></th>
				<th><%=TextFormatter.formatWeb(HRMessages.getMessage("absence.Justified")) %></th>
				<th><%=TextFormatter.formatWeb(HRMessages.getMessage("absence.ApplyDiscount")) %></th>
			</tr>
<%		for (int a = 0;a < list.size();a++){ 
				Absence absence = (Absence) list.get(a); %>
			<tr onclick="javascript:clearForm();document.absence.idAbsence.value='<%=absence.getIdAbsence()%>';document.absence.action.value='search';sendData('absence');">
				<td><%=absence.getDate() != null ? TextFormatter.getFormattedDate(absence.getDate(),null) : ""%></td>
				<td><%=absence.getEmployee() != null ? TextFormatter.formatWeb(absence.getEmployee().getLastName()+(absence.getEmployee().getMiddleName()!=null ? " "+absence.getEmployee().getMiddleName(): "") +" "+ absence.getEmployee().getFirstName()) : ""%></td>
				<td><%=absence.getJustified() != null ? TextFormatter.formatWeb(absence.getJustified().intValue() == 1 ? "S�" : "No") : ""%></td>
				<td><%=absence.getApplyDiscount() != null ? TextFormatter.formatWeb(absence.getApplyDiscount().intValue() == 1 ? "S�" : "No") : ""%></td>
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
		$("#date").datepicker();
	});

	function clearForm(){
		document.absence.idAbsence.value='';
		document.absence.date.value='';
		document.absence.idEmployee.selectedIndex=-1;
		document.absence.justified.selectedIndex=-1;
		document.absence.applyDiscount.selectedIndex=-1;
		document.absence.reason.value='';
		document.getElementById("resultsTable").style.visibility = "hidden";
		document.getElementById("addButton").style.visibility = "hidden";
		document.getElementById("searchButton").style.visibility = "visible";
		activeAdd('date');
		hideUpdateDelete();
		document.absence.idAbsence.focus();
	}
	
<%	if (error != null && !error.equalsIgnoreCase("")){ %>
	showError('<%=error%>');
<% 	} %>
<%	if (dto == null || dto.getIdAbsence() == null || dto.getIdAbsence().intValue() <= 0) {
		if (list == null || list.size() <= 0){ 
			if (error == null || error.equalsIgnoreCase("")){
%>
	clearForm();
<%			}
		} else { %>
	document.absence.idEmployee.selectedIndex=-1;
	activeAdd("date");
<%		} %>
		
	hideUpdateDelete();
<% 	} else { %>
	document.getElementById("addButton").style.visibility = "hidden";
	document.getElementById("searchButton").style.visibility = "hidden";
<%	} %>

</script>