import './style.css'
import { useTranslation } from 'react-i18next';
import {useState} from "react";
import {ContactService} from "../service/ContactService";
import {useNavigate} from "react-router-dom";
import {useForm} from "react-hook-form";
import useFormPersist from "react-hook-form-persist";

export const invalidEmail = (email : String) => {
    if( email.length == 0)
        return false
    return !!String(email)
        .toLowerCase()
        .match(
            /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
        );
};

export const ContactUs = () => {
    const { t } = useTranslation();
    const nav = useNavigate();

    type FormData = {
        name: string;
        content: string;
        mail: string
    };

    const { register, handleSubmit, watch, formState: { errors }, getValues, setValue } = useForm<FormData>();

    watch("mail")
    watch("name")
    watch("content")

    useFormPersist("form", {
        watch,
        setValue,
        storage: window.localStorage,
    })

    const onSubmit = (data: any, e: any) => {
        ContactService.contactUs(e, data.name, data.mail, data.content).
        then((r) => {
                nav('/contact', {replace: true})
            }
        );
    }
    return (
    <div className="grid grid-cols-7 content-start justify-center h-full pt-5 overflow-auto">
    <div className="my-9 w-full col-span-7"></div>
    <form className = "col-start-3 col-span-3 grid h-full w-full" onSubmit={handleSubmit(onSubmit)}>
        <p className="text-3xl font-semibold text-violet-900 mb-4 mt-4 text-center">{t('ContactUs.title')}</p>
            <div className="block p-6 rounded-3xl shadow-lg bg-gray-200">
                <div className="form-group mb-6">
                    <h3>{t('ContactUs.name')}</h3>
                    <input type="text"
                        maxLength={100}
                        minLength={1}
                        value= {getValues("name")}
                        aria-invalid={errors.name ? "true" : "false"}
                        {...register("name", {required: true, maxLength: 100})}
                        className="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-blue-500 focus:border-violet-500"
                    />
                    {errors.name &&
                        <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('ContactUs.nameError')}</p>
                    }
                </div>
                <div className="form-group mb-6">
                    <h3>{t('ContactUs.mail')}</h3>
                    <input
                        type="email"
                        value={getValues("mail")}
                        {...register("mail", {required: true, validate: {invalidEmail}})}
                        className="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-blue-500 focus:border-violet-500"
                    />
                    {errors.mail &&
                        <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('ContactUs.mailError')}</p>
                    }
                </div>
                <div className="form-group mb-6">
                    <h3>{t('ContactUs.message')}</h3>
                    <textarea
                        rows={3}
                        value={getValues("content")}
                        {...register("content", {required:true})}
                        className="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-violet-500 focus:border-violet-500"
                    ></textarea>
                    {errors.content &&
                        <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('ContactUs.messageError')}</p>
                    }
                </div>

                <button type="submit" className="text-lg w-full focus:outline-none text-violet-900 bg-purple-900 bg-opacity-30 hover:bg-purple-900 hover:bg-opacity-50 font-small rounded-lg text-sm px-5 py-2.5">{t('ContactUs.send')}</button>
            </div>
    </form>
</div>
    )
}

export default ContactUs;