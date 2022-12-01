import EmployeeCard from "../components/EmployeeCard";
import FilterForm from "../components/FilterForm";
import {useEffect, useState} from "react";
import {EmployeeService} from "../service/EmployeeService";
import {useTranslation} from "react-i18next";


export const Explore = () => {

    const [employees, setEmployees]: any = useState()

    const { t } = useTranslation();

    useEffect(() => {
        const algo = async () => {
            const val = await EmployeeService.getEmployees()
            setEmployees(val)
        }
        algo()
    }, [])

    // @ts-ignore
    return (
        <div className="grid grid-cols-8 content-start h-screen overflow-auto pl-5 pr-5">
            <div className="col-span-2 mr-4 flex flex-col items-center">
                <FilterForm setList={setEmployees} type="employee"/>
            </div>
            <div className="col-span-5 mr-5">
                <h1 className={'text-3xl font-bold text-violet-900 mt-2 mb-2 ml-8'}>{t('Explore.employees')}</h1>
                <div className={'flex flex-row-reverse'}>
                    <div className={'flex flex-row'}>
                        <h1 className={'font-semibold mr-3'}>{t('Explore.orderBy')}</h1>
                        <h1 className={'mr-3 hover:text-yellow-300 hover:underline hover:cursor-pointer'}>{t('Explore.popularity')}</h1>
                        <h1 className={'mr-3 hover:text-yellow-300 hover:underline hover:cursor-pointer'}>{t('Explore.experience')}</h1>
                    </div>
                </div>
                {employees && employees.map((employee: Object) => (<EmployeeCard employee={employee}/>))}
            </div>
        </div>
    )
}
export default Explore;