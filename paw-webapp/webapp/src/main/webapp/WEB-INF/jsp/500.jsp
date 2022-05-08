<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <script src="https://cdn.tailwindcss.com"></script>
  <link rel="icon" type="image/x-icon" href="<c:url value="/public/favicon.png"/>"/>
  <title>Error</title>
</head>
<body>
<div class = "grid content-center justify-center h-screen">
  <div class = "grid justify-items-center">
    <img src="<c:url value='/public/warning2.png'/>" alt="Error" class="mr-3 h-6 sm:h-52">
    <p class="text-3xl font-semibold text-purple-700" style="margin-top: 50px"><spring:message code="500.message"/> </p>
  </div>
</div>
</body>
</html>