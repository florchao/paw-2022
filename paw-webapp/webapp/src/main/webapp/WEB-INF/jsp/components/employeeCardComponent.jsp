<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
    <title>employeeCard</title>
</head>
<body>
    <a href="<c:url value="/verPerfil/${param.id}"/>" class="flex flex-col items-center bg-white rounded-lg border shadow-md mb-5 md:flex-row md:max-w-full hover:bg-gray-100 ">
        <img class="object-cover w-full h-96 rounded-t-lg md:h-auto md:w-48 md:rounded-none md:rounded-l-lg" src="/docs/images/blog/image-4.jpg" alt="">
        <div class="flex flex-col justify-between p-4 leading-normal">
            <h5 class="mb-2 text-2xl font-bold tracking-tight text-black"><c:out value="${param.name}"/></h5>
            <p class="mb-3 font-normal text-gray-700 dark:text-gray-400"><c:out value="${param.location}"/></p>
<%--            <p class="mb-3 font-normal text-gray-700 dark:text-gray-400">10+ experiencia</p>--%>
        </div>
    </a>
</body>
</html>