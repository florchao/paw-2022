<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.0.0/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.1/jquery.modal.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.1/jquery.modal.min.css" />

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<c:url value="/public/css/style.css"/>"/>
    <link rel="icon" type="image/x-icon" href="<c:url value="/public/favicon.png"/>"/>
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="<c:url value="/public/javascript/utils.js"/>"></script>
    <title><spring:message code="contacts.title"/></title>
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
    <jsp:param name="currentUrl" value="contactos"/>
</jsp:include>
<div class="grid content-start h-screen overflow-auto pl-5 pr-5">
<div class="my-8 w-full"></div>
    <p class="text-3xl font-semibold text-violet-900 mb-4"><spring:message code="contacts.myContacts"/></p>
    <c:choose>
        <c:when test="${ContactList.size() == 0}">
            <div class = "grid content-center justify-center h-5/6 mt-16">
                <div class = "grid justify-items-center">
                    <img src="<c:url value='/public/sinEmpleadas.png'/>" alt="sinEmpleadas" class="mr-3 h-6 sm:h-52">
                    <p class="text-3xl font-semibold text-purple-700"><spring:message code="contacts.noContacts"/></p>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="flex flex-wrap content-start pl-5 pr-5">
                <c:forEach var="contact" items="${ContactList}">
                    <c:set var="contact" value="${contact}" scope="request"/>
                    <a href="#${contact.employerID.id.id}" rel="modal:open" class=" transition hover:scale-105">
                        <jsp:include page="components/contactCard.jsp">
                            <jsp:param name="name" value="${contact.employerID.name}"/>
                            <jsp:param name="message" value="${contact.message}"/>
                            <jsp:param name = "date" value = "${contact.created}"/>
                            <jsp:param name="employerID" value="${contact.employerID.id.id}"/>

                        </jsp:include>
                    </a>

                    <div id="${contact.employerID.id.id}" class="modal w-fit">
                        <div class="flex grid grid-cols-3 items-center py-8 w-fit">
                            <img class="col-span-1 mb-3 w-24 h-24 rounded-full shadow-lg object-cover" src="<c:url value="/user/profile-image/${contact.employerID}"/>" alt="profile pic" onerror="this.src = '<c:url value="/public/user.png"/>'"/>
                            <div class="col-span-2 row-span-2">
                                <h5 class="mb-1 text-xl font-medium text-gray-900"><c:out value="${contact.employerID.name}"/></h5>
                                <p class="text-gray-500 text-sm p-1.5">
                                    <spring:message code = "contact.email"/>
                                    <c:out value="${contact.employerID.id.email}"/>
                                </p>
                                <p class="text-gray-500 text-sm p-1.5">
                                    <spring:message code = "contact.phoneNumber"/>
                                    <c:out value="${contact.phoneNumber}"/>
                                </p>
                            </div>
                            <div class="col-span-3 h-80 overflow-y-auto py-5">
                                <span class="flex flex-wrap text-gray-500 text-justify text-ellipsis px-5" style="width:100%; display:inline-block; word-wrap: break-word;"><c:out value="${contact.message}"/></span>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <div>
                <c:url value="/contactos" var="getPath"/>
                <form method="get" action="${getPath}">
                    <input style="visibility: hidden" type="number" name="page" id="pageNumber"/>
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
                            <h1 class="text-yellow-300">${page + 1} of ${maxPage}</h1>
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

                </form>
            </div>
        </c:otherwise>
    </c:choose>
</div>

</body>
</html>
