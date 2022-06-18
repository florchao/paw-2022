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
<c:url value="/user/profile-image/${employer.id.id}" var="image" />
<c:url value="/deleteProfile" var="deletePath"/>
<div class="grid overflow-auto h-screen grid-cols-6">
    <div class=" grid grid-row-4 col-span-4 col-start-2 h-fit">
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
            <div class="flow-root">
                <h1 class="pb-3 pt-3 font-semibold"><spring:message code="reviews.title"/></h1>
                <c:choose>
                    <c:when test="${ReviewList.size() == 0}">
                        <div class = "grid content-center justify-center h-5/6 mt-16">
                            <div class = "grid justify-items-center">
                                <img src="<c:url value='/public/sinEmpleadas.png'/>" alt="sinEmpleadas" class="mr-3 h-6 sm:h-52">
                                <p class="text-3xl font-semibold text-purple-700"><spring:message code="reviews.noReviews"/></p>
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <ul role="list" class="divide-y divide-gray-300">
                            <c:forEach var="review" items="${ReviewList}">
                                <c:url value="/user/profile-image/${review.employeeId.id.id}" var="image" />
                                <li class="py-3 sm:py-4">
                                    <div class="flex items-center space-x-4">
                                        <div class="flex-shrink-0 self-start">
                                            <img class="w-8 h-8 rounded-full object-cover" src="${image}" alt="Employee Photo" onerror="this.src = '<c:url value="/public/user.png"/>'"/>
                                        </div>
                                        <div class="flex-1 min-w-0">
                                            <p class="text-xl font-medium text-gray-900 text-ellipsis">
                                                <c:out value="${review.review}"/>
                                            </p>
                                            <div class="grid grid-cols-2">
                                                <p class="text-sm text-gray-500 col-start-1">
                                                    <c:out value="${review.employeeId.name}"/>
                                                </p>
                                                <p class="text-sm text-gray-500 col-start-2 text-end">
                                                    <c:out value="${review.created}"/>
                                                </p>
                                            </div>
                                        </div>
                                    </div>
                                </li>
                            </c:forEach>
                            <c:url value="/verPerfil" var="getPath"/>
                            <form:form method="get" action="${getPath}">
                                <c:if test="${maxPage > 0 && page + 1 <= maxPage}">
                                    <div class="flex flex-row justify-center mt-4">
                                    <c:choose>
                                        <c:when test="${page < 1}">
                                            <button type="submit" class="font-semibold border shadow-md focus:outline-none text-violet-900 bg-gray-300 border-purple-900 rounded-lg px-2" disabled="true" onclick="previousPage(${page})"><</button>
                                        </c:when>
                                        <c:otherwise>
                                            <button type="submit" class="font-semibold border shadow-md focus:outline-none text-violet-900 bg-purple-400 border-purple-900 hover:bg-yellow-300 hover:bg-opacity-50 rounded-lg px-2" onclick="previousPage(${page})"><</button>
                                        </c:otherwise>
                                    </c:choose>
                                    <div class="bg--300 w-16 flex justify-center">
                                        <h1 class="text-purple-900">${page + 1} of ${maxPage}</h1>
                                    </div>
                                    <c:choose>
                                        <c:when test="${page + 1 == maxPage}">
                                            <button type="submit" class="font-semibold border shadow-md focus:outline-none text-violet-900 bg-gray-300 border-purple-900 rounded-lg px-2" disabled="true" onclick="nextPage(${page})">></button>
                                        </c:when>
                                        <c:otherwise>
                                            <button type="submit" id="prevPageButton" class=" font-semibold border shadow-md focus:outline-none text-violet-900 bg-purple-400 border-purple-900 hover:bg-yellow-300 hover:bg-opacity-50 rounded-lg px-2" onclick="nextPage(${page})">></button>
                                        </c:otherwise>
                                    </c:choose>
                                </c:if>
                                </div>
                                <input style="visibility: hidden" type="number" name="page" id="pageNumber"/>
                            </form:form>
<%--                                                            <c:url value="/verPerfil/${employee.id.id}" var="getPath"/>--%>
<%--                                                            <form method="get" action="${getPath}">--%>
<%--                                                                <c:if test="${maxPage > 0 && page + 1 <= maxPage}">--%>
<%--                                                                <div class="flex flex-row justify-center mt-4">--%>
<%--                                                                    <c:choose>--%>
<%--                                                                        <c:when test="${page < 1}">--%>
<%--                                                                            <button type="submit" class="font-semibold border shadow-md focus:outline-none text-violet-900 bg-gray-300 border-purple-900 rounded-lg px-2" disabled="true" onclick="previousPage(${page})"><</button>--%>
<%--                                                                        </c:when>--%>
<%--                                                                        <c:otherwise>--%>
<%--                                                                            <button type="submit" class="font-semibold border shadow-md focus:outline-none text-violet-900 bg-purple-400 border-purple-900 hover:bg-yellow-300 hover:bg-opacity-50 rounded-lg px-2" onclick="previousPage(${page})"><</button>--%>
<%--                                                                        </c:otherwise>--%>
<%--                                                                    </c:choose>--%>
<%--                                                                    <div class="bg--300 w-16 flex justify-center">--%>
<%--                                                                        <h1 class="text-purple-900">${page + 1} of ${maxPage}</h1>--%>
<%--                                                                    </div>--%>
<%--                                                                    <c:choose>--%>
<%--                                                                        <c:when test="${page + 1 == maxPage}">--%>
<%--                                                                            <button type="submit" class="font-semibold border shadow-md focus:outline-none text-violet-900 bg-gray-300 border-purple-900 rounded-lg px-2" disabled="true" onclick="nextPage(${page})">></button>--%>
<%--                                                                        </c:when>--%>
<%--                                                                        <c:otherwise>--%>
<%--                                                                            <button type="submit" id="prevPageButton" class=" font-semibold border shadow-md focus:outline-none text-violet-900 bg-purple-400 border-purple-900 hover:bg-yellow-300 hover:bg-opacity-50 rounded-lg px-2" onclick="nextPage(${page})">></button>--%>
<%--                                                                        </c:otherwise>--%>
<%--                                                                    </c:choose>--%>
<%--                                                                    </c:if>--%>
<%--                                                                </div>--%>
<%--                                                                <input style="visibility: hidden" type="number" name="page" id="pageNumber"/>--%>
<%--                                                            </form>--%>
                        </ul>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</div>
</body>
