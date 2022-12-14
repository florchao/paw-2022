import {useEffect, useState} from "react";
import {JobService} from "../service/JobService";
import {useTranslation} from "react-i18next";
import {useNavigate} from "react-router-dom";
import {useForm} from "react-hook-form";
import useFormPersist from "react-hook-form-persist";
import {IdsService} from "../service/IdsService";

export const CreateJob = () => {

    type FormData = {
        title: string;
        description: string;
        location: string;
        experienceYears: number;
        availability: string;
        abilities: string[];
    };

    const {register, handleSubmit, watch, formState: {errors}, getValues, setValue} = useForm<FormData>();

    watch("title")
    watch("description")
    watch("location")
    watch("experienceYears")
    watch("availability")
    watch("abilities")


    useFormPersist("jobForm", {
        watch,
        setValue,
        storage: window.localStorage,
        timeout: 1000 * 60 * 2,
    })

    const [ids, setIds] = useState<any>();

    const {t} = useTranslation();

    const nav = useNavigate();

    useEffect(() => {
        IdsService.getIds().then((i) => {
            setIds(i)
        });
    }, [])

    const setColor = (name: string, id: number) => {
        let label = document.getElementById(name + "-label")
        if (getValues("abilities").toString() === "false" || !getValues("abilities").includes(id.toString())) {
            if (label != null)
                label.style.backgroundColor = "#c4b5fd";
            window.sessionStorage.setItem(name, "#c4b5fd");
        } else {
            if (label != null)
                label.style.backgroundColor = "#ffffff";
        }
    }

    const onSubmit = async (data:any, e: any) => {
        const post = await JobService.postJob(e, data.title, data.location, data.experienceYears, data.availability, data.abilities, data.description)
        localStorage.removeItem("jobForm")
        //TODO: ver que pasar envez de id
        nav('/job', {replace: true, state: {id: post}})
    }

    return (
        <div className="h-screen overflow-auto">
            <div className="grid justify-items-center mt-24" style={{marginBottom: -40}}>
                <p className="text-xl font-semibold text-white">
                    {t('CreateJob.title')}
                </p>
            </div>
            <form onSubmit={handleSubmit(onSubmit)}>
                <div className="grid grid-cols-6 mb-5">
                    <div className="grid grid-row-4 col-span-4 col-start-2 mt-20 ">
                        <div className="bg-gray-200 rounded-3xl p-5 shadow-2xl">
                            <div className="grid grid-cols-6 gap-6">
                                <div className="ml-3 col-span-6 w-4/5 justify-self-center">
                                    <label htmlFor="title" className="block mb-2 text-sm font-medium text-gray-900 ">
                                        {t('CreateJob.label_title')}
                                    </label>
                                    <input type="text"
                                           {...register("title", {required: true, maxLength: 25})}
                                           className="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-blue-500 focus:border-violet-500"/>
                                    {errors.title &&
                                        <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('CreateJob.titleError')}</p>
                                    }
                                </div>
                                <div className="ml-3 col-span-3 w-4/5 justify-self-center">
                                    <label htmlFor="location"
                                           className="block mb-2 text-sm font-medium text-gray-900 ">
                                        {t('CreateJob.location')}
                                    </label>
                                    {ids &&
                                        <select
                                            {...register("location", {required: true})}
                                            className="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-violet-500 focus:border-violet-500">
                                            <option value={ids.locations[0]}
                                                    label={t('Locations.west')}/>
                                            <option value={ids.locations[1]}
                                                    label={t('Locations.north')}/>
                                            <option value={ids.locations[2]}
                                                    label={t('Locations.south')}/>
                                            <option value={ids.locations[3]}
                                                    label={t('Locations.caba')}/>
                                        </select>
                                    }
                                    {errors.location &&
                                        <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('CreateJob.locationError')}</p>
                                    }
                                </div>
                                <div className="ml-3 col-span-3 w-4/5 justify-self-center">
                                    <label htmlFor="experienceYears"
                                           className="block mb-2 text-sm font-medium text-gray-900">
                                        {t('CreateJob.experienceYears')}
                                    </label>
                                    <input type="number" id="expYears"
                                           {...register("experienceYears", {required: true, max: 100, min: 0})}
                                           className="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-violet-500 focus:border-violet-500"/>
                                    {errors.experienceYears &&
                                        <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('CreateJob.expYearsError')}</p>
                                    }
                                </div>
                                {ids &&
                                    <div className="ml-3 col-span-3 w-4/5 justify-self-center">
                                        <label
                                            className="block mb-2 text-sm font-medium text-gray-900 ">
                                            {t('CreateJob.availability')}
                                        </label>
                                        <select
                                            {...register("availability", {required: true})}
                                            className="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-violet-500 focus:border-violet-500">
                                            <option value={ids.availabilities[0]}
                                                    label={t('Availabilities.half')}/>
                                            <option value={ids.availabilities[1]}
                                                    label={t('Availabilities.complete')}/>
                                            <option value={ids.availabilities[2]}
                                                    label={t('Availabilities.bed')}/>
                                        </select>
                                    </div>
                                }
                            </div>
                            <div>
                                <h1 className="pb-3 pt-3 text-sm">
                                    {t('CreateJob.abilities')}
                                </h1>
                            </div>
                            {ids &&
                                <div className="flex flex-wrap ml-8">
                                    <div className="mb-8">
                                        <label htmlFor="cocinar-cb" id="cocinar-label"
                                               onClick={() => {
                                                   setColor('cocinar', ids.abilities[0])
                                               }}
                                               className={getValues("abilities") && getValues("abilities").toString().includes(ids.abilities[0].toString()) ?
                                                   "mt-1 h-fit w-fit text-xs text-gray-900 bg-violet-300 border border-gray-300 focus:outline-none hover:bg-white focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer" :
                                                   "mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer"}>
                                            {t('Abilities.cook')}
                                        </label>
                                        <input type="checkbox"
                                               {...register("abilities", {required: true})}
                                            // onChange={(e) => console.log(getValues("abilities"))}
                                               id="cocinar-cb"
                                               value={ids.abilities[0]}
                                               style={{visibility: "hidden"}}
                                        />
                                    </div>
                                    <div>
                                        <label htmlFor="planchar-cb" id="planchar-label"
                                               onClick={() =>
                                                   setColor('planchar', ids.abilities[1])
                                               }
                                               className={getValues("abilities") && getValues("abilities").toString().includes(ids.abilities[1].toString()) ?
                                                   "mt-1 h-fit w-fit text-xs text-gray-900 bg-violet-300 border border-gray-300 focus:outline-none hover:bg-white focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer" :
                                                   "mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer"}>
                                            {t('Abilities.iron')}
                                        </label>
                                        <input type="checkbox"
                                               id="planchar-cb"
                                               {...register("abilities", {required: true})}
                                               value={ids.abilities[1]}
                                               style={{visibility: "hidden"}}
                                        />
                                    </div>
                                    <div>
                                        <label htmlFor="menores-cb"
                                               id="menores-label"
                                               onClick={() =>
                                                   setColor('menores', ids.abilities[2])
                                               }
                                               className={getValues("abilities") && getValues("abilities").toString().includes(ids.abilities[2].toString()) ?
                                                   "mt-1 h-fit w-fit text-xs text-gray-900 bg-violet-300 border border-gray-300 focus:outline-none hover:bg-white focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer" :
                                                   "mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer"}>
                                            {t('Abilities.child')}
                                        </label>
                                        <input type="checkbox"
                                               {...register("abilities", {required: true})}
                                               id="menores-cb"
                                               value={ids.abilities[2]}
                                               style={{visibility: "hidden"}}
                                        />
                                    </div>
                                    <div>
                                        <label htmlFor="mayores-cb" id="mayores-label"
                                               onClick={() =>
                                                   setColor('mayores', ids.abilities[3])

                                               }
                                               className={getValues("abilities") && getValues("abilities").toString().includes(ids.abilities[3].toString()) ?
                                                   "mt-1 h-fit w-fit text-xs text-gray-900 bg-violet-300 border border-gray-300 focus:outline-none hover:bg-white focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer" :
                                                   "mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer"}>
                                            {t('Abilities.older')}
                                        </label>
                                        <input type="checkbox"
                                               {...register("abilities", {required: true})}
                                               id="mayores-cb"
                                               value={ids.abilities[3]}
                                               style={{visibility: "hidden"}}
                                        />
                                    </div>
                                    <div>
                                        <label htmlFor="especiales-cb" id="especiales-label"
                                               onClick={() =>
                                                   setColor('especiales', ids.abilities[4])
                                               }
                                               className={getValues("abilities") && getValues("abilities").toString().includes(ids.abilities[4].toString()) ?
                                                   "mt-1 h-fit w-fit text-xs text-gray-900 bg-violet-300 border border-gray-300 focus:outline-none hover:bg-white focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer" :
                                                   "mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer"}>
                                            {t('Abilities.specialNeeds')}
                                        </label>
                                        <input type="checkbox"
                                               {...register("abilities", {required: true})}
                                               id="especiales-cb"
                                               value={ids.abilities[4]}
                                               style={{visibility: "hidden"}}
                                        />
                                    </div>
                                    <div>
                                        <label htmlFor="mascotas-cb" id="mascotas-label"
                                               onClick={() =>
                                                   setColor('mascotas', ids.abilities[5])
                                               }
                                               className={getValues("abilities") && getValues("abilities").toString().includes(ids.abilities[5].toString()) ?
                                                   "mt-1 h-fit w-fit text-xs text-gray-900 bg-violet-300 border border-gray-300 focus:outline-none hover:bg-white focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer" :
                                                   "mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer"}>
                                            {t('Abilities.pets')}
                                        </label>
                                        <input type="checkbox"
                                               {...register("abilities", {required: true})}
                                               id="mascotas-cb"
                                               value={ids.abilities[5]}
                                               style={{visibility: "hidden"}}
                                        />
                                    </div>
                                </div>
                            }
                            {errors.abilities &&
                                <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('CreateJob.abilitiesError')}</p>
                            }
                            <div className="col-span-6 my-6">
                                <label className="block mb-2 text-sm font-medium text-gray-900">
                                    {t('CreateJob.description')}
                                </label>
                                <textarea
                                    {...register("description", {required: true, maxLength: 4000, minLength: 10})}
                                    rows={4}
                                    className="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-violet-500 focus:border-violet-500"/>
                                {errors.description &&
                                    <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('CreateJob.descriptionError')}</p>
                                }
                            </div>
                            <div className="mt-5 col-start-2 col-span-4 row-span-3">
                                <button type="submit"
                                        className="text-lg w-full focus:outline-none text-violet-900 bg-purple-900 bg-opacity-30 hover:bg-purple-900 hover:bg-opacity-50 font-small rounded-lg text-sm px-5 py-2.5">
                                    {t('CreateJob.button')}
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    )
}

export default CreateJob