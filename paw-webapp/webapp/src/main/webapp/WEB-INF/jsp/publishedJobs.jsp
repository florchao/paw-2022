<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.0.0/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.1/jquery.modal.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.1/jquery.modal.min.css" />

<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<c:url value="/public/css/style.css"/>"/>
    <link rel="icon" type="image/x-icon" href="<c:url value="/public/favicon.png"/>"/>
    <script src="https://cdn.tailwindcss.com"></script>
    <title><spring:message code="contacts.title"/></title>
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
<div class="grid content-start h-screen overflow-auto pl-5 pr-5">
    <div class="my-8 w-full"></div>
    <p class="text-3xl font-semibold text-violet-900 mb-4"><spring:message code="contacts.myContacts"/></p>
    <c:choose>
        <c:when test="${JobList.size() == 0}">
            <div class = "grid content-center justify-center h-5/6 mt-16">
                <div class = "grid justify-items-center">
                    <img src="<c:url value='/public/sinEmpleadas.png'/>" alt="sinEmpleadas" class="mr-3 h-6 sm:h-52">
                    <p class="text-3xl font-semibold text-purple-700"><spring:message code="contacts.noContacts"/></p>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="flex flex-wrap content-start h-screen pl-5 pr-5">
                <c:forEach var="contact" items="${JobList}">
                    <c:set var="contact" value="${contact}" scope="request"/>
                    <div>
                        <jsp:include page="components/contactCard.jsp">
                            <jsp:param name="name" value="${contact.employerName}"/>
                            <jsp:param name="message" value="${contact.description}"/>
                            <jsp:param name = "date" value = "${contact.location}"/>
                        </jsp:include>
                    </div>
                </c:forEach>
            </div>
        </c:otherwise>
    </c:choose>
</div>

</body>
</html>

