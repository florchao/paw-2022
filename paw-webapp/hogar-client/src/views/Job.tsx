import {useEffect, useState} from "react";
import {useLocation, useNavigate, useParams} from "react-router-dom";
import {useTranslation} from "react-i18next";
import {JobService} from "../service/JobService";
import {ReviewService} from "../service/ReviewService";
import ReviewCard from "../components/ReviewCard";
import MyReviewCard from "../components/MyReviewCard";
import {ApplicantService} from "../service/ApplicantService";
import {useForm} from "react-hook-form";
import useFormPersist from "react-hook-form-persist";
import PaginationButtons from "../components/PaginationButtons";
import ErrorFeedback from "../components/ErrorFeedback";
import {MagnifyingGlass} from "react-loader-spinner";
import bin from "../assets/bin.png";
import editing from "../assets/editing.png";
import editingPurple from "../assets/editing_purple.png";
import noEmployees from "../assets/sinEmpleadas.png";
import {BACK_SLASH, JOB_URL, parseLink} from "../utils/utils";

export const Job = () => {

    const [job, setJob]: any = useState()
    const [reviews, setReviews]: any = useState()
    const [myReview, setMyReview]: any = useState()
    const [pages, setPages]: any = useState(0)
    const [status, setStatus] = useState<string>()
    const [opened, setOpened] = useState<boolean>()
    const [showError, setShowError] = useState<boolean>(false)
    const [current, setCurrent]: any = useState(0)

    const [nextPage, setNextPage]: any = useState("")
    const [prevPage, setPrevPage]: any = useState("")

    let employeeId: number;
    employeeId = localStorage.getItem('hogar-uid') ? parseInt(localStorage.getItem('hogar-uid') as string) : 0;

    const jobId = useParams();
    const location = useLocation();
    let {self}: any = useState()

    if (location.state) {
        self = location.state.self
    }
    const {t} = useTranslation();
    const nav = useNavigate();

    type FormData = {
        content: string;
    };

    const {register, handleSubmit, watch, formState: {errors}, getValues, setValue, reset} = useForm<FormData>();


    watch("content")

    useFormPersist("reviewEmployerForm", {
        watch,
        setValue,
        storage: localStorage.getItem('hogar-role') === "EMPLOYEE" ? window.localStorage : undefined,
        timeout: 1000 * 60 * 2,
    })

    const onSubmit = async (data: any, e: any) => {
        const post = await ReviewService.postEmployerReview(e, job.employerId.id, data.content)
        if (post?.status !== 201)
            setShowError(true)
        else {
            localStorage.removeItem("reviewEmployerForm")
            reset()
            ReviewService.getMyEmployerReview(post.headers.get("Location")!).then((rsp) => setMyReview(rsp))
        }
    }

    useEffect(() => {
        let url
        if (self === undefined && jobId.id !== undefined) {
            url = JOB_URL + BACK_SLASH + jobId.id
        } else {
            url = self
        }
        fetchData(url)
    }, [])

    const fetchData = async (url: string) => {
        await JobService.getJob(url).then((rsp) => {
            if(rsp?.status === 404)
                nav("/*", {replace:true})
            else if (rsp?.status === 200)
                rsp?.json().then((rsp: any) => {
                    if (rsp !== undefined) {
                        setJob(rsp)
                        setOpened(rsp.opened)
                    } else {
                        nav("/*")
                    }
                })
        })
    }

    useEffect(() => {
            if (job !== undefined && localStorage.getItem("hogar-role") === "EMPLOYEE") {
                const employeeID = localStorage.getItem("hogar-uid") != null ? localStorage.getItem("hogar-uid") : "0"
                ReviewService.getEmployerReviews(job.employerId.reviews, 0, employeeID ? parseInt(employeeID) : 0).then(
                    (rsp) => {
                        if (rsp !== undefined) {
                            rsp.headers.get("Total-Count") ? setPages(rsp.headers.get("Total-Count")) : setPages(0)
                            let linkHeader = rsp?.headers.get("link")
                            if (linkHeader !== null && linkHeader !== undefined) {
                                parseLink(linkHeader, setNextPage, setPrevPage)
                            }
                            if (rsp.status === 200)
                                rsp.json().then((reviews) => {
                                    setReviews(reviews)
                                })
                            else
                                setReviews([])
                        }
                    }
                )
                ReviewService.getMyEmployerReview(job.employerId.reviews).then(
                    (rsp) => {
                        setMyReview(rsp)
                        if (rsp !== undefined) {
                            localStorage.removeItem("reviewEmployerForm")
                        }
                    }
                )
            }
        }, [job]
    )

    useEffect(() => {
        if (job && localStorage.getItem("hogar-role") === "EMPLOYEE")
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

    async function createApplicant() {
        const newStatus = await ApplicantService.createApplicant(job.applicants, job.jobId)
        if (newStatus !== undefined) {
            if (newStatus.status === 201)
                setStatus("0")
        }
    }

    async function delJob() {
        const post = await JobService.deleteJob(job.self)
        if (post !== undefined) {
            if (post.status === 204) {
                nav('/jobs', {replace: true})
            }
        }
    }

    async function openJob() {
        await JobService.updateJobStatus(job.self, true)
        setOpened(true)
    }

    async function closeJob() {
        await JobService.updateJobStatus(job.self, false)
        setOpened(false)
    }

    const changePage = async (page: number, linkUrl?: string) => {
        setReviews(null)
        setCurrent(page)
        const employeeID = localStorage.getItem("hogar-uid") != null ? localStorage.getItem("hogar-uid") : "0"
        const get = await ReviewService.getEmployerReviews(job.employerId.reviews, page, employeeID ? parseInt(employeeID) : 0, linkUrl)
        if (get !== undefined) {
            get.headers.get("Total-Count") ? setPages(get.headers.get("Total-Count")) : setPages(0)
            let linkHeader = get?.headers.get("link")
            if (linkHeader !== null && linkHeader !== undefined) {
                parseLink(linkHeader, setNextPage, setPrevPage)
            }
            get.json().then((reviews) => {
                setReviews(reviews)
            })
        }
    }


    return (
        <div className="grid h-screen grid-cols-6 overflow-auto">
            {!job &&
                <div className={'flex items-center justify-center h-screen w-screen'}>
                    <MagnifyingGlass
                        visible={true}
                        height="160"
                        width="160"
                        ariaLabel="MagnifyingGlass-loading"
                        wrapperStyle={{}}
                        wrapperClass="MagnifyingGlass-wrapper"
                        glassColor='#c0efff'
                        color='#e5de00'
                    />
                </div>
            }
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

                            {status &&
                                {
                                    '-1': <div className="col-start-5 row-start-2">
                                        <button type="submit" onClick={createApplicant}
                                                className="ml-2 h-fit w-fit text-xs text-white bg-violet-400 border border-purple-900 focus:outline-none focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 hover:bg-yellow-300 hover:bg-opacity-70 hover:text-purple-900">{t('Job.apply')}</button>
                                    </div>,
                                    '0': <div className="col-start-5 row-start-2">
                                        <h1 className="pb-3 pt-3 font-semibold text-purple-900">
                                            {t('Job.statusTitle')}
                                        </h1>
                                        <a className="text-sm focus:outline-none text-purple-900 bg-yellow-300 font-small rounded-lg text-sm px-5 py-2.5">
                                            {t('Job.pending')}
                                        </a>
                                        <br/><br/>
                                        <button type="submit" onClick={delApplication}
                                                className="ml-2 h-fit w-fit text-xs text-white bg-violet-400 border border-purple-900 focus:outline-none focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 hover:bg-yellow-300 hover:bg-opacity-70 hover:text-purple-900">{t('Job.delete')}</button>
                                    </div>,
                                    '1': <div className="col-start-5 row-start-2">
                                        <h1 className="pb-3 pt-3 font-semibold text-purple-900">
                                            {t('Job.statusTitle')}
                                        </h1>
                                        <a className="text-sm focus:outline-none text-purple-900 bg-green-300 font-small rounded-lg text-sm px-5 py-2.5">
                                            {t('Job.accepted')}
                                        </a>
                                    </div>,
                                    '2': <div className="col-start-5 row-start-2">
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
                                    {job.abilities.map((ability: String, i: number) => (
                                        <li key={i}>
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
                                    {job.availability.map((availability: String, i: number) => (
                                        <li key={i}>
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
                        {localStorage.getItem("hogar-role") === "EMPLOYER" &&
                            <div className="grid grid-rows-3 grid-cols-5">
                                <div className="col-start-2 row-start-3">
                                    <button type="submit" onClick={delJob}
                                            className="text-sm focus:outline-none text-white bg-red-500 hover:bg-red-700 font-small rounded-lg text-sm px-5 py-2.5">
                                        <div className="grid grid-rows-1 grid-cols-3">
                                            <img src={bin} alt="bin" className="mr-3 h-6 sm:h-5 col-start-1"/>
                                            <p className="col-span-2">{t('Job.deleteJob')}</p>
                                        </div>
                                    </button>
                                </div>
                                <div className="col-start-4 row-start-3">
                                    {opened ?
                                        <button type="submit" onClick={closeJob}
                                                className="text-sm focus:outline-none text-purple-700 bg-yellow-300 border-violet-700 hover:bg-yellow-200 font-small rounded-lg text-sm px-5 py-2.5">
                                            <div className="grid grid-rows-1 grid-cols-3">
                                                <img src={editingPurple} alt="edit"
                                                     className="mr-3 h-6 sm:h-5 col-start-1"/>
                                                <p className="col-span-2">{t('Job.closeJob')}</p>
                                            </div>
                                        </button>
                                        :
                                        <button type="submit" onClick={openJob}
                                                className="text-sm focus:outline-none text-white bg-green-500 hover:bg-green-700 font-small rounded-lg text-sm px-5 py-2.5">
                                            <div className="grid grid-rows-1 grid-cols-3">
                                                <img src={editing} alt="edit" className="mr-3 h-6 sm:h-5 col-start-1"/>
                                                <p className="col-span-2">{t('Job.openJob')}</p>
                                            </div>
                                        </button>}
                                </div>
                            </div>}
                        {localStorage.getItem("hogar-role") === "EMPLOYEE" &&
                            <h1 className="pb-3 pt-3 font-semibold text-purple-900">
                                {t('Job.reviewsFor')} {job.employerId.name}
                            </h1>
                        }
                        {localStorage.getItem("hogar-role") === "EMPLOYEE" && (
                            !reviews ?
                                <div className={'flex items-center justify-center h-1/4'}>
                                    <MagnifyingGlass
                                        visible={true}
                                        height="160"
                                        width="160"
                                        ariaLabel="MagnifyingGlass-loading"
                                        wrapperStyle={{}}
                                        wrapperClass="MagnifyingGlass-wrapper"
                                        glassColor='#c0efff'
                                        color='#e5de00'
                                    /></div>
                                :
                                <div className="flow-root">
                                    {!myReview && (
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
                                    {reviews && reviews.length === 0 && !myReview &&
                                        (<div className="grid content-center justify-center h-5/6 mt-16">
                                                <div className="grid justify-items-center">
                                                    <img src={noEmployees} alt="sinEmpleadas"
                                                         className="mr-3 h-6 sm:h-52"/>
                                                    <p className="text-3xl font-semibold text-purple-700">
                                                        {t('Job.noReviews')}
                                                    </p>
                                                </div>
                                            </div>
                                        )}
                                    <ul role="list" className="divide-y divide-gray-300">
                                        {myReview && <MyReviewCard review={myReview}/>}
                                        {reviews && reviews.length > 0 &&
                                            <div>
                                                {reviews.map((rev: any) => <ReviewCard key={rev.employee.id}
                                                                                       review={rev}/>)}
                                                <PaginationButtons changePages={changePage} pages={pages}
                                                                   current={current} nextPage={nextPage}
                                                                   prevPage={prevPage}/>
                                            </div>
                                        }
                                    </ul>
                                </div>
                        )
                        }
                    </div>
                </div>}
            {showError &&
                <ErrorFeedback message={t('Feedback.errorReview')}/>
            }
        </div>
    )


}

export default Job;