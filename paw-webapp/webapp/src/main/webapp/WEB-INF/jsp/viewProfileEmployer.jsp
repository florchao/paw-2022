<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html class="scroll-smooth">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<c:url value="/public/css/style.css"/>"/>
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.0.0/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.1/jquery.modal.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.1/jquery.modal.min.css" />
    <script src="<c:url value="/public/javascript/viewProfile.js"/>"></script>
    <script src="<c:url value="/public/javascript/utils.js"/>"></script>
    <link rel="icon" type="image/x-icon" href="<c:url value="/public/favicon.png"/>"/>
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
<jsp:include page="components/navbar.jsp">
    <jsp:param name="currentUrl" value="verPerfil"/>
</jsp:include>
<c:url value="/user/profile-image/${employee.id.id}" var="image" />
<c:url value="/deleteProfile" var="deletePath"/>
<div class="grid overflow-auto h-screen grid-cols-6">
    <div class=" grid grid-row-4 col-span-4 col-start-2">
        <div class=" bg-gray-200 rounded-3xl p-5 mt-24 mb-5 shadow-2xl">
            <div class="grid grid-cols-5 justify-center">
                <div class="row-span-3 col-span-2 ml-6 mr-6 mb-6 justify-self-center">
                    <img  class = "object-cover mb-3 w-52 h-52 rounded-full shadow-lg" src="${image}"  onerror="this.src = '<c:url value="/public/user.png"/>'" alt="profile pic"/>
                </div>
                <div class="ml-3 col-span-2">
                    <p class="text-2xl font-semibold whitespace-nowrap text-ellipsis overflow-hidden"><c:out value="${employer.name}"/></p>
                </div>
                <div class="ml-3 col-start-5 row-start-2">
                    <form:form action="${deletePath}" method="delete">
                        <button type="submit" class="text-sm focus:outline-none text-white bg-red-500 hover:bg-red-700 font-small rounded-lg text-sm px-5 py-2.5">
                            <div class="grid grid-rows-1 grid-cols-3">
                                <img src="<c:url value='/public/bin.png'/>" alt="bin" class="mr-3 h-6 sm:h-5 col-start-1">
                                <p class="col-span-2"><spring:message code="viewProfile.delete"/></p>
                            </div>
                        </button>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
