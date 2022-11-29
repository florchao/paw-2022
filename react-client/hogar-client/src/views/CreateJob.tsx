import {RefObject, useEffect, useRef, useState} from "react";
import {JobService} from "../service/JobService";
import {useTranslation} from "react-i18next";
import {useNavigate} from "react-router-dom";
import {number} from "zod";
import exp from "constants";

export const CreateJob = () => {
    const [title, setTitle] = useState('');
    const [location, setLocation] = useState('');
    const [experienceYears, setExperienceYears] = useState<number>(0);
    const [availability, setAvailability] = useState('');
    const [abilities, setAbilities] = useState<string[]>([]);
    const [description, setDescription] = useState('');

    const [ids, setIds] = useState<any>();

    const {t} = useTranslation();

    const nav = useNavigate();

    useEffect(() => {
        JobService.getIds().then((i) => {
            setIds(i)
        });
    }, [])

    const cookRef = useRef<HTMLInputElement>(null)
    const childRef = useRef<HTMLInputElement>(null)
    const elderRef = useRef<HTMLInputElement>(null)
    const specialRef = useRef<HTMLInputElement>(null)
    const petRef = useRef<HTMLInputElement>(null)
    const ironRef = useRef<HTMLInputElement>(null)

    const setColor = (name: string, ref: RefObject<HTMLInputElement>) => {
        let label = document.getElementById(name + "-label")
        if (!ref.current?.checked) {
            if (label !== null)
                label.style.backgroundColor = "#c4b5fd";
            window.sessionStorage.setItem(name, "#c4b5fd");
        } else {
            if (label !== null)
                label.style.backgroundColor = "#ffffff";
        }
    }

    const handleCheck = (ref: RefObject<HTMLInputElement>, ability: string) => {
        if (ref.current?.checked) {
            const newList = abilities.concat(ability);
            setAbilities(newList)
        } else {
            const newList = abilities.filter((a) => a !== ability);
            setAbilities(newList)
        }
    }

    const handleSubmit = async (e: any) => {
        if (abilities.length < 1) {
            return
        }
        const post = await JobService.postJob(e, title, location, experienceYears, availability, abilities, description)
        nav('/job', {replace: true, state: {id: post}})

    }

    return (
        <div className="h-screen overflow-auto">
            <div className="grid justify-items-center mt-24" style={{marginBottom: -40}}>
                <p className="text-xl font-semibold text-white">
                    {t('CreateJob.title')}
                </p>
            </div>
            <form onSubmit={handleSubmit}>
                <div className="grid grid-cols-6 mb-5">
                    <div className="grid grid-row-4 col-span-4 col-start-2 mt-20 ">
                        <div className="bg-gray-200 rounded-3xl p-5 shadow-2xl">
                            <div className="grid grid-cols-6 gap-6">
                                <div className="ml-3 col-span-6 w-4/5 justify-self-center">
                                    <label htmlFor="title" className="block mb-2 text-sm font-medium text-gray-900 ">
                                        {t('CreateJob.label_title')}
                                    </label>
                                    <input type="text"
                                           onChange={(e) => {
                                               setTitle(e.target.value)
                                           }}
                                           required
                                           onInput={e => (e.target as HTMLInputElement).setCustomValidity("")}
                                           onInvalid={e => (e.target as HTMLInputElement).setCustomValidity(t('CreateJob.titleError'))}
                                           className="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-blue-500 focus:border-violet-500"/>
                                    {title.length < 1 &&
                                        <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('CreateJob.titleError')}</p>
                                    }
                                </div>
                                <div className="ml-3 col-span-3 w-4/5 justify-self-center">
                                    <label htmlFor="location"
                                           className="block mb-2 text-sm font-medium text-gray-900 ">
                                        {t('CreateJob.location')}
                                    </label>
                                    <input type="text"
                                           onChange={(e) => {
                                               setLocation(e.target.value)
                                           }}
                                           required
                                           onInput={e => (e.target as HTMLInputElement).setCustomValidity("")}
                                           onInvalid={e => (e.target as HTMLInputElement).setCustomValidity(t('CreateJob.locationError'))}
                                           className="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-violet-500 focus:border-violet-500"/>
                                    {location.length < 1 &&
                                        <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('CreateJob.locationError')}</p>
                                    }
                                </div>
                                <div className="ml-3 col-span-3 w-4/5 justify-self-center">
                                    <label htmlFor="experienceYears"
                                           className="block mb-2 text-sm font-medium text-gray-900">
                                        {t('CreateJob.experienceYears')}
                                    </label>
                                    <input type="number" id="expYears"
                                           onChange={(e) => {
                                               setExperienceYears(e.target.valueAsNumber)
                                           }}
                                           required
                                           min={0}
                                           max={100}
                                           onInput={e => (e.target as HTMLInputElement).setCustomValidity("")}
                                           onInvalid={e => (e.target as HTMLInputElement).setCustomValidity(t('CreateJob.expYearsError'))}
                                           className="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-violet-500 focus:border-violet-500"/>
                                    {(experienceYears < 0 || experienceYears > 100) &&
                                        <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('CreateJob.expYearsError')}</p>
                                    }
                                </div>
                                {ids &&
                                    <div className="ml-3 col-span-3 w-4/5 justify-self-center">
                                        {/*<spring:message code="jobForm.availability.half" var="half"/>*/}
                                        {/*<spring:message code="jobForm.availability.complete" var="complete"/>*/}
                                        {/*<spring:message code="jobForm.availability.bed" var="bed"/>*/}
                                        <label
                                            className="block mb-2 text-sm font-medium text-gray-900 ">
                                            {t('CreateJob.availability')}
                                        </label>
                                        <select
                                            onChange={(e) => {
                                                setAvailability(e.target.value)
                                            }}
                                            required
                                            onInvalid={e => (e.target as HTMLInputElement).setCustomValidity(t('CreateJob.availabilityError'))}
                                            className="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-violet-500 focus:border-violet-500">
                                            <option value={ids.availabilities[0]}
                                                    label={t('CreateJob.availability_half')}/>
                                            <option value={ids.availabilities[1]}
                                                    label={t('CreateJob.availability_complete')}/>
                                            <option value={ids.availabilities[2]}
                                                    label={t('CreateJob.availability_bed')}/>
                                        </select>
                                        {/*<form:errors path="availability" element="p" cssStyle="color:red"/>*/}
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
                                               onClick={(e) => {
                                                   setColor('cocinar', cookRef)
                                               }}
                                               className="mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer">
                                            {t('CreateJob.abilities_cook')}
                                        </label>
                                        <input type="checkbox" ref={cookRef}
                                               onChange={(e) => handleCheck(cookRef, e.target.value)} id="cocinar-cb"
                                               value={ids.abilities[0]}
                                               style={{visibility: "hidden"}}/>
                                    </div>
                                    <div>
                                        <label htmlFor="planchar-cb" id="planchar-label"
                                               onClick={() => setColor('planchar', ironRef)}
                                               className="mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer">
                                            {t('CreateJob.abilities_iron')}
                                        </label>
                                        <input type="checkbox" ref={ironRef}
                                               onChange={(e) => handleCheck(ironRef, e.target.value)} id="planchar-cb"
                                               value={ids.abilities[1]}
                                               style={{visibility: "hidden"}}/>
                                    </div>
                                    <div>
                                        <label htmlFor="menores-cb" id="menores-label"
                                               onClick={() => setColor('menores', childRef)}
                                               className="mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer">
                                            {t('CreateJob.abilities_child')}
                                        </label>
                                        <input type="checkbox" ref={childRef}
                                               onChange={(e) => handleCheck(childRef, e.target.value)} id="menores-cb"
                                               value={ids.abilities[2]}
                                               style={{visibility: "hidden"}}/>
                                    </div>
                                    <div>
                                        <label htmlFor="mayores-cb" id="mayores-label"
                                               onClick={() => setColor('mayores', elderRef)}
                                               className="mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer">
                                            {t('CreateJob.abilities_older')}
                                        </label>
                                        <input type="checkbox" ref={elderRef}
                                               onChange={(e) => handleCheck(elderRef, e.target.value)} id="mayores-cb"
                                               value={ids.abilities[3]}
                                               style={{visibility: "hidden"}}/>
                                    </div>
                                    <div>
                                        <label htmlFor="especiales-cb" id="especiales-label"
                                               onClick={() => setColor('especiales', specialRef)}
                                               className="mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer">
                                            {t('CreateJob.abilities_specialNeeds')}
                                        </label>
                                        <input type="checkbox" ref={specialRef}
                                               onChange={(e) => handleCheck(specialRef, e.target.value)}
                                               id="especiales-cb" value={ids.abilities[4]}
                                               style={{visibility: "hidden"}}/>
                                    </div>
                                    <div>
                                        <label htmlFor="mascotas-cb" id="mascotas-label"
                                               onClick={() => setColor('mascotas', petRef)}
                                               className="mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer">
                                            {t('CreateJob.abilities_pets')}
                                        </label>
                                        <input type="checkbox" ref={petRef}
                                               onChange={(e) => handleCheck(petRef, e.target.value)} id="mascotas-cb"
                                               value={ids.abilities[5]}
                                               style={{visibility: "hidden"}}/>
                                    </div>
                                </div>
                            }
                            {abilities.length < 1 &&
                                <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('CreateJob.abilitiesError')}</p>
                            }
                            <div className="col-span-6 my-6">
                                <label className="block mb-2 text-sm font-medium text-gray-900">
                                    {t('CreateJob.description')}
                                </label>
                                <textarea rows={4} onChange={(e) => {
                                    setDescription(e.target.value)
                                }}
                                          required
                                          onInput={e => (e.target as HTMLInputElement).setCustomValidity("")}
                                          onInvalid={e => (e.target as HTMLInputElement).setCustomValidity(t('CreateJob.descriptionError'))}
                                          className="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-violet-500 focus:border-violet-500"/>
                                {/*<form:errors path="description" element="p" cssStyle="color: red"/>*/}
                                {description.length < 1 &&
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