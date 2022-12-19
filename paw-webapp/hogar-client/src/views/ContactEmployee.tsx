import './style.css'
import {useLocation, useNavigate} from 'react-router-dom'
import {ContactService} from "../service/ContactService";
import {useTranslation} from "react-i18next";
import {useForm} from "react-hook-form";
import useFormPersist from "react-hook-form-persist";

export const ContactEmployee = () => {

    type FormData = {
        phone: string;
        content: string;
    };

    const { register, handleSubmit, watch, formState: { errors }, getValues, setValue } = useForm<FormData>();

    watch("phone")
    watch("content")

    useFormPersist("contactForm", {
        watch,
        setValue,
        storage: window.localStorage,
        timeout: 1000 * 60 * 2,
    })

    const { id, name } = useLocation().state

    const {t} = useTranslation();
    const nav = useNavigate();

    const invalidPhone = (number : String) => {
        if( number.length <= 10)
            return false
    };

    const employerId = localStorage.getItem("hogar-uid") as unknown as number

    const onSubmit = async (data: any, e: any) => {
        const contact = await ContactService.contactEmployee(e, data.phone, data.content, id, employerId)
        localStorage.removeItem("contactForm")
        let status;
        if (contact.status === 201)
            status = "0"
        else
            status = "1"
        nav("/employee", {replace: true, state: {id: id, status: status}})
    }

    return (
        <div className="grid grid-cols-7 content-start justify-center h-screen pt-5">
            <div className="my-16 w-full col-span-7"></div>
            <div className="col-start-3 col-span-3 grid h-full w-full">
                <div className="grid justify-items-center mx-6">
                    <p className="text-xl font-semibold text-white mb-5">
                        {t('ContactEmployee.title') + name}
                    </p>
                </div>

                <form onSubmit={handleSubmit(onSubmit)}>
                    <div className="block p-6 rounded-3xl shadow-lg bg-gray-200">
                        <div className="form-group mb-6">
                            <h3 className="block mb-2 text-sm font-medium text-gray-900">
                                {t('ContactEmployee.phone')}
                            </h3>
                            <input type="tel"
                                   value={getValues("phone")}
                                   {...register("phone", {required:true, validate:{invalidPhone}} )}
                                   className="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-violet-500 focus:border-violet-500"
                            />
                            {errors.phone &&
                                <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('ContactEmployee.phoneError')}</p>
                            }
                        </div>
                        <div className="form-group mb-6">
                            <h3 className="block mb-2 text-sm font-medium text-gray-900">
                                {t('ContactEmployee.message')}
                            </h3>
                            <textarea
                                value={getValues("content")}
                                {...register("content", {required:true})}
                                className="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-violet-500 focus:border-violet-500"/>
                            {errors.content &&
                                <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('ContactEmployee.messageError')}</p>
                            }
                        </div>
                        <button type="submit"
                                className="text-lg w-full focus:outline-none text-violet-900 bg-purple-900 bg-opacity-30 hover:bg-purple-900 hover:bg-opacity-50 font-small rounded-lg text-sm px-5 py-2.5">
                            {t('ContactEmployee.submit')}
                        </button>
                    </div>
                </form>
            </div>
        </div>
    )
}
export default ContactEmployee;