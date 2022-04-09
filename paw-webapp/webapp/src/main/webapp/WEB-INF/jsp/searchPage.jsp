<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en" class="scroll-smooth">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
    <title>Buscar</title>
</head>
<body>
<nav class="bg-white absolute w-full px-2 sm:px-4 py-2.5 dark:bg-violet-500">
    <div class="container flex flex-row items-center mx-auto">
        <!-- La imagen tiene que estar en una carpeta assets -->
        <img src="/docs/images/logo.svg" class="mr-3 h-6 sm:h-9">
        <span class="text-xl font-semibold whitespace-nowrap dark:text-white">Hogar</span>
    </div>
</nav>

    <div class="grid content-start h-screen overflow-auto pl-5 pr-5">
<%--        <c:if test="${EmployeeList.size() == 0}">--%>
<%--            <div>--%>
<%--                <p class="text-3xl font-semibold text-purple-700">No hay empleadas registradas</p>--%>
<%--            </div>--%>
<%--        </c:if>--%>
        <c:forEach var="employee" items="${EmployeeList}">
            <c:set var="employee" value="${employee}" scope="request"/>
            <jsp:include page="components/employeeCardComponent.jsp">
                <jsp:param name="name" value="${employee.name}"/>
                <jsp:param name="location" value="${employee.location}"/>
                <jsp:param name="id" value="${employee.id}"/>
            </jsp:include>
        </c:forEach>
    </div>
</body>
</html>