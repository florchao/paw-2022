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
    <script src="<c:url value="/public/javascript/viewJob.js"/>"></script>
    <script src="<c:url value="/public/javascript/utils.js"/>"></script>
    <link rel="icon" type="image/x-icon" href="<c:url value="/public/favicon.png"/>"/>
    <script src="<c:url value="/public/javascript/utils.js"/>"></script>
    <title><spring:message code="viewJob.title"/></title>
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
<c:url value="/apply/${id}" var="postPath"/>
<c:url value="/deleteJob/${id}" var="deletePath"/>
<c:url value="/closeJob/${id}" var="closePath"/>
<c:url value="/openJob/${id}" var="openPath"/>
<div class="grid h-screen grid-cols-6 overflow-auto">
    <div class=" grid grid-row-4 col-span-4 col-start-2 h-fit">
        <div class=" bg-gray-200 rounded-3xl overflow-auto p-5 mt-24 mb-5 shadow-2xl">
            <div class="grid grid-cols-5 justify-center">
                <div class="mb-2 col-span-5">
                    <p class="text-2xl font-semibold whitespace-nowrap text-purple-900 text-ellipsis overflow-hidden"><c:out value="${job.title}"/></p>
                    <p class="text-sm whitespace-nowrap text-ellipsis overflow-hidden"><spring:message code="viewJob.by"/><c:out value="${job.employerId.name}"/></p>
                </div>
                <div class="col-span-2">
                    <h1 class="pb-3 pt-3 text-purple-900 font-semibold"><spring:message code="viewJob.location"/></h1>
                    <h1 class="block mb-2 ml-2 text-sm font-medium text-gray-600 text-ellipsis overflow-hidden"> <c:out value="${job.location}"/></h1>
                </div>
                <div class="col-span-2">
                    <h1 class="pb-3 pt-3 text-purple-900 font-semibold"><spring:message code="viewJob.experience"/></h1>
                    <h1 class="block mb-2 ml-2 text-sm font-medium text-gray-600 "> <c:out value="${job.experienceYears}"/></h1>
                </div>
                <sec:authorize access="hasAuthority('EMPLOYEE')">
                <div class="col-start-5 row-start-2">
                    <c:if test="${alreadyApplied == -1}">
                        <form:form action="${postPath}" method="post">
                            <button class="ml-2 h-fit w-fit text-xs text-white bg-violet-400 border border-purple-900 focus:outline-none focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 hover:bg-yellow-300 hover:bg-opacity-70 hover:text-purple-900"><spring:message code="viewJob.apply"/></button>
                        </form:form>
                    </c:if>
                    <c:if test="${alreadyApplied >= 0}">
                        <h1 class="pb-3 pt-3 font-semibold text-purple-900"><spring:message code="viewJob.status"/></h1>
                        <c:if test="${alreadyApplied == 0}">
                            <a class="text-sm focus:outline-none text-purple-900 bg-yellow-300 font-small rounded-lg text-sm px-5 py-2.5"><spring:message code="viewJob.pending"/> </a>
                        </c:if>
                        <c:if test="${alreadyApplied == 1}">
                            <a class="text-sm focus:outline-none text-purple-900 bg-green-300 font-small rounded-lg text-sm px-5 py-2.5"><spring:message code="viewJob.accepted"/> </a>
                        </c:if>
                        <c:if test="${alreadyApplied == 2}">
                            <a class="text-sm focus:outline-none text-purple-900 bg-red-300 font-small rounded-lg text-sm px-5 py-2.5"><spring:message code="viewJob.denied"/> </a>
                        </c:if>
                    </c:if>
                </div>
                </sec:authorize>
            </div>
            <div class="grid grid-cols-5">
                <div class="col-span-2">
                    <h1 class="pb-3 pt-3 font-semibold text-purple-900"><spring:message code="viewJob.abilities"/></h1>
                    <ul role="list" class="list-inside marker:text-purple-900 list-disc pl-5 space-y-3 text-gray-500">
                        <c:forEach var="ability" items="${job.abilitiesArr}">
                            <li><c:out value="${ability}"/></li>
                        </c:forEach>
                    </ul>
                </div>
                <div class="col-span-1 col-start-3">
                    <h1 class="pb-3 pt-3 font-semibold text-purple-900"><spring:message code="viewJob.availability"/></h1>
                    <ul role="list" class="list-inside marker:text-purple-900 list-disc pl-5 space-y-3 text-gray-500">
                        <c:forEach var="availability" items="${job.availabilityArr}">
                            <li><c:out value="${availability}"/></li>
                        </c:forEach>
                    </ul>
                </div>
                <div class="col-span-5">
                    <h1 class="pb-3 pt-3 font-semibold text-purple-900"><spring:message code="viewJob.description"/></h1>
                    <h1 class="block ml-2 mb-2 text-sm font-medium text-gray-600 text-ellipsis overflow-hidden"> <c:out value="${job.description}"/></h1>
                </div>
            </div>
            <div class="grid grid-rows-3 grid-cols-5">
                <div class="col-start-2 row-start-3">
                    <sec:authorize access="hasAuthority('EMPLOYER')">
                        <form:form action="${deletePath}" method="delete">
                            <button type="submit" class="text-sm focus:outline-none text-white bg-red-500 hover:bg-red-700 font-small rounded-lg text-sm px-5 py-2.5">
                                <div class="grid grid-rows-1 grid-cols-3">
                                    <img src="<c:url value='/public/bin.png'/>" alt="bin" class="mr-3 h-6 sm:h-5 col-start-1">
                                    <p class="col-span-2"><spring:message code="viewJob.delete"/></p>
                                </div>
                            </button>
                        </form:form>
                    </sec:authorize>
                </div>
                <div class="col-start-4 row-start-3">
                    <sec:authorize access="hasAuthority('EMPLOYER')">
                        <c:if test="${job.opened}">
                            <form:form action="${closePath}" method="post">
                                <button type="submit" class="text-sm focus:outline-none text-purple-700 bg-yellow-300 border-violet-700 hover:bg-yellow-200 font-small rounded-lg text-sm px-5 py-2.5">
                                    <div class="grid grid-rows-1 grid-cols-3">
                                        <img src="<c:url value='/public/editing_purple.png'/>" alt="edit" class="mr-3 h-6 sm:h-5 col-start-1">
                                        <p class="col-span-2"><spring:message code="viewJob.close"/></p>
                                    </div>
                                </button>
                            </form:form>
                        </c:if>
                        <c:if test="${!job.opened}">
                            <form:form action="${openPath}" method="post">
                                <button type="submit" class="text-sm focus:outline-none text-white bg-green-500 hover:bg-green-700 font-small rounded-lg text-sm px-5 py-2.5">
                                    <div class="grid grid-rows-1 grid-cols-3">
                                        <img src="<c:url value='/public/editing.png'/>" alt="edit" class="mr-3 h-6 sm:h-5 col-start-1">
                                        <p class="col-span-2"><spring:message code="viewJob.open"/></p>
                                    </div>
                                </button>
                            </form:form>
                        </c:if>
                    </sec:authorize>
                </div>
            </div>
            <sec:authorize access="hasAuthority('EMPLOYEE')">
            <div class="flow-root">
                <h1 class="pb-3 pt-3 font-semibold text-purple-900"><spring:message code="reviews.title_for"/> ${job.employerId.name}</h1>
                <c:if test="${myReview == null}">
                    <c:url value="/addReviewEmployer/${job.jobId}/${job.employerId.id.id}" var="postPath"/>
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
                                    <c:url value="/user/profile-image/${myReview.employeeId.id.id}" var="userImage" />
                                    <div class="flex items-center space-x-4">
                                        <div class="flex-shrink-0 self-start">
                                            <img class="w-8 h-8 rounded-full object-cover" src="${userImage}" alt="Employee Photo" onerror="this.src = '<c:url value="/public/user.png"/>'"/>
                                        </div>
                                        <div class="flex-1 min-w-0">
                                            <p class="text-xl font-medium text-gray-900 text-ellipsis">
                                                <c:out value="${myReview.review}"/>
                                            </p>
                                            <div class="grid grid-cols-2">
                                                <p class="text-sm text-gray-500 col-start-1">
                                                    <c:out value="${myReview.employeeId.name}"/>
                                                </p>
                                                <p class="text-sm text-gray-500 col-start-2 text-end">
                                                    <c:out value="${myReview.created}"/>
                                                </p>
                                            </div>
                                        </div>
                                    </div>
                                </li>
                            </c:if>
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
                            <c:url value="/trabajo/${job.jobId}" var="getPath"/>
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
            </sec:authorize>
        </div>
    </div>
</div>
<sec:authorize access="hasAuthority('EMPLOYEE')">
    <c:if test="${status.equals('sent')}">
        <div id="sent" class="absolute bottom-6 inset-1/3">
            <div class = "grid justify-items-center bg-purple-400 rounded h-1/3 p-5">
                <h1 class="text-2xl font-semibold text-white"><spring:message code="feedback.congrats"/></h1>
                <p class="font-light text-white"><spring:message code="feedback.viewJob.sent"/></p>
            </div>
        </div>
    </c:if>
    <c:if test="${status.equals('error')}">
        <div id="error" class="absolute bottom-6 inset-1/3">
            <div class = "grid justify-items-center bg-white rounded-lg h-1/3 p-5">
                <h1 class="text-2xl font-semibold text-red-700"><spring:message code="feedback.error"/></h1>
                <p class="font-light text-red-700"><spring:message code="feedback.viewJob.errorExists"/></p>
            </div>
        </div>
    </c:if>
</sec:authorize>
</body>