<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8"%>

<html lang="es">
<head>
    <title><spring:message code="register.title"/></title>
    <meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body>
<jsp:include page="components/navbar.jsp"/>
<c:url value="/register" var="registerUrl"/>
<div class="grid grid-cols-7 content-center justify-center h-screen pt-5">
        <form modelAttribute="register" class="col-span-3 col-start-3" action="${registerUrl}" method="post" enctype = "application/x-www-form-urlencoded">
            <div class="block p-6 rounded-lg shadow-lg bg-white">
                <div class="form-group mb-6">
                    <label for="mail"><spring:message code="register.mail"/> </label>
                    <input id="mail" name="mail" type="mail"/>
                </div>
                <div class="form-group mb-6">
                    <label for="password"><spring:message code="register.password"/></label>
                    <input id="password" name="j_password" type="password"/>
                </div>
                    <button type="submit" class="text-lg w-full focus:outline-none text-violet-900 bg-purple-900 bg-opacity-30 hover:bg-purple-900 hover:bg-opacity-50 font-small rounded-lg text-sm px-5 py-2.5"> <spring:message code="register.button"/></button>
                 </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>
