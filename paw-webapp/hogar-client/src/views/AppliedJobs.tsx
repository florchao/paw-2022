import {useTranslation} from "react-i18next";
import {useEffect, useState} from "react";
import {ApplicantService} from "../service/ApplicantService";
import JobCard from "../components/JobCard";
import PaginationButtons from "../components/PaginationButtons";

export const AppliedJobs = () => {

    const [appliedJobs, setAppliedJobs]: any = useState()
    const [pages, setPages]: any = useState(0)

    const { t } = useTranslation();

    let id  = localStorage.getItem("hogar-uid") as unknown as number

    useEffect(() => {
        ApplicantService.getAppliedJobs(id, 0).then( (rsp) => {
                rsp.headers.get("X-Total-Count") ? setPages(rsp.headers.get("X-Total-Count")) : setPages(0)
                rsp.json().then((jobs) => {
                    setAppliedJobs(jobs)
                })
            }
        );
    }, [])

    const changePage = async (page: number) => {
        const get = await ApplicantService.getAppliedJobs(id, page)
        get.headers.get("X-Total-Count") ? setPages(get.headers.get("X-Total-Count")) : setPages(0)
        get.json().then((jobs) => {
            setAppliedJobs(jobs)
        })
    }

    return (
        <div className="grid content-start h-screen overflow-auto pl-5 pr-5">
            <div className="my-9 w-full"></div>
            <p className="text-3xl font-semibold text-violet-900 mb-4 mt-4 text-center">
                {t('AppliedJobs.title')}
            </p>
            {appliedJobs && appliedJobs.length == 0 && <div
                className="grid content-center justify-center h-5/6 mt-16">
                <div className="grid justify-items-center">
                    <img src='../assets/sinTrabajos.png' alt="sinTrabajos"
                         className="mr-3 h-6 sm:h-52"/>
                    <p className="text-3xl font-semibold text-purple-700">
                        {t('AppliedJobs.noJobs')}
                    </p>
                </div>
            </div>}
            <div className="flex flex-wrap content-start justify-center">
            {appliedJobs && appliedJobs.length > 0 &&
                appliedJobs.map((a: any) => (
                    <div key={a.job.jobId} className="flex flex-wrap content-start justify-center">
                        <JobCard job={a.job}/>
                    </div>
                ))}
            </div>
            <PaginationButtons changePages={changePage} pages={pages}/>
        </div>
    )
}

export default AppliedJobs