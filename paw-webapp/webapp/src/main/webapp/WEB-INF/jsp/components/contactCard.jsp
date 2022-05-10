<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
    <title>employeeCard</title>
</head>
<body>
<div class="max-w-sm mb-5 mr-5 px-5 w-80 h-80 bg-white rounded-lg border border-gray-200 shadow-md overflow-hidden" >
    <div class="flex justify-end px-4 pt-4">
        <p class="hidden sm:inline-block text-gray-500 text-sm p-1.5">
            <c:out value="${param.date}"/>
        </p>
    </div>
    <div class="flex flex-col items-center pb-6 ">
        <img class="mb-3 w-24 h-24 rounded-full shadow-lg" src="<c:url value="/user/profile-image/${param.employerID}"/>" alt="" onerror="this.src = '<c:url value="/public/user.png"/>'"/>
        <h5 class="mb-1 text-xl font-medium text-gray-900"><c:out value="${param.name}"/></h5>
        <c:choose>
            <c:when test="${param.message.length() <= 180}">
                <span class="flex flex-wrap text-sm text-gray-500 text-ellipsis px-5" style="width:90%; display:inline-block; word-wrap: break-word;"><c:out value="${param.message}"/></span>
            </c:when>
            <c:otherwise>
                <span class="flex flex-wrap text-sm text-gray-500 text-ellipsis px-5" style="width:18rem; display:inline-block; word-wrap: break-word;"><c:out value="${param.message.substring(0, 180)}"/>...</span>
            </c:otherwise>
        </c:choose>
    </div>
</div>
</body>
</html>
