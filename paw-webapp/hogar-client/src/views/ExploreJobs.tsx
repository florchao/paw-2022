import {useEffect, useState} from "react";
import FilterForm from "../components/FilterForm";
import {JobService} from "../service/JobService";
import JobCard from "../components/JobCard";
import {useTranslation} from "react-i18next";
import {useForm} from "react-hook-form";
import useFormPersist from "react-hook-form-persist";
import PaginationButtonsExplore from "../components/PaginationButtonsExplore";
import {MagnifyingGlass} from "react-loader-spinner";
import noJobs from "../assets/sinTrabajos.png";

export const ExploreJobs = () => {

    const [jobs, setJobs]: any = useState()
    const [pages, setPages] :any = useState(0)

    const { t } = useTranslation();

    type FormData = {
        name: string;
        location: string[];
        experienceYears: number;
        availabilities: string[];
        abilities: string[];
        page:number;
        isActive: boolean;
    };

    const {register, handleSubmit, watch, formState: {errors}, getValues, setValue, reset} = useForm<FormData>({
        defaultValues: {
            name: "",
            location: [],
            experienceYears: 0,
            availabilities: [],
            abilities: [],
            page: 0,
            isActive: false,
        }}
    );

    watch("name");
    watch("location");
    watch("experienceYears");
    watch("availabilities");
    watch("abilities");
    watch("page");

    useFormPersist("exploreJobsForm", {
        watch,
        setValue,
        storage: window.localStorage,
        timeout: 1000 * 60 * 2,
    })

    const onSubmit = (data: any) => {
        setValue("isActive", true)
        JobService.getFilteredJobs(
            data.experienceYears,
            data.page,
            data.name,
            (data.location.toString() === "") ? undefined : data.location.toString().toString(),
            (data.abilities.toString() === "") ? undefined : data.abilities.toString().toString(),
            (data.availabilities.toString() === "") ? undefined : data.availabilities.toString().toString(),
        ).then((rsp) => {
            rsp?.headers.get("X-Total-Count") ? setPages(rsp.headers.get("X-Total-Count")) : setPages(0)
            if(rsp?.status === 200)
                rsp.json().then((j: any) => {
                    setJobs(j)
                })
            else
                setJobs([])
        })
    }

    const changePage = (page: number) => {
        setJobs(null)
        setValue("page", page)
        onSubmit(getValues())
    }

    useEffect(() => {
        if(getValues("isActive")){
            onSubmit(getValues())
        }
        else {
            JobService.getJobs().then((rsp) => {
                rsp?.headers.get("X-Total-Count") != null ? setPages(rsp.headers.get("X-Total-Count")) : setPages(0)
                if(rsp?.status === 200)
                    rsp.json().then((j: any) => {
                        setJobs(j)
                    })
                else
                    setJobs([])
            })
        }
    }, [])

    return (
        <div className="grid content-start h-screen overflow-auto pl-5 pr-5 pb-5">
            <div className="my-10 w-full"></div>
            <div className="grid grid-cols-4">
                <FilterForm handleSubmit={handleSubmit} register={register} errors={errors} onSubmit={onSubmit} reset={reset} setValue={setValue}/>
                <div className="col-span-3 col-start-2">
                    <h1 className="text-3xl font-bold text-violet-900 mt-2 mb-2 ml-8">{t('Explore.jobs')}</h1>
                    <div className="col-span-3 col-start-2">
                        <div className="flex flex-wrap content-start justify-center">
                            {!jobs &&
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
                            {jobs && jobs.map(
                                        (job: any) => (
                                            <div key={job.jobId}
                                                 className="flex flex-wrap content-start justify-center">
                                                <JobCard job={job}/>
                                            </div>
                                        ))}
                            {jobs && jobs.length === 0 && (
                                <div className="grid content-center justify-center h-5/6 mt-16">
                                    <div className="grid justify-items-center">
                                        <img src={noJobs} alt="noJobs"
                                             className="mr-3 h-6 sm:h-52"/>
                                        <p className="text-3xl font-semibold text-purple-700">
                                                {t("Explore.noJobs")}
                                            </p>
                                    </div>
                                </div>
                            )}
                        </div>
                        {jobs &&
                            <PaginationButtonsExplore changePages={changePage} pages={pages} page={getValues("page")}/>
                        }
                    </div>
                </div>
            </div>
        </div>
    )
}

export default ExploreJobs;
