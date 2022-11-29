import {useTranslation} from "react-i18next";
import Background from "../components/Background/Background";
import {useState} from "react";
import {useNavigate} from "react-router-dom";
import {EmployerService} from "../service/EmployerService";


const RegisterEmployer = () => {
    const [mail, setMail] = useState('');
    const [image, setImage] = useState('');
    const [name, setName] = useState('');
    const [lastName, setLastName] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');

    const nav = useNavigate();
    const {t} = useTranslation();

    const handleSubmit = (e: any) => {
        EmployerService.registerEmployer(e, name, lastName, mail, password, confirmPassword, image).
        then((r) => {
                nav('/explore', {replace: true})
            }
        );
    }

    return (
        <body>
        <Background/>
        <div className="h-screen overflow-auto pb-5">
            <div className="grid grid-cols-6">
                <div className="grid grid-row-4 col-span-4 col-start-2 mt-20 ">
                    <form className = "col-start-3 col-span-3 grid h-full w-full" onSubmit={handleSubmit}>
                        <p className="text-3xl font-semibold text-violet-900 mb-4 mt-4 text-center">
                            {t('RegisterEmployer.title')}
                        </p>
                        <div className="bg-gray-200 rounded-3xl p-5 shadow-2xl">
                        <div className="grid grid-cols-5 gap-6">
                            <div className="row-span-4 col-span-2 m-6">
                                <div className="overflow-hidden bg-gray-100 rounded-full">
                                    <img id="picture" src={'./images/user.png'} alt="user pic"/>
                                </div>
                                <h3>{t('RegisterEmployer.image')}</h3>
                                <input
                                    type="image"
                                    required
                                    value={image}
                                    onInvalid={e => (e.target as HTMLInputElement).setCustomValidity(t('RegisterEmployer.imageError'))}
                                    onInput={e => (e.target as HTMLInputElement).setCustomValidity("")}
                                    onChange={(e) => setImage(e.target.value)}
                                    className="overflow-hidden bg-gray-100 rounded-full"
                                />
                                {(image.length < 1 ) &&
                                    <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('RegisterEmployer.imageError')}</p>
                                }
                            </div>
                            <div className="ml-3 col-span-3 col-start-3 w-4/5 justify-self-center">
                                    <h3>{t('RegisterEmployer.name')}</h3>
                                    <input
                                        type="text"
                                        required
                                        value={name}
                                        onInvalid={e => (e.target as HTMLInputElement).setCustomValidity(t('RegisterEmployer.nameError'))}
                                        onInput={e => (e.target as HTMLInputElement).setCustomValidity("")}
                                        onChange={(e) => setName(e.target.value)}
                                        className="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-blue-500 focus:border-violet-500"
                                    />
                                {(name.length < 1 ) &&
                                    <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('RegisterEmployer.nameError')}</p>
                                }
                                </div>
                            <div className="ml-3 col-span-3 col-start-3 w-4/5 justify-self-center">
                                <h3>{t('RegisterEmployer.lastName')}</h3>
                                <input
                                    type="text"
                                    required
                                    value={lastName}
                                    onInvalid={e => (e.target as HTMLInputElement).setCustomValidity(t('RegisterEmployer.lastNameError'))}
                                    onInput={e => (e.target as HTMLInputElement).setCustomValidity("")}
                                    onChange={(e) => setLastName(e.target.value)}
                                    className="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-blue-500 focus:border-violet-500"
                                />
                                {(lastName.length < 1 ) &&
                                    <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('RegisterEmployer.lastNameError')}</p>
                                }
                            </div>
                            <div className="ml-3 col-span-3 col-start-3 w-4/5 justify-self-center">
                                <h3>{t('RegisterEmployer.email')}</h3>
                                <input
                                    type="email"
                                    required
                                    value={mail}
                                    onInvalid={e => (e.target as HTMLInputElement).setCustomValidity(t('RegisterEmployer.emailError'))}
                                    onInput={e => (e.target as HTMLInputElement).setCustomValidity("")}
                                    onChange={(e) => setMail(e.target.value)}
                                    className="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-blue-500 focus:border-violet-500"
                                />
                                {(mail.length < 1 ) &&
                                    <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('RegisterEmployer.emailError')}</p>
                                }
                            </div>
                            <div className="ml-3 col-span-3 col-start-3 w-4/5 justify-self-center">
                                <h3>{t('RegisterEmployer.password')}</h3>
                                <input
                                    type="password"
                                    required
                                    value={password}
                                    onInvalid={e => (e.target as HTMLInputElement).setCustomValidity(t('RegisterEmployer.passwordError'))}
                                    onInput={e => (e.target as HTMLInputElement).setCustomValidity("")}
                                    onChange={(e) => setPassword(e.target.value)}
                                    className="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-blue-500 focus:border-violet-500"
                                />
                                {(password.length < 1 ) &&
                                    <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('RegisterEmployer.passwordError')}</p>
                                }
                                {(password != confirmPassword ) &&
                                    <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('RegisterEmployer.passwordsError')}</p>
                                }
                            </div>
                            <div className="ml-3 col-span-3 col-start-3 w-4/5 justify-self-center">
                                <h3>{t('RegisterEmployer.confirmPassword')}</h3>
                                <input
                                    type="password"
                                    required
                                    value={confirmPassword}
                                    onInvalid={e => (e.target as HTMLInputElement).setCustomValidity(t('RegisterEmployer.passwordError'))}
                                    onInput={e => (e.target as HTMLInputElement).setCustomValidity("")}
                                    onChange={(e) => setConfirmPassword(e.target.value)}
                                    className="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-blue-500 focus:border-violet-500"
                                />
                                {(confirmPassword.length < 1 ) &&
                                    <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('RegisterEmployer.passwordError')}</p>
                                }
                                {(password != confirmPassword ) &&
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
        </body>
    )
}

export default RegisterEmployer;