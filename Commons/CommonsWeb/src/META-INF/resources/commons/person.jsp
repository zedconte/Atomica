<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.industrika.commons.i18n.CommonsMessages" %>
<%@page import="com.industrika.commons.dto.Person" %>
<%@page import="java.util.Vector" %>
<jsp:useBean id="dto" scope="request" class="com.industrika.commons.dto.Person"/>
<jsp:useBean id="list" scope="request" class="java.util.Vector"/>
<jsp:useBean id="message" scope="request" class="java.lang.String"/>
<jsp:useBean id="error" scope="request" class="java.lang.String"/>

<form name="person" id="person" class="normaltext">
	<input type="hidden" name="action" id="action"/>
	<input type="hidden" name="formName" id="formName" value="person"/>
	<table class="commandbuttons">
		<td class="separator">&nbsp;</td>
		<td><img class="resetbutton" src="images/reset.png" onclick="javascript:clearForm()"></td>
		<td class="separator">&nbsp;</td>
		<td><img class="addbutton" src="images/new.png" onclick="javascript:document.person.action.value='add';sendDataConfirm('person','Alta para la Persona')"></td>
		<td class="separator">&nbsp;</td>
		<td><img class="savebutton" src="images/save.png" onclick="javascript:document.person.action.value='update';sendDataConfirm('person','Actualizaci&oacute;n para la Persona')"></td>
		<td class="separator">&nbsp;</td>
		<td><img class="deletebutton" src="images/delete.png" onclick="javascript:document.person.action.value='remove';sendDataConfirm('person','Eliminado para la Persona')"></td>
		<td class="separator">&nbsp;</td>
		<td><img class="searchbutton" src="images/search.png" onclick="javascript:document.person.action.value='search';sendData('person')"></td>
		<td class="separator">&nbsp;</td>
	</table>
	<br/>
	<table align="center">
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext"><%=CommonsMessages.getMessage("personTitle") %></td>
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
			<td><input type='text' name='idPerson' id='idPerson' value='<%= dto.getIdPerson() != null ? ""+dto.getIdPerson().intValue() : "" %>'/></td>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=CommonsMessages.getMessage("firstName") %>:</td>
			<td><input type='text' name='firstName' id='firstName' value='<%=dto.getFirstName() != null ? dto.getFirstName() : ""%>'/></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=CommonsMessages.getMessage("lastName") %>:</td>
			<td><input type='text' name='lastName' id='lastName' value='<%=dto.getLastName() != null ? ""+dto.getLastName() : ""%>'/></td>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=CommonsMessages.getMessage("middleName") %>:</td>
			<td><input type='text' name='middleName' id='middleName' value='<%=dto.getMiddleName() != null ? dto.getMiddleName() : ""%>'/></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=CommonsMessages.getMessage("gender") %>:</td>
			<td>
				<select name="gender" id="gender">
					<option value="H" <%=(dto.getGender() != null &&  dto.getGender().equalsIgnoreCase("H")) ? "selected" : ""%>><%=CommonsMessages.getMessage("male") %></option>
					<option value="F" <%=(dto.getGender() != null &&  dto.getGender().equalsIgnoreCase("M")) ? "selected" : ""%>><%=CommonsMessages.getMessage("female") %></option>
				</select>
			</td>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=CommonsMessages.getMessage("email") %>:</td>
			<td><input type='text' name='email' id='email' value='<%=dto.getEmail() != null ? dto.getEmail() : ""%>'/></td>
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
			<th><%=CommonsMessages.getMessage("firstName") %></th>
			<th><%=CommonsMessages.getMessage("lastName") %></th>
			<th><%=CommonsMessages.getMessage("middleName") %></th>
		</tr>
<%		for (int a = 0;a < list.size();a++){ 
			Person person = (Person) list.get(a); %>
		<tr onclick="javascript:clearForm();document.person.idPerson.value='<%=person.getIdPerson()%>';document.person.action.value='search';sendData('person');">
			<td><%=person.getIdPerson()%></td>
			<td><%=person.getFirstName()%></td>
			<td><%=person.getLastName()%></td>
			<td><%=person.getMiddleName() != null ? person.getMiddleName() : ""%></td>
		</tr>
<%		} %>
	</table>
<%	} %>
</form>
<script>
	function clearForm(){
		document.person.idPerson.value='';
		document.person.firstName.value='';
		document.person.lastName.value='';
		document.person.middleName.value='';
		document.person.gender.selectedIndex=-1;
		document.person.email.value='';
		document.person.idPerson.focus();
	}
	
<%	if (error != null && !error.equalsIgnoreCase("")){ %>
	showError('<%=error%>');
<% 	} %>
<% if (dto.getGender() == null || dto.getGender().equalsIgnoreCase("")){ %>
	document.person.gender.selectedIndex = -1;
<%	} %>
</script>