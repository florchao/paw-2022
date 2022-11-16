import EmployeeCard from "../components/EmployeeCard";
import FilterForm from "../components/FilterForm";
import {useEffect, useState} from "react";
import {EmployeeService} from "../service/EmployeeService";
import LoginCard from "../components/LoginCard";
import Loader from "../components/Loader";
import {string} from "zod";
import exp from "constants";


export const Explore = () => {

    const [employees, setEmployees]: any = useState()
    const [experience, setExperience]: any = useState()
    const [name, setName]: any = useState()
    const [location, setLocation]: any = useState()
    const [abilities, setAbilites]: any = useState()
    const [availability, setAvailabilty]: any = useState()
    const [order, setOrder]: any = useState()

    useEffect(() => {
        const algo = async () => {
            const val = await EmployeeService.getEmployees()
            setEmployees(val)
        }
        algo()
    }, [])

    const orderCriteriaHandler = async (order: string) => {
        setOrder(string)
        const filteredEmployees = await EmployeeService.getFilteredEmployees(experience, name, location, abilities, availability, 0, 5, order)
        setEmployees(filteredEmployees)
    }

    const applyFilters = async (
        experience: number,
        name?: string,
        location?: string,
        abilities?: string,
        availability?: string
    ) => {
        setExperience(experience)
        setName(name)
        setLocation(location)
        setAbilites(abilities)
        setAvailabilty(availability)
        const filteredEmployees = await EmployeeService.getFilteredEmployees(experience, name, location, abilities, availability, 0, 5, order)
        setEmployees(filteredEmployees)
    }

    return (
        <div className="grid grid-cols-8 content-start h-screen overflow-auto pl-5 pr-5">
            <div className="col-span-2 mr-4 flex flex-col items-center">
                {employees && <FilterForm applyFilters={applyFilters} />}
            </div>
            <div className="col-span-5 mr-5">
                <h1 className={'text-3xl font-bold text-violet-900 mt-2 mb-2 ml-8'}>Employees Registered_</h1>
                <div className={'flex flex-row-reverse'}>
                    <div className={'flex flex-row'}>
                        <h1 className={'font-semibold mr-3'}>Order by:_</h1>
                        <h1 onClick={() => orderCriteriaHandler('rating')} className={'mr-3 hover:text-yellow-300 hover:underline hover:cursor-pointer'}>Popularity_</h1>
                        <h1 onClick={() => orderCriteriaHandler('experienceYears')} className={'mr-3 hover:text-yellow-300 hover:underline hover:cursor-pointer'}>Experience_</h1>
                    </div>
                </div>
                {employees? employees.map((employee: Object) => (<EmployeeCard employee={employee}/>)) : <Loader />}
            </div>
        </div>
    )
}
export default Explore;