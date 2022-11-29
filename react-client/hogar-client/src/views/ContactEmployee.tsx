import './style.css'
import {useEffect, useState} from "react";
import {Link, useLocation, useNavigate} from 'react-router-dom'
import {ContactService} from "../service/ContactService";
import {useTranslation} from "react-i18next";

export const ContactEmployee = () => {

    const [phone, setPhone] = useState('');
    const [content, setContent] = useState('');
    const [status, setStatus] = useState<number>();

    const { id, name } = useLocation().state

    const {t} = useTranslation();
    const nav = useNavigate();

    const invalidPhone = (number : String) => {
        if( number.length <= 10)
            return true
    };

    const handleSubmit = (e: any) => {
        ContactService.contactEmployee(e, phone, content, id).then((r) => {
            // history.go(-1);
            console.log("Response: " + r)
            if (r.type === "error") {
                setStatus(1);
            } else {
                setStatus(0);
            }
        }).then(() => {
            console.log(status)
            // nav("/employee", {replace: true, state: {id: id, status: status}})
        })
    }

    useEffect(() => {
            console.log("Status: " + status)
        }, [status]
    )

    return (
        <div className="grid grid-cols-7 content-start justify-center h-screen pt-5">
            <div className="my-16 w-full col-span-7"></div>
            <div className="col-start-3 col-span-3 grid h-full w-full">
                <div className="grid justify-items-center mx-6">
                    <p className="text-xl font-semibold text-white mb-5">
                        {t('ContactEmployee.title') + name}
                    </p>
                </div>

                <form onSubmit={handleSubmit}>
                    <div className="block p-6 rounded-3xl shadow-lg bg-gray-200">
                        <div className="form-group mb-6">
                            <h3 className="block mb-2 text-sm font-medium text-gray-900">
                                {t('ContactEmployee.phone')}
                            </h3>
                            <input type="tel"
                                   required
                                   value={phone}
                                   onInvalid={e => (e.target as HTMLInputElement).setCustomValidity(t('ContactEmployee.phoneError'))}
                                   onInput={e => (e.target as HTMLInputElement).setCustomValidity("")}
                                   onChange={(e) => setPhone(e.target.value)}
                                   className="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-violet-500 focus:border-violet-500"
                            />
                            {(invalidPhone(phone) ) &&
                                <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('ContactEmployee.phoneError')}</p>
                            }
                        </div>
                        <div className="form-group mb-6">
                            <h3 className="block mb-2 text-sm font-medium text-gray-900">
                                {t('ContactEmployee.message')}
                            </h3>
                            <textarea
                                required
                                value={content}
                                onChange={(e) => setContent(e.target.value)}
                                onInvalid={e => (e.target as HTMLInputElement).setCustomValidity(t('ContactEmployee.messageError'))}
                                onInput={e => (e.target as HTMLInputElement).setCustomValidity("")}
                                className="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-violet-500 focus:border-violet-500"/>
                            {(content.length < 1 ) &&
                                <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('ContactEmployee.messageError')}</p>
                            }                        </div>
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