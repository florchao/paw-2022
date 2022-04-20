<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8"%>

<html lang="es">
<head>
    <title><spring:message code="login.title"/></title>
    <meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body>
    <jsp:include page="components/navbar.jsp"/>
    <c:url value="/login" var="loginUrl"/>
    <c:url value="/redirectCreateProfile" var = "createProfile"/>
    <div class="grid grid-cols-7 content-center justify-center h-screen pt-5">
            <form modelAttribute="login" class="col-span-3 col-start-3" action="${loginUrl}" method="post" enctype = "application/x-www-form-urlencoded">
                <div class="block p-6 rounded-lg shadow-lg bg-white">
                    <div class="form-group mb-6">
                        <label for="username">Username: </label>
                        <input id="username" name="j_username" type="text"/>
                    </div>
                    <div class="form-group mb-6">
                        <label for="password">Password: </label>
                        <input id="password" name="j_password" type="password"/>
                    </div>
                     <div class="form-group mb-6">
                        <label><input name="j_rememberme" type="checkbox"/> <spring:message code="remember_me"/></label> </div>
                    <div class="form-group mb-6">
                        <input type="submit" class="text-lg w-full focus:outline-none text-violet-900 bg-purple-900 bg-opacity-30 hover:bg-purple-900 hover:bg-opacity-50 font-small rounded-lg text-sm px-5 py-2.5" value=<spring:message code="login.button"/>/>
                     </div>
                    <div class="form-group mb-6">
                        <p><spring:message code="create.account"/> <form:form method="get" action="${createProfile}">
                                                                    <a class="text-violet-900" href="#"><spring:message code="login.register"/></a>
                                                                    </form:form>
                        </p>
                    </div>
                </div>
            </form>
    </div>
</body>
</html>
