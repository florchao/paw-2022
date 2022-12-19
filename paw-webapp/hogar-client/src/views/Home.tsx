import './style.css'
import Button from "../components/Button";
import { useTranslation } from 'react-i18next';

export const Home = () => {
    const { t } = useTranslation();
    return (
            <div className="grid grid-cols-2 gap-4 pt-16 h-screen">
                <div className="grid grid-rows-2 gap-4 pl-8">
                    <div className="grid content-center">
                        <h3 className="text-3xl font-semibold text-purple-700">
                        {t('Home.title1')}
                        </h3>
                        <h3 className="text-2xl text-purple-700">
                        {t('Home.title2')}
                        </h3>
                    </div>
                    <div className="flex flex-col">
                        <div className="grid">
                            <div className="pb-4 grid col-start-2 col-span-2">
                                <Button name={t('Home.searchEmployee')} link="/explore"/>
                            </div>
                            <div className="pb-4 grid col-start-2 col-span-2">
                                <Button name={t('Home.register')} link="/register"/>
                            </div>
                            <div className="grid col-start-2 col-span-2">
                                <Button name={t('Home.login')} link="/login"/>
                            </div>
                        </div>
                    </div>

                </div>
                <div className="pt-8">
                    <img src={ './assets/indexImage.jpg' } alt="primera foto" className={"rounded-full"}/>
                </div>
            </div>
    )
}

export default Home;