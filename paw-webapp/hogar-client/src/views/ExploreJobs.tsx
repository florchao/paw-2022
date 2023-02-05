import {useCallback, useEffect, useRef, useState} from "react";
import FilterForm from "../components/FilterForm";
import {JobService} from "../service/JobService";
import JobCard from "../components/JobCard";
import {useTranslation} from "react-i18next";
import {useForm} from "react-hook-form";
import useFormPersist from "react-hook-form-persist";
import PaginationButtonsExplore from "../components/PaginationButtonsExplore";
import {MagnifyingGlass} from "react-loader-spinner";
import noJobs from "../assets/sinTrabajos.png";
import {parseLink} from "../utils/utils";

export const ExploreJobs = () => {

    const [jobs, setJobs]: any = useState()
    const [pages, setPages]: any = useState(0)

    const [nextPage, setNextPage]: any = useState("")
    const [prevPage, setPrevPage]: any = useState("")

    const [linkUrl, setLinkUrl] = useStateCallback("")


    function useStateCallback<T>(
        initialState: T
    ): [T, (state: T, cb?: (state: T) => void) => void] {
        const [state, setState] = useState(initialState);
        const cbRef = useRef<((state: T) => void) | undefined>(undefined); // init mutable ref container for callbacks

        const setStateCallback = useCallback((state: T, cb?: (state: T) => void) => {
            cbRef.current = cb; // store current, passed callback in ref
            setState(state);
        }, []); // keep object reference stable, exactly like `useState`

        useEffect(() => {
            // cb.current is `undefined` on initial render,
            // so we only invoke callback on state *updates*
            if (cbRef.current) {
                cbRef.current(state);
                cbRef.current = undefined; // reset callback after execution
            }
        }, [state]);

        return [state, setStateCallback];
    }

    const {t} = useTranslation();

    type FormData = {
        name: string;
        location: string[];
        experienceYears: number;
        availabilities: string[];
        abilities: string[];
        page: number;
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
            }
        }
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

    useEffect(() => {
        // if (linkUrl !== "") {
        //     onSubmit(getValues())
        // }
        onSubmit(getValues())
    }, [linkUrl]);

    const onSubmit = (data: any) => {
        setValue("isActive", true)
        JobService.getFilteredJobs(
            data.experienceYears,
            data.page,
            data.name,
            (data.location.toString() === "") ? undefined : data.location.toString().toString(),
            (data.abilities.toString() === "") ? undefined : data.abilities.toString().toString(),
            (data.availabilities.toString() === "") ? undefined : data.availabilities.toString().toString(),
            linkUrl
        ).then((rsp) => {
            rsp.headers.get("X-Total-Count") ? setPages(rsp.headers.get("X-Total-Count")) : setPages(0)
            if (rsp.status === 200) {
                let linkHeader = rsp.headers.get("Link")
                if (linkHeader !== null) {
                    parseLink(linkHeader, setNextPage, setPrevPage)
                }
                rsp.json().then((j: any) => {
                    setJobs(j)
                })
            } else
                setJobs([])
        })
    }

    function updatePage(page: number) {
        setValue("page", page)
        onSubmit(getValues())
    }

    const changePage = async (page: number, link?: string) => {
        setJobs(null)
        if (link !== undefined) {
            setLinkUrl(link)
        }
        setValue("page", page)
        // onSubmit(getValues())
    }

    useEffect(() => {
        if (getValues("isActive")) {
            onSubmit(getValues())
        } else {
            JobService.getJobs().then(async (rsp) => {
                rsp.headers.get("X-Total-Count") != null ? setPages(rsp.headers.get("X-Total-Count")) : setPages(0)
                if (rsp.status === 200) {
                    let linkHeader = rsp.headers.get("Link")
                    if (linkHeader !== null) {
                        parseLink(linkHeader, setNextPage, setPrevPage)
                    }
                    rsp.json().then((j: any) => {
                        setJobs(j)
                    })
                } else
                    setJobs([])
            })
        }
    }, [])

    return (
        <div className="grid content-start h-screen overflow-auto pl-5 pr-5 pb-5">
            <div className="my-10 w-full"></div>
            <div className="grid grid-cols-4">
                <FilterForm handleSubmit={handleSubmit} register={register} errors={errors} onSubmit={onSubmit}
                            reset={reset} setValue={setValue} setLinkUrl={setLinkUrl} linkUrl={linkUrl}/>
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
                                        glassColor='#c0efff'
                                        color='#e5de00'
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
                            <PaginationButtonsExplore
                                changePages={changePage}
                                pages={pages}
                                page={getValues("page")}
                                nextPage={nextPage}
                                prevPage={prevPage}
                                setLinkUrl={setLinkUrl}
                            />
                        }
                    </div>
                </div>
            </div>
        </div>
    )
}

export default ExploreJobs;
