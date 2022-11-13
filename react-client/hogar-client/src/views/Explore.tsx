import EmployeeCard from "../components/EmployeeCard";
import FilterForm from "../components/FilterForm";
import {useEffect, useState} from "react";
import {EmployeeService} from "../service/EmployeeService";
import LoginCard from "../components/LoginCard";
import Loader from "../components/Loader";


export const Explore = () => {

    const [employees, setEmployees]: any = useState()

    useEffect(() => {
        const algo = async () => {
            const val = await EmployeeService.getEmployees()
            setEmployees(val)
        }
        algo()
    }, [])

    /*useEffect(() => {
        employees.forEach((e: any) =>
            EmployeeService.loadImage(e.id.id).then((img) => console.log(URL.createObjectURL(img)))
        )
        // EmployeeService.loadImage(employees).then((img) => setImage(URL.createObjectURL(img)))
    }, [])*/
    // @ts-ignore
    return (
        <div className="grid grid-cols-8 content-start h-screen overflow-auto pl-5 pr-5">
            <div className="col-span-2 mr-4 flex flex-col items-center">
                {employees && <FilterForm setEmployees={setEmployees} />}
            </div>
            <div className="col-span-5 mr-5">
                <h1 className={'text-3xl font-bold text-violet-900 mt-2 mb-2 ml-8'}>Employees Registered_</h1>
                <div className={'flex flex-row-reverse'}>
                    <div className={'flex flex-row'}>
                        <h1 className={'font-semibold mr-3'}>Order by:_</h1>
                        <h1 className={'mr-3 hover:text-yellow-300 hover:underline hover:cursor-pointer'}>Popularity_</h1>
                        <h1 className={'mr-3 hover:text-yellow-300 hover:underline hover:cursor-pointer'}>Experience_</h1>
                    </div>
                </div>
                {employees? employees.map((employee: Object) => (<EmployeeCard employee={employee}/>)) : <Loader />}
            </div>
        </div>
    )
}
export default Explore;