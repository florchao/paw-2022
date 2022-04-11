<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<html>
<head>
    <title>contactForm2</title>
    <meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body>
    <jsp:include page="components/navbar.jsp"/>
    <c:url value="/contactEmployee/${id}" var="postPath"/>
    <div class="grid grid-cols-4">
        <div: class = "my-20 col-start-2 col-span-2 h-full">
            <p class="text-xl font-semibold text-purple-700 my-5">
                <spring:message code="contactForm2.title" arguments="${name}"/>
            </p>
        <form:form modelAttribute="contactForm" action="${postPath}" method="post">
            <div class="block p-6 rounded-lg shadow-lg bg-white max-w-md">
                <div class="form-group mb-6">
                    <form:label path="name" class="block mb-2 text-sm font-medium text-gray-900"><spring:message code="contactForm2.name"/></form:label>
                    <form:input path="name" type="text" class="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-blue-500 focus:border-violet-500"/>
                    <form:errors path="name"/>
                </div>
                <div class="form-group mb-6">
                    <form:label path="email" class="block mb-2 text-sm font-medium text-gray-900"><spring:message code="contactForm2.mail"/></form:label>
                    <form:input path="email" type="email" class="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-violet-500 focus:border-violet-500"/>
                    <form:errors path="email" element="p"/>
                </div>
                <div class="form-group mb-6">
                    <form:label path="content" class="block mb-2 text-sm font-medium text-gray-900"><spring:message code="contactForm2.message"/></form:label>
                    <form:textarea path="content" rows="3" class="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-violet-500 focus:border-violet-500" />
                    <form:errors path="content" element="p"/>
                </div>

                <button type="submit" class="w-full px-6 py-2.5 bg-purple-700 text-white font-medium text-xs leading-tight uppercase rounded shadow-md hover:bg-purple-500 hover:shadow-lg focus:bg-purple-500 focus:shadow-lg focus:outline-none focus:ring-0 active:bg-purple-500 active:shadow-lg transition duration-150 ease-in-out"><spring:message code="contactForm2.send"/></button>
            </div>
        </form:form>
    </div>

</body>
</html>
