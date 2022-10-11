
import EmployeeCard from "../components/EmployeeCard";
import FilterForm from "../components/FilterForm";
import useFetch from "../service/useFetch";


export const Explore = () => {
    const { data: employees, error, isPending } = useFetch('http://localhost:8080/api/employees')
    // @ts-ignore
    return (
        <div className="grid grid-cols-7 content-start h-screen overflow-auto pl-5 pr-5">
            <FilterForm/>
            <div className="col-span-6 mr-5">
                {employees && employees.map((employee: any) => (<EmployeeCard employee={employee}/>))}
                {error && <p>ERROR</p>}
            </div>
        </div>
    )
}
export default Explore;