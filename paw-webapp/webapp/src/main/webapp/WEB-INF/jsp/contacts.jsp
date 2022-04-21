<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../../public/css/style.css">
    <script src="https://cdn.tailwindcss.com"></script>
    <title>Contactos</title>
</head>
<body>
<div class="area absolute">
    <ul class="circles">
        <li></li>
        <li></li>
        <li></li>
        <li></li>
        <li></li>
        <li></li>
        <li></li>
        <li></li>
        <li></li>
        <li></li>
    </ul>
</div>
<jsp:include page="components/navbar.jsp">
    <jsp:param name="currentUrl" value="contactos"/>
</jsp:include>
    <div class="flex flex-wrap content-start h-screen overflow-auto pl-5 pr-5">
        <div class="my-10 w-full"></div>
        <c:forEach var="contact" items="${ContactList}">
            <c:set var="contact" value="${contact}" scope="request"/>
            <jsp:include page="components/contactCard.jsp">
                <jsp:param name="name" value="${contact.employer}"/>
                <jsp:param name="message" value="${contact.message}"/>
                <jsp:param name = "date" value = "${contact.created}"/>
            </jsp:include>
        </c:forEach>
    </div>

</body>
</html>
