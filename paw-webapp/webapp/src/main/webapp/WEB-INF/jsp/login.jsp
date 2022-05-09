<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
    <title><spring:message code="login.title"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<c:url value="/public/css/style.css"/>"/>
    <link rel="icon" type="image/x-icon" href="<c:url value="/public/favicon.png"/>"/>
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="../../public/javascript/utils.js"></script>
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
        <jsp:param name="currentUrl" value="login"/>
    </jsp:include>
    <c:url value="/login" var="loginUrl"/>
    <div class="grid grid-cols-7 content-center justify-center h-screen pt-5">
            <form modelAttribute="login" class="col-span-3 col-start-3" action="${loginUrl}" method="post" enctype = "application/x-www-form-urlencoded">
                <div class="block p-6 rounded-lg shadow-lg bg-white">
                    <div class="form-group mb-6 grid grid-cols-6">
                        <label for="username" class="text-sm font-medium text-gray-900"><spring:message code="login.mail"/></label>
                        <input id="username" name="j_username" type="text" class=" col-span-5 block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-blue-500 focus:border-violet-500"/>
                    </div>
                    <div class="form-group mb-6 grid grid-cols-6">
                        <label for="password" class="text-sm font-medium text-gray-900"><spring:message code="login.password"/></label>
                        <input id="password" name="j_password" type="password" class=" col-span-5 block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-blue-500 focus:border-violet-500"/>
                    </div>
                    <div class="form-group mb-6">
                                            <p class="text-sm font-normal text-red-500">
                                                <c:if test="${param.error}"> <spring:message code="login.error"/> </c:if>
                                            </p>
                                        </div>
                     <div class="form-group mb-6">
                        <label class="text-sm font-medium text-gray-900">
                        <input name="j_rememberme" type="checkbox"/>
                        <spring:message code="remember_me"/>
                        </label>
                     </div>
                    <div class="form-group mb-6">
                    <button type="submit" class="text-lg w-full focus:outline-none text-violet-900 bg-purple-900 bg-opacity-30 hover:bg-purple-900 hover:bg-opacity-50 font-small rounded-lg text-sm px-5 py-2.5"> <spring:message code="login.button"/></button>
                     </div>
                    <div class="form-group mb-6">
                        <p class="text-sm font-medium text-gray-900">
                            <spring:message code="create.account"/>
                            <a class="text-violet-900" href="<c:url value="/registrarse"/>"><spring:message code="login.register"/></a>
                        </p>
                    </div>
                    <div class="form-group mb-6 grid grid-cols-6">
                        <p class="text-sm font-medium text-gray-900 col-span-2">
                            <spring:message code="login.nopassword"/>
                            <a class="text-violet-800" href="<c:url value="/nuevaContrasena"/>"><spring:message code="login.setPassword"/></a>
                        </p>
                    </div>
                </div>
            </form>
    </div>
</body>
</html>