import {useEffect, useState} from "react";
import FilterForm from "../components/FilterForm";
import {JobService} from "../service/JobService";
import JobCard from "../components/JobCard";
import {useTranslation} from "react-i18next";

export const ExploreJobs = () => {

    const [jobs, setJobs]: any = useState()

    const { t } = useTranslation();

    useEffect(() => {
        const algo = async () => {
            const val = await JobService.getJobs()
            setJobs(val)
        }
        algo()
    }, [])

    return (
        <div className="grid content-start h-screen overflow-auto pl-5 pr-5 pb-5">
            <div className="my-10 w-full"></div>
            <div className="grid grid-cols-4">
                <FilterForm setList={setJobs} type="jobs"/>
                <div className="col-span-3 col-start-2">
                    <h1 className="text-3xl font-bold text-violet-900 mt-2 mb-2 ml-8">{t('Explore.jobs')}</h1>
                    <div className="col-span-3 col-start-2">
                        <div className="flex flex-wrap content-start justify-center">
                            {jobs && jobs.map(
                                (job: Object) => (
                                    <div className="flex flex-wrap content-start justify-center">
                                        <JobCard job={job}/>
                                    </div>
                                ))}
                            {jobs == 0 && (
                                <div className="grid content-center justify-center h-5/6 mt-16">
                                    <div className="grid justify-items-center">
                                        <img src={ '../images/sinTrabajos.png'} alt="noJobs"
                                             className="mr-3 h-6 sm:h-52"/>
                                        <p className="text-3xl font-semibold text-purple-700">
                                                {t("Explore.noJobs")}
                                            </p>
                                    </div>
                                </div>
                            )}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default ExploreJobs;
