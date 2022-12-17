import './style.css'
import Button from "../components/Button";
import { useTranslation } from 'react-i18next';

const Register = () => {
    const {t} = useTranslation();
    return (
        <div className="grid content-start h-screen overflow-auto pl-5 pr-5 ">
            <div id="roles" className="grid grid-cols-3 items-start p-10 gap-y-7 mt-14">
                <div className="h-96 w-96">
                    <img className="object-cover w-full h-full rounded-full"
                         src={'./images/empleador.jpg'} alt="employer"/>
                </div>
                <div className="col-span-2 ">
                    <h3 className="text-2xl text-purple-700 justify-self-center">
                        {t('Register.employer')}
                    </h3>
                    <p className="font-thin text-lg mt-7">
                        {t('Register.employerDesc')} <br/><br/> {t('Register.employerDesc2')}
                        <br/>{t('Register.employerDesc3')}
                    </p>
                    <div className="pb-4 grid col-start-2 col-span-2 mt-7">
                        {/*TODO pagina registerEmployer*/}
                        <Button link="/register/employer" name={t('Register.createEmployer')}/>
                    </div>
                </div>
                <div className="row-start-2 h-96 w-96">
                    <img className="object-cover w-full h-full rounded-full" src={'./images/empl.jpg'}
                         alt="employee"/>
                </div>
                <div className="row-start-2 col-span-2">
                    <h3 className="text-2xl text-purple-700 justify-self-center">
                        {t('Register.employee')}
                    </h3>
                    <p className="font-thin text-lg mt-7">
                        {t('Register.employeeDesc')} <br/><br/> {t('Register.employeeDesc2')}
                        <br/> {t('Register.employerDesc3')}
                    </p>
                    <div className="pb-4 grid col-start-2 col-span-2 mt-7">
                        {/*TODO pagina registerEmployee*/}
                        <Button link="/register/employee" name={t('Register.createEmployee')}/>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Register;