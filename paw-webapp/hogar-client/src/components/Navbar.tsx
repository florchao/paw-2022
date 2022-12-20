import { useTranslation } from "react-i18next";
import {Link, useNavigate} from "react-router-dom";

export const Navbar = () => {
    const { t } = useTranslation();
    const nav = useNavigate()
    return (
        <nav className="bg-white w-full pr-2 sm:px-4 py-2.5 fixed shadow-md rounded z-10" style={{backgroundColor: "#ac70ff"}}>
            <div className="flex flex-wrap justify-between mx-auto">
                <Link to={localStorage.getItem('hogar-role') === "EMPLOYEE"? "/explore" : "/"} className="flex items-center">
                    <img src={'./images/hogar.png'} alt="logo" className={"mr-3 h-9"}/>
                </Link>

                <div className="hidden w-full md:block md:w-auto" id="navbar-default">
                    <ul className="flex flex-col p-4 mt-4 rounded-lg md:flex-row md:space-x-8 md:mt-0 md:text-sm md:font-medium md:border-0">
                        <li>
                            <Link to="/explore">
                                <h1 className={window.location.pathname === "/explore"? "self-center text-xl font-semibold whitespace-nowrap text-violet-900" : "self-center text-xl font-semibold whitespace-nowrap text-white"}>{t('Navbar.explore')}</h1>
                            </Link>
                        </li>
                        {localStorage.getItem("hogar-role") && localStorage.getItem("hogar-role") === "EMPLOYEE" &&
                            <li>
                                <Link to="/jobs" state={{id: localStorage.getItem("hogar-uid")}}>
                                    <h1 className={window.location.pathname === "/jobs"? "self-center text-xl font-semibold whitespace-nowrap text-violet-900" : "self-center text-xl font-semibold whitespace-nowrap text-white"}>{t('Navbar.jobsApplied')}</h1>
                                </Link>
                            </li>
                        }
                        {localStorage.getItem("hogar-role") && localStorage.getItem("hogar-role") === "EMPLOYER" &&
                            <li>
                                <Link to="/jobs" state={{id: localStorage.getItem("hogar-uid")}}>
                                    <h1 className={window.location.pathname === "/jobs"? "self-center text-xl font-semibold whitespace-nowrap text-violet-900" : "self-center text-xl font-semibold whitespace-nowrap text-white"}>{t('Navbar.jobsPosted')}</h1>
                                </Link>
                            </li>
                        }
                        {localStorage.getItem("hogar-role") && localStorage.getItem("hogar-role") === "EMPLOYEE" &&
                            <li>
                                <Link to="/contacts" state={{id: localStorage.getItem("hogar-uid")}} >
                                    <h1 className={window.location.pathname === "/contacts"? "self-center text-xl font-semibold whitespace-nowrap text-violet-900" : "self-center text-xl font-semibold whitespace-nowrap text-white"}>{t('Navbar.contacts')}</h1>
                                </Link>
                            </li>
                        }
                        {localStorage.getItem("hogar-role") &&
                            <li>
                                <Link to="/profile" state={{id: localStorage.getItem("hogar-uid")}}>
                                    <h1 className={window.location.pathname === "/profile"? "self-center text-xl font-semibold whitespace-nowrap text-violet-900" : "self-center text-xl font-semibold whitespace-nowrap text-white"}>{t('Navbar.profile')}</h1>
                                </Link>
                            </li>
                        }
                        {!localStorage.getItem("hogar-uid") &&
                            <li>
                                <Link to="/register">
                                    <h1 className={window.location.pathname === "/register"? "self-center text-xl font-semibold whitespace-nowrap text-violet-900" : "self-center text-xl font-semibold whitespace-nowrap text-white"}>{t('Navbar.register')}</h1>
                                </Link>
                            </li>
                        }
                        {!localStorage.getItem("hogar-uid") &&
                            <li>
                                <Link to="/login">
                                    <h1 className={window.location.pathname === "/login"? "self-center text-xl font-semibold whitespace-nowrap text-violet-900" : "self-center text-xl font-semibold whitespace-nowrap text-white"}>{t('Navbar.login')}</h1>
                                </Link>
                            </li>
                        }
                        <li>
                            <Link to="/contact">
                                <h1 className={window.location.pathname === "/contact"? "self-center text-xl font-semibold whitespace-nowrap text-violet-900" : "self-center text-xl font-semibold whitespace-nowrap text-white"}>{t('Navbar.contactUs')}</h1>
                            </Link>
                        </li>
                        {localStorage.getItem("hogar-uid") &&
                            <li>
                                <button onClick={() => {
                                        localStorage.removeItem("hogar-uid");
                                        localStorage.removeItem("hogar-role");
                                        localStorage.removeItem("hogar-jwt");
                                        nav("/", {replace: true})
                                        window.location.reload()
                                    }
                                }>
                                    <h1 className="self-center text-xl font-semibold whitespace-nowrap text-white">{t('Navbar.logout')}</h1>
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