import {RefObject, useEffect, useRef, useState} from "react";
import {useTranslation} from "react-i18next";
import {useNavigate} from "react-router-dom";
import {JobService} from "../service/JobService";
import {EmployeeService} from "../service/EmployeeService";
import {useForm} from "react-hook-form";
import useFormPersist from "react-hook-form-persist";
import EmployeeForm from "../components/EmployeeForm";

const RegisterEmployee = () => {
    const nav = useNavigate();

    const onSubmit = async (data:any, e: any, image:File) => {
        const post = await EmployeeService.registerEmployee(e, data.mail, data.password, data.confirmPassword, data.name, data.location, data.experienceYears, data.availabilities, data.abilities, image!)
        localStorage.clear()
        nav('/employee', {replace: true, state: {id: post, status: -1}})
    }

    return(
        <EmployeeForm onSubmit={onSubmit} from="create" id={-1}/>
    )
}

export default RegisterEmployee