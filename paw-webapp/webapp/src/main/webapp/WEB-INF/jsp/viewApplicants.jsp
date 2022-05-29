<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html lang="es" class="scroll-smooth">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<c:url value="/public/css/style.css"/>"/>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="icon" type="image/x-icon" href="<c:url value="/public/favicon.png"/>"/>
    <title><spring:message code="applicants.title"/> ${title}</title>
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
<jsp:include page="components/navbar.jsp"/>
<div class="grid h-screen grid-cols-6 overflow-auto">
    <div class="grid justify-items-center mx-6 mt-24 col-span-6">
        <p class="text-xl font-semibold text-white">
            <spring:message code="applicants.title"/> ${title}
        </p>
    </div>
    <div class=" grid grid-row-4 col-span-4 col-start-2 row-span-6 h-full">
        <div class=" bg-gray-200 rounded-3xl overflow-auto p-5 mb-5 shadow-2xl">
            <div class="flow-root">
                <c:choose>
                    <c:when test="${ApplicantList.size() == 0}">
                        <div class = "grid content-center justify-center h-5/6 mt-16">
                            <div class = "grid justify-items-center">
                                <img src="<c:url value='/public/sinEmpleadas.png'/>" alt="sinEmpleadas" class="mr-3 h-6 sm:h-52">
                                <p class="text-3xl font-semibold text-purple-700"><spring:message code="applicants.noApplicants"/></p>
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <ul role="list" class="divide-y divide-gray-300">
                            <c:forEach var="applicant" items="${ApplicantList}">
                                <c:url value="/user/profile-image/${applicant.employeeID}" var="image" />
                                <li class="py-3 sm:py-4 hover:bg-gray-300 rounded">
                                    <a href="<c:url value="/verPerfil/${applicant.employeeID}"/>">
                                        <div class="flex items-center space-x-4">
                                            <div class="flex-shrink-0">
                                                <img class="w-8 h-8 rounded-full" src="${image}" alt="Employee Photo" onerror="this.src = '<c:url value="/public/user.png"/>'"/>
                                            </div>
                                            <div class="flex-1 min-w-0">
                                                <p class="text-xl font-medium text-gray-900 truncate">
                                                    <c:out value="${applicant.employeeID.name}"/>
                                                </p>
                                                <p class="text-sm text-gray-500 truncate">
                                                    <c:out value="${applicant.employeeID.name}"/>
                                                </p>
                                            </div>
                                        </div>
                                    </a>
                                </li>
                            </c:forEach>
                        </ul>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</div>
</body>