import {useEffect, useState} from "react";
import FilterForm from "../components/FilterForm";
import {JobService} from "../service/JobService";
import JobCard from "../components/JobCard";

export const ExploreJobs = () => {

    const [jobs, setJobs]: any = useState()

    useEffect(() => {
        const algo = async () => {
            const val = await JobService.getJobs()
            console.log("LPM")
            setJobs(val)
        }
        algo()
    }, [])

    return (
        <div className="grid grid-cols-8 content-start h-screen overflow-auto pl-5 pr-5">
            <div className="col-span-2 mr-4 flex flex-col items-center">
                <FilterForm setList={setJobs} type="jobs"/>
            </div>
            <div className="col-span-5 mr-5">
                <h1 className={'text-3xl font-bold text-violet-900 mt-2 mb-2 ml-8'}>Employees Registered_</h1>
                <div className={'flex flex-row-reverse'}>
                    <div className={'flex flex-row'}>
                        <h1 className={'font-semibold mr-3'}>Order by:_</h1>
                        <h1 className={'mr-3 hover:text-yellow-300 hover:underline hover:cursor-pointer'}>Popularity_</h1>
                        <h1 className={'mr-3 hover:text-yellow-300 hover:underline hover:cursor-pointer'}>Experience_</h1>
                    </div>
                </div>
                {jobs && jobs.map((job: Object) => (<JobCard job={job}/>))}
            </div>
        </div>
    )
}

export default ExploreJobs;
