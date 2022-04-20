<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Ludmila
  Date: 20/4/2022
  Time: 12:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Contactos</title>
</head>
<body>
<c:forEach var="contact" items="${ContactList}">
    <c:set var="contact" value="${contact}" scope="request"/>
    <jsp:include page="components/employeeCardComponent.jsp">
        <jsp:param name="name" value="${contact.employerID}"/>
        <jsp:param name="location" value="${contact.message}"/>
        <jsp:param name = "years" value = "${contact.created}"/>
    </jsp:include>
</c:forEach>


</body>
</html>
