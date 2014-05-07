<%@page contentType="text/html;charset=ISO-8859-1"%>
<jsp:useBean id="error" scope="request" class="java.lang.String"/>
<%
	String sesion = (String)request.getSession().getAttribute("errorsesion");
%>
<script>
  showError('<%=sesion%>');
  showLoadScreen();document.logout.submit();
</script>