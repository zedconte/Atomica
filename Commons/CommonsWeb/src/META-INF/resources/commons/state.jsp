<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.industrika.commons.i18n.CommonsMessages" %>
<%@page import="com.industrika.commons.dto.State" %>
<%@page import="com.industrika.commons.dto.Country" %>
<%@page import="com.industrika.commons.dao.CountryDao" %>
<%@page import="com.industrika.commons.businesslogic.ApplicationContextProvider" %>
<%@page import="com.industrika.commons.utils.TextFormatter" %>
<%@page import="java.util.Vector" %>
<jsp:useBean id="dto" scope="request" class="com.industrika.commons.dto.State"/>
<jsp:useBean id="list" scope="request" class="java.util.Vector"/>
<jsp:useBean id="message" scope="request" class="java.lang.String"/>
<jsp:useBean id="error" scope="request" class="java.lang.String"/>

<form name="state" id="state" class="normaltext">
	<input type="hidden" name="action" id="action"/>
	<input type="hidden" name="formName" id="formName" value="state"/>
	<table class="commandbuttons">
		<tr>
			<td class="separator">&nbsp;</td>
			<td><img class="resetbutton" src="images/reset.png" onclick="javascript:clearForm()"></td>
			<td class="separator">&nbsp;</td>
			<td><div id="addButton"><img class="addbutton" src="images/new.png" onclick="javascript:document.state.action.value='add';sendDataConfirm('state','Alta para el Estados')"></div></td>
			<td class="separator">&nbsp;</td>
			<td><div id="updateButton"><img class="savebutton" src="images/save.png" onclick="javascript:document.state.action.value='update';sendDataConfirm('state','Actualizaci&oacute;n para el Estado')"></div></td>
			<td class="separator">&nbsp;</td>
			<td><div id="deleteButton"><img class="deletebutton" src="images/delete.png" onclick="javascript:document.state.action.value='remove';sendDataConfirm('state','Eliminado del estado')"></div></td>
			<td class="separator">&nbsp;</td>
			<td><div id="searchButton"><img class="searchbutton" src="images/search.png" onclick="javascript:document.state.action.value='search';sendData('state')"></div></td>
			<td class="separator">&nbsp;</td>
		</tr>
	</table>
	<br/>
	<table align="center">
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext"><%=CommonsMessages.getMessage("state.Title") %></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext">&nbsp;</td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=CommonsMessages.getMessage("state.Name") %>:</td>
			<td>
				<input onkeyup="javascript:activeAdd('name')" size="50" type='text' name='name' id='name' value='<%=dto.getName() != null ? TextFormatter.formatWeb(dto.getName()) : ""%>'/>
				<input type='hidden' name='idState' id='idState' value='<%= dto.getIdState() != null ? ""+dto.getIdState().intValue() : "" %>'/>
			</td>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=CommonsMessages.getMessage("state.ShortName") %>:</td>
			<td><input type='text' name='shortName' id='shortName' value='<%=dto.getShortName() != null ? TextFormatter.formatWeb(dto.getShortName()) : ""%>'/></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=CommonsMessages.getMessage("state.Country") %>:</td>
			<td>
				<select name="idCountry" id="idCountry">
<%
	String[] order = {"name"};
	try{
		Vector<Country> countries = new Vector<Country>(((CountryDao)ApplicationContextProvider.getCtx().getBean("countryDao")).find(new Country(),order));
		if (countries != null && countries.size() > 0){
			for (Country country : countries){
%>			
			<option value="<%=country.getIdCountry()%>" <%=(dto.getCountry()!=null && dto.getCountry().getIdCountry()!=null && dto.getCountry().getIdCountry().intValue()==country.getIdCountry().intValue()) ? "selected='selected'":""%>><%= TextFormatter.formatWeb(country.getName()) %></option>
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
				<th><%=CommonsMessages.getMessage("state.Name") %></th>
				<th><%=CommonsMessages.getMessage("state.ShortName") %></th>
				<th><%=CommonsMessages.getMessage("state.Country") %></th>
			</tr>
<%		for (int a = 0;a < list.size();a++){ 
				State state = (State) list.get(a); %>
			<tr onclick="javascript:clearForm();document.state.idState.value='<%=state.getIdState()%>';document.state.action.value='search';sendData('state');">
				<td><%=TextFormatter.formatWeb(state.getName())%></td>
				<td><%=state.getShortName() != null ? TextFormatter.formatWeb(state.getShortName()) : ""%></td>
				<td><%=state.getCountry() != null ? TextFormatter.formatWeb(state.getCountry().getName()) : ""%></td>
			</tr>
<%		} %>
		</table>
<%	} %>
	</div>
</form>
<script>
	function clearForm(){
		document.state.idState.value='';
		document.state.name.value='';
		document.state.shortName.value='';
		document.state.idCountry.selectedIndex=-1;
		document.getElementById("resultsTable").style.visibility = "hidden";
		document.getElementById("addButton").style.visibility = "hidden";
		document.getElementById("searchButton").style.visibility = "visible";	
		activeAdd('name');
		document.state.idState.focus();
		hideUpdateDelete();
	}
	
<%	if (error != null && !error.equalsIgnoreCase("")){ %>
	showError('<%=error%>');
<% 	} %>
<%	if (dto == null || dto.getIdState() == null || dto.getIdState().intValue() <= 0) {
		if (list == null || list.size() <= 0){ 
			if (error == null || error.equalsIgnoreCase("")){
%>
	clearForm();
<%			}
		} else { %>
	document.state.idCountry.selectedIndex=-1;
	activeAdd("name");
<%		} %>
		
	hideUpdateDelete();
<% 	} else { %>
	document.getElementById("addButton").style.visibility = "hidden";
	document.getElementById("searchButton").style.visibility = "hidden";
<%	} %>

</script>