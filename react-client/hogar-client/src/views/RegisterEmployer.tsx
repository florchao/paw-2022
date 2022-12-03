import {useTranslation} from "react-i18next";
import {useRef, useState} from "react";
import {useNavigate} from "react-router-dom";
import {EmployerService} from "../service/EmployerService";
import {useForm} from "react-hook-form";
import useFormPersist from "react-hook-form-persist";

const RegisterEmployer = () => {

    type FormData = {
        mail: string;
        name: string;
        lastName: string;
        password: string;
        confirmPassword: string;
        image: FileList
    };

    const { register, handleSubmit, watch, formState: { errors }, getValues, setValue } = useForm<FormData>();

    watch("mail")
    watch("name")
    watch("lastName")
    watch("password")
    watch("confirmPassword")

    useFormPersist("form", {
        watch,
        setValue,
        storage: window.localStorage,
    })

    const nav = useNavigate();
    const {t} = useTranslation();

    const imageRef = useRef<HTMLInputElement>(null)

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


    const onSubmit = async (data:any, e: any) => {
        const post = await EmployerService.registerEmployer(e, data.name, data.lastName, data.mail, data.password, data.confirmPassword, data.image![0])
        console.log(post)
        nav('/employer', {replace: true, state: {id: post}})
    }

    return (
        <div className="h-screen overflow-auto pb-5">
            <div className="grid grid-cols-6">
                <div className="grid grid-row-4 col-span-4 col-start-2 mt-20 ">
                    <form className = "col-start-3 col-span-3 grid h-full w-full" onSubmit={handleSubmit(onSubmit)}>
                        <p className="text-3xl font-semibold text-violet-900 mb-4 mt-4 text-center">
                            {t('RegisterEmployer.title')}
                        </p>
                        <div className="bg-gray-200 rounded-3xl p-5 shadow-2xl">
                        <div className="grid grid-cols-5 gap-6">
                            <div className="row-span-4 col-span-2 m-6">
                                <div className="overflow-hidden bg-gray-100 rounded-full">
                                    <img id="picture" src={getValues("image") && getValues("image")[0] != undefined && getValues("image")[0].size != 0? URL.createObjectURL(getValues("image")[0]): '/images/user.png'} alt="user pic"/>
                                </div>
                                <label htmlFor="image-input" id="image-label"
                                       className="mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer">
                                    {t('RegisterEmployee.image')}
                                </label>
                                <input id="image-input"
                                       type="file"
                                       accept="image/png, image/jpeg"
                                       {...register("image", {required:true})}
                                       style={{visibility: "hidden"}}/>
                                {errors.image &&
                                    <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('RegisterEmployer.imageError')}</p>
                                }
                            </div>
                            <div className="ml-3 col-span-3 col-start-3 w-4/5 justify-self-center">
                                    <h3>{t('RegisterEmployer.name')}</h3>
                                    <input
                                        type="text"
                                        value={getValues("name")}
                                        {...register("name", {required: true, maxLength: 100})}
                                        className="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-blue-500 focus:border-violet-500"
                                    />
                                {errors.name &&
                                    <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('RegisterEmployer.nameError')}</p>
                                }
                                </div>
                            <div className="ml-3 col-span-3 col-start-3 w-4/5 justify-self-center">
                                <h3>{t('RegisterEmployer.lastName')}</h3>
                                <input
                                    type="text"
                                    value={getValues("lastName")}
                                    {...register("lastName", {required: true, maxLength: 100})}
                                    className="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-blue-500 focus:border-violet-500"
                                />
                                {errors.lastName &&
                                    <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('RegisterEmployer.lastNameError')}</p>
                                }
                            </div>
                            <div className="ml-3 col-span-3 col-start-3 w-4/5 justify-self-center">
                                <h3>{t('RegisterEmployer.email')}</h3>
                                <input
                                    type="email"
                                    value={getValues("mail")}
                                    {...register("mail", {required: true, validate: {invalidEmail}})}
                                    className="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-blue-500 focus:border-violet-500"
                                />
                                {errors.mail &&
                                    <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('RegisterEmployer.emailError')}</p>
                                }
                            </div>
                            <div className="ml-3 col-span-3 col-start-3 w-4/5 justify-self-center">
                                <h3>{t('RegisterEmployer.password')}</h3>
                                <input
                                    type="password"
                                    value={getValues("password")}
                                    {...register("password", {required: true, validate: {validatePassword}})}
                                    className="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-blue-500 focus:border-violet-500"
                                />
                                {errors.password && errors.password.type === "required" &&
                                    <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('RegisterEmployer.passwordError')}</p>
                                }
                                {errors.password && errors.password.type === "validate" &&
                                    <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('RegisterEmployer.passwordsError')}</p>
                                }
                            </div>
                            <div className="ml-3 col-span-3 col-start-3 w-4/5 justify-self-center">
                                <h3>{t('RegisterEmployer.confirmPassword')}</h3>
                                <input
                                    type="password"
                                    value={getValues("confirmPassword")}
                                    {...register("confirmPassword", {required: true, validate: {validateConPassword}})}
                                    className="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-blue-500 focus:border-violet-500"
                                />
                                {errors.password && errors.password.type === "required" &&
                                    <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('RegisterEmployer.passwordError')}</p>
                                }
                                {errors.password && errors.password.type === "validate" &&
                                    <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('RegisterEmployer.passwordsError')}</p>
                                }
                            </div>
                            <div className="mt-5 col-span-5 row-span-3">
                                <button type="submit"
                                        className="text-lg w-full focus:outline-none text-violet-900 bg-purple-900 bg-opacity-30 hover:bg-purple-900 hover:bg-opacity-50 font-small rounded-lg text-sm px-5 py-2.5">
                                    {t('RegisterEmployer.button')}
                                </button>
                            </div>
                        </div>
                    </div>
                    </form>
                </div>
            </div>
        </div>
    )
}

export default RegisterEmployer;