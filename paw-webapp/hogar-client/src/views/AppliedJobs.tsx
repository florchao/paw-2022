import {useTranslation} from "react-i18next";
import {useEffect, useState} from "react";
import {ApplicantService} from "../service/ApplicantService";
import JobCard from "../components/JobCard";
import PaginationButtons from "../components/PaginationButtons";
import {MagnifyingGlass} from "react-loader-spinner";
import noJobs from "../assets/sinTrabajos.png";
import {parseLink} from "../utils/utils";

export const AppliedJobs = () => {

    const [appliedJobs, setAppliedJobs]: any = useState()
    const [pages, setPages]: any = useState(0)
    const [current, setCurrent]: any = useState(0)
    const [nextPage, setNextPage]: any = useState("")
    const [prevPage, setPrevPage]: any = useState("")

    const {t} = useTranslation();

    let id = localStorage.getItem("hogar-uid") as unknown as number

    useEffect(() => {
        ApplicantService.getAppliedJobs(id, 0).then((rsp) => {
                rsp?.headers.get("Total-Count") ? setPages(rsp.headers.get("Total-Count")) : setPages(0)
                let linkHeader = rsp?.headers.get("link")
                if (linkHeader !== null && linkHeader !== undefined) {
                    parseLink(linkHeader, setNextPage, setPrevPage)
                }
                if (rsp?.status === 200)
                    rsp.json().then((jobs) => {
                        setAppliedJobs(jobs)
                    })
                else
                    setAppliedJobs([])
            }
        );
    }, [])

    const changePage = async (page: number, linkUrl?: string) => {
        setAppliedJobs(null)
        setCurrent(page)

        const get = await ApplicantService.getAppliedJobs(id, page, linkUrl)
        get?.headers.get("Total-Count") ? setPages(get.headers.get("Total-Count")) : setPages(0)
        let linkHeader = get?.headers.get("link")
        if (linkHeader !== null && linkHeader !== undefined) {
            parseLink(linkHeader, setNextPage, setPrevPage)
        }
        get?.json().then((jobs) => {
            setAppliedJobs(jobs)
        })
    }

    return (
        <div className="grid content-start h-screen overflow-auto pl-5 pr-5">
            <div className="my-9 w-full"></div>
            <p className="text-3xl font-semibold text-violet-900 mb-4 mt-4 text-center">
                {t('AppliedJobs.title')}
            </p>
            {!appliedJobs &&
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
            {appliedJobs && appliedJobs.length === 0 && <div
                className="grid content-center justify-center h-5/6 mt-16">
                <div className="grid justify-items-center">
                    <img src={noJobs} alt="sinTrabajos"
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
            <PaginationButtons changePages={changePage} pages={pages} current={current} nextPage={nextPage}
                               prevPage={prevPage}/>
        </div>
    )
}

export default AppliedJobs