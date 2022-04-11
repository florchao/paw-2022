<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
    <title>Hogar</title>
</head>
<body>
<jsp:include page="components/navbar.jsp">
    <jsp:param name="currentUrl" value="init"/>
</jsp:include>
    <div class="grid grid-cols-2 gap-4 pt-16 h-screen">
        <div class="grid grid-rows-2 gap-4 pl-8">
            <div class="grid content-center">
                <h3 class="text-3xl font-semibold text-purple-700">¡Bienvenido a Hogar!</h3>
                <h3 class="text-2xl text-purple-700">Un lugar para conectarse y encontrar lo que estas buscando.</h3>
            </div>
            <div class="flex flex-col">
                <div class="grid">
                    <div class="pb-4 grid col-start-2 col-span-2">
                        <form:form method="GET" action="/redirectSearch">
                            <button class="bg-violet-300 font-semibold hover:bg-yellow-300 shadow-lg text-violet-900 py-2 px-4 rounded-xl w-2/5 border-hidden hover:border-solid border-2 border-purple-300">
                                Buscar Empleadas
                            </button>
                        </form:form>
                    </div>
                    <div class="grid col-start-2 col-span-2">
                        <form:form method="GET" action="/redirectCreateProfile">
                            <button class="bg-violet-300 font-semibold hover:bg-yellow-300 shadow-lg text-violet-900 py-2 px-4 rounded-xl w-2/5 border-hidden hover:border-solid border-2 border-purple-300">
                                Crear Perfil
                            </button>
                        </form:form>
                    </div>
                </div>
            </div>

        </div>
        <div class="pt-8">
            <img src="<c:url value='../../public/indexImage.jpg'/>" alt="primera foto">
        </div>
    </div>
</body>
</html>