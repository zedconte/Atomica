<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.industrika.commons.i18n.CommonsMessages" %>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.industrika.commons.dto.Action" %>
<%@page import="com.industrika.commons.dto.Option" %>
<%@page import="com.industrika.commons.dto.Privilege" %>
<%@page import="com.industrika.commons.view.SystemPrivilegeDTO" %>
<%@page import="com.industrika.commons.utils.TextFormatter" %>
<%@page import="java.util.List" %>
<jsp:useBean id="dto" scope="request" class="com.industrika.commons.view.SystemPrivilegeDTO"/>
<jsp:useBean id="list" scope="request" class="java.util.ArrayList"/>
<jsp:useBean id="message" scope="request" class="java.lang.String"/>
<jsp:useBean id="error" scope="request" class="java.lang.String"/>

<form name="systemprivilege" id="systemprivilege" class="normaltext">
	<input type="hidden" name="action" id="action"/>
	<input type="hidden" name="formName" id="formName" value="systemprivilege"/>
	<table class="commandbuttons">
		<td class="separator">&nbsp;</td>
		<td><img class="resetbutton" src="images/reset.png" onclick="javascript:clearForm()"></td>
		<td class="separator">&nbsp;</td>
		<td><div id="addButton"><img class="addbutton" src="images/new.png" onclick="javascript:document.systemprivilege.action.value='add';sendDataConfirm('systemprivilege','Alta para el privilegio de sistema')"></div></td>
		<td class="separator">&nbsp;</td>
		<td><div id="updateButton"><img class="savebutton" src="images/save.png" onclick="javascript:document.systemprivilege.action.value='update';sendDataConfirm('systemprivilege','Actualizaci&oacute;n para el privilegio de sistema')"></div></td>
		<td class="separator">&nbsp;</td>
		<td><div id="deleteButton"><img class="deletebutton" src="images/delete.png" onclick="javascript:document.systemprivilege.action.value='remove';sendDataConfirm('systemprivilege','Eliminado para el privilegio de sistema')"></div></td>
		<td class="separator">&nbsp;</td>
		<td><div id="searchButton"><img class="searchbutton" src="images/search.png" onclick="javascript:document.systemprivilege.action.value='search';sendData('systemprivilege')"></div></td>
		<td class="separator">&nbsp;</td>
	</table>
	<br/>
	<table align="center">
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext"><%=CommonsMessages.getMessage("privilegeTitle") %></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext"><div style="visibility: hidden"><input type='text' name='id' id='id' value='<%= dto.getId() != null ? ""+dto.getId() : "" %>'/></div></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=CommonsMessages.getMessage("privilegeName") %>:</td>
			<td><input type='text' name='name' id='name' onkeyup="javascript:activeAdd('name')" value='<%=dto.getName() != null ? TextFormatter.formatWeb(dto.getName()) : ""%>'/></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=CommonsMessages.getMessage("action") %>:</td>
			<td>
				<select name="action.id" id="action.id">
					<option value="" > <%=CommonsMessages.getMessage("select") %>
					</option>
<% for (int i = 0;i < dto.getListActions().size();i++){
		Action act= (Action) dto.getListActions().get(i);
		String selected= ((dto.getAction() != null && dto.getAction().equals(act))?"selected":"");%>
					<option value="<%=act.getId()%>" <%=selected%> >
						<%=act.getType()%>
					</option>
<% }%>
				</select>
			</td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=CommonsMessages.getMessage("option") %>:</td>
			<td>
				<select name="option.id" id="option.id">
					<option value="" > <%=CommonsMessages.getMessage("select") %>
					</option>
<% for (int i = 0;i < dto.getListOptions().size();i++){
		Option opt= (Option) dto.getListOptions().get(i);
		String selected= ((dto.getOption() != null && dto.getOption().equals(opt))?"selected":"");%>
					<option value="<%=opt.getId()%>" <%=selected%> >
						<%=TextFormatter.formatWeb(opt.getText())%>
					</option>
<% }%>
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
				<th><%=CommonsMessages.getMessage("privilegeName") %></th>
				<th><%=CommonsMessages.getMessage("action") %></th>
				<th><%=CommonsMessages.getMessage("option") %></th>
			</tr>
<%		for (int i = 0;i < list.size();i++){ 
			Privilege pr= (Privilege) list.get(i); %>
			<tr onclick="javascript:clearForm();document.systemprivilege.id.value='<%=pr.getId()%>';document.systemprivilege.action.value='search';sendData('systemprivilege');">
				<td><%=TextFormatter.formatWeb(pr.getName())%></td>
				<td><%=TextFormatter.formatWeb(pr.getActionType())%></td>
				<td><%=TextFormatter.formatWeb(pr.getOption().getText())%></td>
			</tr>
<%		} %>
		</table>
<%	} %>
	</div>
</form>
<script>
	function clearForm(){
		document.systemprivilege.id.value='';
		document.systemprivilege.name.value='';
		document.systemprivilege['action.id'].value='';
		document.systemprivilege['option.id'].value='';
		document.getElementById("resultsTable").style.visibility = "hidden";
		document.getElementById("addButton").style.visibility = "hidden";
		document.getElementById("searchButton").style.visibility = "visible";
		activeAdd('name');
		hideUpdateDelete();
		document.systemprivilege.name.focus();
	}

<%	if (error != null && !error.equals("")){ %>
	showError('<%=error%>');
<% 	} %>
<%	if (dto == null || dto.getId() == null || dto.getId().intValue() <= 0) {
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