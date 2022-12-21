import React from "react";
import {useTranslation} from "react-i18next";

export const FilterForm = ({handleSubmit, register, errors, onSubmit, reset, setValue}: { handleSubmit: any, register: any, errors: any, onSubmit: any, reset: any, setValue: any}) => {

    const {t} = useTranslation();

    const resetFilter = () => {
        reset()
        handleSubmit(onSubmit)()
    }

    const submit = () => {
        setValue("page", 0)
        handleSubmit(onSubmit)()
    }

    return (
        <div>
            <form
                className="bg-purple-300 mr-8 p-6 rounded-2xl mt-2 shadow-xl border-solid border-violet-500 border-2 h-fit w-full"
                onSubmit={submit}>
                <h1 className="font-semibold text-violet-900 hover:cursor-pointer" onClick={resetFilter}>{t('FilterForm.reset')}</h1>
                <div className={'flex flex-col items-center'}>
                    {(localStorage.getItem('hogar-role') === "EMPLOYER" || localStorage.getItem('hogar-role') === null ) && <h1 className="font-semibold mt-2">{t('FilterForm.minExpYears')}</h1>}
                    {localStorage.getItem('hogar-role') === "EMPLOYEE" && <h1 className="font-semibold mt-2">{t('FilterForm.maxExpYears')}</h1>}
                    <input
                        {...register("experienceYears", {max: 100, min: 0})}
                        className={'rounded-md'}
                        type="number"
                    />
                    {errors.experienceYears &&
                        <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('FilterForm.expYearsError')}</p>
                    }
                    <h1 className="font-semibold mt-2">{t('FilterForm.name')}</h1>
                    <input
                        {...register("name", {maxLength: 100})}
                        type={"text"}
                        className={'rounded-md'}
                        />
                    <h1 className="font-semibold mt-2">{t('FilterForm.location')}</h1>
                    <div className={'grid grid-cols-12 w-3/4 gap-x-1 items-center'}>
                        <div className={'col-span-10 col-start-1]'}>
                            <label htmlFor={"loc-1"}>{t('Locations.west')}</label>
                        </div>
                        <div className={'col-start-11'}>
                            <input type={'checkbox'}
                                   value={1}
                                   id={"loc-1"}
                                   {...register("location")}
                            />
                        </div>
                        <div className={'col-span-10 col-start-1]'}>
                            <label htmlFor={"loc-2"}>{t('Locations.north')}</label>
                        </div>
                        <div className={'col-start-11'}>
                            <input type={'checkbox'}
                                   value={2}
                                   id={"loc-2"}
                                   {...register("location")}
                            />
                        </div>
                        <div className={'col-span-10 col-start-1]'}>
                            <label htmlFor={"loc-3"}>{t('Locations.south')}</label>
                        </div>
                        <div className={'col-start-11'}>
                            <input type={'checkbox'}
                                   value={3}
                                   id={"loc-3"}
                                   {...register("location")}
                            />
                        </div>
                        <div className={'col-span-10 col-start-1'}>
                            <label htmlFor={"loc-4"}>{t('Locations.caba')}</label>
                        </div>
                        <div className={'col-start-11'}>
                            <input type={'checkbox'}
                                   value={4}
                                   id={"loc-4"}
                                   {...register("location")}
                            />
                        </div>
                    </div>
                    <h1 className="font-semibold mt-2">{t('FilterForm.abilities')}</h1>
                    <div className={'grid grid-cols-12 w-3/4 gap-x-1 items-center'}>
                        <div className={'col-span-10 col-start-1]'}>
                            <label htmlFor={"1"}>{t('Abilities.cook')}</label>
                        </div>
                        <div className={'col-start-11'}>
                            <input type={'checkbox'}
                                   value={1}
                                   id={"1"}
                                   {...register("abilities")}
                            />
                        </div>
                        <div className={'col-span-10 col-start-1]'}>
                            <label htmlFor={"2"}>{t('Abilities.iron')}</label>
                        </div>
                        <div className={'col-start-11'}>
                            <input type={'checkbox'}
                                   value={2}
                                   id={"2"}
                                   {...register("abilities")}
                            />
                        </div>
                        <div className={'col-span-10 col-start-1]'}>
                            <label htmlFor={"3"}>{t('Abilities.child')}</label>
                        </div>
                        <div className={'col-start-11'}>
                            <input type={'checkbox'}
                                   value={3}
                                   id={"3"}
                                   {...register("abilities")}
                            />
                        </div>
                        <div className={'col-span-10 col-start-1'}>
                            <label htmlFor={"4"}>{t('Abilities.older')}</label>
                        </div>
                        <div className={'col-start-11'}>
                            <input type={'checkbox'}
                                   value={4}
                                   id={"4"}
                                   {...register("abilities")}
                            />
                        </div>
                        <div className={'col-span-10 col-start-1]'}>
                            <label htmlFor={"5"}>{t('Abilities.specialNeeds')}</label>
                        </div>
                        <div className={'col-start-11'}>
                            <input type={'checkbox'}
                                   value={5}
                                   id={"5"}
                                   {...register("abilities")}
                            />
                        </div>
                        <div className={'col-span-10 col-start-1]'}>
                            <label htmlFor={"6"}>{t('Abilities.pets')}</label>
                        </div>
                        <div className={'col-start-11'}>
                            <input type={'checkbox'}
                                   value={6}
                                   id={"6"}
                                   {...register("abilities")}
                            />
                        </div>
                    </div>
                    <h1 className="font-semibold mt-2">{t('FilterForm.availability')}</h1>
                    <div className={'grid grid-cols-12 w-3/4 gap-x-1'}>
                        <div className={'col-span-10 col-start-1'}>
                            <label htmlFor={"av-1"}>{t('Availabilities.half')}</label>
                        </div>
                        <div className={'col-start-11'}>
                            <input type={'checkbox'}
                                   value={1}
                                   id={"av-1"}
                                   {...register("availabilities")}
                            />
                        </div>
                        <div className={'col-span-10 col-start-1'}>
                            <label htmlFor={"av-2"}>{t('Availabilities.complete')}</label>
                        </div>
                        <div className={'col-start-11'}>
                            <input type={'checkbox'}
                                   value={2}
                                   id={"av-2"}
                                   {...register("availabilities")}
                            />
                        </div>
                        <div className={'col-span-10 col-start-1]'}>
                            <label htmlFor={"av-3"}>{t('Availabilities.bed')}</label>
                        </div>
                        <div className={'col-start-11'}>
                            <input type={'checkbox'}
                                   value={3}
                                   id={"av-3"}
                                   {...register("availabilities")}
                            />
                        </div>
                    </div>
                    <button type="submit"
                           className="mt-4 border shadow-md text-lg w-5/6 focus:outline-none text-violet-900 bg-purple-400 border border-purple-900 hover:bg-yellow-300 hover:bg-opacity-50 font-small rounded-lg text-sm px-5 py-2.5 hover:cursor-pointer">
                        {t('FilterForm.filter')}
                    </button>
                </div>
            </form>
        </div>
    )
}

export default FilterForm
