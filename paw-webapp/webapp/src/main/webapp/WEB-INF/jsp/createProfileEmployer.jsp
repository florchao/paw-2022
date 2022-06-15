<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<c:url value="/public/css/style.css"/>"/>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="icon" type="image/x-icon" href="<c:url value="/public/favicon.png"/>"/>
    <title><spring:message code="createProfileEmployer.title"/></title>
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
<c:url value="/createEmployer" var="postPath"/>
<div class = "h-screen overflow-auto pb-5">
    <form:form modelAttribute="employerForm" action="${postPath}" method="post" pageEncoding="UTF-8" enctype="multipart/form-data">
        <div class="grid grid-cols-6">
            <div class="grid grid-row-4 col-span-4 col-start-2 mt-20 ">
                <p class="text-3xl font-semibold text-violet-900 mb-4 mt-4 text-center"><spring:message code="createProfileEmployer.formTitle"/></p>
                <div class="bg-gray-200 rounded-3xl p-5 shadow-2xl">
                    <div class="grid grid-cols-5 gap-6">
                        <div class="row-span-4 col-span-2 m-6">
                            <div class="overflow-hidden bg-gray-100 rounded-full">
                                <img id="picture" src="<c:url value='/public/user.png'/>" alt="user pic"/>
                            </div>
                            <form:label path="image"><spring:message code="employerForm.insertImage"/></form:label>
                            <form:input type="file" path="image" accept="image/png, image/jpeg" onchange="loadFile(event);"/>
                            <form:errors path="image" element="p" cssStyle="color:red;margin-left: 10px"/>
                        </div>
                        <div class="ml-3 col-span-3 col-start-3 w-4/5 justify-self-center">
                            <form:label path="name" for="name" class="block mb-2 text-sm font-medium text-gray-900 "><spring:message code="employerForm.label.name"/></form:label>
                            <form:input path="name" type="text" class="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-blue-500 focus:border-violet-500"/>
                            <form:errors path="name" element="p" cssStyle="color:red"/>
                        </div>
                        <div class="ml-3 col-span-3 col-start-3 w-4/5 justify-self-center">
                            <form:label path="lastname" for="lastname" class="block mb-2 text-sm font-medium text-gray-900 "><spring:message code="employerForm.label.lastname"/></form:label>
                            <form:input path="lastname" type="text" class="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-violet-500 focus:border-violet-500"/>
                            <form:errors path="lastname" element="p" cssStyle="color:red"/>
                        </div>
                        <div class="ml-3 col-span-3 col-start-3 w-4/5 justify-self-center">
                            <form:label for="mail" path="mail" class="text-sm font-medium text-gray-900" ><spring:message code="register.mail"/> </form:label>
                            <form:input id="mail" path="mail" type="mail" class="col-span-5 block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-blue-500 focus:border-violet-500"/>
                            <form:errors path="mail" element="p" cssStyle="color:red" class="col-start-2 col-span-5"/>
                        </div>
                        <div class="ml-3 col-span-3 col-start-3 w-4/5 justify-self-center">
                            <form:label for="password" path="password" class="text-sm font-medium text-gray-900"><spring:message code="register.password"/></form:label>
                            <form:input id="password" path="password" type="password" class=" col-span-5 block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-blue-500 focus:border-violet-500"/>
                            <form:errors path="password" element="p" cssStyle="color:red" class="col-start-2 col-span-5"/>
                        </div>
                        <div class="ml-3 col-span-3 col-start-3 w-4/5 justify-self-center">
                            <form:label for="confirmPassword" path="confirmPassword" class="text-sm font-medium text-gray-900"><spring:message code="register.confirmPassword"/></form:label>
                            <form:input id="confirmPassword" path="confirmPassword" type="password" class=" col-span-5 block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-blue-500 focus:border-violet-500"/>
                            <form:errors path="confirmPassword" element="p" cssStyle="color:red" class="col-start-2 col-span-5"/>
                            <form:errors element="p" cssStyle="color:red" class="col-start-2 col-span-5"/>
                        </div>
                        <div class="mt-5 col-span-5 row-span-3">
                            <button type="submit" class="text-lg w-full focus:outline-none text-violet-900 bg-purple-900 bg-opacity-30 hover:bg-purple-900 hover:bg-opacity-50 font-small rounded-lg text-sm px-5 py-2.5"><spring:message code="employerForm.button"/></button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form:form>
</div>
</body>
</html>
