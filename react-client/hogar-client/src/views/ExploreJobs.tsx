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
        <div className="grid content-start h-screen overflow-auto pl-5 pr-5">
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
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default ExploreJobs;
