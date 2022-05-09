<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>


<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
    <title>employeeCard</title>
</head>
<body>
<div class="max-w-sm mb-5 mr-5 w-80 h-52 bg-white rounded-lg border border-gray-200 shadow-md overflow-hidden" >
    <div class="flex justify-end px-4 pt-4">
        <p class="hidden sm:inline-block text-gray-500 text-sm p-1.5">
            <c:out value="${param.location}"/>
        </p>
    </div>
    <div class="flex flex-col items-center pb-6 ">
        <c:choose>
            <c:when test="${param.title.length() <= 20}">
                <h5 class="mb-1 text-xl font-medium text-gray-900"><c:out value="${param.title}"/></h5>
            </c:when>
            <c:otherwise>
                <h5 class="mb-1 text-xl font-medium text-gray-900"><c:out value="${param.title.substring(0, 20)}"/>...</h5>
            </c:otherwise>
        </c:choose>
        <c:choose>
            <c:when test="${param.description.length() <= 60}">
                <span class="flex flex-wrap text-sm text-gray-500 text-ellipsis px-5" style="width:90%; display:inline-block; word-wrap: break-word;"><c:out value="${param.description}"/></span>
            </c:when>
            <c:otherwise>
                <span class="flex flex-wrap text-sm text-gray-500 text-ellipsis px-5" style="width:18rem; display:inline-block; word-wrap: break-word;"><c:out value="${param.description.substring(0, 60)}"/>...</span>
            </c:otherwise>
        </c:choose>
    </div>
    <div class="grid grid-col-2 w-80 justify-center">
        <div>
            <a href="<c:url value="/trabajo/${param.jobid}"/>" style="margin-right: 15px" class="text-sm focus:outline-none text-violet-900 bg-purple-900 bg-opacity-30 hover:bg-purple-900 hover:bg-opacity-50 font-small rounded-lg text-sm px-5 py-2.5"><spring:message code="jobCard.publication"/></a>
            <a href="<c:url value="/aplicantes/${param.jobid}"/>" class="text-sm focus:outline-none text-violet-900 bg-purple-900 bg-opacity-30 hover:bg-purple-900 hover:bg-opacity-50 font-small rounded-lg text-sm px-5 py-2.5"><spring:message code="jobCard.applicants"/></a>
        </div>
    </div>
</div>
</body>
</html>
