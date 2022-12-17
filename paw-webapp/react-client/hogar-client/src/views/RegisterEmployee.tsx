import {useNavigate} from "react-router-dom";
import {EmployeeService} from "../service/EmployeeService";
import EmployeeForm from "../components/EmployeeForm";
import {UserService} from "../service/UserService";
import {useState} from "react";
import {useTranslation} from "react-i18next";

const RegisterEmployee = () => {
    const nav = useNavigate();
    const [registerEmployeeError, setRegisterEmployeeError]: any = useState()
    const {t} = useTranslation();


    const onSubmit = async (data: any, e: any, image: File) => {
        const post = await EmployeeService.registerEmployee(e, data.mail, data.password, data.confirmPassword, data.name, data.location, data.experienceYears, data.hourlyFee, data.availabilities, data.abilities, image!)
        if (post.status == 500 || post.status == 400) {
            setRegisterEmployeeError(true)
        }

        if (post.status == 201) {
            setRegisterEmployeeError(false)
            localStorage.removeItem("employeeForm")
            localStorage.removeItem("imgEmployeeForm")
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

    return (
        <div className="h-screen overflow-auto pb-5">

        {registerEmployeeError &&
        <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('EmployeeForm.emailError')}</p>
        }
        <EmployeeForm onSubmit={onSubmit} from="create" self={""} onEdit={false} errorFromRequest={registerEmployeeError}/>
        </div>
    )
}

export default RegisterEmployee