import {useEffect, useState} from "react";
import {useTranslation} from "react-i18next";
import {Link} from "react-router-dom";
import {JobService} from "../service/JobService";
import JobCard from "../components/JobCard";
import {BACK_SLASH, EMPLOYER_URL, JOBS, parseLink} from "../utils/utils";
import PaginationButtons from "../components/PaginationButtons";
import noJobs from "../assets/sinTrabajos.png";
import {MagnifyingGlass} from "react-loader-spinner";
import {union} from "zod";


export const CreatedJobs = () => {


    const [createdJobs, setCreatedJobs]: any = useState()
    const [pages, setPages]: any = useState(0)
    const [current, setCurrent]: any = useState(0)
    const [nextPage, setNextPage]: any = useState("")
    const [prevPage, setPrevPage]: any = useState("")

    const { t } = useTranslation()

    let id  = localStorage.getItem("hogar-uid") as unknown as number

    useEffect(() => {
        changePage(0)
    }, [])

    const changePage = async (page: number, linkUrl?: string) => {
        setCreatedJobs(null)
        setCurrent(page)
        let url = EMPLOYER_URL + BACK_SLASH + id + JOBS
        JobService.getCreatedJobs(url, false, page, linkUrl).then( async (j) => {
            j.headers.get("X-Total-Count") ? setPages(j.headers.get("X-Total-Count")) : setPages(0)
            let linkHeader = j.headers.get("link")
            if (linkHeader !== null) {
                parseLink(linkHeader, setNextPage, setPrevPage)
            }
            j.status == 204 ? setCreatedJobs([]) : setCreatedJobs(await j.json())
        });
    }

    return (
        <div className="grid content-start h-screen overflow-auto pl-5 pr-5">
            <div className="py-9 w-full"></div>
            <p className="text-3xl font-semibold text-violet-900 mb-4 mt-4 text-center">
                {t('CreatedJobs.publications')}
            </p>
            {!createdJobs &&
                <div className={'flex items-center justify-center h-3/4'}>
                    <MagnifyingGlass
                        visible={true}
                        height="160"
                        width="160"
                        ariaLabel="MagnifyingGlass-loading"
                        wrapperStyle={{}}
                        wrapperClass="MagnifyingGlass-wrapper"
                        glassColor = '#c0efff'
                        color = '#e5de00'
                    />
                </div>
            }
            {createdJobs && createdJobs.length === 0 &&
                <div className="grid content-center justify-center h-5/6 mt-16">
                    <div className="grid justify-items-center">
                        <img src={noJobs} alt="sinTrabajos"
                             className="mr-3 h-6 sm:h-52"/>
                            <p className="text-3xl font-semibold text-purple-700">
                                {t('CreatedJobs.noJobs')}
                            </p>
                            <br/>
                            <Link to="/create/job">
                                <button type="button"
                                        className="text-lg focus:outline-none text-purple-700 bg-yellow-300 hover:bg-yellow-200 font-small rounded-lg text-lg px-5 py-2.5">
                                    {t('CreatedJobs.addFirst')}
                                </button>
                            </Link>
                    </div>
                </div>
            }
            {createdJobs && createdJobs.length > 0 &&
                <div>
                <div className="flex flex-wrap content-start justify-center">
                    {createdJobs.map((j: any) => (
                        <JobCard key={j.jobId} job={j}/>
                    ))}
                    <div className="w-80 mr-5 mb-5 grid content-center justify-center">
                        <Link to="/create/job">
                            <button type="button"
                                    className="text-lg focus:outline-none text-purple-700 bg-yellow-300 hover:bg-yellow-200 font-small rounded-lg text-lg px-5 py-2.5">
                                {t('CreatedJobs.addAnother')}
                            </button>
                        </Link>
                    </div>
                </div>
                    <PaginationButtons changePages={changePage} pages={pages} current={current} nextPage={nextPage} prevPage={prevPage} />
                </div>
            }
        </div>
    )
}

export default CreatedJobs;