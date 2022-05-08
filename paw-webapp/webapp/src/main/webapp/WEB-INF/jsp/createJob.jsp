<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<c:url value="/public/css/style.css"/>"/>
    <script type="text/javascript">
        function validateExpYears() {
            var el = document.getElementById('expYears');
            if (el.value ==="") {
                el.value=0;
            }
        }
    </script>
    <link rel="icon" type="image/x-icon" href="<c:url value="/public/favicon.png"/>"/>
    <title><spring:message code="createJob.title"/></title>
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
    <jsp:param name="currentUrl" value="createJob"/>
</jsp:include>
<c:url value="/createJob" var="postPath"/>
<div class = "h-screen overflow-auto pb-5">
    <form:form modelAttribute="jobForm" action="${postPath}" method="post" pageEncoding="UTF-8">
        <div class="grid grid-cols-6">
            <div class="grid grid-row-4 col-span-4 col-start-2 mt-20 ">
                <div class="bg-gray-200 rounded-3xl p-5 shadow-2xl">
                    <div class="grid grid-cols-6 gap-6">
                        <div class="ml-3 col-span-6 w-4/5 justify-self-center">
                            <form:label path="title" for="title" class="block mb-2 text-sm font-medium text-gray-900 "><spring:message code="jobForm.label.title"/></form:label>
                            <form:input path="title" type="text" class="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-blue-500 focus:border-violet-500"/>
                            <form:errors path="title" element="p" cssStyle="color:red"/>
                        </div>
                        <div class="ml-3 col-span-3 w-4/5 justify-self-center">
                            <form:label path="location" for="location" class="block mb-2 text-sm font-medium text-gray-900 "><spring:message code="jobForm.label.location"/></form:label>
                            <form:input type="text" path="location" class="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-violet-500 focus:border-violet-500"/>
                            <form:errors path="location" element="p" cssStyle="color:red"/>
                        </div>
                        <div class="ml-3 col-span-3 w-4/5 justify-self-center">
                            <form:label path="experienceYears" class="block mb-2 text-sm font-medium text-gray-900"><spring:message code="jobForm.label.experienceYears"/></form:label>
                            <form:input type="number" path="experienceYears" id="expYears" onchange="validateExpYears()" class="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-violet-500 focus:border-violet-500"/>
                            <form:errors path="experienceYears" element="p" cssStyle="color:red"/>
                        </div>
                        <div class="ml-3 col-span-3 w-4/5 justify-self-center">
                            <form:label path="availability" class="block mb-2 text-sm font-medium text-gray-900 "><spring:message code="jobForm.availability"/></form:label>
                            <form:select path="availability" class="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-violet-500 focus:border-violet-500">
                                <form:option value="Medio dia" label="Medio dia"/>
                                <form:option value="Dia completo" label="Dia completo"/>
                                <form:option value="Con cama" label="Con cama"/>
                            </form:select>
                            <form:errors path="availability" element="p" cssStyle="color:red"/>
                        </div>
                    </div>
                    <div>
                        <h1 class="pb-3 pt-3 text-sm"><spring:message code="jobForm.abilities"/></h1>
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
                    <div class="col-span-6 my-6">
                        <form:label path="description" class="block mb-2 text-sm font-medium text-gray-900"><spring:message code="jobForm.label.description"/></form:label>
                        <form:textarea path="description" rows="3" class="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-violet-500 focus:border-violet-500" />
                        <form:errors path="description" element="p" cssStyle="color: red"/>
                    </div>
                    <div class="mt-5 col-start-2 col-span-4 row-span-3">
                        <button type="submit" class="text-lg w-full focus:outline-none text-violet-900 bg-purple-900 bg-opacity-30 hover:bg-purple-900 hover:bg-opacity-50 font-small rounded-lg text-sm px-5 py-2.5"><spring:message code="jobForm.button"/></button>
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

    const buttons = ["cocinar", "planchar", "menores", "mayores", "especiales", "mascotas"]
    window.onload = function() {
        console.log("Entre");
        buttons.forEach(function(word) {
            console.log(word)

            let property = document.getElementById(word + "-cb");
            let label = document.getElementById(word + "-label");
            console.log(property.checked)
            if (property.checked === true) {
                label.style.backgroundColor = "#c4b5fd";
                window.sessionStorage.setItem(word, "#c4b5fd");
            }
        })
    };
</script>