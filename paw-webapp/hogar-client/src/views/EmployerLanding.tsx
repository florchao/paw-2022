import './style.css'
import Button from "../components/Button";
import {useTranslation} from "react-i18next";

export const EmployerLanding = () => {

    const { t } = useTranslation();

    return (
        <div className="flex h-screen overflow-auto pl-5 pr-5 justify-center">
            <div id="roles" className="grid grid-cols-2 items-start p-10 gap-y-7 mt-9 content-center">
                <div className="grid grid-cols-3">
                    <div className="h-48 w-48">
                        <img className="object-cover w-full h-full rounded-full" src={ './assets/searchJob.jpeg'} alt="employer"/>
                    </div>
                    <div className="col-span-2 ">
                        <h3 className="text-2xl text-purple-700 justify-self-center">
                            {t('EmployerLanding.searchEmployee')}
                        </h3>
                        <p className="font-thin text-lg mt-7">
                            {t('EmployerLanding.chooseEmployee')}
                        </p>
                        <div className="pb-4 grid col-start-2 col-span-2 mt-7">
                            <Button name={t('EmployerLanding.buttonEmployee')}
                                    link="/explore"/>
                        </div>
                    </div>
                </div>
                <div className="col-start-2 grid grid-cols-3">
                    <div className="h-48 w-48">
                        <img className="object-cover w-full h-full rounded-full" src={ './assets/joboffer.jpeg' } alt="job offer picture"/>
                    </div>
                    <div className="col-span-2">
                        <h3 className="text-2xl text-purple-700 justify-self-center">
                            {t('EmployerLanding.publishJob')}
                        </h3>
                        <p className="font-thin text-lg mt-7">
                            {t('EmployerLanding.descriptionJob')}
                        </p>
                        <div className="pb-4 grid col-start-2 col-span-2 mt-7">
                            <Button name={t('EmployerLanding.buttonJob')}
                                    link="/jobs"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default EmployerLanding;