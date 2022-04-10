<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>contactForm2</title>
    <meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body>
    <c:url value="/contactEmployee/${id}" var="postPath"/>
    <form:form modelAttribute="contactForm" action="${postPath}" method="post">
    <div class="block p-6 rounded-lg shadow-lg bg-white max-w-md">
        <div class="form-group mb-6">
            <form:label path="name">Nombre</form:label>
            <form:input path="name" type="text" class="form-control block
                w-full
                px-3
                py-1.5
                text-base
                font-normal
                text-gray-700
                bg-white bg-clip-padding
                border border-solid border-gray-300
                rounded
                transition
                ease-in-out
                m-0
                focus:text-gray-700 focus:bg-white focus:border-blue-600 focus:outline-none"/>
            <form:errors path="name"/>
        </div>
        <div class="form-group mb-6">
            <form:label path="email">Email</form:label>
            <form:input path="email" type="email" class="form-control block
                w-full
                px-3
                py-1.5
                text-base
                font-normal
                text-gray-700
                bg-white bg-clip-padding
                border border-solid border-gray-300
                rounded
                transition
                ease-in-out
                m-0
                focus:text-gray-700 focus:bg-white focus:border-blue-600 focus:outline-none"/>
        </div>
        <div class="form-group mb-6">
            <form:label path="content">Mensaje</form:label>
            <form:textarea path="content" rows="3" class="
                        form-control
                        block
                        w-full
                        px-3
                        py-1.5
                        text-base
                        font-normal
                        text-gray-700
                        bg-white bg-clip-padding
                        border border-solid border-gray-300
                        rounded
                        transition
                        ease-in-out
                        m-0
                        focus:text-gray-700 focus:bg-white focus:border-blue-600 focus:outline-none" />
        </div>

        <button type="submit" class="
          w-full
          px-6
          py-2.5
          bg-purple-700
          text-white
          font-medium
          text-xs
          leading-tight
          uppercase
          rounded
          shadow-md
          hover:bg-purple-500 hover:shadow-lg
          focus:bg-purple-500 focus:shadow-lg focus:outline-none focus:ring-0
          active:bg-purple-500 active:shadow-lg
          transition
          duration-150
          ease-in-out">Enviar
        </button>
    </div>
    </form:form>
</body>
</html>
