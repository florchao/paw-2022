import {useTranslation} from "react-i18next";
import {useState} from "react";
import {useNavigate} from "react-router-dom";
import {EmployerService} from "../service/EmployerService";
import {useForm} from "react-hook-form";
import useFormPersist from "react-hook-form-persist";
import {UserService} from "../service/UserService";

const RegisterEmployer = () => {
    const [registerEmployerError, setRegisterEmployerError]: any = useState()

    type FormData = {
        mail: string;
        name: string;
        lastName: string;
        password: string;
        confirmPassword: string;
    };

    const { register, handleSubmit, watch, formState: { errors }, getValues, setValue } = useForm<FormData>();


    watch("mail")
    watch("name")
    watch("lastName")
    watch("password")
    watch("confirmPassword")

    useFormPersist("registerEmployerForm", {
        watch,
        setValue,
        storage: window.localStorage,
        timeout: 1000 * 60 * 2,
    })

    const nav = useNavigate();
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
        return password === getValues("confirmPassword")
    };

    const validateConPassword = (confPassword: string) => {
        return confPassword === getValues("password")
    };

    const onSubmit = async (data:any, e: any) => {
        const post = await EmployerService.registerEmployer(e, data.name, data.lastName, data.mail, data.password, data.confirmPassword)
        //TODO: el 400 puede que se mande mal la info, no solo que ya exista el usuario
        if(post.status === 400 || post.status === 500 || post.status === 409){
            setRegisterEmployerError(true)
        }

        if (post.status === 201) {
            setRegisterEmployerError(false)

            const result = await UserService.getUser(e, data.mail, data.password)
            localStorage.removeItem("registerEmployerForm")
            localStorage.removeItem("imgRegisterEmployer")

            if (result.status === 200) {
                let body = await result.json()
                localStorage['hogar-role'] = body.role
                localStorage['hogar-uid'] = body.uid
                let authHeader = result.headers.get('Authorization')
                localStorage['hogar-jwt'] = authHeader?.slice(7)
            }
            nav('/', {replace: true})
            window.location.reload()
        }
    }

    return (
        <div className="h-screen overflow-auto pb-5">
            <div className="grid grid-cols-6">
                <div className="grid grid-row-4 col-span-4 col-start-2 mt-20 ">
                    <form className = "col-span-3 grid h-full w-full" onSubmit={handleSubmit(onSubmit)}>
                        <p className="text-3xl font-semibold text-violet-900 mb-4 mt-4 text-center">
                            {t('RegisterEmployer.title')}
                        </p>
                        <div className="bg-gray-200 rounded-3xl p-5 shadow-2xl">
                        <div className="grid grid-cols-6 gap-6">
                            {/*<div className="row-span-4 col-span-2 m-6">*/}
                            {/*    <div className="overflow-hidden bg-gray-100 rounded-full">*/}
                            {/*        <img id="picture"*/}
                            {/*             src={image? URL.createObjectURL(image) : user}*/}
                            {/*             alt="user pic"/>*/}
                            {/*    </div>*/}
                            {/*    <label htmlFor="image-input" id="image-label"*/}
                            {/*           className="mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer">*/}
                            {/*        {t('RegisterEmployer.image')}*/}
                            {/*    </label>*/}
                            {/*    <input id="image-input"*/}
                            {/*           type="file"*/}
                            {/*           accept="image/png, image/jpeg"*/}
                            {/*           onChange={(e) => {*/}
                            {/*               if (e.target.files != null) {*/}
                            {/*                   setImage(e.target.files[0])*/}
                            {/*                   imageUpload(e)*/}
                            {/*               }*/}
                            {/*           }}*/}
                            {/*           style={{visibility: "hidden"}}/>*/}
                            {/*    {!image &&*/}
                            {/*        <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('RegisterEmployer.imageError')}</p>*/}
                            {/*    }*/}
                            {/*</div>*/}
                            <div className="ml-3 col-span-3 w-4/5 justify-self-center">
                                    <h3>{t('RegisterEmployer.name')}</h3>
                                    <input
                                        type="text"
                                        value={getValues("name")}
                                        {...register("name", {required: true, maxLength: 100, pattern:{
                                            value: /^[a-zA-z\s'-]+|^$/, message:""
                                            }})}
                                        className="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-blue-500 focus:border-violet-500"
                                    />
                                {errors.name &&
                                    <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('RegisterEmployer.nameError')}</p>
                                }
                                </div>
                            <div className="ml-3 col-span-3 col-start-4 w-4/5 justify-self-center">
                                <h3>{t('RegisterEmployer.lastName')}</h3>
                                <input
                                    type="text"
                                    value={getValues("lastName")}
                                    {...register("lastName", {required: true, maxLength: 100, pattern:{
                                            value: /^[a-zA-z\s'-]+|^$/, message:""
                                        }})}
                                    className="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-blue-500 focus:border-violet-500"
                                />
                                {errors.lastName &&
                                    <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('RegisterEmployer.lastNameError')}</p>
                                }
                            </div>
                            <div className="ml-3 col-span-3  w-4/5 justify-self-center">
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
                            <div className="ml-3 col-span-3 col-start-4 w-4/5 justify-self-center">
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
                                {errors.password && errors.password.type !== "validate" &&
                                    <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('RegisterEmployer.passwordsError')}</p>
                                }
                            </div>
                            <div className="ml-3 col-span-3 w-4/5 justify-self-center">
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
                                {errors.password && errors.password.type !== "validate" &&
                                    <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('RegisterEmployer.passwordsError')}</p>
                                }
                            </div>
                            <div className="col-start-1 col-span-6 row-span-3">
                                <button type="submit"
                                        className="text-lg w-full focus:outline-none text-violet-900 bg-purple-900 bg-opacity-30 hover:bg-purple-900 hover:bg-opacity-50 font-small rounded-lg text-sm px-5 py-2.5">
                                    {t('RegisterEmployer.button')}
                                </button>
                                {registerEmployerError &&
                                    <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('EmployeeForm.emailUsedError')}</p>}
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