import './style.css'
import Button from "../components/Button";
import {useEffect, useState} from "react";
import {Link, useLocation, useNavigate} from 'react-router-dom'
import {Contacts} from "./Contacts";
import {ContactService} from "../service/ContactService";

export const ContactEmployee = () => {

    const [phone, setPhone] = useState('');
    const [content, setContent] = useState('');
    const [status, setStatus] = useState<number>();

    const { id, name } = useLocation().state



    const nav = useNavigate();

    const handleSubmit = async (e: any) => {
        const contact = await ContactService.contactEmployee(e, phone, content, id)
        console.log(contact)
        nav("/employee", {replace: true, state: {id: id, status: contact}})
    }

    return (
        <div className="grid grid-cols-7 content-start justify-center h-screen pt-5">
            <div className="my-16 w-full col-span-7"></div>
            <div className="col-start-3 col-span-3 grid h-full w-full">
                <div className="grid justify-items-center mx-6">
                    <p className="text-xl font-semibold text-white mb-5">
                        Contactarse con {name}
                    </p>
                </div>

                <form onSubmit={handleSubmit}>
                    <div className="block p-6 rounded-3xl shadow-lg bg-gray-200">
                        <div className="form-group mb-6">
                            <h3 className="block mb-2 text-sm font-medium text-gray-900">
                                Telefono
                            </h3>
                            <input type="tel"
                                   required
                                   value={phone}
                                   onChange={(e) => setPhone(e.target.value)}
                                   className="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-violet-500 focus:border-violet-500"
                            />

                            {/*<form:errors path="phone" element="p" cssStyle="color: red"/>*/}
                        </div>
                        <div className="form-group mb-6">
                            <h3 className="block mb-2 text-sm font-medium text-gray-900">
                                Mensaje
                            </h3>
                            <textarea
                                required
                                value={content}
                                onChange={(e) => setContent(e.target.value)}
                                className="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-violet-500 focus:border-violet-500"/>
                            {/*<form:errors path="content" element="p" cssStyle="color: red"/>*/}
                        </div>
                        <button type="submit"
                                className="text-lg w-full focus:outline-none text-violet-900 bg-purple-900 bg-opacity-30 hover:bg-purple-900 hover:bg-opacity-50 font-small rounded-lg text-sm px-5 py-2.5">
                            Enviar
                        </button>
                    </div>
                </form>
            </div>
        </div>
    )
}
export default ContactEmployee;