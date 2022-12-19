import { useTranslation } from "react-i18next";
import Button from "../components/Button";

const NotFound = () => {
    const { t } = useTranslation();
    return (
        <div className="grid content-center justify-center h-screen">
            <div className="grid justify-items-center">
                <img src={'./assets/warning2.png'} alt="Error" className="mr-3 h-6 sm:h-52"/>
                <p className="text-3xl font-semibold text-purple-700">{t('NotFound.title')}</p>
                <br/>
                    <Button name={t('NotFound.goBack')} link="/"/>
            </div>
        </div>
    );
}
 
export default NotFound;