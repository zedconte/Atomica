<%@page contentType="text/html;charset=ISO-8859-1"%>
<jsp:useBean id="error" scope="request" class="java.lang.String"/>

<script>
  showError('<%=error%>');
</script>