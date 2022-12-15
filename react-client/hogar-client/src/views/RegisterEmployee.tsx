import {useNavigate} from "react-router-dom";
import {EmployeeService} from "../service/EmployeeService";
import EmployeeForm from "../components/EmployeeForm";

const RegisterEmployee = () => {
    const nav = useNavigate();

    const onSubmit = async (data:any, e: any, image:File) => {
        const post = await EmployeeService.registerEmployee(e, data.mail, data.password, data.confirmPassword, data.name, data.location, data.experienceYears, data.hourlyFee, data.availabilities, data.abilities, image!)
        localStorage.removeItem("employeeForm")
        console.log(post.value)
        nav('/employee', {replace: true, state: {self: post.value, status: -1}})
    }

    return(
        <EmployeeForm onSubmit={onSubmit} from="create" self={""}/>
    )
}

export default RegisterEmployee