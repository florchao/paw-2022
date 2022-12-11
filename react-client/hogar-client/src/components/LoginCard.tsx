import {useTranslation} from "react-i18next";
import {useForm} from "react-hook-form";
import useFormPersist from "react-hook-form-persist";
import {useNavigate} from "react-router-dom";
import {UserService} from "../service/UserService";

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
    })

    const onSubmit = async (data: any, e: any) => {
        const result = await UserService.getUser(e, data.email, data.password)
        if (result.status === 200) {
            let body = await result.json()
            localStorage['hogar-role'] = body.role
            localStorage['hogar-uid'] = body.uid
            let authHeader = result.headers.get('Authorization')
            localStorage['hogar-jwt'] = authHeader?.slice(7)
            localStorage.removeItem("loginForm")
            nav("/explore", {replace: true})
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
                        {/*<input id="username" name="j_username" type="text"*/}
                        {/*       className=" col-span-5 block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-blue-500 focus:border-violet-500"/>*/}
                    </div>
                    <div className="form-group mb-6 grid grid-cols-6">
                        <label className="text-sm font-medium text-gray-900">{t('LogIn.password')}</label>
                        <input type="password"
                               value={getValues("password")}
                               {...register("password", {required: true})}
                               className=" col-span-5 block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-blue-500 focus:border-violet-500"
                        />
                    </div>
                    <div className="form-group mb-6">
                    </div>
                    <div className="form-group mb-6">
                        {/*<input name="j_rememberme" type="checkbox"/>*/}
                        {/*{t('LogIn.remember')}*/}
                    </div>
                    <div className="form-group mb-6">
                        <button type="submit"
                                className="text-lg w-full focus:outline-none text-violet-900 bg-purple-900 bg-opacity-30 hover:bg-purple-900 hover:bg-opacity-50 font-small rounded-lg text-sm px-5 py-2.5">
                            {t('Home.login')}
                        </button>
                        {/*<button type="submit"*/}
                        {/*        className="text-lg w-full focus:outline-none text-violet-900 bg-purple-900 bg-opacity-30 hover:bg-purple-900 hover:bg-opacity-50 font-small rounded-lg text-sm px-5 py-2.5">Login*/}
                        {/*</button>*/}
                    </div>
                    <div className="form-group mb-6">
                        <p className="text-sm font-semibold text-gray-900">
                            {t('LogIn.account')}
                            <a className="text-violet-900">{t('LogIn.register')}</a>
                        </p>
                    </div>
                    <div className="form-group mb-6 grid grid-cols-6">
                        <p className="text-sm font-semibold text-gray-900 col-span-3">
                            {t('LogIn.forgot')}
                            <a className="text-violet-800">{t('LogIn.setP')}</a>
                        </p>
                    </div>
                </div>
            </form>
        </div>
    )
}

export default LoginCard