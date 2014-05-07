<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.industrika.humanresources.i18n.HRMessages" %>
<%@page import="com.industrika.humanresources.dto.Department" %>
<%@page import="com.industrika.humanresources.dto.Management" %>
<%@page import="com.industrika.humanresources.dao.ManagementDao" %>
<%@page import="com.industrika.commons.businesslogic.ApplicationContextProvider" %>
<%@page import="com.industrika.commons.utils.TextFormatter" %>
<%@page import="java.util.Vector" %>
<jsp:useBean id="dto" scope="request" class="com.industrika.humanresources.dto.Department"/>
<jsp:useBean id="list" scope="request" class="java.util.Vector"/>
<jsp:useBean id="message" scope="request" class="java.lang.String"/>
<jsp:useBean id="error" scope="request" class="java.lang.String"/>

<form name="department" id="department" class="normaltext">
	<input type="hidden" name="action" id="action"/>
	<input type="hidden" name="formName" id="formName" value="department"/>
	<table class="commandbuttons">
		<tr>
			<td class="separator">&nbsp;</td>
			<td><img class="resetbutton" src="images/reset.png" onclick="javascript:clearForm()"></td>
			<td class="separator">&nbsp;</td>
			<td><div id="addButton"><img class="addbutton" src="images/new.png" onclick="javascript:document.department.action.value='add';sendDataConfirm('department','Alta para el departamento')"></div></td>
			<td class="separator">&nbsp;</td>
			<td><div id="updateButton"><img class="savebutton" src="images/save.png" onclick="javascript:document.department.action.value='update';sendDataConfirm('department','Actualizaci&oacute;n del departamento')"></div></td>
			<td class="separator">&nbsp;</td>
			<td><div id="deleteButton"><img class="deletebutton" src="images/delete.png" onclick="javascript:document.department.action.value='remove';sendDataConfirm('department','Eliminado del departamento')"></div></td>
			<td class="separator">&nbsp;</td>
			<td><img id="searchButton" class="searchbutton" src="images/search.png" onclick="javascript:document.department.action.value='search';sendData('department')"></td>
			<td class="separator">&nbsp;</td>
		</tr>
	</table>
	<br/>
	<table align="center">
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext"><%=TextFormatter.formatWeb(HRMessages.getMessage("department.Title")) %></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext">&nbsp;</td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=TextFormatter.formatWeb(HRMessages.getMessage("department.Name")) %>:</td>
			<td>
				<input onkeyup="javascript:activeAdd('name')" size="50" type='text' name='name' id='name' value='<%=dto.getName() != null ? TextFormatter.formatWeb(dto.getName()) : ""%>'/>
				<input type='hidden' name='idDepartment' id='idDepartment' value='<%= dto.getIdDepartment() != null ? ""+dto.getIdDepartment().intValue() : "" %>'/>
			</td>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=TextFormatter.formatWeb(HRMessages.getMessage("department.Management")) %>:</td>
			<td>
				<select name="idManagement" id="idManagement">
<%
	String[] order = {"name"};
	try{
		Vector<Management> managements = new Vector<Management>(((ManagementDao)ApplicationContextProvider.getCtx().getBean("managementDao")).find(new Management(),order));
		if (managements != null && managements.size() > 0){
			for (Management management : managements){
%>			
			<option value="<%=management.getIdManagement()%>" <%=(dto.getManagement()!=null && dto.getManagement().getIdManagement()!=null && dto.getManagement().getIdManagement().intValue()==management.getIdManagement().intValue()) ? "selected='selected'":""%>><%= TextFormatter.formatWeb(management.getName()) %></option>
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
				<th><%=TextFormatter.formatWeb(HRMessages.getMessage("department.Name")) %></th>
				<th><%=TextFormatter.formatWeb(HRMessages.getMessage("department.Management")) %></th>
			</tr>
<%		for (int a = 0;a < list.size();a++){ 
				Department department = (Department) list.get(a); %>
			<tr onclick="javascript:clearForm();document.department.idDepartment.value='<%=department.getIdDepartment()%>';document.department.action.value='search';sendData('department');">
				<td><%=TextFormatter.formatWeb(department.getName())%></td>
				<td><%=department.getManagement() != null ? TextFormatter.formatWeb(department.getManagement().getName()) : ""%></td>
			</tr>
<%		} %>
		</table>
<%	} %>
	</div>
</form>
<script>
	function clearForm(){
		document.department.idDepartment.value='';
		document.department.name.value='';
		document.department.idManagement.selectedIndex=-1;
		document.getElementById("resultsTable").style.visibility = "hidden";
		document.getElementById("addButton").style.visibility = "hidden";
		document.getElementById("searchButton").style.visibility = "visible";
		activeAdd('name');
		hideUpdateDelete();
		document.department.idDepartment.focus();
	}
	
<%	if (error != null && !error.equalsIgnoreCase("")){ %>
	showError('<%=error%>');
<% 	} %>
<%	if (dto == null || dto.getIdDepartment() == null || dto.getIdDepartment().intValue() <= 0) {
		if (list == null || list.size() <= 0){ 
			if (error == null || error.equalsIgnoreCase("")){
%>
	clearForm();
<%			}
		} else { %>
	document.department.idManagement.selectedIndex=-1;
	activeAdd("name");
<%		} %>
		
	hideUpdateDelete();
<% 	} else { %>
	document.getElementById("addButton").style.visibility = "hidden";
	document.getElementById("searchButton").style.visibility = "hidden";
<%	} %>

</script>