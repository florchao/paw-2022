import {useLocation, useNavigate, useParams} from "react-router-dom";
import {useTranslation} from "react-i18next";
import {useEffect, useState} from "react";
import ApplicantCard from "../components/ApplicantCard";
import {JobService} from "../service/JobService";
import PaginationButtons from "../components/PaginationButtons";
import noEmployees from "../assets/sinEmpleadas.png";
import {MagnifyingGlass} from "react-loader-spinner";
import {APPLICANT_URL, BACK_SLASH, JOB_URL} from "../utils/utils";

export const Applicants = () => {

    const [applicantList, setApplicantList]: any = useState()

    const jobId = useParams();
    const location = useLocation();
    const [title, setTitle]: any = useState()
    let {applicants}: any = useState()
    const {t} = useTranslation();
    const [totalPages, setTotalPages]: any = useState(0)
    const [current, setCurrent]: any = useState(0)
    const nav = useNavigate();

    async function setApplicantsByPage(page: number) {
        setCurrent(page)
        setApplicantList(null)
        let url
        if (applicants === undefined) {
            url = APPLICANT_URL + BACK_SLASH + jobId.id
        } else {
            url = applicants
        }
        const result = await JobService.getApplicants(url, page)
        if (result?.status === 200) {
            let body = await result.json()
            let pageCountHeader = result.headers.get('X-Total-Count')
            setApplicantList(body)
            setTotalPages(pageCountHeader)
        }
    }

    const fetchData = async (url: string) => {
        await JobService.getJob(url).then((rsp) => {
            if (rsp != undefined) {
                setTitle(rsp.title)
            } else {
                nav("/*")
            }
        })
    }

    useEffect(() => {
        if (location.state) {
            applicants = location.state.applicants
            setTitle(location.state.title)
        }
        setApplicantsByPage(0)
        if (title === undefined) {
            const url = JOB_URL + BACK_SLASH + jobId.id
            fetchData(url)
        }
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
                        {!applicantList &&
                            <div className={'flex items-center justify-center h-3/4'}>
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
                        {applicantList && applicantList.length == 0 ?
                            <div className="grid content-center justify-center h-5/6 mt-16">
                                <div className="grid justify-items-center">
                                    <img src={noEmployees} alt="sinEmpleadas"
                                         className="mr-3 h-6 sm:h-52"/>
                                    <p className="text-3xl font-semibold text-purple-700"> {t('Applicants.noApplicants')}</p>
                                </div>
                            </div>
                            :
                            <ul role="list" className="divide-y divide-gray-300">
                                {applicantList && applicantList.map((applicant: any) => (
                                    <ApplicantCard key={applicant.employee.id} applicant={applicant}/>))}
                            </ul>
                        }
                        <PaginationButtons changePages={setApplicantsByPage} pages={totalPages}
                                           current={current}></PaginationButtons>

                    </div>
                </div>
            </div>
        </div>
    )
}

export default Applicants;