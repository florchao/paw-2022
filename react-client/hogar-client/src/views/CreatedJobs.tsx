import {useEffect, useState} from "react";
import {useTranslation} from "react-i18next";
import {Link, useLocation} from "react-router-dom";
import {JobService} from "../service/JobService";
import JobCard from "../components/JobCard";

export const CreatedJobs = () => {
    const [createdJobs, setCreatedJobs]: any = useState()

    const { t } = useTranslation()

    // const { id } = useLocation().state

    const id = 6

    useEffect(() => {
        JobService.getCreatedJobs(id).then( (j) => {
            setCreatedJobs(j)
        });
    }, [])

    return (
        <div className="grid content-start h-screen overflow-auto pl-5 pr-5">
            <div className="py-9 w-full"></div>
            <p className="text-3xl font-semibold text-violet-900 mb-4 mt-4 text-center">
                {t('CreatedJobs.publications')}
            </p>
            {createdJobs && createdJobs.length === 0 &&
                <div className="grid content-center justify-center h-5/6 mt-16">
                    <div className="grid justify-items-center">
                        <img src="/images/sinTrabajos.png" alt="sinTrabajos"
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
                <div className="flex flex-wrap content-start justify-center">
                    {createdJobs.map((j: any) => (
                        <JobCard job={j}/>
                    ))}
                    <div className="grid content-center justify-center">
                        <Link to="/create/job">
                            <button type="button"
                                    className="text-lg focus:outline-none text-purple-700 bg-yellow-300 hover:bg-yellow-200 font-small rounded-lg text-lg px-5 py-2.5">
                                {t('CreatedJobs.addAnother')}
                            </button>
                        </Link>
                    </div>
                </div>
            }
        </div>
    )
}

export default CreatedJobs;