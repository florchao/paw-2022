<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="from" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
    <title><spring:message code="register.title"/></title>
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
<jsp:include page="components/navbar.jsp">
    <jsp:param name="currentUrl" value="register"/>
</jsp:include>
<c:url value="/buscarEmpleadas" var = "searchEmployees"/>
<c:url value="/misTrabajos" var = "publishJob"/>
<div class="flex h-screen overflow-auto pl-5 pr-5 justify-center">
    <div id="roles" class="grid grid-cols-2 items-start p-10 gap-y-7 mt-9 content-center">
        <div class="grid grid-cols-3">
            <div class="h-48 w-48">
                <img class="object-cover w-full h-full rounded-full" src="<c:url value='/public/searchJob.jpeg'/>" alt="employer"/>
            </div>
            <div class="col-span-2 ">
                <h3 class="text-2xl text-purple-700 justify-self-center"><spring:message code="landingEmployer.searchEmployee"/></h3>
                <p class="font-thin text-lg mt-7"><spring:message code="landingEmployer.employerDesc"/></p>
                <div class="pb-4 grid col-start-2 col-span-2 mt-7">
                    <form:form method="get" action="${searchEmployees}" pageEncoding="UTF-8">
                        <button class="bg-violet-300 font-semibold hover:bg-yellow-300 shadow-lg text-violet-900 py-2 px-4 rounded-xl w-2/5 border-hidden hover:border-solid border-2 border-purple-300">
                            <spring:message code="landingEmployer.search"/>
                        </button>
                    </form:form>
                </div>
            </div>
        </div>
        <div class="col-start-2 grid grid-cols-3">
            <div class="h-48 w-48">
                <img class="object-cover w-full h-full rounded-full" src="<c:url value='/public/joboffer.jpeg'/>" alt="employee"/>
            </div>
            <div class="col-span-2">
                <h3 class="text-2xl text-purple-700 justify-self-center"><spring:message code="landingEmployer.jobOffer"/></h3>
                <p class="font-thin text-lg mt-7"><spring:message code="landingEmployer.jobDesc"/></p>
                <div class="pb-4 grid col-start-2 col-span-2 mt-7">
                    <form:form method="get" action="${publishJob}" pageEncoding="UTF-8">
                        <button class="bg-violet-300 font-semibold hover:bg-yellow-300 shadow-lg text-violet-900 py-2 px-4 rounded-xl w-2/5 border-hidden hover:border-solid border-2 border-purple-300">
                            <spring:message code="landingEmployer.jobs"/>
                        </button>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

</html>

