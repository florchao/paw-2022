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
<div class="grid overflow-auto h-screen grid-cols-6">
    <div class=" grid grid-row-4 col-span-4 col-start-2">
        <div class=" bg-gray-200 rounded-3xl p-5 mt-24 mb-5 shadow-2xl">
            <div class="grid grid-cols-5 justify-center">
                <div class="row-span-3 col-span-2 ml-6 mr-6 mb-6 justify-self-center">
                        <img  class = "object-cover mb-3 w-52 h-52 rounded-full shadow-lg" src="${image}"  onerror="this.src = '<c:url value="/public/user.png"/>'" alt="profile pic"/>
                </div>
                <div class="ml-3 col-span-2">
                    <p class="text-2xl font-semibold whitespace-nowrap text-ellipsis overflow-hidden"><c:out value="${employee.name}"/></p>
                </div>
                <div class="ml-3 col-span-2">
                    <h1 class="block mb-2 font-medium text-gray-900 font-semibold"><spring:message code="viewProfile.location"/></h1>
                    <h1 class="block mb-2 text-sm font-medium text-gray-600 text-ellipsis overflow-hidden"> <c:out value="${employee.location}"/></h1>
                </div>
                <div class="ml-3 col-span-2">
                    <h1 class="block mb-2 font-medium text-gray-900 font-semibold"><spring:message code="viewProfile.experience"/></h1>
                    <h1 class="block mb-2 text-sm font-medium text-gray-600 "> <c:out value="${employee.experienceYears}"/></h1>
                </div>
                <sec:authorize access="hasAuthority('EMPLOYER')">

                    <div class="ml-3 col-start-5 row-start-2">
                        <c:if test="${contacted == null || !contacted}">
                            <a href="<c:url value="/contacto/${employee.id.id}"/>">
                                <button class="h-fit  text-xs text-white bg-violet-400 border border-purple-900 focus:outline-none focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2"><spring:message code="viewProfile.connect"/></button>
                            </a>
                        </c:if>
                        <c:if test="${contacted != null && contacted}">
                            <p class="h-fit w-full text-xs text-white bg-gray-400 border border-gray-900 font-medium rounded-full px-5 py-2.5 mr-2 mb-2"><spring:message code="viewProfile.alreadyConnected"/></p>
                        </c:if>
                    </div>
                </sec:authorize>
                <sec:authorize access="hasAuthority('EMPLOYEE')">
                    <div class="ml-3 col-start-5 row-start-2">
                        <a href="<c:url value="/editarPerfil"/>">
                            <button class="h-fit w-fit text-xs text-white bg-violet-400 border border-purple-900 focus:outline-none focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2"><spring:message code="viewProfile.editProfile"/></button>
                        </a>
                    </div>
                </sec:authorize>
            </div>
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
            <div class="flow-root">
                <h1 class="pb-3 pt-3 font-semibold"><spring:message code="reviews.title"/></h1>
                <c:if test="${myReview == null}">
                    <c:url value="/addReview/${employee.id.id}" var="postPath"/>
                    <sec:authorize access="hasAuthority('EMPLOYER')">
                        <form:form modelAttribute="reviewForm" action="${postPath}" method="post" pageEncoding="UTF-8">
                            <div class="">
                                <form:label path="content" class="block pb-3 pt-3 font-semibold text-gray-900"><spring:message code="reviews.form.label"/></form:label>
                                <form:textarea path="content" rows="3" class="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-violet-500 focus:border-violet-500" />
                                <form:errors path="content" element="p" cssStyle="color: red"/>
                                <div class="mt-5 col-start-2 col-span-4 row-span-3">
                                    <button type="submit" class="text-lg w-full focus:outline-none text-violet-900 bg-purple-900 bg-opacity-30 hover:bg-purple-900 hover:bg-opacity-50 font-small rounded-lg text-sm px-5 py-2.5"><spring:message code="review.button"/></button>
                                </div>
                            </div>
                        </form:form>
                    </sec:authorize>
                </c:if>
                <c:choose>
                    <c:when test="${ReviewList.size() == 0 && myReview == null}">
                        <div class = "grid content-center justify-center h-5/6 mt-16">
                            <div class = "grid justify-items-center">
                                <img src="<c:url value='/public/sinEmpleadas.png'/>" alt="sinEmpleadas" class="mr-3 h-6 sm:h-52">
                                <p class="text-3xl font-semibold text-purple-700"><spring:message code="reviews.noReviews"/></p>
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <ul role="list" class="divide-y divide-gray-300">
                            <c:if test="${myReview != null}">
                                <li class = "py-3 px-3 sm:py-4 bg-violet-300 bg-opacity-25">
                                    <c:url value="/user/profile-image/${myReview.employerId.id.id}" var="userImage" />
                                    <div class="flex items-center space-x-4">
                                        <div class="flex-shrink-0 self-start">
                                            <img class="w-8 h-8 rounded-full object-cover" src="${userImage}" alt="Employee Photo" onerror="this.src = '<c:url value="/public/user.png"/>'"/>
                                        </div>
                                        <div class="flex-1 min-w-0">
                                            <p class="text-xl font-medium text-gray-900 truncate">
                                                <c:out value="${myReview.employerId.name}"/>
                                            </p>
                                            <p class="text-sm text-gray-500">
                                                <c:out value="${myReview.review}"/>
                                            </p>
                                        </div>
                                    </div>
                                </li>
                            </c:if>
                            <c:forEach var="review" items="${ReviewList}">
                                <c:url value="/user/profile-image/${review.employerId.id.id}" var="image" />
                                <li class="py-3 sm:py-4">
                                    <div class="flex items-center space-x-4">
                                        <div class="flex-shrink-0 self-start">
                                            <img class="w-8 h-8 rounded-full object-cover" src="${image}" alt="Employee Photo" onerror="this.src = '<c:url value="/public/user.png"/>'"/>
                                        </div>
                                        <div class="flex-1 min-w-0">
                                            <p class="text-xl font-medium text-gray-900 truncate">
                                                <c:out value="${review.employerId.name}"/>
                                            </p>
                                            <p class="text-sm text-gray-500">
                                                <c:out value="${review.review}"/>
                                            </p>
                                        </div>
                                    </div>
                                </li>
                            </c:forEach>
                            <c:url value="/verPerfil/${employee.id.id}" var="getPath"/>
                            <form method="get" action="${getPath}">
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
                            </form>
                        </ul>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
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
<c:if test="${status.equals('error')}">
    <div id="error" class="absolute bottom-6 inset-1/3">
        <div class = "grid justify-items-center bg-white rounded-lg h-1/3 p-5">
            <h1 class="text-2xl font-semibold text-red-700"><spring:message code="feedback.error"/></h1>
            <p class="font-light text-red-700"><spring:message code="feedback.viewProfile.errorExists"/></p>
        </div>
    </div>
</c:if>
</body>
