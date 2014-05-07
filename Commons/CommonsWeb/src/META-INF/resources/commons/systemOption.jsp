<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.industrika.commons.i18n.CommonsMessages" %>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.industrika.commons.dto.Module" %>
<%@page import="com.industrika.commons.dto.Option" %>
<%@page import="com.industrika.commons.view.SystemOptionDTO" %>
<%@page import="java.util.List" %>
<jsp:useBean id="dto" scope="request" class="com.industrika.commons.view.SystemOptionDTO"/>
<jsp:useBean id="list" scope="request" class="java.util.ArrayList"/>
<jsp:useBean id="message" scope="request" class="java.lang.String"/>
<jsp:useBean id="error" scope="request" class="java.lang.String"/>
<%SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"); %>
<form name="systemoption" id="systemoption" class="normaltext">
	<input type="hidden" name="action" id="action"/>
	<input type="hidden" name="formName" id="formName" value="systemoption"/>
	<table class="commandbuttons">
		<td class="separator">&nbsp;</td>
		<td><img class="resetbutton" src="images/reset.png" onclick="javascript:clearForm()"></td>
		<td class="separator">&nbsp;</td>
		<td><img class="addbutton" src="images/new.png" onclick="javascript:document.systemoption.action.value='add';sendDataConfirm('systemoption','Alta para la Opci&oacute;n del sistema')"></td>
		<td class="separator">&nbsp;</td>
		<td><img class="savebutton" src="images/save.png" onclick="javascript:document.systemoption.action.value='update';sendDataConfirm('systemoption','Actualizaci&oacute;n para la Opci&oacute;n del sistema')"></td>
		<td class="separator">&nbsp;</td>
		<td><img class="deletebutton" src="images/delete.png" onclick="javascript:document.systemoption.action.value='remove';sendDataConfirm('systemoption','Eliminado para la Opci&oacute;n del sistema')"></td>
		<td class="separator">&nbsp;</td>
		<td><img class="searchbutton" src="images/search.png" onclick="javascript:document.systemoption.action.value='search';sendData('systemoption')"></td>
		<td class="separator">&nbsp;</td>
	</table>
	<br/>
	<table align="center">
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext"><%=CommonsMessages.getMessage("optionTitle") %></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext">&nbsp;</td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment "><%=CommonsMessages.getMessage("id") %>:</td>
			<td><input type='text' name='id' id='id' value='<%= dto.getId() != null ? ""+dto.getId() : "" %>' readonly/></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=CommonsMessages.getMessage("resourcename") %>:</td>
			<td><input type='text' name='resourceName' id='resourceName' value='<%=dto.getResourceName() != null ? dto.getResourceName() : ""%>'/></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=CommonsMessages.getMessage("module") %>:</td>
			<td>
				<select name="module.id" id="module.id">
					<option value="" > <%=CommonsMessages.getMessage("select") %>
					</option>
<% for (int a = 0;a < dto.getListModule().size();a++){ 
		Module module = (Module) dto.getListModule().get(a);
		String selected= ((dto.getModule() != null && dto.getModule().equals(module))?"selected":"");%>
					<option value="<%=module.getId()%>" <%=selected%> >
						<%=module.getName()%>
					</option>
<% }%>
				</select>
			</td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=CommonsMessages.getMessage("creationdate") %>:</td>
			<td><input type='text' name='creationDate' id='creationDate' value='<%=dto.getCreationDate() != null ? sdf.format(dto.getCreationDate().getTime()) : ""%>' readonly/></td>
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
<% if (list != null && list.size()>0){ %>	
	<table class="resultsTable">
		<tr>
			<th><%=CommonsMessages.getMessage("id") %></th>
			<th><%=CommonsMessages.getMessage("resourcename") %></th>
			<th><%=CommonsMessages.getMessage("module") %></th>
			<th><%=CommonsMessages.getMessage("creationdate") %></th>
		</tr>
<%		for (int a = 0;a < list.size();a++){ 
			Option option = (Option) list.get(a); %>
		<tr onclick="javascript:clearForm();document.systemoption.id.value='<%=option.getId()%>';document.systemoption.action.value='search';sendData('systemoption');">
			<td><%=option.getId()%></td>
			<td><%=option.getResourceName()%></td>
			<td><%=option.getModule().getName()%></td>
			<td><%=option.getCreationDate() != null ? sdf.format(option.getCreationDate().getTime()) : ""%></td>
		</tr>
<%		} %>
	</table>
<%	} %>
</form>
<script>
	function clearForm(){
		document.systemoption.id.value='';
		document.systemoption.resourceName.value='';
		document.systemoption.creationDate.value='';
		document.systemoption['module.id'].value='';
		document.systemoption.resourceName.focus();
	}
	
<%	if (error != null && !error.equals("")){ %>
	showError('<%=error%>');
<% 	} %>
</script>