import {useEffect, useState} from "react";
import {useTranslation} from "react-i18next";
import {JobService} from "../service/JobService";
import {EmployeeService} from "../service/EmployeeService";
import {UserService} from "../service/UserService";
import {useForm} from "react-hook-form";
import useFormPersist from "react-hook-form-persist";

export const EmployeeForm = ({onSubmit, from, id}: {onSubmit: any ,from: string, id:number}) => {

    type FormData = {
        mail: string;
        password: string;
        confirmPassword: string;
        name: string;
        location: string;
        experienceYears: number;
        availabilities: string[];
        abilities: string[];
    };

    const { register, handleSubmit, watch, formState: { errors }, getValues, setValue } = useForm<FormData>();

    const [image, setImage] = useState<File>()

    watch("password")
    watch("confirmPassword")
    watch("name")
    watch("location")
    watch("experienceYears")
    watch("availabilities")
    watch("abilities")


    useFormPersist("form", {
        watch,
        setValue,
        storage: window.localStorage,
    })

    const [ids, setIds] = useState<any>();

    const {t} = useTranslation();

    const invalidEmail = (email : String) => {
        if( email.length === 0)
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

    const validateConPassword = (confPassword: string) => {
        return confPassword == getValues("password")
    };

    function urltoFile(url: string, filename:string){
        return (fetch(url)
                .then(function(res){return res.arrayBuffer();})
                .then(function(buf){return new File([buf], filename);})
        );
    }

    const setColorAb = (name: string, id:number) => {
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

    const setColorAv = (name: string, id:number) => {
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

    const imageUpload = (e: any) => {
        const file = e.target.files[0];
        getBase64(file).then(base64 => {
            localStorage["img"] = base64;
            console.debug("file stored",base64);
        });
    };

    const imageDownload = async () => {
        if (localStorage.getItem("img") !== null) {
            const img = await urltoFile(localStorage.getItem("img")!, "img")
            setImage(img)
        }
    }

    const getBase64 = (file: File) => {
        return new Promise((resolve, reject) => {
            const reader = new FileReader();
            reader.onload = () => resolve(reader.result);
            reader.onerror = error => reject(error);
            reader.readAsDataURL(file);
        });
    }

    useEffect(() => {
        JobService.getIds().then((i) => {
            setIds(i)
        });
        imageDownload()
    }, [])

    useEffect(() => {
        if(id >= 0) {
            EmployeeService.getEmployee(id, true).then((e: any) => {
                    setValue("name", e.name)
                    setValue("location", e.location)
                    setValue("experienceYears", e.experienceYears)
                    setValue("abilities", e.abilitiesArr)
                    setValue("availabilities", e.availabilityArr)
                }
            )
        }
    }, [])

    useEffect(() => {
        if(id >= 0) {
            UserService.loadImage(id).then(
                (img) => {
                    if (img.size > 0) {
                        setImage(new File([img], "img"));
                    }
                })
        }
    }, [])

    window.onload =  imageDownload

    const submitForm = async (data:any, e: any) => {
        onSubmit(data, e, image)
    }

    return (
        <div className="h-screen overflow-auto pb-5">
            <form onSubmit={handleSubmit(submitForm)}>
                <div className="grid grid-cols-6">
                    <div className="grid grid-row-4 col-span-4 col-start-2 mt-20 ">
                        <p className="text-3xl font-semibold text-violet-900 mb-4 mt-4 text-center">
                            {from == "create"? t('EmployeeForm.title_register') : t('EmployeeForm.title_edit')}
                        </p>
                        <div className="bg-gray-200 rounded-3xl p-5 shadow-2xl">
                            <div className="grid grid-cols-6 gap-6">
                                <div className="row-span-4 col-span-2 m-6">
                                    <div className="overflow-hidden bg-gray-100 rounded-full">
                                        <img id="picture"
                                             src={image? URL.createObjectURL(image) : '/images/user.png'}
                                             alt="user pic"/>
                                    </div>
                                    <label htmlFor="image-input" id="image-label"
                                           className="mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer">
                                        {t('EmployeeForm.image')}
                                    </label>
                                    <input id="image-input"
                                           type="file"
                                           accept="image/png, image/jpeg"
                                           onChange={(e) => {
                                               if (e.target.files != null) {
                                                   setImage(e.target.files[0])
                                                   imageUpload(e)
                                               }
                                           }}
                                           style={{visibility: "hidden"}}/>
                                    {image?.size == 0 &&
                                        <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('EmployeeForm.imageError')}</p>
                                    }
                                </div>
                                <div className="ml-3 col-span-3 col-start-4 w-4/5 justify-self-center">
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
                                    <div className="ml-3 col-span-3 col-start-4 w-4/5 justify-self-center">
                                        <label htmlFor="password"
                                               className="text-sm font-medium text-gray-900">
                                            {t('EmployeeForm.password')}
                                        </label>
                                        <input id="password"
                                               type="password"
                                               {...register("password", {required: true, validate: {validatePassword}})}
                                               className=" col-span-5 block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-blue-500 focus:border-violet-500"/>
                                        {errors.password && errors.password.type == "required" &&
                                            <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('EmployeeForm.passwordError')}</p>
                                        }
                                        {errors.password && errors.password.type == "validate" &&
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
                                                   validate: {validateConPassword}
                                               })}
                                               className=" col-span-5 block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-blue-500 focus:border-violet-500"/>
                                        {errors.confirmPassword && errors.confirmPassword.type == "required" &&
                                            <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('EmployeeForm.passwordError')}</p>
                                        }
                                        {errors.confirmPassword && errors.confirmPassword.type == "validate" &&
                                            <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('EmployeeForm.passwordsError')}</p>
                                        }
                                    </div>
                                }
                                <div className={from == "create"?
                                    "ml-3 col-span-3 w-4/5 justify-self-center":
                                    "ml-3 col-span-3 col-start-4 w-4/5 justify-self-center"
                                }>
                                    <label htmlFor="location"
                                           className="block mb-2 text-sm font-medium text-gray-900 ">
                                        {t('EmployeeForm.location')}
                                    </label>
                                    <input type="text"
                                           {...register("location", {required: true, maxLength: 100})}
                                           className="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-violet-500 focus:border-violet-500"/>
                                    { errors.location &&
                                        <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('EmployeeForm.locationError')}</p>
                                    }
                                </div>
                                <div className="ml-3 col-span-3 col-start-4 w-4/5 justify-self-center">
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
                            </div>
                            <div>
                                <h1 className="pb-3 pt-3 font-bold">
                                    {t('EmployeeForm.abilities')}
                                </h1>
                            </div>
                            {ids &&
                                <div className="flex flex-wrap ml-8">
                                    <div className="mb-8">
                                        <label htmlFor="cocinar-cb" id="cocinar-label"
                                               onClick={() => {
                                                   setColorAb('cocinar', ids.abilities[0])
                                               }}
                                               className={getValues("abilities") && getValues("abilities").toString().includes(ids.abilities[0].toString())?
                                                   "mt-1 h-fit w-fit text-xs text-gray-900 bg-violet-300 border border-gray-300 focus:outline-none hover:bg-white focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer" :
                                                   "mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer"}>
                                            {t('Abilities.cook')}
                                        </label>
                                        <input type="checkbox"
                                               {... register("abilities", {required:true})}
                                            // onChange={(e) => console.log(getValues("abilities"))}
                                               id="cocinar-cb"
                                               value={ids.abilities[0]}
                                               style={{visibility: "hidden"}}
                                        />
                                    </div>
                                    <div>
                                        <label htmlFor="planchar-cb" id="planchar-label"
                                               onClick={() =>
                                                   setColorAb('planchar', ids.abilities[1])
                                               }
                                               className={getValues("abilities") && getValues("abilities").toString().includes(ids.abilities[1].toString())?
                                                   "mt-1 h-fit w-fit text-xs text-gray-900 bg-violet-300 border border-gray-300 focus:outline-none hover:bg-white focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer" :
                                                   "mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer"}>
                                            {t('Abilities.iron')}
                                        </label>
                                        <input type="checkbox"
                                               id="planchar-cb"
                                               {...register("abilities", {required:true})}
                                            // onChange={(e) => console.log(getValues("abilities"))}
                                               value={ids.abilities[1]}
                                               style={{visibility: "hidden"}}
                                        />
                                    </div>
                                    <div>
                                        <label htmlFor="menores-cb"
                                               id="menores-label"
                                               onClick={() =>
                                                   setColorAb('menores', ids.abilities[2])
                                               }
                                               className={getValues("abilities") && getValues("abilities").toString().includes(ids.abilities[2].toString())?
                                                   "mt-1 h-fit w-fit text-xs text-gray-900 bg-violet-300 border border-gray-300 focus:outline-none hover:bg-white focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer" :
                                                   "mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer"}>
                                            {t('Abilities.child')}
                                        </label>
                                        <input type="checkbox"
                                               {...register("abilities", {required:true})}
                                            // onChange={(e) => console.log(getValues("abilities"))}
                                               id="menores-cb"
                                               value={ids.abilities[2]}
                                               style={{visibility: "hidden"}}
                                        />
                                    </div>
                                    <div>
                                        <label htmlFor="mayores-cb" id="mayores-label"
                                               onClick={() =>
                                                   setColorAb('mayores', ids.abilities[3])

                                               }
                                               className={getValues("abilities") && getValues("abilities").toString().includes(ids.abilities[3].toString())?
                                                   "mt-1 h-fit w-fit text-xs text-gray-900 bg-violet-300 border border-gray-300 focus:outline-none hover:bg-white focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer" :
                                                   "mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer"}>
                                            {t('Abilities.older')}
                                        </label>
                                        <input type="checkbox"
                                               {...register("abilities", {required:true})}
                                            // onChange={(e) => console.log(getValues("abilities"))}
                                               id="mayores-cb"
                                               value={ids.abilities[3]}
                                               style={{visibility: "hidden"}}
                                        />
                                    </div>
                                    <div>
                                        <label htmlFor="especiales-cb" id="especiales-label"
                                               onClick={() =>
                                                   setColorAb('especiales', ids.abilities[4])
                                               }
                                               className={getValues("abilities") && getValues("abilities").toString().includes(ids.abilities[4].toString())?
                                                   "mt-1 h-fit w-fit text-xs text-gray-900 bg-violet-300 border border-gray-300 focus:outline-none hover:bg-white focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer" :
                                                   "mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer"}>
                                            {t('Abilities.specialNeeds')}
                                        </label>
                                        <input type="checkbox"
                                               {...register("abilities", {required:true})}
                                                id="especiales-cb"
                                               value={ids.abilities[4]}
                                               style={{visibility: "hidden"}}
                                        />
                                    </div>
                                    <div>
                                        <label htmlFor="mascotas-cb" id="mascotas-label"
                                               onClick={() =>
                                                   setColorAb('mascotas', ids.abilities[5])
                                               }
                                               className={getValues("abilities") && getValues("abilities").toString().includes(ids.abilities[5].toString())?
                                                   "mt-1 h-fit w-fit text-xs text-gray-900 bg-violet-300 border border-gray-300 focus:outline-none hover:bg-white focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer" :
                                                   "mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer"}>
                                            {t('Abilities.pets')}
                                        </label>
                                        <input type="checkbox"
                                               {...register("abilities", {required:true})}
                                            // onChange={(e) => console.log(getValues("abilities"))}
                                               id="mascotas-cb"
                                               value={ids.abilities[5]}
                                               style={{visibility: "hidden"}}
                                        />
                                    </div>
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
                            {ids &&
                                <div className="flex flex-wrap ml-8">
                                    <div className="mb-8">
                                        <label htmlFor="media-cb" id="media-label"
                                               onClick={() => {
                                                   setColorAv('media', ids.availabilities[0])
                                               }}
                                               className={getValues("availabilities") && getValues("availabilities").toString().includes(ids.availabilities[0].toString())?
                                                   "mt-1 h-fit w-fit text-xs text-gray-900 bg-violet-300 border border-gray-300 focus:outline-none hover:bg-white focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer" :
                                                   "mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer"}>
                                            {t('Availabilities.half')}
                                        </label>
                                        <input type="checkbox"
                                               {...register("availabilities", {required:true})}
                                               id="media-cb" value={ids.availabilities[0]}
                                               style={{visibility: "hidden"}}/>
                                    </div>
                                    <div>
                                        <label htmlFor="completa-cb" id="completa-label"
                                               onClick={() => {
                                                   setColorAv('completa', ids.availabilities[1])
                                               }}
                                               className={getValues("availabilities") && getValues("availabilities").toString().includes(ids.availabilities[1].toString())?
                                                   "mt-1 h-fit w-fit text-xs text-gray-900 bg-violet-300 border border-gray-300 focus:outline-none hover:bg-white focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer" :
                                                   "mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer"}>
                                            {t('Availabilities.complete')}
                                        </label>
                                        <input type="checkbox"
                                               {...register("availabilities", {required:true})}
                                               id="completa-cb" value={ids.availabilities[1]}
                                               style={{visibility: "hidden"}}/>
                                    </div>
                                    <div>
                                        <label htmlFor="cama-cb" id="cama-label"
                                               onClick={() => {
                                                   setColorAv('cama', ids.availabilities[2])
                                               }}
                                               className={getValues("availabilities") && getValues("availabilities").toString().includes(ids.availabilities[2].toString())?
                                                   "mt-1 h-fit w-fit text-xs text-gray-900 bg-violet-300 border border-gray-300 focus:outline-none hover:bg-white focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer" :
                                                   "mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer"}>
                                            {t('Availabilities.bed')}
                                        </label>
                                        <input type="checkbox"
                                               {...register("availabilities", {required:true})}
                                               id="cama-cb" value={ids.availabilities[2]}
                                               style={{visibility: "hidden"}}/>
                                    </div>
                                </div>
                            }
                            {errors.availabilities &&
                                <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('EmployeeForm.availabilityError')}</p>
                            }
                            <div className="mt-5 col-start-2 col-span-4 row-span-3">
                                <button type="submit"
                                        className="text-lg w-full focus:outline-none text-violet-900 bg-purple-900 bg-opacity-30 hover:bg-purple-900 hover:bg-opacity-50 font-small rounded-lg text-sm px-5 py-2.5">
                                    {from == "create"? t('EmployeeForm.button_register') : t('EmployeeForm.button_edit')}
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    )
}

export default EmployeeForm