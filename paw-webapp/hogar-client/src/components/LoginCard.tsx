import {useTranslation} from "react-i18next";
import {useForm} from "react-hook-form";
import useFormPersist from "react-hook-form-persist";
import {Link, useNavigate} from "react-router-dom";
import {UserService} from "../service/UserService";
import {useState} from "react";
import {ApplicantService} from "../service/ApplicantService";
import {EmployeeService} from "../service/EmployeeService";

export const LoginCard = () => {
    const {t} = useTranslation();

    type FormData = {
        email: string;
        password: string;
    };

    const {register, handleSubmit, watch, formState: {errors}, getValues, setValue} = useForm<FormData>();
    const nav = useNavigate();

    const invalidEmail = (email: string) => {
        if (email.length <= 5)
            return false
    };

    watch("email")
    watch("password")

    useFormPersist("loginForm", {
        watch,
        setValue,
        storage: window.localStorage,
        timeout: 1000 * 60 * 2,
    })

    const [loginError, setLoginError]: any = useState("")

    const onSubmit = async (data: any, e: any) => {
        let result = undefined
        try {
            // It could be any endpoint with authentication
            result = await EmployeeService.getEmployees(btoa(data.email + ":" + data.password));
        } catch (error: any) {
            setLoginError(t("Feedback.genericError"))
        }
        if (result?.status === 200) {
            let body = await result?.json()
            let authHeader = result?.headers.get('Authorization')
            let jwt = authHeader?.slice(7)
            if (jwt === undefined)
                return
            let payload = JSON.parse(atob(jwt.split('.')[1]))
            localStorage['hogar-jwt'] = jwt
            localStorage['hogar-role'] = payload.role
            localStorage['hogar-uid'] = payload.uid
            localStorage.removeItem("loginForm")
            if (payload.role === "EMPLOYER") {
                nav("/", {replace: true})
            } else {
                nav("/explore")
            }
            window.location.reload()
        } else if (result?.status === 401) {
            setLoginError(t("LogIn.error"))
        } else {
            setLoginError(t("Feedback.genericError"))
        }
    }

    return (
        <div className="grid grid-cols-7 space-between content-start justify-center h-screen pt-5">
            <div className="my-16 w-full col-span-7"></div>
            <form className="col-span-3 col-start-3" onSubmit={handleSubmit(onSubmit)}>
                <div className="block p-6 rounded-lg shadow-lg bg-white">
                    <div className="form-group mb-6 grid grid-cols-6">
                        <label className="text-sm font-medium text-gray-900">{t('LogIn.mail')}</label>
                        <input type="email"
                               value={getValues("email")}
                               {...register("email", {required: true, validate: {invalidEmail}})}
                               className=" col-span-5 block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-blue-500 focus:border-violet-500"
                        />
                        {errors.email &&
                            <p className="text-red-500 text-xs col-span-5 col-start-2 mt-2">{t('LogIn.invalidEmail')}</p>}
                    </div>
                    <div className="form-group mb-6 grid grid-cols-6">
                        <label className="text-sm font-medium text-gray-900">{t('LogIn.password')}</label>
                        <input type="password"
                               value={getValues("password")}
                               {...register("password", {required: true})}
                               className=" col-span-5 block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-blue-500 focus:border-violet-500"
                        />
                        {errors.password &&
                            <p className="text-red-500 text-xs col-span-5 col-start-2 mt-2">{t('LogIn.invalidPassword')}</p>}
                    </div>
                    <div className="form-group mb-6">
                    </div>
                    <div className="form-group mb-6">
                    </div>
                    <div className="form-group mb-6">
                        <button type="submit"
                                className="text-lg w-full focus:outline-none text-violet-900 bg-purple-900 bg-opacity-30 hover:bg-purple-900 hover:bg-opacity-50 font-small rounded-lg text-sm px-5 py-2.5">
                            {t('Home.login')}
                        </button>
                            <p className="text-red-500 text-xs col-span-5 col-start-2 mt-2">{loginError}</p>
                    </div>
                    <div className="form-group mb-6 grid grid-cols-6">
                        <p className="text-xs text-gray-900 col-span-2">
                            {t('LogIn.account')}
                        </p>
                        <Link to={"/register"}>
                            <p className="text-xs text-violet-900">{t('LogIn.register')}</p>
                        </Link>
                    </div>
                </div>
            </form>
        </div>
    )
}

export default LoginCard