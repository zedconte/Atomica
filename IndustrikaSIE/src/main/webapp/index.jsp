<%@page contentType="text/html;charset=ISO-8859-1"%>
<%@page import="java.util.List" %>
<%@page import="com.industrika.commons.utils.TextFormatter" %>
<%@page import="com.industrika.commons.dto.User" %>
<%@page import="com.industrika.commons.dto.Role" %>
<%@page import="com.industrika.commons.dto.Action" %>
<%@page import="com.industrika.commons.dto.Privilege" %>
<%@page import="com.industrika.commons.dto.Module" %>
<%@page import="com.industrika.commons.dto.Option" %>
<%@page import="com.industrika.commons.i18n.CommonsMessages" %>
<%@page import="com.industrika.maintenance.i18n.MaintenanceMessages" %>
<%
    User user = (User) request.getSession().getAttribute("signedUser");
	List<Module> modules = (List<Module>) request.getSession().getAttribute("menuModules");
%>

<html>
<head>
	<title>Industrika - Sistema de Información Empresarial</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<link href="css/style.css" type="text/css" rel="stylesheet" />
	<link rel="stylesheet" href="css/jquery-ui.css">
	<link rel="stylesheet" href="css/jquery.alerts.css">
	<script src="js/jquery-1.9.1.js"></script>
	<script src="js/jquery.validate.min.js"></script>
	<script src="js/jquery-ui.js"></script>
	<script src="js/jquery.form.js"></script>
	<script src="js/jquery.alerts.js"></script>
	<script src="js/knockout-3.0.0.js"></script>
	<script src="js/knockout.mapping-latest.js"></script>    
	<script src="js/main.js"></script>
    <link href="css/jquery.appendGrid-1.3.4.css" type="text/css" rel="stylesheet" />
    <script src="js/jquery.appendGrid-1.3.4.js"></script> 
	<script>
		$(function() {
			$( ".menu" ).accordion({
				collapsible: true
			});
		});
	</script>
</head>
<body>
	<div class="content">
		<div class="top_block heather">
			<div class="content">
				<table style="width: 100%">
					<tr>
						<td style="width: 20px"></td>
						<td><img src="images/industrika.png"/></td>
						<td style="text-align: right; font-family: Tahoma; font-size: 20px; vertical-align: bottom; color: #070050">Sistema de Informaci&oacute;n Empresarial</td>
						<td style="width: 20px"></td>
					</tr>
				</table>				
			</div>
			<div style="position: relative;top: -15px;right: -85px"><span id="date_time" style="color: #000964;font-size: 11px"></span></div>
			<div id="welcome-panel" style="position: relative;top: -28px;right: -250px"><span style="color: #000964;font-size: 11px"><%=(user!=null && user.getName()!=null)? "Bienvenido "+user.getName():""%></span></div>
		</div>
		<div class="background menus">
		</div>
		<div class="left_block menus">
			<div class="content">
				
<%
	if (user == null || user.getId() == null) {
%>
				<div id="login-menu" class="menu">
					<h3>Acceder</h3>
					<div>					
						<form name="login" action="main" method="post">
							<input type="hidden" name="formName" id="formNameLogin" value="login"/>				
							<table style="width: 100%" class="normaltext">
								<tr>
									<td class="labelaligment">Usuario:</td>
									<td><input type="text" name="sieuserid" id="sieuserid" size="10"/></td>
									<td class="separator"></td>
								</tr>
								<tr>
									<td class="labelaligment">Contrase&ntilde;a:</td>
									<td><input type="password" name="sieuserpass" id="sieuserpass" size="10"/></td>
									<td class="separator"></td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td class="separator"></td>
								</tr>
								<tr>
									<td colspan="2" style="text-align: center">
										<input type="button" name="blogin" value="Entrar" onclick="javascript:showLoadScreen();document.login.submit();"/>
									</td>
									<td class="separator"></td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td class="separator"></td>
								</tr>
<%
	if (request.getSession().getAttribute("errorLogin") != null){
%>								
								<tr>
									<td colspan="3" align="center"><%=request.getSession().getAttribute("errorLogin")%></td>
								</tr>
<%
	}
	request.getSession().removeAttribute("errorLogin");
%>
							</table>
						</form>
					</div>
					</div>
<%
	} else {
%>			
<script>
$(function() {
	$( ".menu" ).accordion( "option", "active", "false" );
	
});
</script>
				<div id="main-menu" class="menu">
					<h3>Salir</h3>
					<div>					
						<form name="logout" id="logout" action="main" method="post">
							<input type="hidden" name="formName" id="formNameLogout" value="logout"/>				
							<table style="width: 100%" class="normaltext">
								<tr>
									<td colspan="2" style="text-align: center">
										<input type="button" name="blogout" value="Salir" onclick="javascript:showLoadScreen();document.logout.submit();"/>
									</td>
									<td class="separator"></td>
								</tr>
							</table>
						</form>
					</div>
<%
	if (modules != null && modules.size() > 0){
		for (Module module: modules){
%>
					<h3><%=module.getName() %></h3>
					<div>
						<ul>
<%
			if (module.getOptions() != null){
				for (Option option: module.getOptions()){
					if (option.getVisible() == 1){
						if (user.getRoles() != null && user.getRoles().size() > 0){
							for (Role role : user.getRoles()){
								if (role.getPrivileges() != null && role.getPrivileges().size() > 0){
									for (Privilege privilege : role.getPrivileges()){
										if (privilege.getOption().getId().intValue() == option.getId().intValue() && privilege.getAction().getType().equalsIgnoreCase("enter")){
%>						
							<li class="menutext" onclick="loadFormAction('<%=option.getResourceName()%>')"><%=TextFormatter.formatWeb(option.getText()) %></li>
<%
											break;
										}
									}
								}
							}
						}
					}
				}
			}
 %>							
						</ul>
					</div>
<%
		}
	}
%>					
		     </div>
<%
	}
%>					
				
			</div>
		</div>
		<form name="menuoption" id="menuoption" action="main" method="post">
			<input type="hidden" name="formName" id="formNameMenu"/>
		</form>				
		<div class="background body">
		</div>
		<div class="center_block body">
			<div class="content">
				<div id="divcontent" style="width: 100%;overflow: auto;" class="normaltext">
<!--		Aqui va el contenido dinamico de formas   -->
                </div>
                <div id="modalDiv" class="normaltext" style="visibility: hidden;">
<!--		Aqui va el contenido dinamico de formas que se muestra en un panel   -->
                </div>
			</div>
		</div>
	</div>
	<div class="submitStyle" id="submitDialog"></div>
    <!-- <div style="position: absolute;top: 15px;right: 26px"><span id="date_time" style="color: #000964;font-size: 11px"></span></div> -->
    <!-- <div id="welcome-panel" style="position: absolute;top: 55px;left: 82px"><span style="color: #000964;font-size: 11px">Bienvenido <%=(user!=null && user.getName()!=null)?user.getName():"Desconocido"%></span></div>-->	
    <div class="errorDiv" id="error">
        <div>
            <p style="text-align: center"><img id="errorico" src="images/error.png" alt=""/></p>
            <p><span id="errorMessage"></span></p>
            <p>&nbsp;</p>
            <p style="text-align: center"><img id="closeInfoDiv" src="images/ok.png" onclick="closeDialog()" alt=""/></p>
        </div>
    </div>
    <script type="text/javascript">window.onload = date_time('date_time');</script>
    <script type="text/javascript">
    
	    function loadFormAction(form){
			$("#menuoption #formNameMenu").val(form);
			showForm();
		}
	        
        function closeDialog(){
            $("#error").hide();
        }
    </script>

</body>
</html>
