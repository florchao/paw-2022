<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<c:url value="/public/css/style.css"/>"/>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="icon" type="image/x-icon" href="<c:url value="/public/favicon.png"/>"/>
    <title><spring:message code="editProfile.title"/></title>
    <script src="<c:url value="/public/javascript/editProfile.js"/>"></script>
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
    <jsp:param name="currentUrl" value="editProfile"/>
</jsp:include>
<c:url value="/editEmployee" var="postPath"/>
<div class = "h-screen overflow-auto pb-5">
    <form:form modelAttribute="employeeEditForm" action="${postPath}" method="post" pageEncoding="UTF-8" enctype="multipart/form-data">
        <div class="grid grid-cols-6" onload="">
            <div class="grid grid-row-4 col-span-4 col-start-2 mt-20 ">
                <div class="bg-gray-200 rounded-3xl p-5 shadow-2xl">
                    <div class="grid grid-cols-5 gap-6">
                        <div class="row-span-4 col-span-2 m-6">
                            <div class="overflow-hidden bg-gray-100 rounded-full">
                                <img style="object-fit: fill" id="picture" src="<c:url value="/user/profile-image/${userId}"/>"  onerror="this.src = '<c:url value="/public/user.png"/>'"/>
                            </div>
                            <form:label path="image"><spring:message code="employeeForm.insertImage"/></form:label>
                            <form:input id= "file" type="file" path="image" accept="image/png, image/jpeg" onchange="loadFile(event);"/>
                            <form:errors path="image" element="p" cssStyle="color:red;margin-left: 10px"/>
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
                            <form:checkbox path="abilities" id="planchar-cb" value = "Planchar" cssStyle="visibility: hidden" />
                        </div>
                        <div>
                            <form:label path="abilities" for="menores-cb" id = "menores-label" onclick="setColor('menores');" class="mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer"><spring:message code="employeeForm.abilities.young"/></form:label>
                            <form:checkbox path="abilities" id="menores-cb" value = "Cuidado de menores" cssStyle="visibility: hidden" />
                        </div>
                        <div>
                            <form:label path="abilities" for="mayores-cb" id = "mayores-label" onclick="setColor('mayores');" class="mt-1 h-fit w-fit text-xs text-gray-900border bg-white border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer"><spring:message code="employeeForm.abilities.older"/></form:label>
                            <form:checkbox path="abilities" id="mayores-cb" value = "Cuidado de mayores" cssStyle="visibility: hidden" />
                        </div>
                        <div>
                            <form:label path="abilities" for="especiales-cb" id = "especiales-label" onclick="setColor('especiales');" class="mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer"><spring:message code="employeeForm.abilities.specialNeeds"/></form:label>
                            <form:checkbox path="abilities" id="especiales-cb" value = "Cuidados especiales" cssStyle="visibility: hidden" />
                        </div>
                        <div>
                            <form:label path="abilities" for="mascotas-cb" id = "mascotas-label" onclick="setColor('mascotas');" class="mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer"><spring:message code="employeeForm.abilities.pets"/></form:label>
                            <form:checkbox path="abilities" id="mascotas-cb" value = "Cuidado de mascotas" cssStyle="visibility: hidden" />
                        </div>

                    </div>
                    <form:errors path="abilities" element="p" cssStyle="color: red; margin-top: 1.25rem"/>
                    <div>
                        <h1 class="pb-3 pt-3 font-bold"><spring:message code="employeeForm.availability"/></h1>
                    </div>
                    <div class="flex flex-wrap ml-8">
                        <div>
                            <form:label path="abilities" for="media-cb" id = "media-label" onclick="setColor('media');" class="mt-1 h-fit w-fit text-xs text-gray-900 active:bg-violet-300 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer"><spring:message code="employeeForm.availability.half"/></form:label>
                            <form:checkbox path="availability" id = "media-cb" value="Media jornada" cssStyle="visibility: hidden" />
                        </div>
                        <div>
                            <form:label path="abilities" for="completa-cb" id = "completa-label" onclick="setColor('completa');" class="mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer"><spring:message code="employeeForm.availability.complete"/></form:label>
                            <form:checkbox path="availability" id = "completa-cb" value="Jornada completa" cssStyle="visibility: hidden"/>
                        </div>
                        <div>
                            <form:label path="abilities" for="cama-cb" id = "cama-label" onclick="setColor('cama');" class="mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer"><spring:message code="employeeForm.availability.bed"/></form:label>
                            <form:checkbox path="availability" id = "cama-cb" value="Con cama" cssStyle="visibility: hidden" />
                        </div>
                    </div>
                    <form:errors path="availability" element="p" cssStyle="color: red; margin-top: 1.25rem"/>
                    <div class="mt-5 col-start-2 col-span-4 row-span-3">
                        <button type="submit" class="text-lg w-full focus:outline-none text-violet-900 bg-purple-900 bg-opacity-30 hover:bg-purple-900 hover:bg-opacity-50 font-small rounded-lg text-sm px-5 py-2.5"><spring:message code="editProfile.button"/></button>
                    </div>
                </div>
            </div>
        </div>
    </form:form>
</div>
</body>
</html>