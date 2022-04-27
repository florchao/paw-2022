<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.0.0/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.1/jquery.modal.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.1/jquery.modal.min.css" />

<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<c:url value="/public/css/style.css"/>"/>
    <link rel="icon" type="image/x-icon" href="<c:url value="/public/favicon.png"/>"/>
    <script src="https://cdn.tailwindcss.com"></script>
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
            <div class="flex flex-wrap content-start h-screen pl-5 pr-5">
                <div class="my-10 w-full"></div>
                <c:forEach var="contact" items="${ContactList}">
                    <c:set var="contact" value="${contact}" scope="request"/>
                    <a href="#${contact.email.replaceAll("[@.]*", "")}" rel="modal:open" class=" transition hover:scale-105">
                        <jsp:include page="components/contactCard.jsp">
                            <jsp:param name="name" value="${contact.employer}"/>
                            <jsp:param name="message" value="${contact.message}"/>
                            <jsp:param name = "date" value = "${contact.created}"/>
                        </jsp:include>
                    </a>

                    <div id="${contact.email.replaceAll("[@.]*", "")}" class="modal w-fit">
                        <div class="flex grid grid-cols-3 items-center py-8 w-fit">
                            <img class="col-span-1 mb-3 w-24 h-24 rounded-full shadow-lg" src="/docs/images/people/profile-picture-3.jpg" alt="Bonnie image"/>
                            <div class="col-span-2 row-span-2">
                                <h5 class="mb-1 text-xl font-medium text-gray-900"><c:out value="${contact.employer}"/></h5>
                                <p class="text-gray-500 text-sm p-1.5">
                                    <c:out value="${contact.email}"/>
                                </p>
                                <p class="text-gray-500 text-sm p-1.5">
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
        </c:otherwise>
    </c:choose>
</div>

</body>
</html>
