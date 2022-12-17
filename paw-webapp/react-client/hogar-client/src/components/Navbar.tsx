import { useTranslation } from "react-i18next";
import { Link } from "react-router-dom"

export const Navbar = () => {
    const { t } = useTranslation();

    return (
        <nav className="bg-white w-full pr-2 sm:px-4 py-2.5 fixed shadow-md rounded z-10" style={{backgroundColor: "#ac70ff"}}>
            <div className="flex flex-wrap justify-between mx-auto">
                <Link to={localStorage.getItem('hogar-role') === "EMPLOYEE"? "/explore" : "/"} className="flex items-center">
                    <img src={'./images/hogar.png'} alt="logo" className={"mr-3 h-9"}/>
                    {/*<span className="self-center text-xl font-semibold whitespace-nowrap dark:text-white">Hogar</span>*/}
                </Link>
                {/*<button data-collapse-toggle="navbar-default" type="button" className="inline-flex items-center p-2 ml-3 text-sm text-gray-500 rounded-lg md:hidden hover:bg-gray-100 focus:outline-none focus:ring-2 focus:ring-gray-200 dark:text-gray-400 dark:hover:bg-gray-700 dark:focus:ring-gray-600" aria-controls="navbar-default" aria-expanded="false">*/}
                {/*    <span className="sr-only">Open main menu</span>*/}
                {/*    <svg className="w-6 h-6" aria-hidden="true" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M3 5a1 1 0 011-1h12a1 1 0 110 2H4a1 1 0 01-1-1zM3 10a1 1 0 011-1h12a1 1 0 110 2H4a1 1 0 01-1-1zM3 15a1 1 0 011-1h12a1 1 0 110 2H4a1 1 0 01-1-1z" clip-rule="evenodd"></path></svg>*/}
                {/*</button>*/}

                <div className="hidden w-full md:block md:w-auto" id="navbar-default">
                    <ul className="flex flex-col p-4 mt-4 rounded-lg md:flex-row md:space-x-8 md:mt-0 md:text-sm md:font-medium md:border-0">
                        <li>
                            <Link to="/explore">
                                <h1 className="self-center text-xl font-semibold whitespace-nowrap dark:text-white">{t('Navbar.explore')}</h1>
                            </Link>
                        </li>
                        {localStorage.getItem("hogar-role") && localStorage.getItem("hogar-role") === "EMPLOYEE" &&
                            <li>
                                <Link to="/jobs" state={{id: localStorage.getItem("hogar-uid")}}>
                                    <h1 className="self-center text-xl font-semibold whitespace-nowrap dark:text-white">{t('Navbar.jobsApplied')}</h1>
                                </Link>
                            </li>
                        }
                        {localStorage.getItem("hogar-role") && localStorage.getItem("hogar-role") === "EMPLOYER" &&
                            <li>
                                <Link to="/jobs" state={{id: localStorage.getItem("hogar-uid")}}>
                                    <h1 className="self-center text-xl font-semibold whitespace-nowrap dark:text-white">{t('Navbar.jobsPosted')}</h1>
                                </Link>
                            </li>
                        }
                        {localStorage.getItem("hogar-role") && localStorage.getItem("hogar-role") === "EMPLOYEE" &&
                            <li>
                                <Link to="/contacts" state={{id: localStorage.getItem("hogar-uid")}} >
                                    <h1 className="self-center text-xl font-semibold whitespace-nowrap dark:text-white">{t('Navbar.contacts')}</h1>
                                </Link>
                            </li>
                        }
                        {localStorage.getItem("hogar-role") &&
                            <li>
                                <Link to="/profile" state={{id: localStorage.getItem("hogar-uid")}}>
                                    <h1 className="self-center text-xl font-semibold whitespace-nowrap dark:text-white">{t('Navbar.profile')}</h1>
                                </Link>
                            </li>
                        }
                        {!localStorage.getItem("hogar-uid") &&
                            <li>
                                <Link to="/register">
                                    <h1 className="self-center text-xl font-semibold whitespace-nowrap dark:text-white">{t('Navbar.register')}</h1>
                                </Link>
                            </li>
                        }
                        {!localStorage.getItem("hogar-uid") &&
                            <li>
                                <Link to="/login">
                                    <h1 className="self-center text-xl font-semibold whitespace-nowrap dark:text-white">{t('Navbar.login')}</h1>
                                </Link>
                            </li>
                        }
                        <li>
                            <Link to="/contact">
                                <h1 className="self-center text-xl font-semibold whitespace-nowrap dark:text-white">{t('Navbar.contactUs')}</h1>
                            </Link>
                        </li>
                        {localStorage.getItem("hogar-uid") &&
                            <li>
                                <button onClick={() => {
                                        localStorage.removeItem("hogar-uid");
                                        localStorage.removeItem("hogar-role");
                                        localStorage.removeItem("hogar-jwt");
                                        window.location.replace("/")
                                    }
                                }>
                                    <h1 className="self-center text-xl font-semibold whitespace-nowrap dark:text-white">{t('Navbar.logout')}</h1>
                                </button>
                            </li>
                        }
                    </ul>
                </div>
            </div>
        </nav>
    )
}

export default Navbar