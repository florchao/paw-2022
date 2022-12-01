import {RefObject, useEffect, useRef, useState} from "react";
import {useTranslation} from "react-i18next";
import {useNavigate} from "react-router-dom";
import {JobService} from "../service/JobService";
import {EmployeeService} from "../service/EmployeeService";
import EmployeeForm from "../components/EmployeeForm";

const RegisterEmployee = () => {

    const [mail, setMail] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [name, setName] = useState('');
    const [location, setLocation] = useState('');
    const [experienceYears, setExperienceYears] = useState<number>(0);
    const [availabilities, setAvailability] = useState<string[]>([]);
    const [abilities, setAbilities] = useState<string[]>([]);
    const [image, setImage] = useState<FileList>();


    const [ids, setIds] = useState<any>();

    const nav = useNavigate();

    const {t} = useTranslation();

    const invalidEmail = (email : String) => {
        if( email.length === 0)
            return true
        return !String(email)
            .toLowerCase()
            .match(
                /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
            );
    };

    useEffect(() => {
        JobService.getIds().then((i) => {
            setIds(i)
        });
    }, [])

    const cookRef = useRef<HTMLInputElement>(null)
    const childRef = useRef<HTMLInputElement>(null)
    const elderRef = useRef<HTMLInputElement>(null)
    const specialRef = useRef<HTMLInputElement>(null)
    const petRef = useRef<HTMLInputElement>(null)
    const ironRef = useRef<HTMLInputElement>(null)

    const halfRef = useRef<HTMLInputElement>(null)
    const fullRef = useRef<HTMLInputElement>(null)
    const overRef = useRef<HTMLInputElement>(null)


    const imageRef = useRef<HTMLInputElement>(null)

    const handleAbilityCheck = (ref: RefObject<HTMLInputElement>, ability: string) => {
        if (ref.current?.checked) {
            const newList = abilities.concat(ability);
            setAbilities(newList)
        } else {
            const newList = abilities.filter((a) => a !== ability);
            setAbilities(newList)
        }
    }

    const handleAvailabilityCheck = (ref: RefObject<HTMLInputElement>, availability: string) => {
        if (ref.current?.checked) {
            const newList = availabilities.concat(availability);
            setAvailability(newList)
        } else {
            const newList = availabilities.filter((a) => a !== availability);
            setAvailability(newList)
        }
    }

    const handleSubmit = async (e: any) => {
        const post = await EmployeeService.registerEmployee(e, mail, password, confirmPassword, name, location, experienceYears, availabilities, abilities, image![0])
        nav('/employee', {replace: true, state: {id: post, status: -1}})
    }

    const setColor = (name: string, ref: RefObject<HTMLInputElement>) => {
        let label = document.getElementById(name + "-label")
        if (!ref.current?.checked) {
            if (label != null)
                label.style.backgroundColor = "#c4b5fd";
            window.sessionStorage.setItem(name, "#c4b5fd");
        } else {
            if (label != null)
                label.style.backgroundColor = "#ffffff";
        }
    }

    return (
        <EmployeeForm handleSubmit={handleSubmit} from="create" id={-1}/>
    )
}

export default RegisterEmployee