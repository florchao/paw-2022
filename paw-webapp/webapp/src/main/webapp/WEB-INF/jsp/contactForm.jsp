<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8"%>

<html lang="es">
<head>
    <title>Contacto</title>
    <meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body>
<jsp:include page="components/navbar.jsp"/>
<c:url value="/contactarEmpleado/${id}" var="postPath"/>
<div class="grid grid-cols-7 content-end justify-center h-screen pt-5">
    <div class = "col-start-3 col-span-3 grid h-full w-full">
        <div class="grid justify-items-center mx-6">
            <p class="text-xl font-semibold text-purple-700 mb-5">
                <spring:message code="contactForm2.title" arguments="${name}"/>
            </p>
        </div>

        <form:form modelAttribute="contactForm" action="${postPath}" method="post">
            <div class="block p-6 rounded-lg shadow-lg bg-white">
                <div class="form-group mb-6">
                    <form:label path="name" class="block mb-2 text-sm font-medium text-gray-900"><spring:message code="contactForm2.name"/></form:label>
                    <form:input path="name" type="text" class="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-blue-500 focus:border-violet-500"/>
                    <form:errors path="name" element="p" cssStyle="color: red"/>
                </div>
                <div class="form-group mb-6">
                    <form:label path="email" class="block mb-2 text-sm font-medium text-gray-900"><spring:message code="contactForm2.mail"/></form:label>
                    <form:input path="email" type="email" class="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-violet-500 focus:border-violet-500"/>
                    <form:errors path="email" element="p" cssStyle="color: red"/>
                </div>
                <div class="form-group mb-6">
                    <form:label path="content" class="block mb-2 text-sm font-medium text-gray-900"><spring:message code="contactForm2.message"/></form:label>
                    <form:textarea path="content" rows="3" class="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-violet-500 focus:border-violet-500" />
                    <form:errors path="content" element="p" cssStyle="color: red"/>
                </div>

                <button type="submit" class="text-lg w-full focus:outline-none text-violet-900 bg-purple-900 bg-opacity-30 hover:bg-purple-900 hover:bg-opacity-50 font-small rounded-lg text-sm px-5 py-2.5"><spring:message code="contactForm2.send"/></button>
            </div>
        </form:form>
    </div>
</div>
</body>
</html>
