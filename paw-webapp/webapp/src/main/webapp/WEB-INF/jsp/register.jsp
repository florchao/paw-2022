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
<c:url value="/crearPerfil" var = "createProfile"/>
<c:url value="/crearPerfilEmpleador" var = "createProfileEmployer"/>
<div class="grid content-start h-screen overflow-auto pl-5 pr-5">
    <div id="roles" class="grid grid-cols-3 items-start p-10 gap-y-7 mt-9">
        <div class="h-96 w-96">
            <img class="object-cover w-full h-full rounded-full" src="<c:url value='/public/empleador.jpg'/>" alt="employer"/>
        </div>
        <div class="col-span-2 ">
            <h3 class="text-2xl text-purple-700 justify-self-center"><spring:message code="init.employer"/></h3>
            <p class="font-thin text-lg mt-7"><spring:message code="init.employerDesc"/></p>
            <div class="pb-4 grid col-start-2 col-span-2 mt-7">
                <form:form method="get" action="${createProfileEmployer}" pageEncoding="UTF-8">
                    <button class="bg-violet-300 font-semibold hover:bg-yellow-300 shadow-lg text-violet-900 py-2 px-4 rounded-xl w-2/5 border-hidden hover:border-solid border-2 border-purple-300">
                        <spring:message code="register.employer"/>
                    </button>
                </form:form>
            </div>
        </div>
        <div class="row-start-2 h-96 w-96">
            <img class="object-cover w-full h-full rounded-full" src="<c:url value='/public/empl.jpg'/>" alt="employee"/>
        </div>
        <div class="row-start-2 col-span-2">
            <h3 class="text-2xl text-purple-700 justify-self-center"><spring:message code="init.employee"/></h3>
            <p class="font-thin text-lg mt-7"><spring:message code="init.employeeDesc"/></p>
            <div class="pb-4 grid col-start-2 col-span-2 mt-7">
                <form:form method="get" action="${createProfile}" pageEncoding="UTF-8">
                    <button class="bg-violet-300 font-semibold hover:bg-yellow-300 shadow-lg text-violet-900 py-2 px-4 rounded-xl w-2/5 border-hidden hover:border-solid border-2 border-purple-300">
                        <spring:message code="register.employee"/>
                    </button>
                </form:form>
            </div>
        </div>
    </div>
</div>
</body>

</html>

