<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.industrika.commons.i18n.CommonsMessages" %>
<%@page import="com.industrika.commons.dto.Branch" %>
<%@page import="com.industrika.commons.dto.Phone" %>
<%@page import="com.industrika.commons.utils.TextFormatter" %>
<%@page import="com.industrika.commons.dto.City" %>
<%@page import="com.industrika.commons.dao.CityDao" %>
<%@page import="com.industrika.commons.businesslogic.ApplicationContextProvider" %>
<%@page import="java.util.Vector" %>

<jsp:useBean id="dto" scope="request" class="com.industrika.commons.dto.Branch"/>
<jsp:useBean id="message" scope="request" class="java.lang.String"/>
<jsp:useBean id="list" scope="request" class="java.util.Vector"/>
<jsp:useBean id="error" scope="request" class="java.lang.String"/>

<form name="branch" id="branch" class="normalText">
	<input type="hidden" name="action" id="action"/>
	<input type="hidden" name="formName" id="formName" value="branch"/>
	<input type="hidden" name="address.idAddress" id="address.idAddress" value="<%= dto.getAddress() != null && dto.getAddress().getIdAddress() != null ? "" + dto.getAddress().getIdAddress().intValue() : "" %>">
	
	<table class="commandbuttons">
		<tr>
			<td class="separator">&nbsp;</td>
			<td><img class="resetbutton" src="images/reset.png"></td>
			<td class="separator">&nbsp;</td>
			<td><div id="addButton"><img class="addbutton" src="images/new.png"></div></td>
			<td class="separator">&nbsp;</td>
			<td><div id="updateButton"><img class="savebutton" src="images/save.png"></div></td>
			<td class="separator">&nbsp;</td>
			<td><div id="deleteButton"><img class="deletebutton" src="images/delete.png"></div></td>
			<td class="separator">&nbsp;</td>
			<td><div id="searchButton"><img class="searchbutton" src="images/search.png"></div></td>
			<td class="separator">&nbsp;</td>
		</tr>		
	</table>
	<table align="center">		
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="2" class="titletext"><%=CommonsMessages.getMessage("branch.Title") %></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="2" class="titletext"><div style="visibility: hidden">
				<input type='text' name='idBranch' id='idBranch' readonly="readonly" value='<%= dto.getIdBranch() != null ? ""+dto.getIdBranch().intValue() : "" %>'/>
				<input type="text" name="idaddress" id="idaddress" value="<%=(dto.getAddress()!=null && dto.getAddress().getIdAddress()!=null) ? ""+dto.getAddress().getIdAddress() : ""%>"/>
			</div></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td valign="top" style="vertical-align: top;">
				<table>
					<tr>
						<td class="separator"></td>
						<td class="labelaligment"><%=CommonsMessages.getMessage("branch.Name") %>:</td>
						<td>
							<input type='text' name='name' id='name' size="50" onkeyup="javascript:activeAdd('name')" value='<%=dto.getName() != null ? TextFormatter.formatWeb(dto.getName()) : ""%>'/>
						</td>
						<td class="separator">&nbsp;</td>
					</tr>
					<tr>
						<td class="separator">&nbsp;</td>
						<td class="labelaligment"><%=CommonsMessages.getMessage("branch.responsibleName") %>:</td>			
						<td><input type='text' name='responsibleName' id='responsibleName' value='<%=dto.getResponsibleName() != null ? TextFormatter.formatWeb(dto.getResponsibleName()) : ""%>'/></td>
						<td class="separator">&nbsp;</td>
					</tr>
					<tr>
						<td class="separator">&nbsp;</td>
						<td class="labelaligment "><%=CommonsMessages.getMessage("street") %>:</td>
						<td><input type='text' name='street' id='street' value='<%= dto.getAddress() != null && dto.getAddress().getStreet() != null ? "" + TextFormatter.formatWeb(dto.getAddress().getStreet()) : "" %>'/></td>							
						<td class="separator">&nbsp;</td>
					</tr>
					<tr>
						<td class="separator">&nbsp;</td>
						<td class="labelaligment "><%=CommonsMessages.getMessage("city") %>:</td>
						<td>
							<select name="city" id="city">
<%
	String[] order = {"name"};
	try{
		Vector<City> cities = new Vector<City>(((CityDao)ApplicationContextProvider.getCtx().getBean("cityDao")).find(new City(),order));
		if (cities != null && cities.size() > 0){
			for (City city : cities){
%>			
								<option value="<%=city.getIdCity()%>" <%=(dto.getAddress()!=null && dto.getAddress().getCity()!=null && dto.getAddress().getCity().getIdCity()!=null && dto.getAddress().getCity().getIdCity().intValue()==city.getIdCity().intValue()) ? "selected='selected'":""%>><%= TextFormatter.formatWeb(city.getName()) %></option>
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
						<td class="separator" rowspan="2">&nbsp;</td>
					</tr>
				</table>
			</td>
			<td >
				<table>
					<tr>
						<td class="separator">&nbsp;</td>
						<td class="labelaligment "><%=CommonsMessages.getMessage("intNumber") %>:</td>
						<td><input type='text' name='intNumber' id='intNumber' value='<%= dto.getAddress() != null && dto.getAddress().getIntNumber() != null ? "" + dto.getAddress().getIntNumber() : "" %>'/></td>							
						<td class="separator">&nbsp;</td>
					</tr>
					<tr>
						<td class="separator">&nbsp;</td>
						<td class="labelaligment "><%=CommonsMessages.getMessage("extNumber") %>:</td>
						<td><input type='text' name='extNumber' id='extNumber' value='<%= dto.getAddress() != null && dto.getAddress().getExtNumber() != null ? "" + dto.getAddress().getExtNumber() : "" %>'/></td>							
						<td class="separator">&nbsp;</td>
					</tr>
					<tr>
						<td class="separator">&nbsp;</td>
						<td class="labelaligment "><%=CommonsMessages.getMessage("suburb") %>:</td>
						<td><input type='text' name='suburb' id='suburb' value='<%= dto.getAddress() != null && dto.getAddress().getSuburb()!= null ? "" + TextFormatter.formatWeb(dto.getAddress().getSuburb()) : "" %>'/></td>
						<td class="separator">&nbsp;</td>
					</tr>
					<tr>
						<td class="separator">&nbsp;</td>
						<td class="labelaligment "><%=CommonsMessages.getMessage("zipCode") %>:</td>
						<td><input type='text' name='zipCode' id='zipCode' value='<%= dto.getAddress() != null && dto.getAddress().getZipCode()!= null ? "" + dto.getAddress().getZipCode() : "" %>'/></td>
						<td class="separator">&nbsp;</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="2" class="titletext">&nbsp;</td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td></td>
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
	<hr/>
	<div id="resultsTable">
<% if (list != null && list.size()>0){ %>	
	<table class="resultsTable" id="branchesList">
		<tr>
			<th><%=CommonsMessages.getMessage("branch.Name") %></th>
			<th><%=CommonsMessages.getMessage("branch.responsibleName") %></th>
		</tr>
<%		for (int a = 0;a < list.size();a++){
			Branch branch = (Branch) list.get(a); 
%>
		<tr data-id="<%=branch.getIdBranch()%>">
			<td><%= TextFormatter.formatWeb(branch.getName())%></td>
			<td><%= TextFormatter.formatWeb(branch.getResponsibleName())%></td>
		</tr>
<%		} %>
	</table>
<%	} %>
	</div>
</form>
<script>
	var catalogName="Sucursal";
	var formNameInJsp = "branch";
	
	$(function(){
		
		var actionField = $("#" + formNameInJsp + " #action");		
		
		$(".commandbuttons .resetbutton").click(function(){
			clearForm();
		});		
		
		$(".commandbuttons .addbutton").click(function(){
			actionField.val("add");
			sendDataConfirm(formNameInJsp,'Alta para '+ catalogName)
		});
		
		$(".commandbuttons .savebutton").click(function(){
			actionField.val("update");
			sendDataConfirm(formNameInJsp,'Actualizaci&oacute;n para '+ catalogName)
		});
		
		$(".commandbuttons .deletebutton").click(function(){
			actionField.val("remove");						
			sendDataConfirm(formNameInJsp,'Eliminado para '+ catalogName);
		});
		
		$(".commandbuttons .searchbutton").click(function(){
			actionField.val("search");
			sendData(formNameInJsp);			
		});
		
		$("#branchesList tr").click(function(){
			clearForm();
			$("#idBranch").val($(this).data("id"));
			actionField.val("search");
			sendData(formNameInJsp);			
		});
		
	});

	function clearForm(){
		$("#" + formNameInJsp + " input[type!='hidden']").each(function(){
			this.value='';
		});
		document.branch.city.selectedIndex=-1;
		document.getElementById("resultsTable").style.visibility = "hidden";
		document.getElementById("addButton").style.visibility = "hidden";
		document.getElementById("searchButton").style.visibility = "visible";
		activeAdd('name');
		hideUpdateDelete();		
		
		$("#" + formNameInJsp + " #name").focus();
	}
<% 	if (dto.getAddress()==null || dto.getAddress().getCity() == null || dto.getAddress().getCity().getIdCity() == null || dto.getAddress().getCity().getIdCity().intValue() <= 0){%>
	document.branch.city.selectedIndex=-1;
<%	}%>
	
<%	if (error != null && !error.equalsIgnoreCase("")){ %>
	showError('<%=error%>');
<% 	}%>
<%	if (dto == null || dto.getIdBranch() == null || dto.getIdBranch().intValue() <= 0) {
		if (list == null || list.size() <= 0){ 
			if (error == null || error.equalsIgnoreCase("")){
%>
	clearForm();
<%			}
		} else { %>
	activeAdd("type");
<%		} %>
	
	hideUpdateDelete();
<% 	} else { %>
	document.getElementById("addButton").style.visibility = "hidden";
	document.getElementById("searchButton").style.visibility = "hidden";
<%	}%>

</script>