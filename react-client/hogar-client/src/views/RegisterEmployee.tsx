import {RefObject, useEffect, useRef, useState} from "react";
import {useTranslation} from "react-i18next";
import {useNavigate} from "react-router-dom";
import {JobService} from "../service/JobService";
import {EmployeeService} from "../service/EmployeeService";

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

    const { t } = useTranslation();

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

    const handleAbilityCheck = (ref: RefObject<HTMLInputElement>, ability:string) => {
        if (ref.current?.checked) {
            const newList = abilities.concat(ability);
            setAbilities(newList)
        } else {
            const newList = abilities.filter((a) => a !== ability);
            setAbilities(newList)
        }
    }

    const handleAvailabilityCheck = (ref: RefObject<HTMLInputElement>, availability:string) => {
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
            if(label != null)
                label.style.backgroundColor = "#c4b5fd";
            window.sessionStorage.setItem(name, "#c4b5fd");
        } else {
            if(label != null)
                label.style.backgroundColor = "#ffffff";
        }
    }

    return (
        <div className="h-screen overflow-auto pb-5">
            <form onSubmit={handleSubmit}>
                <div className="grid grid-cols-6">
                    <div className="grid grid-row-4 col-span-4 col-start-2 mt-20 ">
                        <p className="text-3xl font-semibold text-violet-900 mb-4 mt-4 text-center">
                            Ttile_
                            {/*<spring:message code="createProfile.formTitle"/>*/}
                        </p>
                        <div className="bg-gray-200 rounded-3xl p-5 shadow-2xl">
                            <div className="grid grid-cols-6 gap-6">
                                <div className="row-span-4 col-span-2 m-6">
                                    <div className="overflow-hidden bg-gray-100 rounded-full">
                                        <img id="picture" src={image? URL.createObjectURL(image[0]): '/images/user.png'} alt="user pic"/>
                                    </div>
                                    <label htmlFor="image-input" id="image-label"
                                           className="mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer">
                                        Choose Image_
                                        {/*<spring:message code="employeeForm.insertImage"/>*/}
                                    </label>
                                    <input id="image-input" ref={imageRef} type="file" accept="image/png, image/jpeg" onChange={(e) => {
                                            if (e.target.files != null)
                                                setImage(e.target.files)
                                        }
                                    } style={{visibility: "hidden"}}/>
                                    {/*<form:errors path="image" element="p" cssStyle="color:red;margin-left: 10px"/>*/}
                                </div>
                                <div className="ml-3 col-span-3 col-start-4 w-4/5 justify-self-center">
                                    <label htmlFor="name"
                                           className="block mb-2 text-sm font-medium text-gray-900 ">
                                        Name_
                                        {/*<spring:message code="employeeForm.label.name"/>*/}
                                    </label>
                                    <input type="text" onChange={(e) => setName(e.target.value)}
                                                className="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-blue-500 focus:border-violet-500"/>
                                    {/*<form:errors path="name" element="p" cssStyle="color:red"/>*/}
                                </div>
                                <div className="ml-3 col-span-3 col-start-4 w-4/5 justify-self-center">
                                    <label htmlFor="mail"
                                                className="text-sm font-medium text-gray-900">
                                        Mail_
                                        {/*<spring:message code="register.mail"/>*/}
                                    </label>
                                    <input id="mail" type="mail" onChange={(e) => setMail(e.target.value)}
                                                className="col-span-5 block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-blue-500 focus:border-violet-500"/>
                                    {/*<form:errors path="mail" element="p" cssStyle="color:red" className="col-start-2 col-span-5"/>*/}
                                </div>
                                <div className="ml-3 col-span-3 col-start-4 w-4/5 justify-self-center">
                                    <label htmlFor="password"
                                                className="text-sm font-medium text-gray-900">
                                        Password_
                                        {/*<spring:message code="register.password"/>*/}
                                    </label>
                                    <input id="password" type="password" onChange={(e) => setPassword(e.target.value)}
                                                className=" col-span-5 block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-blue-500 focus:border-violet-500"/>
                                    {/*<form:errors path="password" element="p" cssStyle="color:red" className="col-start-2 col-span-5"/>*/}
                                </div>
                                <div className="ml-3 col-span-3 col-start-4 w-4/5 justify-self-center">
                                    <label htmlFor="confirmPassword"
                                                className="text-sm font-medium text-gray-900">
                                        PassConf_
                                        {/*<spring:message code="register.confirmPassword"/>*/}
                                    </label>
                                    <input id="confirmPassword" type="password" onChange={(e) => setConfirmPassword(e.target.value)}
                                                className=" col-span-5 block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-blue-500 focus:border-violet-500"/>
                                    {/*<form:errors path="confirmPassword" element="p" cssStyle="color:red" className="col-start-2 col-span-5"/>*/}
                                    {/*<form:errors element="p" cssStyle="color:red" className="col-start-2 col-span-5"/>*/}
                                </div>
                                <div className="ml-3 col-span-3 w-4/5 justify-self-center">
                                    <label  htmlFor="location"
                                                className="block mb-2 text-sm font-medium text-gray-900 ">
                                        Location_
                                        {/*<spring:message code="employeeForm.label.location"/>*/}
                                    </label>
                                    <input type="text" onChange={(e) => setLocation(e.target.value)}
                                                className="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-violet-500 focus:border-violet-500"/>
                                    {/*<form:errors path="location" element="p" cssStyle="color:red"/>*/}
                                </div>
                                <div className="ml-3 col-span-3 col-start-4 w-4/5 justify-self-center">
                                    <label className="block mb-2 text-sm font-medium text-gray-900 ">
                                        Years_
                                        {/*<spring:message code="employeeForm.label.experienceYears"/>*/}
                                    </label>
                                    <input type="number" id="expYears" onChange={(e) => setExperienceYears(e.target.valueAsNumber)}
                                                className="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-violet-500 focus:border-violet-500"/>
                                    {/*<form:errors path="experienceYears" element="p" cssStyle="color:red"/>*/}
                                </div>
                            </div>
                            <div>
                                <h1 className="pb-3 pt-3 font-bold">
                                    Abilities
                                    {/*<spring:message code="employeeForm.abilities"/>*/}
                                </h1>
                            </div>
                            {ids &&
                                <div className="flex flex-wrap ml-8">
                                    <div className="mb-8">
                                        <label htmlFor="cocinar-cb" id="cocinar-label"
                                               onClick={(e) => {setColor('cocinar', cookRef)}}
                                               className="mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer">
                                            {t('CreateJob.abilities_cook')}
                                        </label>
                                        <input type="checkbox" ref={cookRef} onChange={ (e) => handleAbilityCheck(cookRef, e.target.value)} id="cocinar-cb" value={ids.abilities[0]}
                                               style={{visibility: "hidden"}}/>
                                    </div>
                                    <div>
                                        <label htmlFor="planchar-cb" id="planchar-label"
                                               onClick={() => setColor('planchar', ironRef)}
                                               className="mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer">
                                            {t('CreateJob.abilities_iron')}
                                        </label>
                                        <input type="checkbox" ref={ironRef} onChange={(e) => handleAbilityCheck(ironRef, e.target.value)} id="planchar-cb" value={ids.abilities[1]}
                                               style={{visibility: "hidden"}}/>
                                    </div>
                                    <div>
                                        <label htmlFor="menores-cb" id="menores-label"
                                               onClick={() => setColor('menores', childRef)}
                                               className="mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer">
                                            {t('CreateJob.abilities_child')}
                                        </label>
                                        <input type="checkbox" ref={childRef} onChange={(e) => handleAbilityCheck(childRef, e.target.value)} id="menores-cb" value={ids.abilities[2]}
                                               style={{visibility: "hidden"}}/>
                                    </div>
                                    <div>
                                        <label htmlFor="mayores-cb" id="mayores-label"
                                               onClick={() => setColor('mayores', elderRef)}
                                               className="mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer">
                                            {t('CreateJob.abilities_older')}
                                        </label>
                                        <input type="checkbox" ref={elderRef} onChange={(e) => handleAbilityCheck(elderRef, e.target.value)} id="mayores-cb" value={ids.abilities[3]}
                                               style={{visibility: "hidden"}}/>
                                    </div>
                                    <div>
                                        <label htmlFor="especiales-cb" id="especiales-label"
                                               onClick={() => setColor('especiales', specialRef)}
                                               className="mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer">
                                            {t('CreateJob.abilities_specialNeeds')}
                                        </label>
                                        <input type="checkbox" ref={specialRef} onChange={(e) => handleAbilityCheck(specialRef, e.target.value)} id="especiales-cb" value={ids.abilities[4]}
                                               style={{visibility: "hidden"}}/>
                                    </div>
                                    <div>
                                        <label htmlFor="mascotas-cb" id="mascotas-label"
                                               onClick={() => setColor('mascotas', petRef)}
                                               className="mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer">
                                            {t('CreateJob.abilities_pets')}
                                        </label>
                                        <input type="checkbox" ref={petRef} onChange={(e) => handleAbilityCheck(petRef, e.target.value)} id="mascotas-cb" value={ids.abilities[5]}
                                               style={{visibility: "hidden"}}/>
                                    </div>
                                </div>
                            }
                            {/*<form:errors path="abilities" element="p" cssStyle="color: red; margin-top: 1.25rem"/>*/}
                            <div>
                                <h1 className="pb-3 pt-3 font-bold">
                                    Avail_
                                    {/*<spring:message code="employeeForm.availability"/>*/}
                                </h1>
                            </div>
                            {ids &&
                                <div className="flex flex-wrap ml-8">
                                    <div className="mb-8">
                                        <label htmlFor="media-cb" id="media-label"
                                               onClick={(e) => {setColor('media', halfRef)}}
                                               className="mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer">
                                            media_
                                        </label>
                                        <input type="checkbox" ref={halfRef} onChange={ (e) => handleAvailabilityCheck(halfRef, e.target.value)} id="media-cb" value={ids.availabilities[0]}
                                               style={{visibility: "hidden"}}/>
                                    </div>
                                    <div>
                                        <label htmlFor="completa-cb" id="completa-label"
                                               onClick={() => setColor('completa', fullRef)}
                                               className="mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer">
                                            completa_
                                        </label>
                                        <input type="checkbox" ref={fullRef} onChange={(e) => handleAvailabilityCheck(fullRef, e.target.value)} id="completa-cb" value={ids.availabilities[1]}
                                               style={{visibility: "hidden"}}/>
                                    </div>
                                    <div>
                                        <label htmlFor="cama-cb" id="cama-label"
                                               onClick={() => setColor('cama', overRef)}
                                               className="mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer">
                                            cama_
                                        </label>
                                        <input type="checkbox" ref={overRef} onChange={(e) => handleAvailabilityCheck(overRef, e.target.value)} id="cama-cb" value={ids.availabilities[2]}
                                               style={{visibility: "hidden"}}/>
                                    </div>
                                </div>
                            }
                            {/*<form:errors path="availability" element="p" cssStyle="color: red; margin-top: 1.25rem"/>*/}
                            <div className="mt-5 col-start-2 col-span-4 row-span-3">
                                <button type="submit"
                                        className="text-lg w-full focus:outline-none text-violet-900 bg-purple-900 bg-opacity-30 hover:bg-purple-900 hover:bg-opacity-50 font-small rounded-lg text-sm px-5 py-2.5">
                                    {/*<spring:message code="employeeForm.button"/>*/}
                                    utton_
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    )
}

export default RegisterEmployee