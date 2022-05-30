<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html lang="es" class="scroll-smooth">
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="<c:url value="/public/css/style.css"/>"/>
  <script src="https://cdn.tailwindcss.com"></script>
  <script src="<c:url value="/public/javascript/utils.js"/>"></script>
  <link rel="icon" type="image/x-icon" href="<c:url value="/public/favicon.png"/>"/>
  <title><spring:message code="reviews.title"/> ${employee.name}</title>
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
<c:url value="/addReview/${id}" var="postPath"/>
<div class="grid h-screen grid-cols-6 overflow-auto">
  <div class="grid justify-items-center mx-6 mt-24 col-span-6">
    <p class="text-xl font-semibold text-white">
      <spring:message code="reviews.title"/> ${employee.name}
    </p>
  </div>
  <div class=" grid grid-row-4 col-span-4 col-start-2 row-span-6 h-full">
    <div class=" bg-gray-200 rounded-3xl overflow-auto p-5 mb-5 shadow-2xl">
      <div class="flow-root">
        <c:if test="${myReview == null}">
          <sec:authorize access="hasAuthority('EMPLOYER')">
            <form:form modelAttribute="reviewForm" action="${postPath}" method="post" pageEncoding="UTF-8">
              <div class="">
                <form:label path="content" class="block mb-2 text-sm font-medium text-gray-900"><spring:message code="reviews.form.label"/></form:label>
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
                  <c:url value="/user/profile-image/${myReview.employerId}" var="userImage" />
                  <div class="flex items-center space-x-4">
                    <div class="flex-shrink-0 self-start">
                      <img class="w-8 h-8 rounded-full object-cover" src="${userImage}" alt="Employee Photo" onerror="this.src = '<c:url value="/public/user.png"/>'"/>
                    </div>
                    <div class="flex-1 min-w-0">
                      <p class="text-xl font-medium text-gray-900 truncate">
                        <c:out value="${myReview.employerName}"/>
                      </p>
                      <p class="text-sm text-gray-500">
                        <c:out value="${myReview.review}"/>
                      </p>
                    </div>
                  </div>
                </li>
              </c:if>
              <c:forEach var="review" items="${ReviewList}">
                <c:url value="/user/profile-image/${review.employerId}" var="image" />
                <li class="py-3 sm:py-4">
                    <div class="flex items-center space-x-4">
                      <div class="flex-shrink-0 self-start">
                        <img class="w-8 h-8 rounded-full object-cover" src="${image}" alt="Employee Photo" onerror="this.src = '<c:url value="/public/user.png"/>'"/>
                      </div>
                      <div class="flex-1 min-w-0">
                        <p class="text-xl font-medium text-gray-900 truncate">
                          <c:out value="${review.employerName}"/>
                        </p>
                        <p class="text-sm text-gray-500">
                          <c:out value="${review.review}"/>
                        </p>
                      </div>
                    </div>
                </li>
              </c:forEach>
              <c:url value="/opiniones/${id}" var="getPath"/>
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
</body>
