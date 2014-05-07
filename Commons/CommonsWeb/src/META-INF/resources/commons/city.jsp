<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.industrika.commons.i18n.CommonsMessages" %>
<%@page import="com.industrika.commons.dto.City" %>
<%@page import="com.industrika.commons.dto.State" %>
<%@page import="com.industrika.commons.dao.StateDao" %>
<%@page import="com.industrika.commons.businesslogic.ApplicationContextProvider" %>
<%@page import="com.industrika.commons.utils.TextFormatter" %>
<%@page import="java.util.Vector" %>
<jsp:useBean id="dto" scope="request" class="com.industrika.commons.dto.City"/>
<jsp:useBean id="list" scope="request" class="java.util.Vector"/>
<jsp:useBean id="message" scope="request" class="java.lang.String"/>
<jsp:useBean id="error" scope="request" class="java.lang.String"/>

<form name="city" id="city" class="normaltext">
	<input type="hidden" name="action" id="action"/>
	<input type="hidden" name="formName" id="formName" value="city"/>
	<table class="commandbuttons">
		<tr>
			<td class="separator">&nbsp;</td>
			<td><img class="resetbutton" src="images/reset.png" onclick="javascript:clearForm()"></td>
			<td class="separator">&nbsp;</td>
			<td><div id="addButton"><img class="addbutton" src="images/new.png" onclick="javascript:document.city.action.value='add';sendDataConfirm('city','Alta para la Ciudad')"></div></td>
			<td class="separator">&nbsp;</td>
			<td><div id="updateButton"><img class="savebutton" src="images/save.png" onclick="javascript:document.city.action.value='update';sendDataConfirm('city','Actualizaci&oacute;n para la Ciudad')"></div></td>
			<td class="separator">&nbsp;</td>
			<td><div id="deleteButton"><img class="deletebutton" src="images/delete.png" onclick="javascript:document.city.action.value='remove';sendDataConfirm('city','Eliminado de la Ciudad')"></div></td>
			<td class="separator">&nbsp;</td>
			<td><img id="searchButton" class="searchbutton" src="images/search.png" onclick="javascript:document.city.action.value='search';sendData('city')"></td>
			<td class="separator">&nbsp;</td>
		</tr>
	</table>
	<br/>
	<table align="center">
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext"><%=CommonsMessages.getMessage("city.Title") %></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext">&nbsp;</td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=CommonsMessages.getMessage("city.Name") %>:</td>
			<td>
				<input onkeyup="javascript:activeAdd('name')" size="50" type='text' name='name' id='name' value='<%=dto.getName() != null ? TextFormatter.formatWeb(dto.getName()) : ""%>'/>
				<input type='hidden' name='idCity' id='idCity' value='<%= dto.getIdCity() != null ? ""+dto.getIdCity().intValue() : "" %>'/>
			</td>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=CommonsMessages.getMessage("city.ShortName") %>:</td>
			<td><input type='text' name='shortName' id='shortName' value='<%=dto.getShortName() != null ? TextFormatter.formatWeb(dto.getShortName()) : ""%>'/></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=CommonsMessages.getMessage("city.State") %>:</td>
			<td>
				<select name="idState" id="idState">
<%
	String[] order = {"name"};
	try{
		Vector<State> countries = new Vector<State>(((StateDao)ApplicationContextProvider.getCtx().getBean("stateDao")).find(new State(),order));
		if (countries != null && countries.size() > 0){
			for (State state : countries){
%>			
			<option value="<%=state.getIdState()%>" <%=(dto.getState()!=null && dto.getState().getIdState()!=null && dto.getState().getIdState().intValue()==state.getIdState().intValue()) ? "selected='selected'":""%>><%= TextFormatter.formatWeb(state.getName()) %></option>
<%
			}
		}
	}catch(Exception ex){ex.getMessage();}
%>
				</select>
			</td>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment">&nbsp;</td>
			<td>&nbsp;</td>
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
				<th><%=CommonsMessages.getMessage("city.Name") %></th>
				<th><%=CommonsMessages.getMessage("city.ShortName") %></th>
				<th><%=CommonsMessages.getMessage("city.State") %></th>
			</tr>
<%		for (int a = 0;a < list.size();a++){ 
				City city = (City) list.get(a); %>
			<tr onclick="javascript:clearForm();document.city.idCity.value='<%=city.getIdCity()%>';document.city.action.value='search';sendData('city');">
				<td><%=TextFormatter.formatWeb(city.getName())%></td>
				<td><%=city.getShortName() != null ? TextFormatter.formatWeb(city.getShortName()) : ""%></td>
				<td><%=city.getState() != null ? TextFormatter.formatWeb(city.getState().getName()) : ""%></td>
			</tr>
<%		} %>
		</table>
<%	} %>
	</div>
</form>
<script>
	function clearForm(){
		document.city.idCity.value='';
		document.city.name.value='';
		document.city.shortName.value='';
		document.city.idState.selectedIndex=-1;
		document.getElementById("resultsTable").style.visibility = "hidden";
		document.getElementById("addButton").style.visibility = "hidden";
		document.getElementById("searchButton").style.visibility = "visible";
		activeAdd('name');
		hideUpdateDelete();
		document.city.idCity.focus();
	}
	
<%	if (error != null && !error.equalsIgnoreCase("")){ %>
	showError('<%=error%>');
<% 	} %>
<%	if (dto == null || dto.getIdCity() == null || dto.getIdCity().intValue() <= 0) {
		if (list == null || list.size() <= 0){ 
			if (error == null || error.equalsIgnoreCase("")){
%>
	clearForm();
<%			}
		} else { %>
	document.city.idState.selectedIndex=-1;
	activeAdd("name");
<%		} %>
		
	hideUpdateDelete();
<% 	} else { %>
	document.getElementById("addButton").style.visibility = "hidden";
	document.getElementById("searchButton").style.visibility = "hidden";
<%	} %>

</script>