import {useTranslation} from "react-i18next";
import {useEffect, useState} from "react";
import {EmployeeService} from "../service/EmployeeService";
import {ContactService} from "../service/ContactService";
import {useLocation} from "react-router-dom";
import ReviewCard from "../components/ReviewCard";
import ContactCard from "../components/ContactCard";
import ContactCardPopUp from "../components/ContactCardPopUp";

export const Contacts = () => {

    const { t } = useTranslation();
    const [contacts, setContacts]: any = useState()

    const {id} = useLocation().state

    useEffect(() => {
        ContactService.contacts(id).then((val) => setContacts(val));
    }, [])

    return (
        <div className="grid content-start h-screen overflow-auto pl-5 pr-5">
            <div className="my-9 w-full"></div>
            <p className="text-3xl font-semibold text-violet-900 mb-4 mt-4 text-center">
                {t('Contacts.title')}
            </p>
            {contacts && contacts.length == 0 &&
                <div className="grid content-center justify-center h-5/6 mt-16">
                    <div className="grid justify-items-center">
                        <img src="/images/sinEmpleadas.png" alt="sinEmpleadas" className="mr-3 h-6 sm:h-52"/>
                        <p className="text-3xl font-semibold text-purple-700">
                            {t('Contacts.noContacts')}
                        </p>
                    </div>
                </div>
            }
            {contacts && contacts.length > 0 && contacts.map((contact: any) =>
                <div className="flex flex-wrap content-start pl-5 pr-5">

                        <a href={"#" + contact.employerID.id.id} rel="modal:open"
                           className=" transition hover:scale-105">
                            <ContactCard contact={contact}/>
                        </a>
                        <ContactCardPopUp contact={contact}/>
                </div>)
            }

{/*            <c:choose>*/}
{/*                <c:when test="${ContactList.size() == 0}">*/}
{/*                    <div class = "grid content-center justify-center h-5/6 mt-16">*/}
{/*                        <div class = "grid justify-items-center">*/}
{/*                            <img src="<c:url value='/public/sinEmpleadas.png'/>" alt="sinEmpleadas" class="mr-3 h-6 sm:h-52">*/}
{/*                                <p class="text-3xl font-semibold text-purple-700"><spring:message code="contacts.noContacts"/></p>*/}
{/*                        </div>*/}
{/*                    </div>*/}
{/*                </c:when>*/}
{/*                <c:otherwise>*/}
{/*                    <div class="flex flex-wrap content-start pl-5 pr-5">*/}
{/*                        <c:forEach var="contact" items="${ContactList}">*/}
{/*                            <c:set var="contact" value="${contact}" scope="request"/>*/}
{/*                            <a href="#${contact.employerID.id.id}" rel="modal:open" class=" transition hover:scale-105">*/}
{/*                                <jsp:include page="components/contactCard.jsp">*/}
{/*                                    <jsp:param name="name" value="${contact.employerID.name}"/>*/}
{/*                                    <jsp:param name="message" value="${contact.message}"/>*/}
{/*                                    <jsp:param name = "date" value = "${contact.created}"/>*/}
{/*                                    <jsp:param name="employerID" value="${contact.employerID.id.id}"/>*/}

{/*                                </jsp:include>*/}
{/*                            </a>*/}

{/*                            <div id="${contact.employerID.id.id}" class="modal w-fit">*/}
{/*                                <div class="flex grid grid-cols-3 items-center py-8 w-fit">*/}
{/*                                    <img class="col-span-1 mb-3 w-24 h-24 rounded-full shadow-lg object-cover" src="<c:url value="/user/profile-image/${contact.employerID}"/>" alt="profile pic" onerror="this.src = '<c:url value="/public/user.png"/>'"/>*/}
{/*                                    <div class="col-span-2 row-span-2">*/}
{/*                                        <h5 class="mb-1 text-xl font-medium text-gray-900"><c:out value="${contact.employerID.name}"/></h5>*/}
{/*                                        <p class="text-gray-500 text-sm p-1.5">*/}
{/*                                            <spring:message code = "contact.email"/>*/}
{/*                                            <c:out value="${contact.employerID.id.email}"/>*/}
{/*                                        </p>*/}
{/*                                        <p class="text-gray-500 text-sm p-1.5">*/}
{/*                                            <spring:message code = "contact.phoneNumber"/>*/}
{/*                                            <c:out value="${contact.phoneNumber}"/>*/}
{/*                                        </p>*/}
{/*                                    </div>*/}
{/*                                    <div class="col-span-3 h-80 overflow-y-auto py-5">*/}
{/*                                        <span class="flex flex-wrap text-gray-500 text-justify text-ellipsis px-5" style="width:100%; display:inline-block; word-wrap: break-word;"><c:out value="${contact.message}"/></span>*/}
{/*                                    </div>*/}
{/*                                </div>*/}
{/*                            </div>*/}
{/*                        </c:forEach>*/}
{/*                    </div>*/}
{/*                    <div>*/}
{/*                        <c:url value="/contactos" var="getPath"/>*/}
{/*                        <form method="get" action="${getPath}">*/}
{/*                            <input style="visibility: hidden" type="number" name="page" id="pageNumber"/>*/}
{/*                            <c:if test="${maxPage > 0 && page + 1 <= maxPage}">*/}
{/*                                <div class="flex flex-row justify-center mt-4">*/}
{/*                                    <c:choose>*/}
{/*                                        <c:when test="${page < 1}">*/}
{/*                                            <button type="submit" class="font-semibold border shadow-md focus:outline-none text-violet-900 bg-gray-300 border-purple-900 rounded-lg px-2" disabled="true" onclick="previousPage(${page})"><</button>*/}
{/*                                        </c:when>*/}
{/*                                        <c:otherwise>*/}
{/*                                            <button type="submit" class="font-semibold border shadow-md focus:outline-none text-violet-900 bg-purple-400 border-purple-900 hover:bg-yellow-300 hover:bg-opacity-50 rounded-lg px-2" onclick="previousPage(${page})"><</button>*/}
{/*                                        </c:otherwise>*/}
{/*                                    </c:choose>*/}
{/*                                    <div class="bg--300 w-16 flex justify-center">*/}
{/*                                        <h1 class="text-yellow-300">${page + 1} of ${maxPage}</h1>*/}
{/*                                    </div>*/}
{/*                                    <c:choose>*/}
{/*                                        <c:when test="${page + 1 == maxPage}">*/}
{/*                                            <button type="submit" class="font-semibold border shadow-md focus:outline-none text-violet-900 bg-gray-300 border-purple-900 rounded-lg px-2" disabled="true" onclick="nextPage(${page})">></button>*/}
{/*                                        </c:when>*/}
{/*                                        <c:otherwise>*/}
{/*                                            <button type="submit" id="prevPageButton" class=" font-semibold border shadow-md focus:outline-none text-violet-900 bg-purple-400 border-purple-900 hover:bg-yellow-300 hover:bg-opacity-50 rounded-lg px-2" onclick="nextPage(${page})">></button>*/}
{/*                                        </c:otherwise>*/}
{/*                                    </c:choose>*/}
{/*                            </c:if>*/}
{/*                    </div>*/}

{/*                </form>*/}
{/*        </div>*/}
{/*</c:otherwise>*/}
{/*</c:choose>*/}
        </div>
    )
}
