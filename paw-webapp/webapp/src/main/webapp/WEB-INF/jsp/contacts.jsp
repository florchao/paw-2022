<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.0.0/jquery.min.js"></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.1/jquery.modal.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.1/jquery.modal.min.css" />
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../../public/css/style.css">
    <script src="https://cdn.tailwindcss.com"></script>
    <title>Contactos</title>
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
    <div class="flex flex-wrap content-start h-screen overflow-auto pl-5 pr-5">
        <div class="my-10 w-full"></div>
        <c:forEach var="contact" items="${ContactList}">
            <c:set var="contact" value="${contact}" scope="request"/>
            <a href="#${contact.employer}" rel="modal:open">
                <jsp:include page="components/contactCard.jsp">
                    <jsp:param name="name" value="${contact.employer}"/>
                    <jsp:param name="message" value="${contact.message}"/>
                    <jsp:param name = "date" value = "${contact.created}"/>
                </jsp:include>
            </a>

        <div id="${contact.employer}" class="modal">
            <div class="flex flex-col justify-end px-4 pt-4">
                <p class="hidden sm:inline-block text-gray-500 text-sm p-1.5">
                    <c:out value="${contact.created}"/>
                </p>
            </div>
            <div class="flex grid grid-cols-2 items-center pb-10">
                <img class="col-span-1 mb-3 w-24 h-24 rounded-full shadow-lg" src="/docs/images/people/profile-picture-3.jpg" alt="Bonnie image"/>
                <div class="col-span-1 row-span-2">
                    <h5 class="mb-1 text-xl font-medium text-gray-900"><c:out value="${contact.employer}"/></h5>
                    <p class="text-gray-500 text-sm p-1.5">
                        <c:out value="${contact.phoneNumber}"/>
                    </p>
                </div>
                <div class="col-span-2">
                    <c:choose>
                        <c:when test="${contact.message.length() <= 180}">
                            <span class="flex flex-wrap text-sm text-gray-500 text-ellipsis px-5" style="width:18rem; display:inline-block; word-wrap: break-word;"><c:out value="${contact.message}"/></span>
                        </c:when>
                        <c:otherwise>
                            <span class="flex flex-wrap text-sm text-gray-500 text-ellipsis px-5" style="width:18rem; display:inline-block; word-wrap: break-word;"><c:out value="${contact.message.substring(0, 180)}"/>...</span>
                        </c:otherwise>
                    </c:choose>
                </div>

            </div>
        </div>
        </c:forEach>
    </div>

</body>
</html>
