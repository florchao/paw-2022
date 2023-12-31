import EmployeeCard from "../components/EmployeeCard";
import FilterForm from "../components/FilterForm";
import {useCallback, useEffect, useRef, useState} from "react";
import {EmployeeService} from "../service/EmployeeService";
import {useTranslation} from "react-i18next";
import {useForm} from "react-hook-form";
import useFormPersist from "react-hook-form-persist";
import PaginationButtonsExplore from "../components/PaginationButtonsExplore";
import noEmployees from "../assets/sinEmpleadas.png";
import {MagnifyingGlass} from "react-loader-spinner";
import {parseLink, useDidMountEffect} from "../utils/utils";


export const Explore = () => {

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

    const [employees, setEmployees]: any = useState()
    const [pages, setPages]: any = useState(0)

    const [nextPage, setNextPage]: any = useState("")
    const [prevPage, setPrevPage]: any = useState("")

    const [linkUrl, setLinkUrl] = useStateCallback("")

    const {t} = useTranslation();

    type FormData = {
        name: string;
        location: string[];
        experienceYears: number;
        availabilities: string[];
        abilities: string[];
        orderBy: string;
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
    watch("orderBy");
    watch("page");

    useFormPersist("exploreForm", {
        watch,
        setValue,
        storage: window.localStorage,
        timeout: 1000 * 60 * 2,
    })

    useDidMountEffect(() => {
        onSubmit(getValues())
    }, [linkUrl]);

    const onSubmit = (data: any) => {
        setValue("isActive", true)
        EmployeeService.getFilteredEmployees(
            data.experienceYears,
            data.page,
            data.name,
            (data.location.toString() === "") ? undefined : data.location.toString().toString(),
            (data.abilities.toString() === "") ? undefined : data.abilities.toString().toString(),
            (data.availabilities.toString() === "") ? undefined : data.availabilities.toString().toString(),
            data.orderBy,
            linkUrl
        ).then((rsp?) => {
            let linkHeader = rsp?.headers.get("Link")
            if (linkHeader !== null && linkHeader !== undefined) {
                parseLink(linkHeader, setNextPage, setPrevPage)
            }
            rsp?.headers.get("Total-Count") ? setPages(rsp.headers.get("Total-Count")) : setPages(0)
            if (rsp?.status === 200)
                rsp.json().then((employees: any) => {
                    setEmployees(employees)
                })
            else
                setEmployees([])
        })
    }

    const changePage = async (page: number, link?: string) => {
        setEmployees(null)
        if (link !== undefined) {
            setLinkUrl(link)
        }
        setValue("page", page)
        // onSubmit(getValues())
    }

    const changeOrder = (order: string) => {
        setValue("orderBy", order)
        setValue("page", 0)
        onSubmit(getValues())
    }

    useEffect(() => {
        if (getValues("isActive")) {
            onSubmit(getValues())
        } else {
            EmployeeService.getEmployees().then((rsp) => {
                rsp?.headers.get("Total-Count") != null ? setPages(rsp.headers.get("Total-Count")) : setPages(0)
                if (rsp?.status === 200) {
                    let linkHeader = rsp.headers.get("Link")
                    if (linkHeader !== null) {
                        parseLink(linkHeader, setNextPage, setPrevPage)
                    }
                    rsp.json().then((employees: any) => {
                        setEmployees(employees)
                    })
                } else {
                    setEmployees([])
                }
            })
        }

    }, [])

    return (
        <div className="grid grid-cols-12 content-start h-screen overflow-auto pl-5 pr-5 pt-20 pb-5">
            <div className="col-span-3 mr-4 flex flex-col items-center">
                <FilterForm handleSubmit={handleSubmit} register={register} errors={errors} onSubmit={onSubmit}
                            reset={reset} setValue={setValue} setLinkUrl={setLinkUrl} linkUrl={linkUrl}/>
            </div>
            <div className="col-span-9 mr-5">
                <h1 className={'text-3xl font-bold text-violet-900 mt-2 mb-2 ml-8'}>{t('Explore.employees')}</h1>
                <div className={'flex flex-row-reverse'}>
                    <form className={'flex flex-row'} onSubmit={handleSubmit(onSubmit)}>
                        <h1 className={'font-semibold mr-3'}>{t('Explore.orderBy')}</h1>
                        <label htmlFor={"pop"}
                               className={getValues("orderBy") === "rating" ? 'text-yellow-300 underline' : 'mr-3 hover:text-yellow-300 hover:underline hover:cursor-pointer'}>{t('Explore.popularity')}</label>
                        <input type='radio'
                               value="rating"
                               id={'pop'}
                               onClick={() => {
                                   if (getValues("orderBy") !== "rating") {
                                       changeOrder("rating")
                                   }
                               }}
                               style={{visibility: "hidden"}}
                        />
                        <label htmlFor={"exp"}
                               className={getValues("orderBy") === "experienceYears" ? 'text-yellow-300 underline' : 'mr-3 hover:text-yellow-300 hover:underline hover:cursor-pointer'}>{t('Explore.experience')}</label>
                        <input type='radio'
                               {...register("orderBy")}
                               value="experienceYears"
                               id={'exp'}
                               onClick={() => {
                                   if (getValues("orderBy") !== "experienceYears") {
                                       changeOrder("experienceYears")
                                   }
                               }}
                               style={{visibility: "hidden"}}
                        />
                    </form>
                </div>
                {!employees &&
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
                {employees &&
                    <div>
                        {employees.map((employee: any) => (<EmployeeCard key={employee.id} employee={employee}/>))}
                        <PaginationButtonsExplore
                            changePages={changePage}
                            pages={pages}
                            page={getValues("page")}
                            nextPage={nextPage}
                            prevPage={prevPage}
                            setLinkUrl={setLinkUrl}
                        />
                    </div>
                }
                {employees && employees.length === 0 && (
                    <div className="grid content-center justify-center h-5/6 mt-16">
                        <div className="grid justify-items-center">
                            <img src={noEmployees} alt="noEmployees"
                                 className="mr-3 h-6 sm:h-52"/>
                            <p className="text-3xl font-semibold text-purple-700">
                                {t("Explore.noEmployees")}
                            </p>
                        </div>
                    </div>
                )}
            </div>
        </div>
    )
}
export default Explore;