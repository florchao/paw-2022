import {useEffect, useState} from "react";
import {useLocation, useNavigate} from "react-router-dom";
import {useTranslation} from "react-i18next";
import {JobService} from "../service/JobService";
import {ReviewService} from "../service/ReviewService";
import ReviewCard from "../components/ReviewCard";
import MyReviewCard from "../components/MyReviewCard";
import {ApplicantService} from "../service/ApplicantService";
import {useForm} from "react-hook-form";
import useFormPersist from "react-hook-form-persist";

export const Job = () => {

    const [job, setJob]: any = useState()
    const [reviews, setReviews]: any = useState()
    const [myReview, setMyReview]: any = useState()
    const [status, setStatus] = useState<string>()
    const [opened, setOpened] = useState<boolean>()

    let employeeId: number;
    employeeId = localStorage.getItem('hogar-uid')? parseInt(localStorage.getItem('hogar-uid') as string) : 0;

    const {self} = useLocation().state

    const {t} = useTranslation();
    const nav = useNavigate();

    type FormData = {
        content: string;
    };

    const {register, handleSubmit, watch, formState: {errors}, getValues, setValue} = useForm<FormData>();


    watch("content")

    useFormPersist("reviewEmployerForm", {
        watch,
        setValue,
        storage: localStorage.getItem('hogar-role') == "EMPLOYEE"? window.localStorage : undefined,
        timeout: 1000 * 60 * 2,
    })

    const onSubmit = async (data: any, e: any) => {
        const post = await ReviewService.postEmployerReview(e, job.employerId.id, data.content)
        localStorage.removeItem("reviewEmployerForm")
        setMyReview(post)
    }

    useEffect(() => {
        JobService.getJob(self).then((e) => {
            setJob(e)
            setOpened(e.opened)
        })
    }, [])

    useEffect(() => {
        //TODO: corregir tema reviews
            if (job !== undefined && localStorage.getItem("hogar-role") == "EMPLOYEE") {
                ReviewService.getEmployerReviews(job.employerId.reviews).then(
                    (rsp) => {
                        setReviews(rsp)
                    }
                )
                ReviewService.getMyEmployerReview(job.employerId.employeeReview).then(
                    (rsp) => {
                        setMyReview(rsp)
                    }
                )
            }
        }, [job]
    )

    useEffect(() => {
        if (job && localStorage.getItem("hogar-role") == "EMPLOYEE")
            ApplicantService.getApplicationStatus(employeeId, job.jobId).then((s) => {
                setStatus(s)
            })
    }, [job])

    function delApplication() {
        ApplicantService.deleteApplication(employeeId, job.jobId).then(() => {
                nav('/jobs', {replace: true})
            }
        );
    }

    async function createApplicant(){
        const newStatus = await ApplicantService.createApplicant(job.jobId)
        setStatus(newStatus)
    }

    function delJob(){
        JobService.deleteJob(job.jobId).then(()=>{
            nav('/jobs', {replace: true})
        })
    }

    async function openJob(){
        await JobService.updateJobStatus(job.jobId, true)
        setOpened(true)
    }

    async function closeJob(){
        await JobService.updateJobStatus(job.jobId, false)
        setOpened(false)
    }


    return (
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

                            { status &&
                                {
                                    '-1':<div className="col-start-5 row-start-2">
                                            <button type="submit" onClick={createApplicant} className="ml-2 h-fit w-fit text-xs text-white bg-violet-400 border border-purple-900 focus:outline-none focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 hover:bg-yellow-300 hover:bg-opacity-70 hover:text-purple-900">{t('Job.apply')}</button>
                                        </div>,
                                    '0': <div className="col-start-5 row-start-2">
                                            <h1 className="pb-3 pt-3 font-semibold text-purple-900">
                                                {t('Job.statusTitle')}
                                            </h1>
                                            <a className="text-sm focus:outline-none text-purple-900 bg-yellow-300 font-small rounded-lg text-sm px-5 py-2.5">
                                                {t('Job.pending')}
                                            </a>
                                        <br/><br/>
                                         <button type="submit" onClick={delApplication} className="ml-2 h-fit w-fit text-xs text-white bg-violet-400 border border-purple-900 focus:outline-none focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 hover:bg-yellow-300 hover:bg-opacity-70 hover:text-purple-900">{t('Job.delete')}</button>
                                        </div>,
                                    '1':<div className="col-start-5 row-start-2">
                                            <h1 className="pb-3 pt-3 font-semibold text-purple-900">
                                                {t('Job.statusTitle')}
                                            </h1>
                                            <a className="text-sm focus:outline-none text-purple-900 bg-green-300 font-small rounded-lg text-sm px-5 py-2.5">
                                                {t('Job.accepted')}
                                            </a>
                                        </div>,
                                    '2':<div className="col-start-5 row-start-2">
                                            <h1 className="pb-3 pt-3 font-semibold text-purple-900">
                                                {t('Job.statusTitle')}
                                            </h1>
                                            <a className="text-sm focus:outline-none text-purple-900 bg-red-300 font-small rounded-lg text-sm px-5 py-2.5">
                                                {t('Job.rejected')}
                                            </a>
                                        </div>
                                }[status]
                            }
                        </div>
                        <div className="grid grid-cols-5">
                            <div className="col-span-2">
                                <h1 className="pb-3 pt-3 font-semibold text-purple-900"> {t('Job.abilities')}</h1>
                                <ul role="list"
                                    className="list-inside marker:text-purple-900 list-disc pl-5 space-y-3 text-gray-500">
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
                                <ul role="list"
                                    className="list-inside marker:text-purple-900 list-disc pl-5 space-y-3 text-gray-500">
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
                        {localStorage.getItem("hogar-role") == "EMPLOYER" &&
                        <div className="grid grid-rows-3 grid-cols-5">
                            <div className="col-start-2 row-start-3">
                                <button type="submit" onClick={delJob} className="text-sm focus:outline-none text-white bg-red-500 hover:bg-red-700 font-small rounded-lg text-sm px-5 py-2.5">
                                    <div className="grid grid-rows-1 grid-cols-3">
                                        <img src="/images/bin.png" alt="bin" className="mr-3 h-6 sm:h-5 col-start-1"/>
                                            <p className="col-span-2">{t('Job.deleteJob')}</p>
                                    </div>
                                </button>
                            </div>
                            <div className="col-start-4 row-start-3">
                                {opened ?
                                    <button type="submit" onClick={closeJob} className="text-sm focus:outline-none text-purple-700 bg-yellow-300 border-violet-700 hover:bg-yellow-200 font-small rounded-lg text-sm px-5 py-2.5">
                                        <div className="grid grid-rows-1 grid-cols-3">
                                            <img src="/images/editing_purple.png" alt="edit" className="mr-3 h-6 sm:h-5 col-start-1"/>
                                                <p className="col-span-2">{t('Job.closeJob')}</p>
                                        </div>
                                    </button>
                                    :
                                    <button type="submit" onClick={openJob} className="text-sm focus:outline-none text-white bg-green-500 hover:bg-green-700 font-small rounded-lg text-sm px-5 py-2.5">
                                        <div className="grid grid-rows-1 grid-cols-3">
                                            <img src="/images/editing.png" alt="edit" className="mr-3 h-6 sm:h-5 col-start-1"/>
                                                <p className="col-span-2">{t('Job.openJob')}</p>
                                        </div>
                                    </button>}
                            </div>
                        </div>}
                        {localStorage.getItem("hogar-role") == "EMPLOYEE" &&
                            <div className="flow-root">
                                <h1 className="pb-3 pt-3 font-semibold text-purple-900">
                                    {t('Job.reviewsFor')} {job.employerId.name}
                                </h1>
                                {myReview == null && (
                                    <form onSubmit={handleSubmit(onSubmit)}>
                                        <div className="">
                                            <label htmlFor="title"
                                                   className="block pb-3 pt-3 font-semibold text-gray-900">
                                                {t('ReviewForm.label_title')}
                                            </label>
                                            <textarea
                                                value={getValues("content")}
                                                {...register("content", {
                                                    required: true,
                                                    maxLength: 1000,
                                                    minLength: 10
                                                })}
                                                className="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-violet-500 focus:border-violet-500"/>
                                            {errors.content && (
                                                <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">
                                                    {t('ReviewForm.error')}
                                                </p>

                                            )}
                                            <div className="mt-5 col-start-2 col-span-4 row-span-3">
                                                <button type="submit"
                                                        className="text-lg w-full focus:outline-none text-violet-900 bg-purple-900 bg-opacity-30 hover:bg-purple-900 hover:bg-opacity-50 font-small rounded-lg text-sm px-5 py-2.5">
                                                    {t('ReviewForm.button')}
                                                </button>
                                            </div>
                                        </div>
                                    </form>)}
                                {reviews === 0 && !myReview &&
                                    (<div className="grid content-center justify-center h-5/6 mt-16">
                                            <div className="grid justify-items-center">
                                                <img src={'./images/sinEmpleadas.png'} alt="sinEmpleadas"
                                                     className="mr-3 h-6 sm:h-52"/>
                                                <p className="text-3xl font-semibold text-purple-700">
                                                    {t('Job.noReviews')}
                                                </p>
                                            </div>
                                        </div>
                                    )}
                                <ul role="list" className="divide-y divide-gray-300">
                                    {myReview && <MyReviewCard review={myReview}/>}
                                    {reviews && reviews.length > 0 && reviews.map((rev: any) => <ReviewCard
                                        review={rev}/>)}
                                    {/*                    <c:url value="/trabajo/${job.jobId}" var="getPath"/>*/}
                                    {/*                    <form method="get" action="${getPath}">*/}
                                    {/*                        <c:if test="${maxPage > 0 && page + 1 <= maxPage}">*/}
                                    {/*                            <div class="flex flex-row justify-center mt-4">*/}
                                    {/*                                <c:choose>*/}
                                    {/*                                    <c:when test="${page < 1}">*/}
                                    {/*                                        <button type="submit" class="font-semibold border shadow-md focus:outline-none text-violet-900 bg-gray-300 border-purple-900 rounded-lg px-2" disabled="true" onclick="previousPage(${page})"><</button>*/}
                                    {/*                                    </c:when>*/}
                                    {/*                                    <c:otherwise>*/}
                                    {/*                                        <button type="submit" class="font-semibold border shadow-md focus:outline-none text-violet-900 bg-purple-400 border-purple-900 hover:bg-yellow-300 hover:bg-opacity-50 rounded-lg px-2" onclick="previousPage(${page})"><</button>*/}
                                    {/*                                    </c:otherwise>*/}
                                    {/*                                </c:choose>*/}
                                    {/*                                <div class="bg--300 w-16 flex justify-center">*/}
                                    {/*                                    <h1 class="text-purple-900">${page + 1} of ${maxPage}</h1>*/}
                                    {/*                                </div>*/}
                                    {/*                                <c:choose>*/}
                                    {/*                                    <c:when test="${page + 1 == maxPage}">*/}
                                    {/*                                        <button type="submit" class="font-semibold border shadow-md focus:outline-none text-violet-900 bg-gray-300 border-purple-900 rounded-lg px-2" disabled="true" onclick="nextPage(${page})">></button>*/}
                                    {/*                                    </c:when>*/}
                                    {/*                                    <c:otherwise>*/}
                                    {/*                                        <button type="submit" id="prevPageButton" class=" font-semibold border shadow-md focus:outline-none text-violet-900 bg-purple-400 border-purple-900 hover:bg-yellow-300 hover:bg-opacity-50 rounded-lg px-2" onclick="nextPage(${page})">></button>*/}
                                    {/*                                    </c:otherwise>*/}
                                    {/*                                </c:choose>*/}
                                    {/*                        </c:if>*/}
                                    {/*    </div>*/}
                                    {/*    <input style="visibility: hidden" type="number" name="page" id="pageNumber"/>*/}
                                    {/*</form>*/}
                                </ul>
                                {/*)}*/}
                            </div>
                        }
                    </div>
                </div>}
        </div>
    )


}

export default Job;