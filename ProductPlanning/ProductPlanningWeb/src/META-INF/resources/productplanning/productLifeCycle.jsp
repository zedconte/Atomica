<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.industrika.commons.i18n.CommonsMessages" %>
<%@page import="com.industrika.productplanning.i18n.ProductPlanningMessages" %>
<%@page import="com.industrika.productplanning.dto.ProductLifeCycle" %>
<%@page import="com.industrika.commons.utils.TextFormatter" %>
<%@page import="java.util.Vector" %>

<jsp:useBean id="dto" scope="request" class="com.industrika.productplanning.dto.ProductLifeCycle"/>
<jsp:useBean id="message" scope="request" class="java.lang.String"/>
<jsp:useBean id="list" scope="request" class="java.util.ArrayList"/>
<jsp:useBean id="error" scope="request" class="java.lang.String"/>

<form name="productLifeCycle" id="productLifeCycle" class="normalText">
	<input type="hidden" name="action" id="action"/>
	<input type="hidden" name="formName" id="formName" value="productLifeCycle"/>
	
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
			<td colspan="5" class="titletext"><%=ProductPlanningMessages.getMessage("productLifeCycle.Title") %></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext"><div style="visibility: hidden"><input type='text' name='idProductLifeCycle' id='idProductLifeCycle'  value='<%= dto.getIdProductLifeCycle() != null ? ""+dto.getIdProductLifeCycle().intValue() : "" %>'/></div></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=CommonsMessages.getMessage("movementConcept.Name") %>:</td>
			<td>
				<input type='text' name='name' id='name' size="50" onkeyup="javascript:activeAdd('name')" value='<%=dto.getName() != null ? TextFormatter.formatWeb(dto.getName()) : ""%>'/>
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
				<th><%=CommonsMessages.getMessage("movementConcept.Name") %></th>
			</tr>
<%		for (int a = 0;a < list.size();a++){ 
		ProductLifeCycle productLifeCycle = (ProductLifeCycle) list.get(a); 
%>
			<tr data-id="<%=productLifeCycle.getIdProductLifeCycle()%>">
				<td><%=TextFormatter.formatWeb(productLifeCycle.getName())%></td>
			</tr>
<%		} %>
		</table>
<%	} %>
	</div>
</form>
<script>
	var catalogName="Ciclo de vida del Producto";
	var formNameInJsp = "productLifeCycle";
	
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
			$("#idProductLifeCycle").val($(this).data("id"));
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
		activeAdd('name');
		hideUpdateDelete();
		$("#" + formNameInJsp + " #name").focus();
	}
	
	
<%	if (error != null && !error.equalsIgnoreCase("")){ %>
	showError('<%=error%>');
<% 	} %>
<%	if (dto == null || dto.getIdProductLifeCycle() == null || dto.getIdProductLifeCycle().intValue() <= 0) {
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