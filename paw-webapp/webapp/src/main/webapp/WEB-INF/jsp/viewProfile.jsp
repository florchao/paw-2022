<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!DOCTYPE html>
<html lang="es" class="scroll-smooth">
<head>
    <meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/../../../public/css/style.css">
    <script src="https://cdn.tailwindcss.com"></script>
    <title><spring:message code="viewProfile.title"/></title>
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
<div class="grid grid-cols-6">
    <div class=" grid grid-row-4 col-span-4 col-start-2">
        <div class=" bg-gray-200 rounded-3xl overflow-auto p-5 mt-24 mb-5 shadow-2xl">
            <div class="grid grid-cols-5 justify-center">
                <div class="row-span-3 col-span-2 ml-10 mr-6 mb-6">
                    <div class="overflow-hidden w-48 bg-gray-100 rounded-full">
                        <svg class="text-gray-400 -left-1" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M10 9a3 3 0 100-6 3 3 0 000 6zm-7 9a7 7 0 1114 0H3z" clip-rule="evenodd"></path></svg>
                    </div>
                </div>
                <div class="ml-3 col-span-2">
                    <span class="text-2xl font-semibold whitespace-nowrap"><c:out value="${employee.name}"/></span>
                </div>
                <div class="ml-3 col-span-2">
                    <h1 class="block mb-2 font-medium text-gray-900 font-semibold"><spring:message code="viewProfile.location"/></h1>
                    <h1 class="block mb-2 text-sm font-medium text-gray-600"> <c:out value="${employee.location}"/></h1>
                </div>
                <div class="ml-3 col-span-2">
                    <h1 class="block mb-2 font-medium text-gray-900 font-semibold"><spring:message code="viewProfile.experience"/></h1>
                    <h1 class="block mb-2 text-sm font-medium text-gray-600 "> <c:out value="${employee.experienceYears}"/></h1>
                </div>
                <div class="ml-3 col-start-5 row-start-2">
                    <a href="<c:url value="/contacto/${user.id}"/>">
                        <button class="h-fit w-fit text-xs text-white bg-violet-400 border border-purple-900 focus:outline-none focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2"><spring:message code="viewProfile.connect"/></button>
                    </a>
                </div>
            </div>
            <%--            <div>--%>
            <%--                <h1 class="pb-3">Experiencia</h1>--%>
            <%--            </div>--%>
            <%--            <div class="grid grid-flow-row-dense grid-cols-2 gap-4">--%>
            <%--                <a class="flex flex-col items-center bg-white rounded-lg border shadow-lg mb-5 md:flex-row md:max-w-full w-full">--%>
            <%--                    <div class="flex flex-col justify-between p-4 leading-normal">--%>
            <%--                        <h5 class="mb-2 text-xl font-bold tracking-tight text-black">Titulo</h5>--%>
            <%--                        <p class="mb-3 font-normal text-gray-700 dark:text-gray-400">Años</p>--%>
            <%--                        <p class="mb-3 font-normal text-gray-500 dark:text-gray-400">Descripción</p>--%>
            <%--                    </div>--%>
            <%--                </a>--%>
            <%--                <a class="flex flex-col items-center bg-white rounded-lg border shadow-lg mb-5 md:flex-row md:max-w-full w-full">--%>
            <%--                    <div class="flex flex-col justify-between p-4 leading-normal">--%>
            <%--                        <h5 class="mb-2 text-xl font-bold tracking-tight text-black">Titulo</h5>--%>
            <%--                        <p class="mb-3 font-normal text-gray-700 dark:text-gray-400">Años</p>--%>
            <%--                        <p class="mb-3 font-normal text-gray-500 dark:text-gray-400">Descripción</p>--%>
            <%--                    </div>--%>
            <%--                </a>--%>
            <%--                <a class="flex flex-col items-center bg-white rounded-lg border shadow-lg mb-5 md:flex-row md:max-w-full w-full">--%>
            <%--                    <div class="flex flex-col justify-between p-4 leading-normal">--%>
            <%--                        <h5 class="mb-2 text-xl font-bold tracking-tight text-black">Titulo</h5>--%>
            <%--                        <p class="mb-3 font-normal text-gray-700 dark:text-gray-400">Años</p>--%>
            <%--                        <p class="mb-3 font-normal text-gray-500 dark:text-gray-400">Descripción</p>--%>
            <%--                    </div>--%>
            <%--                </a>--%>
            <%--                <a class="flex flex-col items-center bg-white rounded-lg border shadow-lg mb-5 md:flex-row md:max-w-full w-full">--%>
            <%--                    <div class="flex flex-col justify-between p-4 leading-normal">--%>
            <%--                        <h5 class="mb-2 text-xl font-bold tracking-tight text-black">Titulo</h5>--%>
            <%--                        <p class="mb-3 font-normal text-gray-700 dark:text-gray-400">Años</p>--%>
            <%--                        <p class="mb-3 font-normal text-gray-500 dark:text-gray-400">Descripción</p>--%>
            <%--                    </div>--%>
            <%--                </a>--%>
            <%--                <a class="flex flex-col items-center bg-white rounded-lg border shadow-lg mb-5 md:flex-row md:max-w-full w-full">--%>
            <%--                    <div class="flex flex-col justify-between p-4 leading-normal">--%>
            <%--                        <h5 class="mb-2 text-xl font-bold tracking-tight text-black">Titulo</h5>--%>
            <%--                        <p class="mb-3 font-normal text-gray-700 dark:text-gray-400">Años</p>--%>
            <%--                        <p class="mb-3 font-normal text-gray-500 dark:text-gray-400">Descripción</p>--%>
            <%--                    </div>--%>
            <%--                </a>--%>

            <%--            </div>--%>
            <div class="grid grid-cols-2">
                <div class="col-span-1">
                    <h1 class="pb-3 pt-3 font-semibold"><spring:message code="viewProfile.abilities"/></h1>
                    <ul role="list" class="list-inside marker:text-purple-900 list-disc pl-5 space-y-3 text-gray-500">
                        <c:forEach var="ability" items="${employee.abilitiesArr}">
                            <li><c:out value="${ability}"/></li>
                        </c:forEach>
                    </ul>
                </div>
                <div class="col-span-1 col-start-2">
                    <h1 class="pb-3 pt-3 font-semibold"><spring:message code="viewProfile.availability"/></h1>
                    <ul role="list" class="list-inside marker:text-purple-900 list-disc pl-5 space-y-3 text-gray-500">
                        <c:forEach var="availability" items="${employee.availabilityArr}">
                            <li><c:out value="${availability}"/></li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
</body>