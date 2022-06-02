<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
    <title>employeeCard</title>
    <meta charset="UTF-8"/>
</head>
<body>
    <c:url value="/user/profile-image/${param.id}" var="image" />
    <a href="<c:url value="/verPerfil/${param.id}"/>" class="grid grid-cols-8 items-center bg-white rounded-lg border shadow-md mb-5 md:flex-row md:max-w-full hover:bg-gray-100 ">
        <img style="max-height: 150px" class="object-cover w-full h-96 rounded-t-lg md:h-auto md:w-48 md:rounded-none md:rounded-l-lg col-start-1 col-span-1" src="${image}" alt="user photo" onerror="this.src = '<c:url value="/public/user.png"/>'"/>
        <div class="flex flex-col justify-between p-4 leading-normal col-start-2 col-span-4">
            <h5 class="mb-2 text-2xl font-bold tracking-tight text-black text-ellipsis overflow-hidden"><c:out value="${param.name}"/></h5>
            <p class="mb-3 font-normal text-gray-400 text-ellipsis overflow-hidden"><c:out value="${param.location}"/></p>
            <p class="mb-3 font-normal text-gray-700 dark:text-gray-400 "><spring:message code="employeeCardComponent.experience" htmlEscape="true" arguments="${param.years}"/>
            </p>
        </div>
        <c:if test="${param.contacted != null && param.contacted}">
            <div class="col-start-7 col-span-1">
                <p class="h-fit w-full text-xs text-white bg-gray-400 border border-gray-900 font-medium rounded-full px-5 py-2.5 mr-2.5 mb-2"><spring:message code="viewProfile.alreadyConnected"/></p>
            </div>
        </c:if>
    </a>
</body>

</html>