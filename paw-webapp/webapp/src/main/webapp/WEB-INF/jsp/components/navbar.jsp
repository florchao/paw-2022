<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <title>navbar</title>
</head>
<body>
<c:url value="/redirectSearch" var = "search"/>
<c:url value="/redirectContacts" var = "contacts"/>
<c:url value="/" var = "home"/>
<nav class="bg-white absolute w-full px-2 sm:px-4 py-2.5 shadow" style="background-color: #ac70ff">
    <div class="h-12 grid grid-cols-3 space-between">
        <div class = "flex items-center mx-8">
        <form:form method="get" action="${home}">
            <button><img src="<c:url value='/public/hogar.png'/>" alt="logo" class="mr-3 h-9"></button>
        </form:form>
        </div>
        <c:if test="${!param.currentUrl.equals('init')}">
            <c:if test="${param.currentUrl.equals('searchPage')}">
                <c:url value="/filterEmployees" var="postPath"/>
                <div class="col-start-2 ">
                    <div class="search-box">
                        <form:form modelAttribute="filterBy" action="${postPath}" method="get">
                            <form:input type="text" path="name" cssStyle="border-radius: 5px;"/>
                            <button type="submit"><i class="fa fa-search"></i></button>
                        </form:form>
                    </div>
                </div>
            </c:if>
            <div class="grid grid-cols-3 col-start-3">
                <sec:authorize access="!isAuthenticated()">
                    <div class = "flex items-center justify-items-end">
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
                <div class = "flex col-start-2 items-center justify-items-end">

                    <form:form method="get" action="${search}">
                        <c:choose>
                            <c:when test="${param.currentUrl.equals('searchPage')}">

<%--                                <div class="search-box">--%>
<%--                                    <button class="btn-search"><i class="fas fa-search"></i></button>--%>
<%--                                    <input type="text" class="input-search" placeholder="Type to Search...">--%>
<%--                                </div>--%>

                                <p class="text-m whitespace-nowrap font-semibold text-violet-900"><spring:message code="navbar.searchEmployee"/></p>
                            </c:when>
                            <c:otherwise>
                                <a href="<c:url value="/buscarEmpleadas"/>" class="text-m whitespace-nowrap font-semibold hover:text-violet-300 text-white"><spring:message code="navbar.searchEmployee"/></a>
                            </c:otherwise>
                        </c:choose>
                    </form:form>
                </div>
                </sec:authorize>
                <sec:authorize access="hasAuthority('EMPLOYEE') && isAuthenticated()">
                <div class = "flex items-center justify-items-end">
                    <form:form method="get" action="${contacts}">
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
                    <form:form method="get" action="${contacts}">
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
                    <div class = "flex items-center justify-items-end">
                        <a href="<c:url value="/logout"/>" class="text-m whitespace-nowrap font-semibold hover:text-violet-300 text-white"><spring:message code="navbar.logout"/></a>
                    </div>
                </sec:authorize>
            </div>
        </c:if>
    </div>
</nav>
</body>
</html>

<%--<style>--%>
<%--    /**{*/--%>
<%--    /*    box-sizing: border-box;*/--%>
<%--    /*}*/--%>
<%--    /*body{*/--%>
<%--    /*    margin: 0px;*/--%>
<%--    /*    padding: 0px;*/--%>
<%--    /*    width: 100vw;*/--%>
<%--    /*    height: 100vh;*/--%>
<%--    /*    display: flex;*/--%>
<%--    /*    justify-content: center;*/--%>
<%--    /*    align-items: center;*/--%>
<%--    /*    background-color: #130f40;*/--%>
<%--    /*}*/--%>
<%--    .search-box{--%>
<%--        width: fit-content;--%>
<%--        height: fit-content;--%>
<%--        position: relative;--%>
<%--    }--%>
<%--    .input-search{--%>
<%--        height: 50px;--%>
<%--        width: 50px;--%>
<%--        border-style: none;--%>
<%--        padding: 10px;--%>
<%--        font-size: 18px;--%>
<%--        letter-spacing: 2px;--%>
<%--        outline: none;--%>
<%--        border-radius: 25px;--%>
<%--        transition: all .5s ease-in-out;--%>
<%--        background-color: #22a6b3;--%>
<%--        padding-right: 40px;--%>
<%--        color:#fff;--%>
<%--    }--%>
<%--    .input-search::placeholder{--%>
<%--        color:rgba(255,255,255,.5);--%>
<%--        font-size: 18px;--%>
<%--        letter-spacing: 2px;--%>
<%--        font-weight: 100;--%>
<%--    }--%>
<%--    .btn-search{--%>
<%--        width: 50px;--%>
<%--        height: 50px;--%>
<%--        border-style: none;--%>
<%--        font-size: 20px;--%>
<%--        font-weight: bold;--%>
<%--        outline: none;--%>
<%--        cursor: pointer;--%>
<%--        border-radius: 50%;--%>
<%--        position: absolute;--%>
<%--        right: 0px;--%>
<%--        color:#ffffff ;--%>
<%--        background-color:transparent;--%>
<%--        pointer-events: painted;--%>
<%--    }--%>
<%--    .btn-search:focus ~ .input-search{--%>
<%--        width: 300px;--%>
<%--        border-radius: 0px;--%>
<%--        background-color: transparent;--%>
<%--        border-bottom:1px solid rgba(255,255,255,.5);--%>
<%--        transition: all 500ms cubic-bezier(0, 0.110, 0.35, 2);--%>
<%--    }--%>
<%--    .input-search:focus{--%>
<%--        width: 300px;--%>
<%--        border-radius: 0px;--%>
<%--        background-color: transparent;--%>
<%--        border-bottom:1px solid rgba(255,255,255,.5);--%>
<%--        transition: all 500ms cubic-bezier(0, 0.110, 0.35, 2);--%>
<%--    }--%>
<%--</style>--%>