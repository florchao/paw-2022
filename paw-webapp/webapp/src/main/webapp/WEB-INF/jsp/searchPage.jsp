<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!DOCTYPE html>
<html lang="es" class="scroll-smooth">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
    <title>Buscar</title>
</head>
<body>
<jsp:include page="components/navbar.jsp">
    <jsp:param name="currentUrl" value="seachPage"/>
</jsp:include>
    <c:choose>
        <c:when test="${EmployeeList.size() == 0}">
            <div class = "grid content-center justify-center h-screen">
                <div class = "grid justify-items-center">
                    <img src="<c:url value='/public/sinEmpleadas.png'/>" alt="sinEmpleadas" class="mr-3 h-6 sm:h-52">
                    <p class="text-3xl font-semibold text-purple-700"><spring:message code="searchPage.noEmployees"/></p>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="grid content-start h-screen overflow-auto pl-5 pr-5">
                <div class="my-8 w-full"></div>
                <c:forEach var="employee" items="${EmployeeList}">
                    <c:set var="employee" value="${employee}" scope="request"/>
                    <jsp:include page="components/employeeCardComponent.jsp">
                        <jsp:param name="name" value="${employee.name}"/>
                        <jsp:param name="location" value="${employee.location}"/>
                        <jsp:param name="id" value="${employee.id}"/>
                    </jsp:include>
                </c:forEach>
            </div>
        </c:otherwise>
    </c:choose>

</body>
</html>