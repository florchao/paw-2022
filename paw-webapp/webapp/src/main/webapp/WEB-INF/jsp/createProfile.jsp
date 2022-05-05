<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<html lang="es">
<head>
    <meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<c:url value="/public/css/style.css"/>"/>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="icon" type="image/x-icon" href="<c:url value="/public/favicon.png"/>"/>
    <title><spring:message code="createProfile.title"/></title>
    <script type="text/javascript">
            function validateExpYears() {
                var el = document.getElementById('expYears');
                if (el.value ==="") {
                    el.value=0;
                }
            }
        </script>
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
    <jsp:param name="currentUrl" value="createProfile"/>
</jsp:include>
<c:url value="/createEmployee/${userID}" var="postPath"/>
<div class = "h-screen overflow-auto pb-5">
    <form:form modelAttribute="employeeForm" action="${postPath}" method="post">
        <div class="grid grid-cols-6">
            <div class="grid grid-row-4 col-span-4 col-start-2 mt-20 ">
                <div class="bg-gray-200 rounded-3xl p-5 shadow-2xl">
                    <div class="grid grid-cols-5 gap-6">
                        <div class="row-span-4 col-span-2 m-6">
                            <div class="overflow-hidden bg-gray-100 rounded-full">
                                <svg class="text-gray-400 -left-1" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M10 9a3 3 0 100-6 3 3 0 000 6zm-7 9a7 7 0 1114 0H3z" clip-rule="evenodd"></path></svg>
                            </div>
                        </div>
                        <div class="ml-3 col-span-3 col-start-3 w-4/5 justify-self-center">
                            <form:label path="name" for="name" class="block mb-2 text-sm font-medium text-gray-900 "><spring:message code="employeeForm.label.name"/></form:label>
                            <form:input path="name" type="text" class="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-blue-500 focus:border-violet-500"/>
                            <form:errors path="name" element="p" cssStyle="color:red"/>
                        </div>
                        <div class="ml-3 col-span-3 w-4/5 justify-self-center">
                            <form:label path="location" for="location" class="block mb-2 text-sm font-medium text-gray-900 "><spring:message code="employeeForm.label.location"/></form:label>
                            <form:input type="text" path="location" class="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-violet-500 focus:border-violet-500"/>
                            <form:errors path="location" element="p" cssStyle="color:red"/>
                        </div>
                        <div class="ml-3 col-span-3 w-4/5 justify-self-center">
                            <form:label path="experienceYears" class="block mb-2 text-sm font-medium text-gray-900 "><spring:message code="employeeForm.label.experienceYears"/></form:label>
                            <form:input type="number" id="expYears" onchange="validateExpYears()" path="experienceYears" class="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-violet-500 focus:border-violet-500"/>
                            <form:errors path="experienceYears" element="p" cssStyle="color:red"/>
                        </div>
                    </div>
                    <div>
                        <h1 class="pb-3 pt-3 font-bold"><spring:message code="employeeForm.abilities"/></h1>
                    </div>
                    <div class="flex flex-wrap ml-8">
                        <div class = "mb-8">
                            <form:label path="abilities" for="cocinar-cb" id = "cocinar-label" onclick="setColor('cocinar');" class="mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer"> <spring:message code="employeeForm.abilities.cook"/></form:label>
                            <form:checkbox path="abilities" id="cocinar-cb" value = "Cocinar" cssStyle="visibility: hidden"/>
                        </div>
                        <div>
                            <form:label path="abilities" for="planchar-cb" id = "planchar-label" onclick="setColor('planchar');" class="mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer"><spring:message code="employeeForm.abilities.iron"/></form:label>
                            <form:checkbox path="abilities" id="planchar-cb" value = "Planchar" cssStyle="visibility: hidden"/>
                        </div>
                        <div>
                            <form:label path="abilities" for="menores-cb" id = "menores-label" onclick="setColor('menores');" class="mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer"><spring:message code="employeeForm.abilities.young"/></form:label>
                            <form:checkbox path="abilities" id="menores-cb" value = "Cuidado de menores" cssStyle="visibility: hidden"/>
                        </div>
                        <div>
                            <form:label path="abilities" for="mayores-cb" id = "mayores-label" onclick="setColor('mayores');" class="mt-1 h-fit w-fit text-xs text-gray-900border bg-white border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer"><spring:message code="employeeForm.abilities.older"/></form:label>
                            <form:checkbox path="abilities" id="mayores-cb" value = "Cuidado de mayores" cssStyle="visibility: hidden"/>
                        </div>
                        <div>
                            <form:label path="abilities" for="especiales-cb" id = "especiales-label" onclick="setColor('especiales');" class="mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer"><spring:message code="employeeForm.abilities.specialNeeds"/></form:label>
                            <form:checkbox path="abilities" id="especiales-cb" value = "Cuidados especiales" cssStyle="visibility: hidden"/>
                        </div>
                        <div>
                            <form:label path="abilities" for="mascotas-cb" id = "mascotas-label" onclick="setColor('mascotas');" class="mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer"><spring:message code="employeeForm.abilities.pets"/></form:label>
                            <form:checkbox path="abilities" id="mascotas-cb" value = "Cuidado de mascotas" cssStyle="visibility: hidden"/>
                        </div>

                    </div>
                    <form:errors path="abilities" element="p" cssStyle="color: red; margin-top: 1.25rem"/>
                    <div>
                        <h1 class="pb-3 pt-3 font-bold"><spring:message code="employeeForm.availability"/></h1>
                    </div>
                    <div class="flex flex-wrap ml-8">
                        <div>
                            <form:label path="abilities" for="media-cb" id = "media-label" onclick="setColor('media');" class="mt-1 h-fit w-fit text-xs text-gray-900 active:bg-violet-300 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer"><spring:message code="employeeForm.availability.half"/></form:label>
                            <form:checkbox path="availability" id = "media-cb" value="Media jornada" cssStyle="visibility: hidden"/>
                        </div>
                        <div>
                            <form:label path="abilities" for="completa-cb" id = "completa-label" onclick="setColor('completa');" class="mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer"><spring:message code="employeeForm.availability.complete"/></form:label>
                            <form:checkbox path="availability" id = "completa-cb" value="Jornada completa" cssStyle="visibility: hidden"/>
                        </div>
                        <div>
                            <form:label path="abilities" for="cama-cb" id = "cama-label" onclick="setColor('cama');" class="mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer"><spring:message code="employeeForm.availability.bed"/></form:label>
                            <form:checkbox path="availability" id = "cama-cb" value="Con cama" cssStyle="visibility: hidden"/>
                        </div>
                    </div>
                    <form:errors path="availability" element="p" cssStyle="color: red; margin-top: 1.25rem"/>
                    <div class="mt-5 col-start-2 col-span-4 row-span-3">
                        <button type="submit" class="text-lg w-full focus:outline-none text-violet-900 bg-purple-900 bg-opacity-30 hover:bg-purple-900 hover:bg-opacity-50 font-small rounded-lg text-sm px-5 py-2.5"><spring:message code="employeeForm.button"/></button>
                    </div>
                </div>
            </div>
        </div>
    </form:form>
</div>
</body>
</html>

<script>

    function setColor(btn) {
        let property = document.getElementById(btn + "-cb");
        let label = document.getElementById(btn + "-label")
        if (property.checked === false) {
            label.style.backgroundColor = "#c4b5fd";
            window.sessionStorage.setItem(btn, "#c4b5fd");
        }
        else {
            label.style.backgroundColor = "#ffffff";
        }
    }
</script>