<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="es" class="scroll-smooth">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../../public/css/style.css">
    <script src="https://cdn.tailwindcss.com"></script>
    <title>Buscar</title>
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
            <div class="grid grid-cols-4">
                <div class="col-span-1 bg-red-300 mr-8 h-screen">
<%--                    <c:url value="/filterEmployees" var="postPath"/>--%>
<%--                    <form:form modelAttribute="filterBy" action="${postPath}" method="post">--%>
<%--                        <form:checkbox path="abilities" value="Cuidado de menores"/>--%>
<%--                        <button type="submit">Filtrar</button>--%>
<%--                    </form:form>--%>
                </div>
                <div class="col-span-3 col-start-2 mt-8">
<%--                    <div class="bg-green-300 flex justify-end">--%>
<%--                        <h1 class="font-semibold">Ordenar por</h1>--%>
<%--                        <div class="dropdown">--%>
<%--                            <button class="dropbtn">Dropdown</button>--%>
<%--                            <div class="dropdown-content">--%>
<%--                                    &lt;%&ndash;                                <c:url value="/filterEmployees" var="postPath"/>&ndash;%&gt;--%>
<%--                                <form method="post" action="/filterEmployees">--%>
<%--                                    <input type="submit" value="Filtrar"/>--%>
<%--                                </form>--%>
<%--                                <a href="#">Link 2</a>--%>
<%--                                <a href="#">Link 3</a>--%>
<%--                            </div>--%>
<%--                        </div>--%>
<%--                    </div>--%>
                    <c:forEach var="employee" items="${EmployeeList}">
                        <c:set var="employee" value="${employee}" scope="request"/>
                        <jsp:include page="components/employeeCardComponent.jsp">
                        <jsp:param name="name" value="${employee.name}"/>
                        <jsp:param name="location" value="${employee.location}"/>
                        <jsp:param name="id" value="${employee.id}"/>
                        <jsp:param name = "years" value = "${employee.experienceYears}"/>
                    </jsp:include>
                    </c:forEach>
                </div>
            </div>
        </div>
    </c:otherwise>
</c:choose>

</body>
</html>
<style>
    .dropbtn {
        background-color: white;
        color: red;
        font-size: 16px;
        border: none;
        cursor: pointer;
    }

    .dropdown {
        position: relative;
        display: inline-block;
    }

    /* Dropdown Content (Hidden by Default) */
    .dropdown-content {
        display: none;
        position: absolute;
        background-color: #f9f9f9;
        min-width: 160px;
        box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
        z-index: 1;
    }

    /* Links inside the dropdown */
    .dropdown-content a {
        color: black;
        padding: 12px 16px;
        text-decoration: none;
        display: block;
    }

    /* Change color of dropdown links on hover */
    .dropdown-content a:hover {background-color: #f1f1f1}

    /* Show the dropdown menu on hover */
    .dropdown:hover .dropdown-content {
        display: block;
    }

    /* Change the background color of the dropdown button when the dropdown content is shown */
    .dropdown:hover .dropbtn {
        background-color: #3e8e41;
    }
</style>