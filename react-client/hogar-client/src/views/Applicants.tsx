import {useLocation} from "react-router-dom";
import {useTranslation} from "react-i18next";
import {useEffect, useState} from "react";
import ApplicantCard from "../components/ApplicantCard";
import {JobService} from "../service/JobService";

export const Applicants =()=>{

    const [applicantList, setApplicantList]: any = useState()

    const {applicants, title} = useLocation().state
    const { t } = useTranslation();

    useEffect(() => {
        JobService.getApplicants(applicants).then( (e) => {setApplicantList(e)});
    }, [])

    return (
        <div className="grid h-screen grid-cols-6 overflow-auto">
            <div className="grid justify-items-center mx-6 mt-24 col-span-6">
                <p className="text-xl font-semibold text-white">
                    {t('Applicants.title')}{title}
                </p>
            </div>
            <div className=" grid grid-row-4 col-span-4 col-start-2 row-span-6 h-full">
                <div className=" bg-gray-200 rounded-3xl overflow-auto p-5 mb-5 shadow-2xl">
                    <div className="flow-root">
                        {applicantList && applicantList.length == 0 ?
                            <div className = "grid content-center justify-center h-5/6 mt-16">
                                <div className = "grid justify-items-center">
                                    <img src={ '/images/sinEmpleadas.png' } alt="sinEmpleadas" className="mr-3 h-6 sm:h-52"/>
                                    <p className="text-3xl font-semibold text-purple-700"> {t('Applicants.noApplicants')}</p>
                                </div>
                            </div>
                            :
                            <ul role="list" className="divide-y divide-gray-300">
                                {applicantList && applicantList.map((applicant: Object) => (<ApplicantCard applicant={applicant}/>))}
                            </ul>
                        }

                {/*<c:url value="/aplicantes/${jobID}" var="getPath"/>*/}
                {/*                    <form:form method="get" action="${getPath}">*/}
                {/*                        <c:if test="${maxPage > 0 && page + 1 <= maxPage}">*/}
                {/*                            <div className="flex flex-row justify-center mt-4">*/}
                {/*                                <c:choose>*/}
                {/*                                    <c:when test="${page < 1}">*/}
                {/*                                        <button type="submit" className="font-semibold border shadow-md focus:outline-none text-violet-900 bg-gray-300 border-purple-900 rounded-lg px-2" disabled="true" onclick="previousPage(${page})"><</button>*/}
                {/*                                    </c:when>*/}
                {/*                                    <c:otherwise>*/}
                {/*                                        <button type="submit" className="font-semibold border shadow-md focus:outline-none text-violet-900 bg-purple-400 border-purple-900 hover:bg-yellow-300 hover:bg-opacity-50 rounded-lg px-2" onclick="previousPage(${page})"><</button>*/}
                {/*                                    </c:otherwise>*/}
                {/*                                </c:choose>*/}
                {/*                                <div class="bg--300 w-16 flex justify-center">*/}
                {/*                                    <h1 class="text-purple-900">${page + 1} of ${maxPage}</h1>*/}
                {/*                                </div>*/}
                {/*                                <c:choose>*/}
                {/*                                    <c:when test="${page + 1 == maxPage}">*/}
                {/*                                        <button type="submit" className="font-semibold border shadow-md focus:outline-none text-violet-900 bg-gray-300 border-purple-900 rounded-lg px-2" disabled="true" onclick="nextPage(${page})">></button>*/}
                {/*                                    </c:when>*/}
                {/*                                    <c:otherwise>*/}
                {/*                                        <button type="submit" id="prevPageButton" className=" font-semibold border shadow-md focus:outline-none text-violet-900 bg-purple-400 border-purple-900 hover:bg-yellow-300 hover:bg-opacity-50 rounded-lg px-2" onclick="nextPage(${page})">></button>*/}
                {/*                                    </c:otherwise>*/}
                {/*                                </c:choose>*/}
                {/*                        </c:if>*/}
                {/*    </div>*/}
                {/*    <input style="visibility: hidden" type="number" name="page" id="pageNumber"/>*/}
                {/*</form:form>*/}
{/*            </ul>*/}
{/*        </c:otherwise>*/}
{/*</c:choose>*/}
</div>
</div>
</div>
</div>
    )
}

export default Applicants;