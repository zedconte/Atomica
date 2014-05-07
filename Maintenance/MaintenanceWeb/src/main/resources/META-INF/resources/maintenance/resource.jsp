<%@page import="com.industrika.maintenance.i18n.MaintenanceMessages"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.industrika.maintenance.dto.Resource" %>
<%@page import="com.industrika.maintenance.dto.ResourceType" %>
<%@page import="com.industrika.maintenance.dao.IResourceTypeDao" %>
<%@page import="java.util.Vector" %>
<%@page import="com.industrika.commons.utils.TextFormatter" %>
<%@page import="com.industrika.commons.businesslogic.ApplicationContextProvider" %>

<jsp:useBean id="dto" scope="request" class="com.industrika.maintenance.dto.Resource"/>
<jsp:useBean id="message" scope="request" class="java.lang.String"/>
<jsp:useBean id="list" scope="request" class="java.util.Vector"/>
<jsp:useBean id="error" scope="request" class="java.lang.String"/>

<form name="resource" id="resource" class="normalText">
	<input type="hidden" name="action" id="action"/>
	<input type="hidden" name="formName" id="formName" value="resource"/>
	
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
	<table style="margin: auto;">
		<tr>
			<td class="separator">&nbsp;</td>
			<td id="content-title" colspan="5" class="titletext"><%=MaintenanceMessages.getMessage("resource.Title") %></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext"><div style="visibility: hidden"><input type='text' name='id' id='id' readonly="readonly" value='<%= dto.getId() != null ? ""+dto.getId().intValue() : "" %>'/></div></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<!--<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment "><%=MaintenanceMessages.getMessage("Id") %>:</td>
			<td></td>
			<td class="separator">&nbsp;</td>
		</tr>-->
		<tr>
			<td class="separator">&nbsp;</td>
			<td id="name-field-label" class="labelaligment"><%=MaintenanceMessages.getMessage("Name") %>:</td>
			<td><input type='text' name='name' id='name-field-input' onkeyup="javascript:activeAdd('name-field-input')" value='<%=dto.getName() != null ? TextFormatter.formatWeb(dto.getName()) : ""%>'/></td>
			<td class="separator">&nbsp;</td>
		</tr>				
		<tr>
			<td class="separator">&nbsp;</td>
			<td id="name-field-label" class="labelaligment"><%=MaintenanceMessages.getMessage("ResourceType") %>:</td>
			<td>
				<select name="resourceType" id="resourceType">
<%
	String[] order = {"name"};
	try{
		Vector<ResourceType> resources = new Vector<ResourceType>(((IResourceTypeDao)ApplicationContextProvider.getCtx().getBean("ResourceTypeDaoHibernate")).find(new ResourceType(),order));
		if (resources != null && resources.size() > 0){
			for (ResourceType resource : resources){
%>			
			<option value="<%=resource.getId()%>" <%=(dto.getResourceType()!=null && dto.getResourceType().getId()!=null && dto.getResourceType().getId().intValue()==resource.getId().intValue()) ? "selected='selected'":""%>><%= TextFormatter.formatWeb(resource.getName()) %></option>
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
				<th><%=MaintenanceMessages.getMessage("Name") %></th>
				<th><%=MaintenanceMessages.getMessage("ResourceType") %></th>
			</tr>
<%		for (int a = 0;a < list.size();a++){
			Resource resource = (Resource) list.get(a); 
%>
			<tr data-id="<%=resource.getId()%>">
				<td><%=TextFormatter.formatWeb(resource.getName())%></td>
				<td><%=(resource.getResourceType() != null) ? TextFormatter.formatWeb(resource.getResourceType().getName()) : ""%></td>
			</tr>
<%		} %>
		</table>
<%	} %>
	</div>
</form>
<script>
	var catalogName="<%=MaintenanceMessages.getMessage("resource.Title") %>";
	var formNameInJsp = "resource";
	
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
		
		$(".resultsTable tr").click(function(){
			clearForm();
			$("#id").val($(this).data("id"));
			actionField.val("search");
			sendData(formNameInJsp);			
		});
		
	});

	function clearForm(){
		$("#" + formNameInJsp + " input[type!='hidden']").each(function(){
			this.value='';
		});
		document.getElementById("resourceType").selectedIndex = -1;
		document.getElementById("resultsTable").style.visibility = "hidden";
		document.getElementById("addButton").style.visibility = "hidden";
		document.getElementById("searchButton").style.visibility = "visible";
		activeAdd('name-field-input');
		hideUpdateDelete();		
		
		$("#" + formNameInJsp + " #name").focus();
	}
	
<%	if (error != null && !error.equalsIgnoreCase("")){ %>
	showError('<%=error%>');
<% 	} %>
<% if (dto.getResourceType() == null || dto.getResourceType().getId() == null || dto.getResourceType().getId().intValue() <= 0){%>
	document.getElementById("resourceType").selectedIndex = -1;
<%}%>
<%	if (dto == null || dto.getId() == null || dto.getId().intValue() <= 0) {
	if (list == null || list.size() <= 0){ 
			if (error == null || error.equalsIgnoreCase("")){
%>
	clearForm();
			
<%			}
		} else { %>
	activeAdd("name-field-input");
<%		} %>
	
	hideUpdateDelete();
<% 	} else { %>
	document.getElementById("addButton").style.visibility = "hidden";
	document.getElementById("searchButton").style.visibility = "hidden";
<%	} %>

</script>