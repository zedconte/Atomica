<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.industrika.commons.i18n.CommonsMessages" %>
<%@page import="com.industrika.humanresources.i18n.HRMessages" %>
<%@page import="com.industrika.humanresources.dto.Employee" %>
<%@page import="com.industrika.humanresources.dto.Department" %>
<%@page import="com.industrika.humanresources.dto.Position" %>
<%@page import="com.industrika.humanresources.dto.Shift" %>
<%@page import="com.industrika.humanresources.dao.DepartmentDao" %>
<%@page import="com.industrika.humanresources.dao.PositionDao" %>
<%@page import="com.industrika.humanresources.dao.ShiftDao" %>
<%@page import="com.industrika.commons.dto.City" %>
<%@page import="com.industrika.commons.dao.CityDao" %>
<%@page import="com.industrika.commons.businesslogic.ApplicationContextProvider" %>
<%@page import="com.industrika.commons.utils.TextFormatter" %>
<%@page import="java.util.Vector" %>
<jsp:useBean id="dto" scope="request" class="com.industrika.humanresources.dto.Employee"/>
<jsp:useBean id="list" scope="request" class="java.util.Vector"/>
<jsp:useBean id="message" scope="request" class="java.lang.String"/>
<jsp:useBean id="error" scope="request" class="java.lang.String"/>
<form name="employee" id="employee" class="normaltext">
	<input type="hidden" name="action" id="action"/>
	<input type="hidden" name="formName" id="formName" value="employee"/>
	<table class="commandbuttons">
		<tr>
			<td class="separator">&nbsp;</td>
			<td><img class="resetbutton" src="images/reset.png" onclick="javascript:clearForm()"></td>
			<td class="separator">&nbsp;</td>
			<td><div id="addButton"><img class="addbutton" src="images/new.png" onclick="javascript:document.employee.action.value='add';sendDataConfirm('employee','Alta para el empleado')"></div></td>
			<td class="separator">&nbsp;</td>
			<td><div id="updateButton"><img class="savebutton" src="images/save.png" onclick="javascript:document.employee.action.value='update';sendDataConfirm('employee','Actualizaci&oacute;n para el empleado')"></div></td>
			<td class="separator">&nbsp;</td>
			<td><div id="deleteButton"><img class="deletebutton" src="images/delete.png" onclick="javascript:document.employee.action.value='remove';sendDataConfirm('employee','Eliminado del empleado')"></div></td>
			<td class="separator">&nbsp;</td>
			<td><img id="searchButton" class="searchbutton" src="images/search.png" onclick="javascript:document.employee.action.value='search';sendData('employee')"></td>
			<td class="separator">&nbsp;</td>
		</tr>
	</table>
	<br/>
	<table align="center">
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext"><%=TextFormatter.formatWeb(HRMessages.getMessage("employee.Title")) %></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td colspan="5" class="titletext">&nbsp;</td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=TextFormatter.formatWeb(HRMessages.getMessage("employee.Rfc")) %>:</td>
			<td><input type='text' name='rfc' id='rfc' value='<%=dto.getRfc() != null ? TextFormatter.formatWeb(dto.getRfc()) : ""%>'/></td>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=TextFormatter.formatWeb(HRMessages.getMessage("employee.FirstName")) %>:</td>
			<td>
				<input onkeyup="javascript:activeAdd('firstName')" type='text' size="50" name='firstName' id='firstName' value='<%=dto.getFirstName() != null ? TextFormatter.formatWeb(dto.getFirstName()) : ""%>'/>
				<input type='hidden' name='idPerson' id='idPerson' value='<%= dto.getIdPerson() != null ? ""+dto.getIdPerson().intValue() : "" %>'/>
			</td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=TextFormatter.formatWeb(HRMessages.getMessage("employee.LastName")) %>:</td>
			<td>
				<input type='text' size="50" name='lastName' id='lastName' value='<%=dto.getLastName() != null ? TextFormatter.formatWeb(dto.getLastName()) : ""%>'/>
			</td>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=TextFormatter.formatWeb(HRMessages.getMessage("employee.MiddleName")) %>:</td>
			<td>
				<input type='text' size="50" name='middleName' id='middleName' value='<%=dto.getMiddleName() != null ? TextFormatter.formatWeb(dto.getMiddleName()) : ""%>'/>
			</td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=TextFormatter.formatWeb(HRMessages.getMessage("employee.Nss")) %>:</td>
			<td>
				<input type='text' name='nss' id='nss' value='<%=dto.getNss() != null ? TextFormatter.formatWeb(dto.getNss()) : ""%>'/>
			</td>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=TextFormatter.formatWeb(HRMessages.getMessage("employee.Salary")) %>:</td>
			<td><input type='text' class="labelaligment" onblur="javascript:this.value=formatNumber(Number(this.value));" name='salary' id='salary' value='<%=dto.getSalary() != null ? TextFormatter.getCurrencyValue(dto.getSalary()) : "0.00"%>'/></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=TextFormatter.formatWeb(HRMessages.getMessage("employee.Gender")) %>:</td>
			<td>
				<select name="gender" id="gender">
					<option value="Femenino" <%=(dto.getGender() != null && dto.getGender().equalsIgnoreCase("Femenino")) ? "selected = 'selected'" : "" %>>Femenino</option>
					<option value="Masculino" <%=(dto.getGender() != null && dto.getGender().equalsIgnoreCase("Masculino")) ? "selected = 'selected'" : "" %>>Masculino</option>
				</select>
			</td>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=TextFormatter.formatWeb(HRMessages.getMessage("employee.Department")) %>:</td>
			<td>
				<select name="department" id="department">
<%
	String[] orderDepartment = {"name"};
	try{
		Vector<Department> departments = new Vector<Department>(((DepartmentDao)ApplicationContextProvider.getCtx().getBean("departmentDao")).find(new Department(), orderDepartment));
		if (departments != null && departments.size() > 0){
			for (Department department : departments){
%>				
					<option value="<%=department.getIdDepartment() %>" <%=(dto.getDepartment() != null && dto.getDepartment().getIdDepartment() != null && dto.getDepartment().getIdDepartment().intValue() == department.getIdDepartment()) ? "selected = 'selected'" : "" %>><%= TextFormatter.formatWeb(department.getName()) %></option>
<%			}
		}
	}catch(Exception ex){}
%>
				</select>
			</td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=TextFormatter.formatWeb(HRMessages.getMessage("employee.Position")) %>:</td>
			<td>
				<select name="position" id="position">
<%
	String[] orderPosition = {"name"};
	try{
		Vector<Position> positions = new Vector<Position>(((PositionDao)ApplicationContextProvider.getCtx().getBean("positionDao")).find(new Position(), orderPosition));
		if (positions != null && positions.size() > 0){
			for (Position position : positions){
%>				
					<option value="<%=position.getIdPosition() %>" <%=(dto.getPosition() != null && dto.getPosition().getIdPosition() != null && dto.getPosition().getIdPosition().intValue() == position.getIdPosition()) ? "selected = 'selected'" : "" %>><%=TextFormatter.formatWeb(position.getName()) %></option>
<%			}
		}
	}catch(Exception ex){}
%>
				</select>
			</td>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=TextFormatter.formatWeb(HRMessages.getMessage("employee.Shift")) %>:</td>
			<td>
				<select name="shift" id="shift">
<%
	String[] orderShift = {"name"};
	try{
		Vector<Shift> shifts = new Vector<Shift>(((ShiftDao)ApplicationContextProvider.getCtx().getBean("shiftDao")).find(new Shift(), orderShift));
		if (shifts != null && shifts.size() > 0){
			for (Shift shift : shifts){
%>				
					<option value="<%=shift.getIdShift() %>" <%=(dto.getShift() != null && dto.getShift().getIdShift() != null && dto.getShift().getIdShift().intValue() == shift.getIdShift()) ? "selected = 'selected'" : "" %>><%=TextFormatter.formatWeb(shift.getName()) %></option>
<%			}
		}
	}catch(Exception ex){}
%>
				</select>
			</td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=CommonsMessages.getMessage("street") %>:</td>
			<td>
				<input type='hidden' name='idAddress' id='idAddress' value='<%= (dto.getAddresses() != null && dto.getAddresses().size()>0 && dto.getAddresses().get(0) != null && dto.getAddresses().get(0).getIdAddress() != null) ? "" + dto.getAddresses().get(0).getIdAddress().intValue() : "" %>'/>
				<input type='text' size="50" name='street' id='street' value='<%=(dto.getAddresses() != null && dto.getAddresses().size()>0 && dto.getAddresses().get(0) != null && dto.getAddresses().get(0).getStreet() != null) ? TextFormatter.formatWeb(dto.getAddresses().get(0).getStreet()) : ""%>'/>
			</td>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=CommonsMessages.getMessage("extNumber") %>:</td>
			<td><input type='text' name='extNumber' id='extNumber' value='<%=(dto.getAddresses() != null && dto.getAddresses().size() > 0 && dto.getAddresses().get(0) != null && dto.getAddresses().get(0).getExtNumber() !=null) ? TextFormatter.formatWeb(dto.getAddresses().get(0).getExtNumber()) : ""%>'/></td>
			<td class="separator">&nbsp;</td>
		</tr>

		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=CommonsMessages.getMessage("intNumber") %>:</td>
			<td>
				<input type='text' name='intNumber' id='intNumber' value='<%=(dto.getAddresses() != null && dto.getAddresses().size()>0 && dto.getAddresses().get(0) != null && dto.getAddresses().get(0).getIntNumber() != null)? TextFormatter.formatWeb(dto.getAddresses().get(0).getIntNumber()) : ""%>'/>
			</td>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=CommonsMessages.getMessage("suburb") %>:</td>
			<td><input type='text' name='suburb' id='suburb' value='<%=(dto.getAddresses() != null && dto.getAddresses().size() > 0 && dto.getAddresses().get(0) != null && dto.getAddresses().get(0).getSuburb() !=null) ? TextFormatter.formatWeb(dto.getAddresses().get(0).getSuburb()) : ""%>'/></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=CommonsMessages.getMessage("city") %>:</td>
			<td>
				<select name="idCity" id="idCity">
<%
	String[] order = {"name"};
	try{
		Vector<City> cities = new Vector<City>(((CityDao)ApplicationContextProvider.getCtx().getBean("cityDao")).find(new City(),order));
		if (cities != null && cities.size() > 0){
			for (City city : cities){
%>			
			<option value="<%=city.getIdCity()%>" <%=(dto.getAddresses()!=null && dto.getAddresses().size() > 0 && dto.getAddresses().get(0) != null && dto.getAddresses().get(0).getCity()!=null && dto.getAddresses().get(0).getCity().getIdCity()!=null && dto.getAddresses().get(0).getCity().getIdCity().intValue()==city.getIdCity().intValue()) ? "selected='selected'":""%>><%= TextFormatter.formatWeb(city.getName()) %></option>
<%
			}
		}
	}catch(Exception ex){ex.getMessage();}
%>
				</select>
			</td>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=CommonsMessages.getMessage("zipCode") %>:</td>
			<td><input type='text' name='zipcode' id='zipcode' value='<%=(dto.getAddresses() != null && dto.getAddresses().size() > 0 && dto.getAddresses().get(0) != null && dto.getAddresses().get(0).getZipCode() !=null) ? TextFormatter.formatWeb(dto.getAddresses().get(0).getZipCode()) : ""%>'/></td>
			<td class="separator">&nbsp;</td>
		</tr>
		<tr>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=CommonsMessages.getMessage("type") %>:</td>
			<td>
				<input type='hidden' name='idPhone' id='idPhone' value='<%= (dto.getPhones() != null && dto.getPhones().size()>0 && dto.getPhones().get(0) != null && dto.getPhones().get(0).getIdPhone() != null) ? "" + dto.getPhones().get(0).getIdPhone().intValue() : "" %>'/>
				<input type='text' name='phoneType' id='phoneType' value='<%= (dto.getPhones() != null && dto.getPhones().size()>0 && dto.getPhones().get(0) != null && dto.getPhones().get(0).getType() != null) ? dto.getPhones().get(0).getType() : "" %>'/>
				&nbsp;&nbsp;&nbsp;<%=CommonsMessages.getMessage("areaCode") %>:
				<input type='text' name='areaCode' id='areaCode' size = "4" value='<%=(dto.getPhones() != null && dto.getPhones().size()>0 && dto.getPhones().get(0) != null && dto.getPhones().get(0).getAreaCode() != null)? TextFormatter.formatWeb(dto.getPhones().get(0).getAreaCode()) : ""%>'/>
			</td>
			<td class="separator">&nbsp;</td>
			<td class="labelaligment"><%=CommonsMessages.getMessage("number") %>:</td>
			<td><input type='text' name='phoneNumber' id='phoneNumber' value='<%=(dto.getPhones() != null && dto.getPhones().size() > 0 && dto.getPhones().get(0) != null && dto.getPhones().get(0).getNumber() !=null) ? TextFormatter.formatWeb(dto.getPhones().get(0).getNumber()) : ""%>'/></td>
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
				<th><%=TextFormatter.formatWeb(HRMessages.getMessage("employee.FirstName")) %></th>
				<th><%=TextFormatter.formatWeb(HRMessages.getMessage("employee.LastName")) %></th>
				<th><%=TextFormatter.formatWeb(HRMessages.getMessage("employee.MiddleName")) %></th>
				<th><%=TextFormatter.formatWeb(HRMessages.getMessage("employee.Rfc")) %></th>
				<th><%=TextFormatter.formatWeb(HRMessages.getMessage("employee.Nss")) %></th>
			</tr>
<%		for (int a = 0;a < list.size();a++){ 
				Employee employee = (Employee) list.get(a); %>
			<tr onclick="javascript:clearForm();document.employee.idPerson.value='<%=employee.getIdPerson()%>';document.employee.action.value='search';sendData('employee');">
				<td><%=TextFormatter.formatWeb(employee.getFirstName())%></td>
				<td><%=TextFormatter.formatWeb(employee.getLastName())%></td>
				<td><%=TextFormatter.formatWeb(employee.getMiddleName())%></td>
				<td><%=TextFormatter.formatWeb(employee.getRfc())%></td>
				<td><%=TextFormatter.formatWeb(employee.getNss())%></td>
			</tr>
<%		} %>
		</table>
<%	} %>
	</div>
</form>
<script>
	function clearForm(){
		document.employee.idPerson.value='';
		document.employee.firstName.value='';
		document.employee.lastName.value='';
		document.employee.middleName.value='';
		document.employee.rfc.value='';
		document.employee.nss.value='';
		document.employee.salary.value='';
		document.employee.idAddress.value='';
		document.employee.street.value='';
		document.employee.extNumber.value='';
		document.employee.intNumber.value='';
		document.employee.suburb.value='';
		document.employee.zipcode.value='';
		document.employee.idPhone.value='';
		document.employee.areaCode.value='';
		document.employee.phoneNumber.value='';
		document.employee.phoneType.value='';
		document.employee.idCity.selectedIndex=-1;
		document.employee.department.selectedIndex=-1;
		document.employee.position.selectedIndex=-1;
		document.employee.shift.selectedIndex=-1;
		document.getElementById("resultsTable").style.visibility = "hidden";
		document.getElementById("addButton").style.visibility = "hidden";
		document.getElementById("searchButton").style.visibility = "visible";
		activeAdd('firstName');
		hideUpdateDelete();
		document.employee.idPerson.focus();
	}
<% 	if (dto.getAddresses()==null || dto.getAddresses().size() <= 0 || dto.getAddresses().get(0) == null || dto.getAddresses().get(0).getCity() == null || dto.getAddresses().get(0).getCity().getIdCity() == null || dto.getAddresses().get(0).getCity().getIdCity().intValue() <= 0){%>
	document.employee.idCity.selectedIndex=-1;
<%	}%>
<% 	if (dto.getDepartment()==null || dto.getDepartment().getIdDepartment() == null || dto.getDepartment().getIdDepartment().intValue() <= 0){%>
	document.employee.department.selectedIndex=-1;
<%	}%>
<% 	if (dto.getPosition()==null || dto.getPosition().getIdPosition() == null || dto.getPosition().getIdPosition().intValue() <= 0){%>
	document.employee.position.selectedIndex=-1;
<%	}%>
<% 	if (dto.getShift()==null || dto.getShift().getIdShift() == null || dto.getShift().getIdShift().intValue() <= 0){%>
	document.employee.shift.selectedIndex=-1;
<%	}%>
<% 	if (dto.getGender()==null || dto.getGender().equalsIgnoreCase("")){%>
	document.employee.gender.selectedIndex=-1;
<%	}%>
<%	if (error != null && !error.equalsIgnoreCase("")){ %>
	showError('<%=error%>');
<% 	} %>
<%	if (dto == null || dto.getIdPerson() == null || dto.getIdPerson().intValue() <= 0) {
		if (list == null || list.size() <= 0){ 
			if (error == null || error.equalsIgnoreCase("")){
%>
	clearForm();
<%			}
		} else { %>
	document.employee.idCity.selectedIndex=-1;
	activeAdd("firstName");
<%		} %>
		
	hideUpdateDelete();
<% 	} else { %>
	document.getElementById("addButton").style.visibility = "hidden";
	document.getElementById("searchButton").style.visibility = "hidden";
<%	} %>

</script>