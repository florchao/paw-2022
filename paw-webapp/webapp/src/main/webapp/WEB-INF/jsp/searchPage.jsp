<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="es" class="scroll-smooth">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<c:url value="/public/css/style.css"/>"/>
    <script src="https://cdn.tailwindcss.com"></script>
    <script type="text/javascript">
        function validateExpYears() {
            var el = document.getElementById('expYears');
            if (el.value ==="") {
                el.value=0;
            }
        }
    </script>
    <link rel="icon" type="image/x-icon" href="<c:url value="/public/favicon.png"/>"/>
    <title><spring:message code="searchPage.title"/></title>
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
    <jsp:param name="currentUrl" value="searchPage"/>
</jsp:include>
        <div class="grid content-start h-screen overflow-auto pl-5 pr-5">
            <div class="my-8 w-full"></div>
            <div class="grid grid-cols-4">
                <div class="col-start-1 bg-purple-300 mr-8 p-6 rounded-2xl mt-2 shadow-xl border-solid border-violet-500 border-2 h-fit w-fit" >
                    <c:url value="/filterEmployees" var="postPath"/>
                    <form:form modelAttribute="filterBy" action="${postPath}" method="get">
                        <div class="flex flex-col items-center">
                            <h1 class="font-semibold mt-2"><spring:message code="searchPage.label.experienceYears"/></h1>
                            <div class="grid grid-cols-12">
                                <form:input type="number" id="expYears" onchange="validateExpYears()" path="experienceYears" class="col-span-10 col-start-2" cssStyle="border-radius: 5px; padding-left: 5px"/>
<%--                                   TODO arreglar el codigo de error --%>
                                <form:errors path="experienceYears" element="p" cssStyle="color:red"/>
                            </div>
                            <h1 class="font-semibold mt-4"><spring:message code="searchPage.label.location"/></h1>
                            <div class="grid grid-cols-12">
                                <form:input type="text" path="location" class="col-span-10 col-start-2" cssStyle="border-radius: 5px; padding-left: 5px"/>
                                    <%--TODO arreglar el codigo de error --%>
                                <form:errors path="location" element="p" cssStyle="color:red"/>
                            </div>
                            <h1 class="font-semibold mt-4 "><spring:message code="searchPage.abilities"/></h1>
                            <div class="grid grid-cols-4 w-5/6">
                                <div class="col-span-3">
                                    <form:label path="abilities"><spring:message code="searchPage.abilities.cook"/></form:label>
                                </div>
                                <div class="col-start-4 col-span-1 ml-2">
                                    <form:checkbox path="abilities" value="Cocinar"/>
                                </div>
                            </div>
                            <div class="grid grid-cols-4 w-5/6">
                                <div class="col-span-3">
                                    <form:label path="abilities"><spring:message code="searchPage.abilities.iron"/></form:label>
                                </div>
                                <div class="col-start-4 col-span-1 ml-2">
                                    <form:checkbox path="abilities" value="Planchar"/>
                                </div>
                            </div>
                            <div class="grid grid-cols-4 w-5/6">
                                <div class="col-span-3">
                                    <form:label path="abilities"><spring:message code="searchPage.abilities.pets"/></form:label>
                                </div>
                                <div class="col-start-4 col-span-1 ml-2">
                                    <form:checkbox path="abilities" value="Cuidado de mascotas"/>
                                </div>
                            </div>
                            <div class="grid grid-cols-4 w-5/6">
                                <div class="col-span-3">
                                    <form:label path="abilities"><spring:message code="searchPage.abilities.older"/></form:label>
                                </div>
                                <div class="col-start-4 col-span-1 ml-2">
                                <form:checkbox path="abilities" value="Cuidado de mayores"/>
                                </div>
                            </div>
                            <div class="grid grid-cols-4 w-5/6">
                                <div class="col-span-3">
                                    <form:label path="abilities"><spring:message code="searchPage.abilities.young"/></form:label>
                                </div>
                                <div class="col-start-4 col-span-1 ml-2">
                                    <form:checkbox path="abilities" value="Cuidado de menores"/>
                                </div>
                            </div>
                            <div class="grid grid-cols-4 w-5/6">
                                <div class="col-span-3">
                                    <form:label path="abilities"><spring:message code="searchPage.abilities.specialNeeds"/></form:label>
                                </div>
                                <div class="col-start-4 col-span-1 ml-2">
                                    <form:checkbox path="abilities" value="Cuidados especiales"/>
                                </div>
                            </div>
                            <h1 class="font-semibold mt-4"><spring:message code="employeeForm.availability"/></h1>
                            <div class="grid grid-cols-4 w-5/6">
                                <div class="col-span-3">
                                    <form:label path="availability"><spring:message code="searchPage.availability.half"/></form:label>
                                </div>
                                <div class="col-start-4 col-span-1 ml-2">
                                    <form:checkbox path="availability" value="Media jornada"/>
                                </div>
                            </div>
                            <div class="grid grid-cols-4 w-5/6">
                                <div class="col-span-3">
                                    <form:label path="availability"><spring:message code="searchPage.availability.complete"/></form:label>
                                </div>
                                <div class="col-start-4 col-span-1 ml-2">
                                    <form:checkbox path="availability" value="Jornada completa"/>
                                </div>
                            </div>
                            <div class="grid grid-cols-4 w-5/6">
                                <div class="col-span-3">
                                    <form:label path="availability"><spring:message code="searchPage.availability.bed"/></form:label>
                                </div>
                                <div class="col-start-4 col-span-1 ml-2">
                                    <form:checkbox path="availability" value="Con cama"/>
                                </div>
                            </div>
                            <button type="submit" class="mt-4 border shadow-md text-lg w-5/6 focus:outline-none text-violet-900 bg-purple-400 border border-purple-900 hover:bg-yellow-300 hover:bg-opacity-50 font-small rounded-lg text-sm px-5 py-2.5">Filtrar</button>
                        </div>
                    </form:form>
                </div>
                <div class="col-span-3 col-start-2">
<%--                    <div class="bg-green-300 flex justify-end">--%>
<%--                        <h1 class="font-semibold">Ordenar por</h1>--%>
<%--                        <div class="dropdown">--%>
<%--                            <button class="dropbtn">Dropdown</button>--%>
<%--                            <div class="dropdown-content">--%>
<%--                                    &lt;%&ndash;                                <c:url value="/filterEmployees" var="postPath"/>&ndash;%&gt;--%>
<%--                                <form method="post" action="/filterEmployees">--%>
<%--                                    <button type="submit" value="Filtrar"/>--%>
<%--                                </form>--%>
<%--                                <a href="#">Link 2</a>--%>
<%--                                <a href="#">Link 3</a>--%>
<%--                            </div>--%>
<%--                        </div>--%>
<%--                    </div>--%>
                    <p class="text-3xl font-semibold text-violet-900 mb-4"><spring:message code="searchPage.searchEmployees"/></p>
                    <c:choose>
                        <c:when test="${EmployeeList.size() == 0}">
                            <div class = "grid content-center justify-center h-5/6 mt-16">
                                <div class = "grid justify-items-center">
                                    <img src="<c:url value='/public/sinEmpleadas.png'/>" alt="sinEmpleadas" class="mr-3 h-6 sm:h-52">
                                    <p class="text-3xl font-semibold text-purple-700"><spring:message code="searchPage.noEmployees"/></p>
                                </div>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="employee" items="${EmployeeList}">
                                <c:set var="employee" value="${employee}" scope="request"/>
                                <jsp:include page="components/employeeCardComponent.jsp">
                                    <jsp:param name="name" value="${employee.name}"/>
                                    <jsp:param name="location" value="${employee.location}"/>
                                    <jsp:param name="id" value="${employee.id}"/>
                                    <jsp:param name = "years" value = "${employee.experienceYears}"/>
                                </jsp:include>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>

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