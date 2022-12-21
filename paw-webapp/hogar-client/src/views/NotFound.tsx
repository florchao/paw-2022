import { useTranslation } from "react-i18next";
import warning from '../assets/warning2.png';
import {Link} from "react-router-dom";

const NotFound = () => {
    const { t } = useTranslation();
    return (
        <div className="grid content-center justify-center h-screen">
            <div className="grid justify-items-center">
                <img src={warning} alt="Error" className="mr-3 h-6 sm:h-52"/>
                <p className="text-3xl font-semibold text-purple-700">{t('NotFound.title')}</p>
                <br/>
                <Link to={"/"}>
                    <button
                        className="bg-violet-300 w-full font-semibold hover:bg-yellow-300 shadow-lg text-violet-900 py-2 px-4 rounded-xl border-solid border-transparent border-2 hover:border-purple-300">
                        {t('NotFound.goBack')}
                    </button>
                </Link>
            </div>
        </div>
    );
}
 
export default NotFound;