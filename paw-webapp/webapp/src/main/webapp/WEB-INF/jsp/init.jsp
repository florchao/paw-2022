<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!DOCTYPE html>
<html class="scroll-smooth">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="icon" type="image/x-icon" href="<c:url value="/public/favicon.png"/>"/>
    <title><spring:message code="init.page"/></title>
</head>
<body>
<c:url value="/buscarEmpleadas" var = "search"/>
<c:url value="/registrarse" var = "createProfile"/>
<c:url value="/login" var = "login"/>
<jsp:include page="components/navbar.jsp">
    <jsp:param name="currentUrl" value="init"/>
</jsp:include>
    <div class="grid grid-cols-2 gap-4 pt-16 h-screen">
        <div class="grid grid-rows-2 gap-4 pl-8">
            <div class="grid content-center">
                <h3 class="text-3xl font-semibold text-purple-700"><spring:message code="init.title"/></h3>
                <h3 class="text-2xl text-purple-700"><spring:message code="init.description"/></h3>
            </div>
            <div class="flex flex-col">
                <div class="grid">
                    <div class="pb-4 grid col-start-2 col-span-2">
                        <form:form method="get" action="${search}" pageEncoding="UTF-8">
                            <button class="bg-violet-300 font-semibold hover:bg-yellow-300 shadow-lg text-violet-900 py-2 px-4 rounded-xl w-2/5 border-hidden hover:border-solid border-2 border-purple-300">
                                <spring:message code="init.searchEmployee"/>
                            </button>
                        </form:form>
                    </div>
                    <div class="pb-4 grid col-start-2 col-span-2">
                        <form:form method="get" action="${createProfile}" pageEncoding="UTF-8">
                            <button class="bg-violet-300 font-semibold hover:bg-yellow-300 shadow-lg text-violet-900 py-2 px-4 rounded-xl w-2/5 border-hidden hover:border-solid border-2 border-purple-300">
                                <spring:message code="init.profile"/>
                            </button>
                        </form:form>
                    </div>
                    <div class="grid col-start-2 col-span-2">
                        <form:form method="get" action="${login}" pageEncoding="UTF-8">
                            <button class="bg-violet-300 font-semibold hover:bg-yellow-300 shadow-lg text-violet-900 py-2 px-4 rounded-xl w-2/5 border-hidden hover:border-solid border-2 border-purple-300">
                                <spring:message code="init.login"/>
                            </button>
                        </form:form>
                    </div>
                </div>
            </div>

        </div>
        <div class="pt-8">
            <img src="<c:url value='/public/indexImage.jpg'/>" alt="primera foto">
        </div>
    </div>
</body>
</html>