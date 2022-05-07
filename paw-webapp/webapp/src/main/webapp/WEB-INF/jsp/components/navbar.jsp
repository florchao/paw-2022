<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <title>navbar</title>
</head>
<body>
<c:url value="/redirectSearch" var = "search"/>
<c:url value="/redirectContacts" var = "contacts"/>
<c:url value="/" var = "home"/>
<nav class="bg-white absolute w-full px-2 sm:px-4 py-2.5 shadow-md" style="background-color: #ac70ff">
    <div class="h-12 grid grid-cols-5 space-between">
        <div class = "flex items-center mx-8">
        <form:form method="get" action="${home}" pageEncoding="UTF-8">
            <button><img src="<c:url value='/public/hogar.png'/>" alt="logo" class="mr-3 h-9"></button>
        </form:form>
        </div>
        <c:if test="${!param.currentUrl.equals('init')}">
            <c:if test="${param.currentUrl.equals('searchPage')}">
                <c:url value="/filterEmployees" var="postPath"/>
                <div class="col-start-2">
                    <div class="search-box">
                        <form:form modelAttribute="filterBy" action="${postPath}" method="get" pageEncoding="UTF-8">
                            <form:input type="text" path="name" cssStyle="border-radius: 5px;"/>
                            <button type="submit"><i class="fa fa-search"></i></button>
                        </form:form>
                    </div>
                </div>
            </c:if>
            <div class="grid grid-cols-3 items-center justify-items-center col-span-2 col-start-4">
                <sec:authorize access="!isAuthenticated()">
                    <div class = "items-center ">
                        <c:choose>
                            <c:when test="${param.currentUrl.equals('register')}">
                                <p class="text-m whitespace-nowrap font-semibold text-violet-900"><spring:message code="navbar.register"/></p>
                            </c:when>
                            <c:otherwise>
                                <a href="<c:url value="/registrarse"/>" class="text-m whitespace-nowrap font-semibold hover:text-violet-300 text-white"><spring:message code="navbar.register"/></a>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </sec:authorize>
                <sec:authorize access="!isAuthenticated() || hasAuthority('EMPLOYER')">
                    <div class = "items-center">
                        <form:form method="get" action="${search}" pageEncoding="UTF-8">
                            <c:choose>
                                <c:when test="${param.currentUrl.equals('searchPage')}">
                                    <p class="text-m whitespace-nowrap font-semibold text-violet-900"><spring:message code="navbar.searchEmployee"/></p>
                                </c:when>
                                <c:otherwise>
                                    <a href="<c:url value="/buscarEmpleadas"/>" class="text-m whitespace-nowrap font-semibold hover:text-violet-300 text-white"><spring:message code="navbar.searchEmployee"/></a>
                                </c:otherwise>
                            </c:choose>
                        </form:form>
                    </div>
                </sec:authorize>
                <sec:authorize access="hasAuthority('EMPLOYER')">
                    <div class = "items-center">
                        <c:choose>
                            <c:when test="${param.currentUrl.equals('publishedJobs')}">
                                <p class="text-m whitespace-nowrap font-semibold text-violet-900"><spring:message code="navbar.jobs"/></p>
                            </c:when>
                            <c:otherwise>
                                <a href="<c:url value="/trabajos"/>"  class="text-m whitespace-nowrap font-semibold hover:text-violet-300 text-white"><spring:message code="navbar.jobs"/></a>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </sec:authorize>
                <sec:authorize access="hasAuthority('EMPLOYEE') && isAuthenticated()">
                <div class = "flex items-center justify-items-end">
                    <form:form method="get" action="${contacts}" pageEncoding="UTF-8">
                        <c:choose>
                            <c:when test="${param.currentUrl.equals('contactos')}">
                                <p class="text-m whitespace-nowrap font-semibold text-violet-900"><spring:message code="navbar.contacts"/></p>
                            </c:when>
                            <c:otherwise>
                                <button class="text-m whitespace-nowrap font-semibold hover:text-violet-300 text-white"><spring:message code="navbar.contacts"/></button>
                            </c:otherwise>
                        </c:choose>
                    </form:form>
                </div>
                </sec:authorize>
                <sec:authorize access="hasAuthority('EMPLOYEE') && isAuthenticated()">
                <div class = "flex items-center justify-items-end">
                    <form:form method="get" action="${contacts}" pageEncoding="UTF-8">
                        <c:choose>
                            <c:when test="${param.currentUrl.equals('verPerfil')}">
                                <p class="text-m whitespace-nowrap font-semibold text-violet-900"><spring:message code="navbar.profile"/></p>
                            </c:when>
                            <c:otherwise>
                                <a href="<c:url value="/verPerfil"/>" class="text-m whitespace-nowrap font-semibold hover:text-violet-300 text-white"><spring:message code="navbar.profile"/></a>
                            </c:otherwise>
                        </c:choose>
                    </form:form>
                </div>
                </sec:authorize>
                <sec:authorize access="isAuthenticated()">
                    <div class = "items-center">
                        <a href="<c:url value="/logout"/>" class="text-m whitespace-nowrap font-semibold hover:text-violet-300 text-white"><spring:message code="navbar.logout"/></a>
                    </div>
                </sec:authorize>
            </div>
        </c:if>
    </div>
</nav>
</body>
</html>