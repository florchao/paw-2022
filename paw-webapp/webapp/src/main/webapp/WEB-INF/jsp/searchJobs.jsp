<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html class="scroll-smooth">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<c:url value="/public/css/style.css"/>"/>
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="<c:url value="/public/javascript/utils.js"/>"></script>
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
    <jsp:param name="currentUrl" value="trabajos"/>
</jsp:include>
<div class="grid content-start h-screen overflow-auto pl-5 pr-5">
    <div class="my-9 w-full"></div>
    <div class="grid grid-cols-4">
        <div class="col-start-1 bg-purple-300 mr-8 p-6 rounded-2xl mt-2 shadow-xl border-solid border-violet-500 border-2 h-fit w-fit" >
            <c:url value="/trabajos" var="getReturnPath"/>
            <form method="get" action="${getReturnPath}" id="returnForm">
                <button style="color: rebeccapurple" class="font-semibold" form="returnForm" id="resetButton"><spring:message code="searchJobs.resetFilters"/></button>
            </form>
            <c:url value="/filterJobs" var="getPath"/>
            <form:form modelAttribute="filterJobsBy" action="${getPath}" method="get" pageEncoding="UTF-8">
            <div class="flex flex-col items-center">
                <h1 class="font-semibold mt-2"><spring:message code="searchPage.label.experienceYears"/></h1>
                <div class="grid grid-cols-12">
                    <form:input type="number" id="expYears" onchange="validateEmptyNumberForm('expYears')" path="experienceYears" class="col-span-10 col-start-2" cssStyle="border-radius: 5px; padding-left: 5px"/>
                    <form:errors path="experienceYears" element="p" class="col-start-2 col-span-full" cssStyle="color:red"/>
                </div>
                <h1 class="font-semibold mt-4"><spring:message code="searchPage.label.location"/></h1>
                <div class="grid grid-cols-12">
                    <form:input type="text" path="location" class="col-span-10 col-start-2" cssStyle="border-radius: 5px; padding-left: 5px"/>
                    <form:errors path="location" element="p"  class="col-start-2 col-span-full" cssStyle="color:red"/>
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
                <div class="grid grid-cols-4 w-5/6">
                    <form:errors path="abilities" element="p"  class="col-start-2 col-span-full" cssStyle="color:red"/>
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
                <button type="submit" class="mt-4 border shadow-md text-lg w-5/6 focus:outline-none text-violet-900 bg-purple-400 border border-purple-900 hover:bg-yellow-300 hover:bg-opacity-50 font-small rounded-lg text-sm px-5 py-2.5"><spring:message code="searchJobs.filter"></spring:message></button>
            </div>
        </div>
        <div class="col-span-3 col-start-2">
            <p class="text-3xl font-semibold text-violet-900 mb-4"><spring:message code="searchJobs.availableJobs"/></p>
            <form:input type="text" path="name" cssClass="hidden" cssStyle="border-radius: 5px;"/>
            <c:choose>
            <c:when test="${jobList.size() == 0}">
                <div class = "grid content-center justify-center h-5/6 mt-16">
                    <div class = "grid justify-items-center">
                        <img src="<c:url value='/public/sinTrabajos.png'/>" alt="sinTrabajos" class="mr-3 h-6 sm:h-52">
                        <p class="text-3xl font-semibold text-purple-700"><spring:message code="searchJobs.noAvailableJobs"/></p>
                    </div>
                    <div>
                        <input type="button" class="cursor-pointer mt-4 ml-5 border shadow-md text-lg w-5/6 focus:outline-none text-violet-900 bg-yellow-300 hover:bg-yellow-200 border font-small rounded-lg text-sm px-5 py-2.5" onclick="document.getElementById('resetButton').click()" value="<spring:message code="searchJobs.resetFilters"/>">
                    </div>
                </div>
            </c:when>
            <c:otherwise>
            <div class="flex flex-wrap content-start justify-center">
                <c:forEach var="entry" items="${jobList}">
                    <c:set var="job" value="${entry.key}" scope="request"/>
                    <c:set var="applied" value="${entry.value}" scope="request"/>
                    <div>
                        <% request.setCharacterEncoding("utf-8");%>
                        <jsp:include page="components/jobCard.jsp">
                            <jsp:param name="title" value="${job.title}"/>
                            <jsp:param name="description" value="${job.description}"/>
                            <jsp:param name = "location" value = "${job.location}"/>
                            <jsp:param name="jobid" value="${job.jobId}"/>
                            <jsp:param name="apply" value="${applied}"/>
                        </jsp:include>
                    </div>
                </c:forEach>
            </div>
                </c:otherwise>
                </c:choose>
                <c:if test="${maxPage > 0  && page + 1 <= maxPage}">
                <div class="flex flex-row justify-center">
                    <c:choose>
                        <c:when test="${page < 1}">
                            <button type="submit" class="font-semibold border shadow-md focus:outline-none text-violet-900 bg-gray-300 border-purple-900 rounded-lg px-2" disabled="true" onclick="previousPage(${page})"><</button>
                        </c:when>
                        <c:otherwise>
                            <button type="submit" class="font-semibold border shadow-md focus:outline-none text-violet-900 bg-purple-400 border-purple-900 hover:bg-yellow-300 hover:bg-opacity-50 rounded-lg px-2" onclick="previousPage(${page})"><</button>
                        </c:otherwise>
                    </c:choose>
                    <div class="bg--300 w-16 flex justify-center">
                        <h1 class="text-yellow-300">${page + 1} of ${maxPage}</h1>
                    </div>
                    <c:choose>
                        <c:when test="${page + 1 == maxPage}">
                            <button type="submit" class="font-semibold border shadow-md focus:outline-none text-violet-900 bg-gray-300 border-purple-900 rounded-lg px-2" disabled="true" onclick="nextPage(${page})">></button>
                        </c:when>
                        <c:otherwise>
                            <button type="submit" id="prevPageButton" class=" font-semibold border shadow-md focus:outline-none text-violet-900 bg-purple-400 border-purple-900 hover:bg-yellow-300 hover:bg-opacity-50 rounded-lg px-2" onclick="nextPage(${page})">></button>
                        </c:otherwise>
                    </c:choose>
                    </c:if>
                </div>
                <form:input cssStyle="visibility: hidden" type="number" id="pageNumber" path="pageNumber"/>
                </form:form>

            </div>
        </div>
    </div>
</body>
</html>
<style>
    /* Links inside the dropdown */
    .dropdown-content a {
        color: black;
        padding: 12px 16px;
        text-decoration: none;
        display: block;
    }

    /* Change color of dropdown links on hover */
    .dropdown-content a:hover {background-color: #f1f1f1}

</style>