<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
    <title><spring:message code="contactUs.title"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<c:url value="/public/css/style.css"/>"/>
    <link rel="icon" type="image/x-icon" href="<c:url value="/public/favicon.png"/>"/>
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
    <script src="<c:url value="/public/javascript/contactUs.js"/>"></script>
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
   <jsp:param name="currentUrl" value="contactUs"/>
</jsp:include>
<div class="grid grid-cols-7 content-start justify-center h-full pt-5 overflow-auto">
    <div class="my-16 w-full col-span-7"></div>
    <div class = "col-start-3 col-span-3 grid h-full w-full">
        <div class="grid justify-items-center mx-6">
            <p class="text-xl font-semibold text-white mb-5">
                <spring:message code="contactUs.title"/>
            </p>
        </div>
        <c:url value="/contactUs" var="postPath"/>
         <form:form modelAttribute="contactUsForm" action="${postPath}" method="post" pageEncoding="UTF-8">
            <div class="block p-6 rounded-3xl shadow-lg bg-gray-200">
                <div class="form-group mb-6">
                    <form:label path="name" class="block mb-2 text-sm font-medium text-gray-900"><spring:message code="contactUs.name"/></form:label>
                    <form:input path="name" value = "${name}" type="text" class="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-blue-500 focus:border-violet-500"/>
                    <form:errors path="name" element="p" cssStyle="color: red"/>
                </div>
                <div class="form-group mb-6">
                    <form:label path="mail" class="block mb-2 text-sm font-medium text-gray-900"><spring:message code="contactUs.mail"/></form:label>
                    <form:input path="mail" value = "${mail}" type="tel" class="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-violet-500 focus:border-violet-500"/>
                    <form:errors path="mail" element="p" cssStyle="color: red"/>
                </div>
                <div class="form-group mb-6">
                    <form:label path="content" class="block mb-2 text-sm font-medium text-gray-900"><spring:message code="contactUs.message"/></form:label>
                    <form:textarea path="content" rows="3" class="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-violet-500 focus:border-violet-500" />
                    <form:errors path="content" element="p" cssStyle="color: red"/>
                </div>

                <button type="submit" class="text-lg w-full focus:outline-none text-violet-900 bg-purple-900 bg-opacity-30 hover:bg-purple-900 hover:bg-opacity-50 font-small rounded-lg text-sm px-5 py-2.5"><spring:message code="contactForm2.send"/></button>
            </div>
        </form:form>
    </div>
</div>
<c:if test="${status.equals('sent')}">
    <div id="sent" class="absolute bottom-6 inset-1/3">
        <div class = "grid justify-items-center bg-purple-400 rounded h-1/3 p-5">
            <h1 class="text-2xl font-semibold text-white"><spring:message code="feedback.congrats"/></h1>
            <p class="font-light text-white"><spring:message code="feedback.viewProfile.sent"/></p>
        </div>
    </div>
</c:if>
</body>
</html>

