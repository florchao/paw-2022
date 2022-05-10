<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
    <title><spring:message code="newpassword.title"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<c:url value="/public/css/style.css"/>"/>
    <link rel="icon" type="image/x-icon" href="<c:url value="/public/favicon.png"/>"/>
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="<c:url value="/public/javascript/utils.js"/>"></script>
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
<jsp:include page="components/navbar.jsp"/>
<c:url value="/newPassword" var="newpassurl"/>
<div class="grid grid-cols-7 content-center justify-center h-screen pt-5">
    <form:form modelAttribute="newPasswordForm" class="col-span-3 col-start-3" action="${newpassurl}" method="post" enctype = "application/x-www-form-urlencoded" pageEncoding="UTF-8">
        <div class="block p-6 rounded-lg shadow-lg bg-white">
            <div class="form-group mb-6 grid grid-cols-6">
                <form:label for="mail" path="mail" class="text-sm font-medium text-gray-900" ><spring:message code="register.mail"/> </form:label>
                <form:input id="mail" name="mail" path="mail" type="mail" class="col-span-5 block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-blue-500 focus:border-violet-500"/>
                 <form:errors path="mail" element="p" class="col-start-2 col-span-5" cssStyle="color:red"/>
            </div>
            <div class="form-group mb-6 grid grid-cols-6">
                <form:label for="password" path="password" class="text-sm font-medium text-gray-900"><spring:message code="register.password"/></form:label>
                <form:input id="password" name="password" path="password" type="password" class=" col-span-5 block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-blue-500 focus:border-violet-500"/>
                 <form:errors path="password" element="p" class="col-start-2 col-span-5" cssStyle="color:red"/>
             </div>
             <div class="form-group mb-6 grid grid-cols-6">
                <form:label for="confirmPassword" path="confirmPassword" class="text-sm font-medium text-gray-900"><spring:message code="register.confirmPassword"/></form:label>
                <form:input id="confirmPassword" name="confirmPassword" path="confirmPassword" type="password" class=" col-span-5 block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-blue-500 focus:border-violet-500"/>
                 <form:errors path="confirmPassword" element="p" class="col-start-2 col-span-5" cssStyle="color:red"/>
             </div>
             <div>
                <button type="submit" class="text-lg w-full focus:outline-none text-violet-900 bg-purple-900 bg-opacity-30 hover:bg-purple-900 hover:bg-opacity-50 font-small rounded-lg text-sm px-5 py-2.5"> <spring:message code="newpassword.button"/></button>
              </div>
        </div>
    </form:form>
</div>
</body>

</html>

