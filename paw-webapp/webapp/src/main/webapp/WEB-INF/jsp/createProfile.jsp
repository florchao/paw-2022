<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
    <title>Crear Perfil</title>
</head>
<body>
    <nav class="bg-white absolute w-full px-2 sm:px-4 py-2.5 dark:bg-violet-500">
        <div class="container flex flex-row items-center mx-auto">
            <!-- La imagen tiene que estar en una carpeta assets -->
            <img src="../logo.svg" class="mr-3 h-6 sm:h-9">
            <span class="text-xl font-semibold whitespace-nowrap dark:text-white">Hogar</span>
        </div>
    </nav>
    <c:url value="/createEmployee" var="postPath"/>
    <form:form modelAttribute="employeeForm" action="${postPath}" method="post">
    <div class="grid grid-cols-6">
        <div class=" grid grid-row-4 col-span-4 col-start-2 h-full">
            <div class=" bg-gray-200 rounded-3xl overflow-auto p-5 mt-24">
                <div class="grid grid-cols-5 gap-6">
                    <div class="row-span-4 col-span-2 m-6">
                        <div class="overflow-hidden bg-gray-100 rounded-full">
                            <svg class="text-gray-400 -left-1" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M10 9a3 3 0 100-6 3 3 0 000 6zm-7 9a7 7 0 1114 0H3z" clip-rule="evenodd"></path></svg>
                        </div>
                    </div>
                    <div class="ml-3 col-span-3 col-start-3 w-4/5 justify-self-center">
                        <form:label path="name" class="block mb-2 text-sm font-medium text-gray-900 ">Nombre y Apellido</form:label>
                        <form:input path="name" type="text" class="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-blue-500 focus:border-violet-500"/>
                        <form:errors path="name" element="p"/>
                    </div>
                    <div class="ml-3 col-span-3 w-4/5 justify-self-center">
                        <form:label path="mail" class="block mb-2 text-sm font-medium text-gray-900 ">Mail</form:label>
                        <form:input path="mail" type="text" class="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-violet-500 focus:border-violet-500"/>
                        <form:errors path="mail" element="p"/>
                    </div>
                    <div class="ml-3 col-span-3 w-4/5 justify-self-center">
                        <form:label path="location" class="block mb-2 text-sm font-medium text-gray-900 ">Zona</form:label>
                        <form:input type="text" path="location" class="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-violet-500 focus:border-violet-500"/>
                        <form:errors path="location" element="p"/>
                    </div>
                    <div class="ml-3 col-span-3 w-4/5 justify-self-center">
                        <form:label path="experienceYears" class="block mb-2 text-sm font-medium text-gray-900 ">Años de experiencia</form:label>
                        <form:input type="text" path="experienceYears" class="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-violet-500 focus:border-violet-500"/>
                        <form:errors path="experienceYears" element="p"/>
                    </div>
                </div>

<%--                <div>--%>
<%--                    <h1 class="pb-3 font-bold">Experiencia</h1>--%>
<%--                </div>--%>
<%--                <div class="flex flex-col items-center bg-white rounded-lg border shadow-lg mb-5 md:flex-row md:max-w-full hover:bg-gray-100 w-full">--%>
<%--                    <div class="flex flex-col justify-between p-4 leading-normal">--%>
<%--                        <h5 class="mb-2 text-xl font-bold tracking-tight text-black-">Ama de llaves</h5>--%>
<%--                        <p class="mb-3 text-md font-normal text-gray-700 dark:text-gray-400">Marzo 2011 - Abril 2018</p>--%>
<%--                        <p class="mb-3 text-md font-normal text-gray-700 dark:text-gray-400">Era la encargada de realizar las tareas de la casa</p>--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--                <div class="border border-violet-300 p-5 mb-5">--%>
<%--                    <div class="grid grid-cols-6 gap-4">--%>
<%--                        <div class="ml-3 col-span-4">--%>
<%--                            <label for="experience1" class="block mb-2 text-sm font-medium text-gray-900 ">Titulo</label>--%>
<%--                            <input type="text" id="experience1" class="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-violet-300 focus:border-violet-500">--%>
<%--                        </div>--%>
<%--                        <div class="self-end col-span-2">--%>
<%--                            <button type="button" class="text-lg w-full focus:outline-none text-violet-900 bg-purple-900 bg-opacity-30 hover:bg-purple-900 hover:bg-opacity-50 font-small rounded-lg text-sm px-5 py-2.5">Añadir</button>--%>
<%--                        </div>--%>
<%--                        <div class="ml-3 col-span-2">--%>
<%--                            <label for="experience2" class="block mb-2 text-sm font-medium text-gray-900 ">Duración</label>--%>
<%--                            <input type="date" id="experience2" class="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-violet-500 focus:border-violet-500">--%>
<%--                        </div>--%>
<%--                        <div class="self-end justify-self-center">--%>
<%--                            <h1>hasta</h1>--%>
<%--                        </div>--%>
<%--                        <div class="ml-3 self-end justify-self-center col-span-2 w-full">--%>
<%--                            <label for="experience3"></label>--%>
<%--                            <input id="experience3" type="date" class="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-violet-500 focus:border-violet-500">--%>
<%--                        </div>--%>
<%--                        <div class="ml-3 col-span-6">--%>
<%--                            <label for="experience4" class="block mb-2 text-sm font-medium text-gray-900 ">Descripción</label>--%>
<%--                            <textarea id="experience4" rows="4" class="block p-2.5 w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-violet-300 focus:ring-violet-500 focus:border-violet-500"></textarea>--%>
<%--                        </div>--%>
<%--                    </div>--%>
<%--                </div>--%>
                <div>
                    <h1 class="pb-3 pt-3 font-bold">Habilidades</h1>
                </div>
                <div class="flex flex-wrap">
                    <form:checkbox path="habilities" value = "Cocinar" class="ml-8 mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2" /> Cocinar
                    <form:checkbox path="habilities" value = "Planchar" class="ml-8 mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2" />Planchar
                    <form:checkbox path="habilities" value = "Cuidado de niños" class="ml-8 mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2" />Cuidado de niños
                    <form:checkbox path="habilities" value = "Cuidado de mayores" class="ml-8 mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2" />Cuidado de mayores
                    <form:checkbox path="habilities" value = "Cuidadoss especiales" class="ml-8 mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2" />Cuidados especiales
                    <form:checkbox path="habilities" value = "Cuidado de mascotas" class="ml-8 mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2" />Cuidado de mascotas
<%--                    <button type="button" class="h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-gray-100 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2">Planchar</button>--%>
<%--                    <button type="button" class="h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-gray-100 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2">Cuidado de niños</button>--%>
<%--                    <button type="button" class="h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-gray-100 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2">Cuidado de mayores</button>--%>
<%--                    <button type="button" class="h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-gray-100 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2">Cuidados especiales</button>--%>
<%--                    <button type="button" class="h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-gray-100 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2">Cuidado de mascotas</button>--%>

                </div>
                <div>
                    <h1 class="pb-3 pt-3 font-bold">Jornada</h1>
                </div>
                <div class="flex flex-wrap">
                    <form:checkbox path="availability" value="Media jornada" class="ml-8 mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-gray-100 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2"/>Media jornada
                    <form:checkbox path="availability" value="Jornada completa" class="ml-8 mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-gray-100 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2"/>Jornada completa
                    <form:checkbox path="availability" value="Con cama" class="ml-8 mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-gray-100 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2"/>Con cama
<%--                    <button type="button" class="h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-gray-100 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2">Media jornada</button>--%>
<%--                    <button type="button" class="h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-gray-100 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2">Jornada completa</button>--%>
<%--                    <button type="button" class="h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-gray-100 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2">Con cama</button>--%>
                </div>

            </div>
            </div>
        <div class="mt-5 col-start-2 col-span-4 row-span-3">
            <input type="submit" value="Crear Perfil" class="text-lg w-full focus:outline-none text-violet-900 bg-purple-900 bg-opacity-30 hover:bg-purple-900 hover:bg-opacity-50 font-small rounded-lg text-sm px-5 py-2.5"/>
        </div>
        </div>
        </form:form>
</body>
</html>