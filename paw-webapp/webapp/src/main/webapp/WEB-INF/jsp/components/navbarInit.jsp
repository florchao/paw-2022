<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
    <title>navbarInit</title>
</head>
<body>
    <nav class="bg-white absolute w-full px-2 sm:px-4 py-2.5 dark:bg-violet-500">
        <div class="h-30 grid grid-cols-3 space-between">
            <a href ="/" class = "flex items-center mx-8">
                <img src="<c:url value='../../public/hogar.png'/>" alt="logo" class="mr-3 h-6 sm:h-9">
            </a>
        </div>
    </nav>
</body>
</html>