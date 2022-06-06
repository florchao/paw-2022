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
<c:url value="/login" var = "login"/>
<c:url value="/contactanos" var = "contactus"/>
<c:url value="/" var = "home"/>
<nav class="bg-white absolute w-full px-2 sm:px-4 py-2.5 shadow-md" style="background-color: #ac70ff">
    <div class="h-12 grid grid-cols-10 space-between">
        <div class = "flex items-center mx-8 col-span-2">
        <form:form method="get" action="${home}" pageEncoding="UTF-8">
            <button><img src="<c:url value='/public/hogar.png'/>" alt="logo" class="mr-3 h-9"></button>
        </form:form>
        </div>
        <c:if test="${!param.currentUrl.equals('init') && !param.currentUrl.equals('createProfile')}">
            <c:if test="${param.currentUrl.equals('searchPage')}">
                <c:url value="/filterEmployees" var="postPath"/>
                <div class="col-start-3 col-span-2">
                    <div class="search-box mt-2">
                        <form:form modelAttribute="filterBy" action="${postPath}" method="get" pageEncoding="UTF-8">
                            <form:input type="text" path="name" cssStyle="border-radius: 5px;background-color: #ac70ff;border-width: 0 0 2px;border-color: #8a52d9;"/>
                            <button type="submit"><i class="fa fa-search"></i></button>
                        </form:form>
                    </div>
                </div>
            </c:if>
            <c:if test="${param.currentUrl.equals('trabajos')}">
                <c:url value="/filterJobs" var="postPath"/>
                <div class="col-start-3 col-span-2">
                    <div class="search-box mt-2">
                        <form:form modelAttribute="filterJobsBy" action="${postPath}" method="get" pageEncoding="UTF-8">
                            <form:input type="text" path="name" cssStyle="border-radius: 5px;background-color: #ac70ff;border-width: 0 0 2px;border-color: #8a52d9;"/>
                            <button type="submit"><i class="fa fa-search"></i></button>
                        </form:form>
                    </div>
                </div>
            </c:if>
            <div class="flex felx-wrap grid grid-cols-6 items-center justify-items-center col-span-5 col-start-6">
                <sec:authorize access="hasAuthority('EMPLOYEE') && isAuthenticated()">
                    <div class = "flex items-center justify-items-end">
                            <c:choose>
                                <c:when test="${param.currentUrl.equals('trabajos')}">
                                    <p class="text-sm whitespace-nowrap font-semibold text-violet-900"><spring:message code="navbar.exploreJobs"/></p>
                                </c:when>
                                <c:otherwise>
                                    <a href="<c:url value="/trabajos"/>" class="text-sm whitespace-nowrap font-semibold hover:text-violet-300 text-white"><spring:message code="navbar.exploreJobs"/></a>
                                </c:otherwise>
                            </c:choose>
                    </div>
                </sec:authorize>
                <sec:authorize access="!isAuthenticated() || hasAuthority('EMPLOYER')">
                    <div class = "items-center col-start-2">
                            <c:choose>
                                <c:when test="${param.currentUrl.equals('searchPage')}">
                                    <p class="text-sm whitespace-nowrap font-semibold text-violet-900"><spring:message code="navbar.searchEmployee"/></p>
                                </c:when>
                                <c:otherwise>
                                    <a href="<c:url value="/buscarEmpleadas"/>" class="text-sm whitespace-nowrap font-semibold hover:text-violet-300 text-white"><spring:message code="navbar.searchEmployee"/></a>
                                </c:otherwise>
                            </c:choose>
                    </div>
                </sec:authorize>
                <sec:authorize access="hasAuthority('EMPLOYER')">
                    <div class = "items-center">
                        <c:choose>
                            <c:when test="${param.currentUrl.equals('publishedJobs')}">
                                <p class="text-sm whitespace-nowrap font-semibold text-violet-900"><spring:message code="navbar.jobs"/></p>
                            </c:when>
                            <c:otherwise>
                                <a href="<c:url value="/misTrabajos"/>"  class="text-sm whitespace-nowrap font-semibold hover:text-violet-300 text-white"><spring:message code="navbar.jobs"/></a>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </sec:authorize>
                <sec:authorize access="hasAuthority('EMPLOYEE') && isAuthenticated()">
                    <div class = "flex items-center justify-items-end">
                        <c:choose>
                            <c:when test="${param.currentUrl.equals('trabajosAplicados')}">
                                <p class="text-sm whitespace-nowrap font-semibold text-violet-900"><spring:message code="navbar.appliedJobs"/></p>
                            </c:when>
                            <c:otherwise>
                                <a href="<c:url value="/trabajosAplicados"/>" class="text-sm whitespace-nowrap font-semibold hover:text-violet-300 text-white"><spring:message code="navbar.appliedJobs"/></a>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </sec:authorize>
                <sec:authorize access="hasAuthority('EMPLOYEE') && isAuthenticated()">
                <div class = "flex items-center justify-items-end">
                        <c:choose>
                            <c:when test="${param.currentUrl.equals('contactos')}">
                                <p class="text-sm whitespace-nowrap font-semibold text-violet-900"><spring:message code="navbar.contacts"/></p>
                            </c:when>
                            <c:otherwise>
                                <a href="<c:url value="/contactos"/>" class="text-sm whitespace-nowrap font-semibold hover:text-violet-300 text-white"><spring:message code="navbar.contacts"/></a>
                            </c:otherwise>
                        </c:choose>
                </div>
                </sec:authorize>
                <sec:authorize access="hasAuthority('EMPLOYEE') && isAuthenticated()">
                <div class = "flex items-center justify-items-end">
                    <form:form method="get" action="${contacts}" pageEncoding="UTF-8">
                        <c:choose>
                            <c:when test="${param.currentUrl.equals('verPerfil')}">
                                <p class="text-sm whitespace-nowrap font-semibold text-violet-900"><spring:message code="navbar.profile"/></p>
                            </c:when>
                            <c:otherwise>
                                <a href="<c:url value="/verPerfil"/>" class="text-sm whitespace-nowrap font-semibold hover:text-violet-300 text-white"><spring:message code="navbar.profile"/></a>
                            </c:otherwise>
                        </c:choose>
                    </form:form>
                </div>
                </sec:authorize>
                <sec:authorize access="!isAuthenticated()">
                    <div class = "items-center ">
                        <c:choose>
                            <c:when test="${param.currentUrl.equals('register')}">
                                <p class="text-sm whitespace-nowrap font-semibold text-violet-900"><spring:message code="navbar.register"/></p>
                            </c:when>
                            <c:otherwise>
                                <a href="<c:url value="/registrarse"/>" class="text-sm whitespace-nowrap font-semibold hover:text-violet-300 text-white"><spring:message code="navbar.register"/></a>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class = "items-center ">
                        <c:choose>
                            <c:when test="${param.currentUrl.equals('login')}">
                                <p class="text-sm whitespace-nowrap font-semibold text-violet-900"><spring:message code="navbar.login"/></p>
                            </c:when>
                            <c:otherwise>
                                <a href="<c:url value="/login"/>" class="text-sm whitespace-nowrap font-semibold hover:text-violet-300 text-white"><spring:message code="navbar.login"/></a>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </sec:authorize>
                <div class = "items-center ">
                    <c:choose>
                        <c:when test="${param.currentUrl.equals('contactUs')}">
                            <p class="text-sm whitespace-normal font-semibold text-violet-900"><spring:message code="navbar.contactus"/></p>
                        </c:when>
                        <c:otherwise>
                            <a href="<c:url value="/contactanos"/>" class="text-sm whitespace-nowrap font-semibold hover:text-violet-300 text-white"><spring:message code="navbar.contactus"/></a>
                        </c:otherwise>
                    </c:choose>
                </div>
                <sec:authorize access="isAuthenticated()">
                    <div class = "items-center">
                        <a href="<c:url value="/logout"/>" class="text-sm whitespace-nowrap font-semibold hover:text-violet-300 text-white"><spring:message code="navbar.logout"/></a>
                    </div>
                </sec:authorize>
            </div>
        </c:if>
    </div>
</nav>
</body>
</html>