<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.industrika.humanresources.i18n.HRMessages" %>
<%@page import="com.industrika.humanresources.dto.Management" %>
<%@page import="com.industrika.humanresources.dto.Direction" %>
<%@page import="com.industrika.humanresources.dao.DirectionDao" %>
<%@page import="com.industrika.commons.businesslogic.ApplicationContextProvider" %>
<%@page import="com.industrika.commons.utils.TextFormatter" %>
<%@page import="java.util.Vector" %>
<jsp:useBean id="dto" scope="request" class="com.industrika.humanresources.dto.Management"/>
<jsp:useBean id="list" scope="request" class="java.util.Vector"/>
<jsp:useBean id="message" scope="request" class="java.lang.String"/>
<jsp:useBean id="error" scope="request" class="java.lang.String"/>

<form name="management" id="management" class="normaltext">
	<input type="hidden" name="action" id="action"/>
	<input type="hidden" name="formName" id="formName" value="management"/>
	<table class="commandbuttons">
		<tr>
			<td class="separator">&nbsp;</td>
			<td><img class="resetbutton" src="images/reset.png" onclick="javascript:clearForm()"></td>
			<td class="separator">&nbsp;</td>
			<td><div id="addButton"><img class="addbutton" src="images/new.png" onclick="javascript:document.management.action.value='add';sendDataConfirm('management','Alta para la gerencia')"></div></td>
			<td class="separator">&nbsp;</td>
			<td><div id="updateButton"><img class="savebutton" src="images/save.png" onclick="javascript:document.management.action.value='update';sendDataConfirm('management','Actualizaci&oacute;n para la gerencia')"></div></td>
			<td class="separator">&nbsp;</td>
			<td><div id="deleteButton"><img class="deletebutton" src="images/delete.png" onclick="javascript:document.management.action.value='remove';sendDataConfirm('management','Eliminado de la gerencia')"></div></td>
			<td class="separator">&nbsp;</td>
			<td><img id="searchButton" class="searchbutton" src="images/search.png" onclick="javascript:document.management.action.value='search';sendData('management')"></td>
			<td class="separator">&nbsp;</td>
		</tr>
	</table>
	<br/>
	<table align="center">
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext"><%=TextFormatter.formatWeb(HRMessages.getMessage("management.Title")) %></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext">&nbsp;</td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=TextFormatter.formatWeb(HRMessages.getMessage("management.Name")) %>:</td>
			<td>
				<input onkeyup="javascript:activeAdd('name')" type='text' size="50" name='name' id='name' value='<%=dto.getName() != null ? TextFormatter.formatWeb(dto.getName()) : ""%>'/>
				<input type='hidden' name='idManagement' id='idManagement' value='<%= dto.getIdManagement() != null ? ""+dto.getIdManagement().intValue() : "" %>'/>
			</td>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=TextFormatter.formatWeb(HRMessages.getMessage("management.Direction")) %>:</td>
			<td>
				<select name="idDirection" id="idDirection">
<%
	String[] order = {"name"};
	try{
		Vector<Direction> directions = new Vector<Direction>(((DirectionDao)ApplicationContextProvider.getCtx().getBean("directionDao")).find(new Direction(),order));
		if (directions != null && directions.size() > 0){
			for (Direction direction : directions){
%>			
			<option value="<%=direction.getIdDirection()%>" <%=(dto.getDirection()!=null && dto.getDirection().getIdDirection()!=null && dto.getDirection().getIdDirection().intValue()==direction.getIdDirection().intValue()) ? "selected='selected'":""%>><%= TextFormatter.formatWeb(direction.getName()) %></option>
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
				<th><%=TextFormatter.formatWeb(HRMessages.getMessage("management.Name")) %></th>
				<th><%=TextFormatter.formatWeb(HRMessages.getMessage("management.Direction")) %></th>
			</tr>
<%		for (int a = 0;a < list.size();a++){ 
				Management management = (Management) list.get(a); %>
			<tr onclick="javascript:clearForm();document.management.idManagement.value='<%=management.getIdManagement()%>';document.management.action.value='search';sendData('management');">
				<td><%=TextFormatter.formatWeb(management.getName())%></td>
				<td><%=management.getDirection() != null ? TextFormatter.formatWeb(management.getDirection().getName()) : ""%></td>
			</tr>
<%		} %>
		</table>
<%	} %>
	</div>
</form>
<script>
	function clearForm(){
		document.management.idManagement.value='';
		document.management.name.value='';
		document.management.idDirection.selectedIndex=-1;
		document.getElementById("resultsTable").style.visibility = "hidden";
		document.getElementById("addButton").style.visibility = "hidden";
		document.getElementById("searchButton").style.visibility = "visible";
		activeAdd('name');
		hideUpdateDelete();
		document.management.idManagement.focus();
	}
	
<%	if (error != null && !error.equalsIgnoreCase("")){ %>
	showError('<%=error%>');
<% 	} %>
<%	if (dto == null || dto.getIdManagement() == null || dto.getIdManagement().intValue() <= 0) {
		if (list == null || list.size() <= 0){ 
			if (error == null || error.equalsIgnoreCase("")){
%>
	clearForm();
<%			}
		} else { %>
	document.management.idDirection.selectedIndex=-1;
	activeAdd("name");
<%		} %>
		
	hideUpdateDelete();
<% 	} else { %>
	document.getElementById("addButton").style.visibility = "hidden";
	document.getElementById("searchButton").style.visibility = "hidden";
<%	} %>

</script>