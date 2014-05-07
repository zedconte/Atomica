<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.industrika.shipping.i18n.ShippingMessages" %>
<%@page import="com.industrika.commons.utils.TextFormatter" %>
<%@page import="java.util.Vector" %>
<%@page import="com.industrika.shipping.dto.ShippingRoute" %>
<jsp:useBean id="list" scope="request" class="java.util.Vector"/>
<jsp:useBean id="dto" scope="request" class="com.industrika.shipping.dto.ShippingRoute"/>
<jsp:useBean id="message" scope="request" class="java.lang.String"/>
<jsp:useBean id="error" scope="request" class="java.lang.String"/>
<jsp:useBean id="action" scope="request" class="java.lang.String"/>

<form name="shippingroutes" id="shippingroutes" class="normaltext">
	<input type="hidden" name="action" id="action"/>
	<input type="hidden" name="formName" id="formName" value="shippingroutes"/>
	<br>
	<table class="commandbuttons">
		<tr>
			<td class="separator">&nbsp;</td>
			<td><img class="resetbutton" src="images/reset.png" onclick="javascript:clearForm()"></td>
			<td class="separator">&nbsp;</td>
			<td><div id="addButton"><img class="addbutton" src="images/new.png" onclick="javascript:document.shippingroutes.action.value='add';sendDataConfirm('shippingroutes','alta de la ruta')"></div></td>
			<td class="separator">&nbsp;</td>
			<td><div id="updateButton"><img class="savebutton" src="images/save.png" onclick="javascript:document.shippingroutes.action.value='update';sendDataConfirm('shippingroutes','actualización de la ruta')"></div></td>
			<td class="separator">&nbsp;</td>
			<td><div id="deleteButton"><img class="deletebutton" src="images/delete.png" onclick="javascript:document.shippingroutes.action.value='remove';sendDataConfirm('shippingroutes','eliminado de la ruta')"></div></td>
			<td class="separator">&nbsp;</td>
			<td><div id="searchButton"><img class="searchbutton" src="images/search.png" onclick="javascript:document.shippingroutes.action.value='search';sendData('shippingroutes')"></div></td>
			<td class="separator">&nbsp;</td>
		</tr>
	</table>
	<br>
	<table align="center">
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext"><%=TextFormatter.formatWeb(ShippingMessages.getMessage("shipping.Title")) %></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext">&nbsp;</td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td><input type='hidden' name='idRoute' id='idRoute' onkeyup="javascript:activeSearch('idRoute');" value='<%= dto.getIdRoute() != null ? ""+dto.getIdRoute().intValue() : "" %>'/></td>
			<td class="labelaligment"><%=TextFormatter.formatWeb(ShippingMessages.getMessage("route.Name")) %>:</td>
			<td><input type='text' name='name' id='name' onkeyup="javascript:activeAdd('name')" value='<%=dto.getName() != null ? TextFormatter.formatWeb(dto.getName()) : ""%>'/></td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=TextFormatter.formatWeb(ShippingMessages.getMessage("route.Acronym")) %>:</td>
			<td><input type='text' name='acronym' id='acronym' value='<%=dto.getAcronym() != null ? ""+TextFormatter.formatWeb(dto.getAcronym()) : ""%>'/></td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=TextFormatter.formatWeb(ShippingMessages.getMessage("route.Distance")) %>:</td>
			<td><input type='text' name='distance' id='distance' onblur="javascript:this.value=formatNumberWithoutSign(Number(this.value));" class="labelaligment numeric" value='<%=dto.getDistance() != null ? TextFormatter.getNumberValue(dto.getDistance()) : "0.00"%>'/></td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=TextFormatter.formatWeb(ShippingMessages.getMessage("route.Time")) %>:</td>
			<td><input type='text' name='time' id='time' onblur="javascript:this.value=formatNumberWithoutSign(Number(this.value));" class="labelaligment numeric" value='<%=dto.getTime() != null ? TextFormatter.getNumberValue(dto.getTime()) : "0.00"%>'/></td>
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
				<th><%=TextFormatter.formatWeb(ShippingMessages.getMessage("route.Name")) %></th>
				<th><%=TextFormatter.formatWeb(ShippingMessages.getMessage("route.Acronym")) %></th>
				<th><%=TextFormatter.formatWeb(ShippingMessages.getMessage("route.Distance")) %></th>
				<th><%=TextFormatter.formatWeb(ShippingMessages.getMessage("route.Time")) %></th>
			</tr>
<%		for (int a = 0;a < list.size();a++){ 
		ShippingRoute route = (ShippingRoute) list.get(a); %>
			<tr onclick="javascript:clearForm();document.shippingroutes.idRoute.value='<%=route.getIdRoute()%>';document.shippingroutes.action.value='search';sendData('shippingroutes');">
				<td><%=TextFormatter.formatWeb(route.getName())%></td>
				<td><%=route.getAcronym() != null ? TextFormatter.formatWeb(route.getAcronym()) : ""%></td>
				<td><%=route.getDistance() != null ? TextFormatter.getNumberValue(route.getDistance()) : ""%></td>
				<td><%=route.getTime() != null ? TextFormatter.getNumberValue(route.getTime()) : ""%></td>
			</tr>
<%		} %>
		</table>
<%	} %>
	</div>
</form>
<script>
function clearForm(){
	document.shippingroutes.idRoute.value='';
	document.shippingroutes.name.value='';
	document.shippingroutes.acronym.value='';
	document.shippingroutes.distance.value='';
	document.shippingroutes.time.value='';
	document.shippingroutes.idRoute.focus();
	activeAdd("name");
	document.getElementById("resultsTable").style.visibility = "hidden";
	document.getElementById("addButton").style.visibility = "hidden";
	document.getElementById("searchButton").style.visibility = "visible";
}

<%	if (error != null && !error.equalsIgnoreCase("")){ %>
	showError('<%=error%>');
<% 	} %>
<%	if (dto == null || dto.getIdRoute() == null || dto.getIdRoute().intValue() <= 0) {
	if (list == null || list.size() <= 0){ 
		if (error == null || error.equalsIgnoreCase("")){
%>
	clearForm();
<%			}
	} else { %>
	activeAdd("name");
<%		} %>
	
	hideUpdateDelete();
<% 	} else { %>
	document.getElementById("addButton").style.visibility = "hidden";
	document.getElementById("searchButton").style.visibility = "hidden";
<%	} %>

</script>
