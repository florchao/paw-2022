import {EmployeeService} from "../service/EmployeeService";
import {useLocation, useNavigate} from "react-router-dom";
import EmployeeForm from "../components/EmployeeForm";

const EditEmployee = () => {

    const { self }  = useLocation().state

    const nav = useNavigate();

    const handleSubmit = async (data: any, e: any, image:File) => {
        const edit = await EmployeeService.editEmployee(e, self, data.name, data.location, data.experienceYears, data.hourlyFee, data.availabilities, data.abilities, image!)
        localStorage.removeItem("employeeForm")
        localStorage.removeItem("imgEmployeeForm")

        nav('/profile', {replace: true, state: {id: edit, status: -1}})
    }

    return(
        <EmployeeForm onSubmit={handleSubmit} from="edit" self={self}/>
    )

}

export default EditEmployee