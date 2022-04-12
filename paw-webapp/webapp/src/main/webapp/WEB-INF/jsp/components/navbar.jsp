<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
    <title>navbar</title>
</head>
<body>
<c:url value="/redirectSearch" var = "search"/>
<c:url value="/redirectCreateProfile" var = "createProfile"/>
<c:url value="/" var = "home"/>
<nav class="bg-white absolute w-full px-2 sm:px-4 py-2.5 dark:bg-violet-500">
    <div class="h-30 grid grid-cols-3 space-between">
        <div class = "flex items-center mx-8">
        <form:form method="get" action="${home}">
            <button><img src="<c:url value='/public/hogar.png'/>" alt="logo" class="mr-3 h-6 sm:h-9"></button>
        </form:form>
        </div>
        <c:if test="${!param.currentUrl.equals('init')}">
            <div class="h-30 grid grid-cols-2 col-start-3">
                <div class = "flex items-center justify-items-end">
                    <form:form method="get" action="${search}">
                        <c:choose>
                            <c:when test="${param.currentUrl.equals('seachPage')}">
                                <button class="text-m whitespace-nowrap font-semibold text-violet-900"><spring:message code="navbar.searchEmployee"/></button>
                            </c:when>
                            <c:otherwise>
                                <button class="text-m whitespace-nowrap font-semibold hover:text-violet-300 text-white"><spring:message code="navbar.searchEmployee"/></button>
                            </c:otherwise>
                        </c:choose>
                    </form:form>
                </div>
                <div class = "flex items-center justify-items-end">
                    <form:form method="get" action="${createProfile}">
                        <c:choose>
                            <c:when test="${param.currentUrl.equals('createProfile')}">
                                <button class="text-m whitespace-nowrap font-semibold text-violet-900"><spring:message code="navbar.createProfile"/></button>
                            </c:when>
                            <c:otherwise>
                                <button class="text-m whitespace-nowrap font-semibold hover:text-violet-300 text-white"><spring:message code="navbar.createProfile"/></button>
                            </c:otherwise>
                        </c:choose>
                    </form:form>
                </div>
            </div>
        </c:if>
    </div>
</nav>
</body>
</html>