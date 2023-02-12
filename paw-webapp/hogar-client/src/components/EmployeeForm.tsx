import {useEffect, useState} from "react";
import {useTranslation} from "react-i18next";
import {EmployeeService} from "../service/EmployeeService";
import {useForm} from "react-hook-form";
import useFormPersist from "react-hook-form-persist";
import {IdsService} from "../service/IdsService";

export const EmployeeForm = ({onSubmit, from, self, onEdit, errorFromRequest}: { onSubmit: any, from: string, self: string, onEdit: boolean, errorFromRequest: boolean}) => {

    type FormData = {
        mail: string;
        password: string;
        confirmPassword: string;
        name: string;
        location: string;
        experienceYears: number;
        hourlyFee: number;
        availabilities: string[];
        abilities: string[];
    };

    const {register, handleSubmit, watch, formState: {errors}, getValues, setValue} = useForm<FormData>();


    watch("password")
    watch("confirmPassword")
    watch("name")
    watch("location")
    watch("experienceYears")
    watch("hourlyFee")
    watch("availabilities")
    watch("abilities")


    useFormPersist(from == "create" ? "employeeForm" : "employeeEditForm", {
        watch,
        setValue,
        storage: window.localStorage,
        timeout: 1000 * 60 * 2,
    })

    const [locations, setLocations] = useState<any>();
    const [availabilities, setAvailabilities] = useState<any>();
    const [abilities, setAbilities] = useState<any>();

    const {t} = useTranslation();

    const invalidEmail = (email: String) => {
        if (email.length === 0)
            return false
        return !!String(email)
            .toLowerCase()
            .match(
                /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
            );
    };

    const validatePassword = (password: string) => {
        return password == getValues("confirmPassword")
    };

    const validateConfPassword = (confPassword: string) => {
        return confPassword == getValues("password")
    };

    const setColorAb = (name: string, id: number) => {
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

    const setColorAv = (name: string, id: number) => {
        let label = document.getElementById(name + "-label")
        if (getValues("availabilities").toString() === "false" || !getValues("availabilities").includes(id.toString())) {
            if (label != null)
                label.style.backgroundColor = "#c4b5fd";
            window.sessionStorage.setItem(name, "#c4b5fd");
        } else {
            if (label != null)
                label.style.backgroundColor = "#ffffff";
        }
    }

    useEffect(() => {
        IdsService.getLocations().then((l) => {
            setLocations(l)
        });
        IdsService.getAbilities().then((a) => {
            setAbilities(a)
        });
        IdsService.getAvail().then((a) => {
            setAvailabilities(a)
        });
    }, [])

    useEffect(() => {
        if (self.length > 0) {
            EmployeeService.getEmployee(self, onEdit).then((e: any) => {
                    setValue("name", e.name)
                    setValue("location", e.location)
                    setValue("experienceYears", e.experienceYears)
                    setValue("abilities", e.abilitiesArr)
                    setValue("availabilities", e.availabilityArr)
                    setValue("hourlyFee", e.hourlyFee)
                }
            )
        }
    }, [])

    const submitForm = async (data: any, e: any) => {
        onSubmit(data, e)
    }

    return (
        <div className="h-screen overflow-auto pb-5">
            <form onSubmit={handleSubmit(submitForm)}>
                <div className="grid grid-cols-6">
                    <div className="grid grid-row-4 col-span-4 col-start-2 mt-20 ">
                        <p className="text-3xl font-semibold text-violet-900 mb-4 mt-4 text-center">
                            {from == "create" ? t('EmployeeForm.title_register') : t('EmployeeForm.title_edit')}
                        </p>
                        <div className="bg-gray-200 rounded-3xl p-5 shadow-2xl">
                            <div className="grid grid-cols-6 gap-6">
                                <div className="ml-3 col-span-3 w-4/5 justify-self-center">
                                    <label htmlFor="name"
                                           className="block mb-2 text-sm font-medium text-gray-900 ">
                                        {t('EmployeeForm.name')}
                                    </label>
                                    <input type="text"
                                           {...register("name", {required: true, maxLength: 100})}

                                           className="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-blue-500 focus:border-violet-500"/>
                                    {errors.name &&
                                        <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('EmployeeForm.nameError')}</p>
                                    }
                                </div>
                                {from == "create" &&
                                    <div className="ml-3 col-span-3 col-start-4 w-4/5 justify-self-center">
                                        <label htmlFor="mail"
                                               className="text-sm font-medium text-gray-900">
                                            {t('EmployeeForm.email')}
                                        </label>
                                        <input id="mail"
                                               type="mail"
                                               {...register("mail", {required: true, validate: {invalidEmail}})}
                                               className="col-span-5 block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-blue-500 focus:border-violet-500"/>
                                        {errors.mail &&
                                            <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('EmployeeForm.emailError')}</p>
                                        }
                                    </div>
                                }
                                {from == "create" &&
                                    <div className="ml-3 col-span-3 w-4/5 justify-self-center">
                                        <label htmlFor="password"
                                               className="text-sm font-medium text-gray-900">
                                            {t('EmployeeForm.password')}
                                        </label>
                                        <input id="password"
                                               type="password"
                                               {...register("password", {required: true, validate: {validatePassword}})}
                                               className=" col-span-5 block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-blue-500 focus:border-violet-500"/>
                                        {errors.password && errors.password.type === "required" &&
                                            <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('EmployeeForm.passwordError')}</p>
                                        }
                                        {errors.password && errors.password.type !== "validate" &&
                                            <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('EmployeeForm.passwordsError')}</p>
                                        }
                                    </div>
                                }
                                {from == "create" &&
                                    <div className="ml-3 col-span-3 col-start-4 w-4/5 justify-self-center">
                                        <label htmlFor="confirmPassword"
                                               className="text-sm font-medium text-gray-900">
                                            {t('EmployeeForm.confirmPassword')}
                                        </label>
                                        <input id="confirmPassword"
                                               type="password"
                                               {...register("confirmPassword", {
                                                   required: true,
                                                   validate: {validateConfPassword}
                                               })}
                                               className=" col-span-5 block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-blue-500 focus:border-violet-500"/>
                                        {errors.confirmPassword && errors.confirmPassword.type === "required" &&
                                            <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('EmployeeForm.passwordError')}</p>
                                        }
                                        {errors.confirmPassword && errors.confirmPassword.type !== "validate" &&
                                            <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('EmployeeForm.passwordsError')}</p>
                                        }
                                    </div>
                                }
                                <div className={from == "create" ?
                                    "ml-3 col-span-3 w-4/5 justify-self-center" :
                                    "ml-3 col-span-3 col-start-4 w-4/5 justify-self-center"
                                }>

                                    <label htmlFor="location"
                                           className="block mb-2 text-sm font-medium text-gray-900 ">
                                        {t('EmployeeForm.location')}
                                    </label>
                                    {locations &&
                                        <select
                                            {...register("location", {required: true})}
                                            className="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-violet-500 focus:border-violet-500">
                                            {locations.locations && locations.locations.length > 0 && locations.locations.map((l: any) => (
                                                <option value={l.id}
                                                        label={l.name}/>
                                            ))}
                                            {/*<option value={ids.locations[0]}*/}
                                            {/*        label={t('Locations.west')}/>*/}
                                            {/*<option value={ids.locations[1]}*/}
                                            {/*        label={t('Locations.north')}/>*/}
                                            {/*<option value={ids.locations[2]}*/}
                                            {/*        label={t('Locations.south')}/>*/}
                                            {/*<option value={ids.locations[3]}*/}
                                            {/*        label={t('Locations.caba')}/>*/}
                                        </select>
                                    }
                                    {errors.location &&
                                        <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('EmployeeForm.locationError')}</p>
                                    }
                                </div>
                                <div className={from == "create"? "ml-3 col-span-2 col-start-4 w-4/5 justify-self-center" : "ml-3 col-span-3 w-4/5 justify-self-center"}>
                                    <label className="block mb-2 text-sm font-medium text-gray-900 ">
                                        {t('EmployeeForm.experienceYears')}
                                    </label>
                                    <input type="number"
                                           id="expYears"
                                           {...register("experienceYears", {required: true, max: 100})}
                                           className="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-violet-500 focus:border-violet-500"/>
                                    {errors.experienceYears &&
                                        <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('EmployeeForm.expYearsError')}</p>
                                    }
                                </div>
                                <div className={from == "create"? "ml-3 col-span-1 col-start-6 w-4/5 justify-self-center" : "ml-3 col-span-3 col-start-4 w-4/5 justify-self-center" }>
                                    <label className="block mb-2 text-sm font-medium text-gray-900 ">
                                        {t('EmployeeForm.hourlyFee')}
                                    </label>
                                    <input type="number"
                                           id="hourlyFee"
                                           {...register("hourlyFee", {required: true})}
                                           className="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-violet-500 focus:border-violet-500"/>
                                    {errors.hourlyFee &&
                                        <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('EmployeeForm.hourlyFeeError')}</p>
                                    }
                                </div>
                            </div>
                            <div>
                                <h1 className="pb-3 pt-3 font-bold">
                                    {t('EmployeeForm.abilities')}
                                </h1>
                            </div>
                            {abilities &&
                                <div className="flex flex-wrap ml-8">
                                    {abilities.abilities && abilities.abilities.length > 0 &&
                                        abilities.abilities.map((a: any) =>
                                    <div className="mb-8">
                                        <label htmlFor={a.name + "-cb"} id={a.name + "-label"}
                                               onClick={() => {
                                                   setColorAb(a.name, a.id)
                                               }}
                                               className={getValues("abilities") && getValues("abilities").toString().includes(a.id.toString()) ?
                                                   "mt-1 h-fit w-fit text-xs text-gray-900 bg-violet-300 border border-gray-300 focus:outline-none hover:bg-white focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer" :
                                                   "mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer"}>
                                            {a.name}
                                        </label>
                                        <input type="checkbox"
                                               {...register("abilities", {required: true})}
                                               id={a.name + "-cb"}
                                               value={a.id}
                                               style={{visibility: "hidden"}}
                                        />
                                    </div>
                                        )}
                                </div>
                            }
                            {errors.abilities &&
                                <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('EmployeeForm.abilitiesError')}</p>
                            }
                            <div>
                                <h1 className="pb-3 pt-3 font-bold">
                                    {t('EmployeeForm.availability')}
                                </h1>
                            </div>
                            {availabilities &&
                                <div className="flex flex-wrap ml-8">
                                    {availabilities.availability && availabilities.availability.length > 0 &&
                                        availabilities.availability.map((a: any) =>
                                            <div className="mb-8">
                                                <label htmlFor={a.name + "-cb"} id={a.name + "-label"}
                                                       onClick={() => {
                                                           setColorAv(a.name, a.id)
                                                       }}
                                                       className={getValues("availabilities") && getValues("availabilities").toString().includes(a.id.toString()) ?
                                                           "mt-1 h-fit w-fit text-xs text-gray-900 bg-violet-300 border border-gray-300 focus:outline-none hover:bg-white focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer" :
                                                           "mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer"}>
                                                    {a.name}
                                                </label>
                                                <input type="checkbox"
                                                       {...register("availabilities", {required: true})}
                                                       id={a.name + "-cb"} value={a.id}
                                                       style={{visibility: "hidden"}}/>
                                            </div>
                                        )}
                                </div>
                            }
                            {errors.availabilities &&
                                <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('EmployeeForm.availabilityError')}</p>
                            }
                            <div className="mt-5 col-start-2 col-span-4 row-span-3">
                                <button type="submit"
                                        className="text-lg w-full focus:outline-none text-violet-900 bg-purple-900 bg-opacity-30 hover:bg-purple-900 hover:bg-opacity-50 font-small rounded-lg text-sm px-5 py-2.5">
                                    {from == "create" ? t('EmployeeForm.button_register') : t('EmployeeForm.button_edit')}
                                </button>
                                {errorFromRequest &&
                                    <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('EmployeeForm.emailUsedError')}</p>}
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    )
}

export default EmployeeForm