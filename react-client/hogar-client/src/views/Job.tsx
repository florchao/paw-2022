import {useEffect, useState} from "react";
import {useLocation} from "react-router-dom";
import {useTranslation} from "react-i18next";
import {JobService} from "../service/JobService";
import {ContactService} from "../service/ContactService";

export const Job = () => {

    const [job, setJob]: any = useState()

    const { id } = useLocation().state

    const { t } = useTranslation();

    useEffect(()=> {
        JobService.getJob(id).then((e) => {setJob(e)});
    }, [])


    return(
        <div className="grid h-screen grid-cols-6 overflow-auto">
            {job &&
            <div className=" grid grid-row-4 col-span-4 col-start-2 h-fit">
                <div className=" bg-gray-200 rounded-3xl overflow-auto p-5 mt-24 mb-5 shadow-2xl">
                    <div className="grid grid-cols-5 justify-center">
                        <div className="mb-2 col-span-5">
                            <p className="text-2xl font-semibold whitespace-nowrap text-purple-900 text-ellipsis overflow-hidden">{job.title}</p>
                            <p className="text-sm whitespace-nowrap text-ellipsis overflow-hidden">{job.employerId.name}</p>
                        </div>
                        <div className="col-span-2">
                            <h1 className="pb-3 pt-3 text-purple-900 font-semibold">{t('Job.jobLocation')}</h1>
                            <h1 className="block mb-2 ml-2 text-sm font-medium text-gray-600 text-ellipsis overflow-hidden"> {job.location}</h1>
                        </div>
                        <div className="col-span-2">
                            <h1 className="pb-3 pt-3 text-purple-900 font-semibold">{t('Job.experience')}</h1>
                            <h1 className="block mb-2 ml-2 text-sm font-medium text-gray-600 "> {job.experienceYears}</h1>
                        </div>
                        {/*<sec:authorize access="hasAuthority('EMPLOYEE')">*/}
                        {/*    <div class="col-start-5 row-start-2">*/}
                        {/*        <c:if test="${alreadyApplied == -1}">*/}
                        {/*            <form:form action="${postPath}" method="post">*/}
                        {/*                <button class="ml-2 h-fit w-fit text-xs text-white bg-violet-400 border border-purple-900 focus:outline-none focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 hover:bg-yellow-300 hover:bg-opacity-70 hover:text-purple-900"><spring:message code="viewJob.apply"/></button>*/}
                        {/*            </form:form>*/}
                        {/*        </c:if>*/}
                        {/*        <c:if test="${alreadyApplied >= 0}">*/}
                        {/*            <h1 class="pb-3 pt-3 font-semibold text-purple-900"><spring:message code="viewJob.status"/></h1>*/}
                        {/*            <c:if test="${alreadyApplied == 0}">*/}
                        {/*                <a class="text-sm focus:outline-none text-purple-900 bg-yellow-300 font-small rounded-lg text-sm px-5 py-2.5"><spring:message code="viewJob.pending"/> </a>*/}
                        {/*            </c:if>*/}
                        {/*            <c:if test="${alreadyApplied == 1}">*/}
                        {/*                <a class="text-sm focus:outline-none text-purple-900 bg-green-300 font-small rounded-lg text-sm px-5 py-2.5"><spring:message code="viewJob.accepted"/> </a>*/}
                        {/*            </c:if>*/}
                        {/*            <c:if test="${alreadyApplied == 2}">*/}
                        {/*                <a class="text-sm focus:outline-none text-purple-900 bg-red-300 font-small rounded-lg text-sm px-5 py-2.5"><spring:message code="viewJob.denied"/> </a>*/}
                        {/*            </c:if>*/}
                        {/*        </c:if>*/}
                        {/*    </div>*/}
                        {/*</sec:authorize>*/}
                    </div>
                    <div className="grid grid-cols-5">
                        <div className="col-span-2">
                            <h1 className="pb-3 pt-3 font-semibold text-purple-900"> {t('Job.abilities')}</h1>
                            <ul role="list" className="list-inside marker:text-purple-900 list-disc pl-5 space-y-3 text-gray-500">
                                {/*<c:forEach var="ability" items="${job.abilitiesArr}">*/}
                                {/*    <li><c:out value="${ability}"/></li>*/}
                                {/*</c:forEach>*/}
                                {job.abilities.map((ability: String) => (
                                    <li>
                                        {ability}
                                    </li>
                                ))}
                            </ul>
                        </div>
                        <div className="col-span-1 col-start-3">
                            <h1 className="pb-3 pt-3 font-semibold text-purple-900">{t('Job.availability')}
                            </h1>
                            <ul role="list" className="list-inside marker:text-purple-900 list-disc pl-5 space-y-3 text-gray-500">
                                {/*<c:forEach var="availability" items="${job.availabilityArr}">*/}
                                {/*    <li><c:out value="${availability}"/></li>*/}
                                {/*</c:forEach>*/}
                                {job.availability.map((availability: String) => (
                                    <li>
                                        {availability}
                                    </li>
                                ))}
                            </ul>
                        </div>
                        <div className="col-span-5">
                            <h1 className="pb-3 pt-3 font-semibold text-purple-900">{t('Job.description')}</h1>
                            <h1 className="block ml-2 mb-2 text-sm font-medium text-gray-600 text-ellipsis overflow-hidden">{job.description}</h1>
                        </div>
                    </div>
                    <div className="grid grid-rows-3 grid-cols-5">
                        <div className="col-start-2 row-start-3">
                            {/*<sec:authorize access="hasAuthority('EMPLOYER')">*/}
                            {/*    <form:form action="${deletePath}" method="delete">*/}
                            {/*        <button type="submit" class="text-sm focus:outline-none text-white bg-red-500 hover:bg-red-700 font-small rounded-lg text-sm px-5 py-2.5">*/}
                            {/*            <div class="grid grid-rows-1 grid-cols-3">*/}
                            {/*                <img src="<c:url value='/public/bin.png'/>" alt="bin" class="mr-3 h-6 sm:h-5 col-start-1">*/}
                            {/*                    <p class="col-span-2"><spring:message code="viewJob.delete"/></p>*/}
                            {/*            </div>*/}
                            {/*        </button>*/}
                            {/*    </form:form>*/}
                            {/*</sec:authorize>*/}
                        </div>
                        <div className="col-start-4 row-start-3">
                            {/*<sec:authorize access="hasAuthority('EMPLOYER')">*/}
                            {/*    <c:if test="${job.opened}">*/}
                            {/*        <form:form action="${closePath}" method="post">*/}
                            {/*            <button type="submit" class="text-sm focus:outline-none text-purple-700 bg-yellow-300 border-violet-700 hover:bg-yellow-200 font-small rounded-lg text-sm px-5 py-2.5">*/}
                            {/*                <div class="grid grid-rows-1 grid-cols-3">*/}
                            {/*                    <img src="<c:url value='/public/editing_purple.png'/>" alt="edit" class="mr-3 h-6 sm:h-5 col-start-1">*/}
                            {/*                        <p class="col-span-2"><spring:message code="viewJob.close"/></p>*/}
                            {/*                </div>*/}
                            {/*            </button>*/}
                            {/*        </form:form>*/}
                            {/*    </c:if>*/}
                            {/*    <c:if test="${!job.opened}">*/}
                            {/*        <form:form action="${openPath}" method="post">*/}
                            {/*            <button type="submit" class="text-sm focus:outline-none text-white bg-green-500 hover:bg-green-700 font-small rounded-lg text-sm px-5 py-2.5">*/}
                            {/*                <div class="grid grid-rows-1 grid-cols-3">*/}
                            {/*                    <img src="<c:url value='/public/editing.png'/>" alt="edit" class="mr-3 h-6 sm:h-5 col-start-1">*/}
                            {/*                        <p class="col-span-2"><spring:message code="viewJob.open"/></p>*/}
                            {/*                </div>*/}
                            {/*            </button>*/}
                            {/*        </form:form>*/}
                            {/*    </c:if>*/}
                            {/*</sec:authorize>*/}
                        </div>
                    </div>
{/*                    <sec:authorize access="hasAuthority('EMPLOYEE')">*/}
{/*                        <div class="flow-root">*/}
{/*                            <h1 class="pb-3 pt-3 font-semibold text-purple-900"><spring:message code="reviews.title_for"/> ${job.employerId.name}</h1>*/}
{/*                            <c:if test="${myReview == null}">*/}
{/*                                <c:url value="/addReviewEmployer/${job.jobId}/${job.employerId.id.id}" var="postPath"/>*/}
{/*                                <form:form modelAttribute="reviewForm" action="${postPath}" method="post" pageEncoding="UTF-8">*/}
{/*                                    <div class="">*/}
{/*                                        <form:label path="content" class="block pb-3 pt-3 font-semibold text-gray-900"><spring:message code="reviews.form.label"/></form:label>*/}
{/*                                        <form:textarea path="content" rows="3" class="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-violet-500 focus:border-violet-500" />*/}
{/*                                        <form:errors path="content" element="p" cssStyle="color: red"/>*/}
{/*                                        <div class="mt-5 col-start-2 col-span-4 row-span-3">*/}
{/*                                            <button type="submit" class="text-lg w-full focus:outline-none text-violet-900 bg-purple-900 bg-opacity-30 hover:bg-purple-900 hover:bg-opacity-50 font-small rounded-lg text-sm px-5 py-2.5"><spring:message code="review.button"/></button>*/}
{/*                                        </div>*/}
{/*                                    </div>*/}
{/*                                </form:form>*/}
{/*                            </c:if>*/}
{/*                            <c:choose>*/}
{/*                                <c:when test="${ReviewList.size() == 0 && myReview == null}">*/}
{/*                                    <div class = "grid content-center justify-center h-5/6 mt-16">*/}
{/*                                        <div class = "grid justify-items-center">*/}
{/*                                            <img src="<c:url value='/public/sinEmpleadas.png'/>" alt="sinEmpleadas" class="mr-3 h-6 sm:h-52">*/}
{/*                                                <p class="text-3xl font-semibold text-purple-700"><spring:message code="reviews.noReviews"/></p>*/}
{/*                                        </div>*/}
{/*                                    </div>*/}
{/*                                </c:when>*/}
{/*                                <c:otherwise>*/}
{/*                                    <ul role="list" class="divide-y divide-gray-300">*/}
{/*                                        <c:if test="${myReview != null}">*/}
{/*                                            <li class = "py-3 px-3 sm:py-4 bg-violet-300 bg-opacity-25">*/}
{/*                                                <c:url value="/user/profile-image/${myReview.employeeId.id.id}" var="userImage" />*/}
{/*                                                <div class="flex items-center space-x-4">*/}
{/*                                                    <div class="flex-shrink-0 self-start">*/}
{/*                                                        <img class="w-8 h-8 rounded-full object-cover" src="${userImage}" alt="Employee Photo" onerror="this.src = '<c:url value="/public/user.png"/>'"/>*/}
{/*                                                    </div>*/}
{/*                                                    <div class="flex-1 min-w-0">*/}
{/*                                                        <p class="text-xl font-medium text-gray-900 text-ellipsis">*/}
{/*                                                            <c:out value="${myReview.review}"/>*/}
{/*                                                        </p>*/}
{/*                                                        <div class="grid grid-cols-2">*/}
{/*                                                            <p class="text-sm text-gray-500 col-start-1">*/}
{/*                                                                <c:out value="${myReview.employeeId.name}"/>*/}
{/*                                                            </p>*/}
{/*                                                            <p class="text-sm text-gray-500 col-start-2 text-end">*/}
{/*                                                                <c:out value="${myReview.created}"/>*/}
{/*                                                            </p>*/}
{/*                                                        </div>*/}
{/*                                                    </div>*/}
{/*                                                </div>*/}
{/*                                            </li>*/}
{/*                                        </c:if>*/}
{/*                                        <c:forEach var="review" items="${ReviewList}">*/}
{/*                                            <c:url value="/user/profile-image/${review.employeeId.id.id}" var="image" />*/}
{/*                                            <li class="py-3 sm:py-4">*/}
{/*                                                <div class="flex items-center space-x-4">*/}
{/*                                                    <div class="flex-shrink-0 self-start">*/}
{/*                                                        <img class="w-8 h-8 rounded-full object-cover" src="${image}" alt="Employee Photo" onerror="this.src = '<c:url value="/public/user.png"/>'"/>*/}
{/*                                                    </div>*/}
{/*                                                    <div class="flex-1 min-w-0">*/}
{/*                                                        <p class="text-xl font-medium text-gray-900 text-ellipsis">*/}
{/*                                                            <c:out value="${review.review}"/>*/}
{/*                                                        </p>*/}
{/*                                                        <div class="grid grid-cols-2">*/}
{/*                                                            <p class="text-sm text-gray-500 col-start-1">*/}
{/*                                                                <c:out value="${review.employeeId.name}"/>*/}
{/*                                                            </p>*/}
{/*                                                            <p class="text-sm text-gray-500 col-start-2 text-end">*/}
{/*                                                                <c:out value="${review.created}"/>*/}
{/*                                                            </p>*/}
{/*                                                        </div>*/}
{/*                                                    </div>*/}
{/*                                                </div>*/}
{/*                                            </li>*/}
{/*                                        </c:forEach>*/}
{/*                                        <c:url value="/trabajo/${job.jobId}" var="getPath"/>*/}
{/*                                        <form method="get" action="${getPath}">*/}
{/*                                            <c:if test="${maxPage > 0 && page + 1 <= maxPage}">*/}
{/*                                                <div class="flex flex-row justify-center mt-4">*/}
{/*                                                    <c:choose>*/}
{/*                                                        <c:when test="${page < 1}">*/}
{/*                                                            <button type="submit" class="font-semibold border shadow-md focus:outline-none text-violet-900 bg-gray-300 border-purple-900 rounded-lg px-2" disabled="true" onclick="previousPage(${page})"><</button>*/}
{/*                                                        </c:when>*/}
{/*                                                        <c:otherwise>*/}
{/*                                                            <button type="submit" class="font-semibold border shadow-md focus:outline-none text-violet-900 bg-purple-400 border-purple-900 hover:bg-yellow-300 hover:bg-opacity-50 rounded-lg px-2" onclick="previousPage(${page})"><</button>*/}
{/*                                                        </c:otherwise>*/}
{/*                                                    </c:choose>*/}
{/*                                                    <div class="bg--300 w-16 flex justify-center">*/}
{/*                                                        <h1 class="text-purple-900">${page + 1} of ${maxPage}</h1>*/}
{/*                                                    </div>*/}
{/*                                                    <c:choose>*/}
{/*                                                        <c:when test="${page + 1 == maxPage}">*/}
{/*                                                            <button type="submit" class="font-semibold border shadow-md focus:outline-none text-violet-900 bg-gray-300 border-purple-900 rounded-lg px-2" disabled="true" onclick="nextPage(${page})">></button>*/}
{/*                                                        </c:when>*/}
{/*                                                        <c:otherwise>*/}
{/*                                                            <button type="submit" id="prevPageButton" class=" font-semibold border shadow-md focus:outline-none text-violet-900 bg-purple-400 border-purple-900 hover:bg-yellow-300 hover:bg-opacity-50 rounded-lg px-2" onclick="nextPage(${page})">></button>*/}
{/*                                                        </c:otherwise>*/}
{/*                                                    </c:choose>*/}
{/*                                            </c:if>*/}
{/*                        </div>*/}
{/*                        <input style="visibility: hidden" type="number" name="page" id="pageNumber"/>*/}
{/*                    </form>*/}
{/*                </ul>*/}
{/*            </c:otherwise>*/}
{/*        </c:choose>*/}
{/*</div>*/}
{/*</sec:authorize>*/}
</div>
</div> }
</div>
    )


}

export default Job;