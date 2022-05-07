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
    <title><spring:message code="viewJob.title"/></title>
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
<c:url value="/apply/${id}" var="postPath"/>
<div class="grid h-screen grid-cols-6 overflow-auto">
    <div class=" grid grid-row-4 col-span-4 col-start-2">
        <div class=" bg-gray-200 rounded-3xl overflow-auto p-5 mt-24 mb-5 shadow-2xl">
            <div class="grid grid-cols-5 justify-center">
                <div class="mb-2 col-span-5">
                    <p class="text-2xl font-semibold whitespace-nowrap text-purple-900 text-ellipsis overflow-hidden"><c:out value="${job.title}"/></p>
                    <p class="text-sm whitespace-nowrap text-ellipsis overflow-hidden"><spring:message code="viewJob.by"/><c:out value="${job.employerName}"/></p>
                </div>
                <div class="col-span-2">
                    <h1 class="pb-3 pt-3 text-purple-900 font-semibold"><spring:message code="viewJob.location"/></h1>
                    <h1 class="block mb-2 ml-2 text-sm font-medium text-gray-600 text-ellipsis overflow-hidden"> <c:out value="${job.location}"/></h1>
                </div>
                <div class="col-span-2">
                    <h1 class="pb-3 pt-3 text-purple-900 font-semibold"><spring:message code="viewJob.experience"/></h1>
                    <h1 class="block mb-2 ml-2 text-sm font-medium text-gray-600 "> <c:out value="${job.experienceYears}"/></h1>
                </div>
                <sec:authorize access="hasAuthority('EMPLOYEE')">
                <div class="ml-3 col-start-5 row-start-2">
                    <form:form action="${postPath}" method="post">
                        <button class="h-fit w-fit text-xs text-white bg-violet-400 border border-purple-900 focus:outline-none focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2"><spring:message code="viewJob.apply"/></button>
                    </form:form>
                </div>
                </sec:authorize>
            </div>
            <div class="grid grid-cols-5">
                <div class="col-span-2">
                    <h1 class="pb-3 pt-3 font-semibold text-purple-900"><spring:message code="viewJob.abilities"/></h1>
                    <ul role="list" class="list-inside marker:text-purple-900 list-disc pl-5 space-y-3 text-gray-500">
                        <c:forEach var="ability" items="${job.abilitiesArr}">
                            <li><c:out value="${ability}"/></li>
                        </c:forEach>
                    </ul>
                </div>
                <div class="col-span-1 col-start-3">
                    <h1 class="pb-3 pt-3 font-semibold text-purple-900"><spring:message code="viewJob.availability"/></h1>
                    <ul role="list" class="list-inside marker:text-purple-900 list-disc pl-5 space-y-3 text-gray-500">
                        <c:forEach var="availability" items="${job.availabilityArr}">
                            <li><c:out value="${availability}"/></li>
                        </c:forEach>
                    </ul>
                </div>
                <div class="col-span-5">
                    <h1 class="pb-3 pt-3 font-semibold text-purple-900"><spring:message code="viewJob.description"/></h1>
                    <h1 class="block ml-2 mb-2 text-sm font-medium text-gray-600 text-ellipsis overflow-hidden"> <c:out value="${job.description}"/></h1>
                </div>
            </div>
        </div>
    </div>
</div>
</body>