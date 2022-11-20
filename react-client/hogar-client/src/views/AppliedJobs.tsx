import {useTranslation} from "react-i18next";
import {useEffect, useState} from "react";
import {ApplicantService} from "../service/ApplicantService";
import {useLocation} from "react-router-dom";
import JobCard from "../components/JobCard";

export const AppliedJobs = () => {

    const [appliedJobs, setAppliedJobs]: any = useState()

    const { t } = useTranslation();

    const { id } = useLocation().state

    useEffect(() => {
        ApplicantService.getAppliedJobs(id).then( (e) => {setAppliedJobs(e)});
    }, [])

    return (
        <div className="grid content-start h-screen overflow-auto pl-5 pr-5">
            <div className="my-9 w-full"></div>
            <p className="text-3xl font-semibold text-violet-900 mb-4 mt-4 text-center">
                {t('AppliedJobs.title')}
            </p>
            {appliedJobs && appliedJobs.length == 0 && <div
                className="grid content-center justify-center h-5/6 mt-16">
                <div className="grid justify-items-center">
                    <img src='/images/sinEmpleadas.png' alt="sinEmpleadas"
                         className="mr-3 h-6 sm:h-52"/>
                    <p className="text-3xl font-semibold text-purple-700">
                        {t('AppliedJobs.noJobs')}
                    </p>
                </div>
            </div>}
            <div className="flex flex-wrap content-start justify-center">
            {appliedJobs && appliedJobs.length > 0 &&
                appliedJobs.map((a: any) => (
                    <div className="flex flex-wrap content-start justify-center">
                        <JobCard job={a}/>
                    </div>
                ))}
            </div>
            {/*<c:choose>*/}
            {/*    <c:when test="${jobList.size() == 0}">*/}
            {/*        <div className="grid content-center justify-center h-5/6 mt-16">*/}
            {/*            <div className="grid justify-items-center">*/}
            {/*                <img src="<c:url value='/public/sinTrabajos.png'/>" alt="sinTrabajos"*/}
            {/*                     className="mr-3 h-6 sm:h-52">*/}
            {/*                    <p className="text-3xl font-semibold text-purple-700">*/}
            {/*                        <spring:message code="appliedJobs.noJobs"/>*/}
            {/*                    </p>*/}
            {/*                    <br/>*/}
            {/*                    <a href="<c:url value="/trabajos"/>">*/}
            {/*                        <button type="button"*/}
            {/*                                className="text-lg focus:outline-none text-purple-700 bg-yellow-300 hover:bg-yellow-200 font-small rounded-lg text-lg px-5 py-2.5">*/}
            {/*                            <spring:message code="appliedJobs.explore"/>*/}
            {/*                        </button>*/}
            {/*                    </a>*/}
            {/*            </div>*/}
            {/*        </div>*/}
            {/*    </c:when>*/}
            {/*    <c:otherwise>*/}
            {/*        <div className="flex flex-wrap content-start justify-center">*/}
            {/*            <c:forEach var="entry" items="${jobList}">*/}
            {/*                <c:set var="job" value="${entry.key}" scope="request"/>*/}
            {/*                <c:set var="applied" value="${entry.value}" scope="request"/>*/}
            {/*                <div>*/}
            {/*                    <*/}
            {/*                    % request.setCharacterEncoding("utf-8");%>*/}
            {/*                    <jsp:include page="components/jobCard.jsp">*/}
            {/*                        <jsp:param name="title" value="${job.title}"/>*/}
            {/*                        <jsp:param name="description" value="${job.description}"/>*/}
            {/*                        <jsp:param name="location" value="${job.location}"/>*/}
            {/*                        <jsp:param name="jobid" value="${job.jobId}"/>*/}
            {/*                        <jsp:param name="apply" value="${applied}"/>*/}
            {/*                    </jsp:include>*/}
            {/*                </div>*/}
            {/*            </c:forEach>*/}
            {/*        </div>*/}
        </div>
    )
}

export default AppliedJobs