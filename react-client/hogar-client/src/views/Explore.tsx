import EmployeeCard from "../components/EmployeeCard";
import FilterForm from "../components/FilterForm";
import useFetch from "../service/useFetch";
import {useEffect, useState} from "react";
import {EmployeeService} from "../service/EmployeeService";
import {List} from "postcss/lib/list";


export const Explore = () => {

    const [employees, setEmployees]: any = useState()

    useEffect(() => {
        const algo = async () => {
            console.log("algoo")
            const val = await EmployeeService.getFilteredEmployees()
            console.log(val)
            setEmployees(val)
        }
        algo()
    })
    // @ts-ignore
    return (
        <div className="grid grid-cols-7 content-start h-screen overflow-auto pl-5 pr-5">
            <FilterForm/>
            <div className="col-span-6 mr-5">
                {employees && employees.map((employee: Object) => (<EmployeeCard employee={employee}/>))}
            </div>
        </div>
    )
}
export default Explore;