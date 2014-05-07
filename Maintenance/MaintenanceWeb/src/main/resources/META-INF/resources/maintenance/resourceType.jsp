<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.industrika.maintenance.i18n.MaintenanceMessages"%>
<%@page import="com.industrika.maintenance.dto.ResourceType" %>
<%@page import="com.industrika.commons.utils.TextFormatter" %>
<%@page import="java.util.Vector" %>

<jsp:useBean id="dto" scope="request" class="com.industrika.maintenance.dto.ResourceType"/>
<jsp:useBean id="message" scope="request" class="java.lang.String"/>
<jsp:useBean id="list" scope="request" class="java.util.ArrayList"/>
<jsp:useBean id="error" scope="request" class="java.lang.String"/>

<form name="resourceType" id="resourceType" class="normalText">
	<input type="hidden" name="action" id="action"/>
	<input type="hidden" name="formName" id="formName" value="resourceType"/>
	
	<table class="commandbuttons">
		<tr>
			<td class="separator">&nbsp;</td>
			<td><img class="resetbutton" src="images/reset.png" title="Resetear la forma" /></td>
			<td class="separator">&nbsp;</td>
			<td><div id="addButton"><img class="addbutton" src="images/new.png" title="Agregar" /></div></td>
			<td class="separator">&nbsp;</td>
			<td><div id="updateButton"><img class="savebutton" src="images/save.png" title="Actualizar" /></div></td>
			<td class="separator">&nbsp;</td>
			<td><div id="deleteButton"><img class="deletebutton" src="images/delete.png" title="Eliminar" /></div></td>
			<td class="separator">&nbsp;</td>
			<td><div id="searchButton"><img class="searchbutton" src="images/search.png"></div></td>
			<td class="separator">&nbsp;</td>
		</tr>		
	</table>
	<table style="margin: auto;">
		<tr>
			<td class="separator">&nbsp;</td>
			<td id="content-title" colspan="5" class="titletext"><%=MaintenanceMessages.getMessage("resourceType.Title") %></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext"><div style="visibility: hidden"><input type='text' name='id' id='id' value='<%= dto.getId() != null ? ""+dto.getId().intValue() : "" %>'/></div></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td id="name-field-label" class="labelaligment"><%=MaintenanceMessages.getMessage("Name") %>:</td>
			<td>
				<input type='text' size="50" name='name' id='name-field-input' onkeyup="javascript:activeAdd('name-field-input')" value='<%= dto.getName() != null ? TextFormatter.formatWeb(dto.getName()) : "" %>'/>
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
			<td id="message-text" colspan="5" class="messagetext"><%=message %></td>
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
			</tr>
<%		for (int a = 0;a < list.size();a++){
			ResourceType resourceType = (ResourceType) list.get(a); 
%>
			<tr data-id="<%=resourceType.getId()%>">
				<td><%=TextFormatter.formatWeb(resourceType.getName())%></td>
			</tr>
<%		} %>
		</table>
<%	} %>
	</div>
</form>
<script>
	var catalogName="<%=MaintenanceMessages.getMessage("resourceType.Title") %>";
	var formNameInJsp = "resourceType";
    var formValidator;
    var viewModel = {
       Name: ko.observable(""),
       Message: ko.observable("")
    };
    	
	$(function(){
		
		var actionField = $("#" + formNameInJsp + " #action");	
		
		formValidator = $("#resourceType").validate({
		    rules: {
		       name: "required"
		    },
		    messages: {
		      name: "El campo Nombre es requerido"
		    }
		});	
		
		$(".commandbuttons .resetbutton").click(function(){
		    formValidator.resetForm();
			clearForm();
		});		
		
		$(".commandbuttons .addbutton").click(function(){
			actionField.val("add");
			if (formValidator.form()){
				sendDataConfirm(formNameInJsp,'Alta para '+ catalogName)
			}
		});
		
		$(".commandbuttons .savebutton").click(function(){
			actionField.val("update");
			if (formValidator.form()){
				sendDataConfirm(formNameInJsp,'Actualizaci&oacute;n para '+ catalogName)
			}
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