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
    <title><spring:message code="appliedJobs.title"/></title>
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
    <jsp:param name="currentUrl" value="trabajosAplicados"/>
</jsp:include>
<div class="grid content-start h-screen overflow-auto pl-5 pr-5">
    <div class="my-8 w-full"></div>
    <p class="text-3xl font-semibold text-violet-900 mb-4"><spring:message code="appliedJobs.myJobs"/></p>
    <c:choose>
        <c:when test="${JobsList.size() == 0}">
            <div class = "grid content-center justify-center h-5/6 mt-16">
                <div class = "grid justify-items-center">
                    <img src="<c:url value='/public/sinTrabajos.png'/>" alt="sinTrabajos" class="mr-3 h-6 sm:h-52">
                    <p class="text-3xl font-semibold text-purple-700"><spring:message code="appliedJobs.noJobs"/></p>
                    <br/>
                    <a href="<c:url value="/trabajos"/>">
                        <button type="button" class="text-lg focus:outline-none text-purple-700 bg-yellow-300 hover:bg-yellow-200 font-small rounded-lg text-lg px-5 py-2.5"><spring:message code="appliedJobs.explore"/></button>
                    </a>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="flex flex-wrap content-start justify-center h-screen pl-5 pr-5">
                <c:forEach var="job" items="${JobsList}">
                    <c:set var="job" value="${job}" scope="request"/>
                    <div>
                        <% request.setCharacterEncoding("utf-8");%>
                        <jsp:include page="components/jobCard.jsp">
                            <jsp:param name="title" value="${job.title}"/>
                            <jsp:param name="description" value="${job.description}"/>
                            <jsp:param name = "location" value = "${job.location}"/>
                            <jsp:param name="jobid" value="${job.jobId}"/>
                        </jsp:include>
                    </div>
                </c:forEach>
            </div>
        </c:otherwise>
    </c:choose>
</div>

</body>
</html>
