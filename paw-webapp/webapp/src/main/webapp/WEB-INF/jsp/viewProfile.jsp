<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html class="scroll-smooth">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<c:url value="/public/css/style.css"/>"/>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="icon" type="image/x-icon" href="<c:url value="/public/favicon.png"/>"/>
    <title><spring:message code="viewProfile.title"/></title>
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
    <jsp:param name="currentUrl" value="verPerfil"/>
</jsp:include>
<c:url value="/user/profile-image/${param.id}" var="image" />
<div class="grid overflow-auto h-screen grid-cols-6">
    <div class=" grid grid-row-4 col-span-4 col-start-2">
        <div class=" bg-gray-200 rounded-3xl p-5 mt-24 mb-5 shadow-2xl">
            <div class="grid grid-cols-5 justify-center">
                <div class="row-span-3 col-span-2 ml-6 mr-6 mb-6">
                    <img src="<c:url value="/user/profile-image/${userId}"/>"  onerror="this.src = '/public/user.png'"/>
                </div>
                <div class="ml-3 col-span-2">
                    <p class="text-2xl font-semibold whitespace-nowrap text-ellipsis overflow-hidden"><c:out value="${employee.name}"/></p>
                </div>
                <div class="ml-3 col-span-2">
                    <h1 class="block mb-2 font-medium text-gray-900 font-semibold"><spring:message code="viewProfile.location"/></h1>
                    <h1 class="block mb-2 text-sm font-medium text-gray-600 text-ellipsis overflow-hidden"> <c:out value="${employee.location}"/></h1>
                </div>
                <div class="ml-3 col-span-2">
                    <h1 class="block mb-2 font-medium text-gray-900 font-semibold"><spring:message code="viewProfile.experience"/></h1>
                    <h1 class="block mb-2 text-sm font-medium text-gray-600 "> <c:out value="${employee.experienceYears}"/></h1>
                </div>
                <sec:authorize access="hasAuthority('EMPLOYER')">
                <div class="ml-3 col-start-5 row-start-2">
                    <a href="<c:url value="/contacto/${user.id}"/>">
                        <button class="h-fit w-fit text-xs text-white bg-violet-400 border border-purple-900 focus:outline-none focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2"><spring:message code="viewProfile.connect"/></button>
                    </a>
                    <c:if test="${status.equals('error')}">
                        <p class="text-red-600 text-xs"><spring:message code="viewProfile.errorExists"/></p>
                    </c:if>
                </div>
                </sec:authorize>
                <sec:authorize access="hasAuthority('EMPLOYEE')">
                    <div class="ml-3 col-start-5 row-start-2">
                        <a href="<c:url value="/editarPerfil"/>">
                            <button class="h-fit w-fit text-xs text-white bg-violet-400 border border-purple-900 focus:outline-none focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2"><spring:message code="viewProfile.editProfile"/></button>
                        </a>
                    </div>
                </sec:authorize>
            </div>
            <div class="grid grid-cols-2">
                <div class="col-span-1">
                    <h1 class="pb-3 pt-3 font-semibold" onclick="printValue('${status}')"><spring:message code="viewProfile.abilities"/></h1>
                    <ul role="list" class="list-inside marker:text-purple-900 list-disc pl-5 space-y-3 text-gray-500">
                        <c:forEach var="ability" items="${employee.abilitiesArr}">
                            <li><c:out value="${ability}"/></li>
                        </c:forEach>
                    </ul>
                </div>
                <div class="col-span-1 col-start-2">
                    <h1 class="pb-3 pt-3 font-semibold"><spring:message code="viewProfile.availability"/></h1>
                    <ul role="list" class="list-inside marker:text-purple-900 list-disc pl-5 space-y-3 text-gray-500">
                        <c:forEach var="availability" items="${employee.availabilityArr}">
                            <li><c:out value="${availability}"/></li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<c:if test="${status.equals('sent')}">
    <div class="absolute bottom-6 inset-x-1/3">
        <div class = "grid justify-items-center bg-purple-600 bg-opacity-25 rounded-full">
            <p><spring:message code="viewProfile.sent"/></p>
        </div>
    </div>
</c:if>
</body>

<script>
    function printValue(value){
        console.log(value)
    }
    
</script>