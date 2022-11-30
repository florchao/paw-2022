import {EmployerService} from "../service/EmployerService";
import {useTranslation} from "react-i18next";
import {useState} from "react";
import {UserService} from "../service/UserService";
import {useNavigate} from "react-router-dom";

const NewPassword = () => {

    const {t} = useTranslation();
    const nav = useNavigate();

    const [mail, setMail] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');

    const invalidEmail = (email : String) => {
        if( email.length === 0)
            return true
        return !String(email)
            .toLowerCase()
            .match(
                /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
            );
    };

    const handleSubmit = (e: any) => {
        UserService.newPassword(e, mail, password, confirmPassword)
    }

    return (
        <body>
        <div className="grid grid-cols-7 content-center justify-center h-screen pt-5">
            <form className="col-span-3 col-start-3" onSubmit={handleSubmit}>
                <div className="grid justify-items-center mx-6">
                    <p className="text-3xl font-semibold text-violet-900 mb-4 mt-4 text-center">
                        {t('NewPassword.title')}
                    </p>
                </div>
                <div className="block p-6 rounded-lg shadow-lg bg-white">
                    <div className="form-group mb-6 grid grid-cols-6">
                        <h3>{t('NewPassword.email')}</h3>
                        <input
                            id="mail"
                            required
                            minLength={1}
                            value={mail}
                            type="mail"
                            onInvalid={e => (e.target as HTMLInputElement).setCustomValidity(t('NewPassword.emailError'))}
                            onInput={e => (e.target as HTMLInputElement).setCustomValidity("")}
                            onChange={(e) => setMail(e.target.value)}
                            className="col-span-5 block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-blue-500 focus:border-violet-500"/>
                        {(invalidEmail(mail) ) &&
                            <p className="col-span-5 block p-2 w-full block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('NewPassword.emailError')}</p>
                        }
                    </div>
                    <div className="form-group mb-6 grid grid-cols-6">
                        <h3>{t('NewPassword.password')}</h3>
                        <input
                            id="password"
                            value={password}
                            required
                            minLength={1}
                            type="password"
                            onInvalid={e => (e.target as HTMLInputElement).setCustomValidity(t('NewPassword.passwordError'))}
                            onInput={e => (e.target as HTMLInputElement).setCustomValidity("")}
                            onChange={(e) => setPassword(e.target.value)}
                            className="col-span-5 block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-blue-500 focus:border-violet-500"/>
                        {(password.length < 1 ) &&
                            <p className="col-span-5 block p-2 w-full block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('NewPassword.passwordError')}</p>
                        }
                        {(password !== confirmPassword ) &&
                            <p className="col-span-5 block p-2 w-full block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('NewPassword.passwordsError')}</p>
                        }
                    </div>
                    <div className="form-group mb-6 grid grid-cols-6">
                        <h3>{t('NewPassword.confirmPassword')}</h3>
                        <input id="confirmPassword"
                               required
                               value={confirmPassword}
                               type="password"
                               minLength={1}
                               onChange={(e) => setConfirmPassword(e.target.value)}
                               onInvalid={e => (e.target as HTMLInputElement).setCustomValidity(t('NewPassword.passwordError'))}
                               onInput={e => (e.target as HTMLInputElement).setCustomValidity("")}
                               className=" col-span-5 block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-blue-500 focus:border-violet-500"/>
                        {(confirmPassword.length < 1 ) &&
                            <p className=" col-span-5 block p-2 w-full block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('NewPassword.passwordError')}</p>
                        }
                        {(password !== confirmPassword ) &&
                            <p className="col-span-5 block p-2 w-full block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('NewPassword.passwordsError')}</p>
                        }
                    </div>
                    <div>
                        <button type="submit"
                                className="text-lg w-full focus:outline-none text-violet-900 bg-purple-900 bg-opacity-30 hover:bg-purple-900 hover:bg-opacity-50 font-small rounded-lg text-sm px-5 py-2.5">
                            {t('NewPassword.button')}
                        </button>
                    </div>
                </div>
            </form>
        </div>
        </body>
    )
}

export default NewPassword;