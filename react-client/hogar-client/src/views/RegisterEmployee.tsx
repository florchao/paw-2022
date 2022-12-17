import {useNavigate} from "react-router-dom";
import {EmployeeService} from "../service/EmployeeService";
import EmployeeForm from "../components/EmployeeForm";
import {UserService} from "../service/UserService";

const RegisterEmployee = () => {
    const nav = useNavigate();

    const onSubmit = async (data:any, e: any, image:File) => {
        const post = await EmployeeService.registerEmployee(e, data.mail, data.password, data.confirmPassword, data.name, data.location, data.experienceYears, data.hourlyFee, data.availabilities, data.abilities, image!)

        localStorage.removeItem("employeeForm")
        localStorage.removeItem("imgEmployeeForm")

        if (post.status == 201) {
            const result = await UserService.getUser(e, data.mail, data.password)

            if (result.status === 200) {
                let body = await result.json()
                localStorage['hogar-role'] = body.role
                localStorage['hogar-uid'] = body.uid
                let authHeader = result.headers.get('Authorization')
                localStorage['hogar-jwt'] = authHeader?.slice(7)
            }
            let body = await post.json()
            nav('/profile', {replace: true, state: {self: body.value, status: -1}})
            window.location.reload()
        }


    }

    return(
        <EmployeeForm onSubmit={onSubmit} from="create" self={""} onEdit={false}/>
    )
}

export default RegisterEmployee