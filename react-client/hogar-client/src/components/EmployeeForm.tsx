import {FormEventHandler, RefObject, useEffect, useRef, useState} from "react";
import {useNavigate} from "react-router-dom";
import {useTranslation} from "react-i18next";
import {JobService} from "../service/JobService";
import {EmployeeService} from "../service/EmployeeService";
import {UserService} from "../service/UserService";

export const EmployeeForm = ({handleSubmit, from, id}: {handleSubmit: FormEventHandler<HTMLFormElement>, from: string, id:number}) => {

    const [mail, setMail] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [name, setName] = useState<string>('');
    const [location, setLocation] = useState('');
    const [experienceYears, setExperienceYears] = useState<number>(0);
    const [availabilities, setAvailability] = useState<string[]>([]);
    const [abilities, setAbilities] = useState<string[]>([]);
    const [image, setImage] = useState<File>();

    const [ids, setIds] = useState<any>();

    const {t} = useTranslation();

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
    useEffect(() => {
        if(from == "edit") {
            EmployeeService.getEmployee(id).then((e) => {
                setName(e.name)
                localStorage.setItem("name",name);
                setLocation(e.location)
                localStorage.setItem("location",location);
                setAbilities(e.abilitiesArr)
                localStorage.setItem("abilities",abilities.toString());
                setAvailability(e.availabilityArr)
                localStorage.setItem("availabilities",availabilities.toString());
                setExperienceYears(e.experienceYears)
                localStorage.setItem("experienceYears",experienceYears.toString());
            });
        }
    }, [])

    useEffect(() => {
        if(from == "edit") {
            UserService.loadImage(id).then((i) => {
                setImage(new File([i], "name"))
            });
        }
    }, [])


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
        <div className="h-screen overflow-auto pb-5">
            <form onSubmit={handleSubmit}>
                <div className="grid grid-cols-6">
                    <div className="grid grid-row-4 col-span-4 col-start-2 mt-20 ">
                        <p className="text-3xl font-semibold text-violet-900 mb-4 mt-4 text-center">
                            {t('RegisterEmployee.title')}
                            {/*<spring:message code="createProfile.formTitle"/>*/}
                        </p>
                        <div className="bg-gray-200 rounded-3xl p-5 shadow-2xl">
                            <div className="grid grid-cols-6 gap-6">
                                <div className="row-span-4 col-span-2 m-6">
                                    <div className="overflow-hidden bg-gray-100 rounded-full">
                                        <img id="picture"
                                             src={image ? URL.createObjectURL(image) : '/images/user.png'}
                                             alt="user pic"/>
                                    </div>
                                    <label htmlFor="image-input" id="image-label"
                                           className="mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer">
                                        {t('RegisterEmployee.image')}
                                    </label>
                                    <input id="image-input"
                                           ref={imageRef}
                                           type="file"
                                           accept="image/png, image/jpeg"
                                           required
                                           onInvalid={e => (e.target as HTMLInputElement).setCustomValidity(t('RegisterEmployee.imageError'))}
                                           onInput={e => (e.target as HTMLInputElement).setCustomValidity("")}
                                           onChange={(e) => {
                                               if (e.target.files != null)
                                                   setImage(e.target.files[0])
                                           }
                                           } style={{visibility: "hidden"}}/>
                                    {(!image) &&
                                        <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('RegisterEmployee.imageError')}</p>
                                    }                                </div>
                                <div className="ml-3 col-span-3 col-start-4 w-4/5 justify-self-center">
                                    <label htmlFor="name"
                                           className="block mb-2 text-sm font-medium text-gray-900 ">
                                        {t('RegisterEmployee.name')}
                                    </label>
                                    <input type="text"
                                           required
                                           value={name}
                                           maxLength={100}
                                           minLength={1}
                                           onInvalid={e => (e.target as HTMLInputElement).setCustomValidity(t('RegisterEmployee.nameError'))}
                                           onInput={e => (e.target as HTMLInputElement).setCustomValidity("")}
                                           onChange={(e) => {
                                               setName(e.target.value)
                                               localStorage.setItem("name",e.target.value);
                                           }}
                                           className="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-blue-500 focus:border-violet-500"/>
                                    {(name.length < 1 || name.length > 100) &&
                                        <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('RegisterEmployee.nameError')}</p>
                                    }
                                </div>
                                {from == "create" &&
                                    <div className="ml-3 col-span-3 col-start-4 w-4/5 justify-self-center">
                                        <label htmlFor="mail"
                                               className="text-sm font-medium text-gray-900">
                                            {t('RegisterEmployee.email')}
                                        </label>
                                        <input id="mail"
                                               type="mail"
                                               required
                                               onInvalid={e => (e.target as HTMLInputElement).setCustomValidity(t('RegisterEmployee.emailError'))}
                                               onInput={e => (e.target as HTMLInputElement).setCustomValidity("")}
                                               onChange={(e) => {
                                                   setMail(e.target.value)
                                                   localStorage.setItem("mail",e.target.value);
                                               }}
                                               className="col-span-5 block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-blue-500 focus:border-violet-500"/>
                                        {(invalidEmail(mail)) &&
                                            <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('RegisterEmployee.emailError')}</p>
                                        }
                                    </div>
                                }
                                {from == "create" &&
                                    <div className="ml-3 col-span-3 col-start-4 w-4/5 justify-self-center">
                                        <label htmlFor="password"
                                               className="text-sm font-medium text-gray-900">
                                            {t('RegisterEmployee.password')}
                                        </label>
                                        <input id="password" type="password"
                                               required
                                               onInvalid={e => (e.target as HTMLInputElement).setCustomValidity(t('RegisterEmployee.passwordError'))}
                                               onInput={e => (e.target as HTMLInputElement).setCustomValidity("")}
                                               onChange={(e) => {
                                                   setPassword(e.target.value)
                                                   localStorage.setItem("password",e.target.value);
                                               }}
                                               className=" col-span-5 block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-blue-500 focus:border-violet-500"/>
                                        {(password.length < 1) &&
                                            <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('RegisterEmployee.passwordError')}</p>
                                        }
                                        {(password !== confirmPassword) &&
                                            <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('RegisterEmployee.passwordsError')}</p>
                                        }
                                    </div>
                                }
                                {from == "create" &&
                                    <div className="ml-3 col-span-3 col-start-4 w-4/5 justify-self-center">
                                        <label htmlFor="confirmPassword"
                                               className="text-sm font-medium text-gray-900">
                                            {t('RegisterEmployee.confirmPassword')}
                                        </label>
                                        <input id="confirmPassword" type="password"
                                               required
                                               onInvalid={e => (e.target as HTMLInputElement).setCustomValidity(t('RegisterEmployee.passwordError'))}
                                               onInput={e => (e.target as HTMLInputElement).setCustomValidity("")}
                                               onChange={(e) => {
                                                   setConfirmPassword(e.target.value)
                                                   localStorage.setItem("confirmPassword",e.target.value);
                                               }}
                                               className=" col-span-5 block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-blue-500 focus:border-violet-500"/>
                                        {(confirmPassword.length < 1) &&
                                            <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('RegisterEmployee.passwordError')}</p>
                                        }
                                        {(password !== confirmPassword) &&
                                            <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('RegisterEmployee.passwordsError')}</p>
                                        }
                                    </div>
                                }
                                <div className={from == "edit"? "ml-3 col-span-3 w-4/5 col-start-4 justify-self-center" : "ml-3 col-span-3 w-4/5 justify-self-center"}>
                                    <label htmlFor="location"
                                           className="block mb-2 text-sm font-medium text-gray-900 ">
                                        {t('RegisterEmployee.location')}
                                    </label>
                                    <input type="text"
                                           required
                                           value={location}
                                           maxLength={100}
                                           minLength={1}
                                           onInvalid={e => (e.target as HTMLInputElement).setCustomValidity(t('RegisterEmployee.locationError'))}
                                           onInput={e => (e.target as HTMLInputElement).setCustomValidity("")}
                                           onChange={(e) => {
                                               setLocation(e.target.value)
                                               localStorage.setItem("location",e.target.value);
                                           }}
                                           className="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-violet-500 focus:border-violet-500"/>
                                    {(location.length < 1 || location.length > 100) &&
                                        <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('RegisterEmployee.locationError')}</p>
                                    }
                                </div>
                                <div className="ml-3 col-span-3 col-start-4 w-4/5 justify-self-center">
                                    <label className="block mb-2 text-sm font-medium text-gray-900 ">
                                        {t('RegisterEmployee.experienceYears')}
                                    </label>
                                    <input type="number" id="expYears"
                                           required
                                           value={experienceYears}
                                           maxLength={100}
                                           minLength={1}
                                           onInvalid={e => (e.target as HTMLInputElement).setCustomValidity(t('RegisterEmployee.expYearsError'))}
                                           onInput={e => (e.target as HTMLInputElement).setCustomValidity("")}
                                           onChange={(e) => {
                                               setExperienceYears(e.target.valueAsNumber)
                                               localStorage.setItem("expYears",e.target.value);
                                           }}
                                           className="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-violet-500 focus:border-violet-500"/>
                                    {(experienceYears < 1 || experienceYears > 100) &&
                                        <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('RegisterEmployee.expYearsError')}</p>
                                    }
                                </div>
                            </div>
                            <div>
                                <h1 className="pb-3 pt-3 font-bold">
                                    {t('RegisterEmployee.abilities')}
                                </h1>
                            </div>
                            {ids &&
                                <div className="flex flex-wrap ml-8">
                                    <div className="mb-8">
                                        <label htmlFor="cocinar-cb" id="cocinar-label"
                                               onClick={(e) => {
                                                   setColor('cocinar', cookRef)
                                               }}
                                               className="mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer">
                                            {t('Abilities.cook')}
                                        </label>
                                        <input type="checkbox" ref={cookRef}
                                               onChange={(e) => handleAbilityCheck(cookRef, e.target.value)}
                                               id="cocinar-cb" value={ids.abilities[0]}
                                               style={{visibility: "hidden"}}/>
                                    </div>
                                    <div>
                                        <label htmlFor="planchar-cb" id="planchar-label"
                                               onClick={() => setColor('planchar', ironRef)}
                                               className="mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer">
                                            {t('Abilities.iron')}
                                        </label>
                                        <input type="checkbox" ref={ironRef}
                                               onChange={(e) => handleAbilityCheck(ironRef, e.target.value)}
                                               id="planchar-cb" value={ids.abilities[1]}
                                               style={{visibility: "hidden"}}/>
                                    </div>
                                    <div>
                                        <label htmlFor="menores-cb" id="menores-label"
                                               onClick={() => setColor('menores', childRef)}
                                               className="mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer">
                                            {t('Abilities.child')}
                                        </label>
                                        <input type="checkbox" ref={childRef}
                                               onChange={(e) => handleAbilityCheck(childRef, e.target.value)}
                                               id="menores-cb" value={ids.abilities[2]}
                                               style={{visibility: "hidden"}}/>
                                    </div>
                                    <div>
                                        <label htmlFor="mayores-cb" id="mayores-label"
                                               onClick={() => setColor('mayores', elderRef)}
                                               className="mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer">
                                            {t('Abilities.older')}
                                        </label>
                                        <input type="checkbox" ref={elderRef}
                                               onChange={(e) => handleAbilityCheck(elderRef, e.target.value)}
                                               id="mayores-cb" value={ids.abilities[3]}
                                               style={{visibility: "hidden"}}/>
                                    </div>
                                    <div>
                                        <label htmlFor="especiales-cb" id="especiales-label"
                                               onClick={() => setColor('especiales', specialRef)}
                                               className="mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer">
                                            {t('Abilities.specialNeeds')}
                                        </label>
                                        <input type="checkbox" ref={specialRef}
                                               onChange={(e) => handleAbilityCheck(specialRef, e.target.value)}
                                               id="especiales-cb" value={ids.abilities[4]}
                                               style={{visibility: "hidden"}}/>
                                    </div>
                                    <div>
                                        <label htmlFor="mascotas-cb" id="mascotas-label"
                                               onClick={() => setColor('mascotas', petRef)}
                                               className="mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer">
                                            {t('Abilities.pets')}
                                        </label>
                                        <input type="checkbox" ref={petRef}
                                               onChange={(e) => handleAbilityCheck(petRef, e.target.value)}
                                               id="mascotas-cb" value={ids.abilities[5]}
                                               style={{visibility: "hidden"}}/>
                                    </div>
                                </div>
                            }
                            {abilities.length < 1 &&
                                <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('RegisterEmployee.abilitiesError')}</p>
                            }
                            <div>
                                <h1 className="pb-3 pt-3 font-bold">
                                    {t('RegisterEmployee.availability')}
                                </h1>
                            </div>
                            {ids &&
                                <div className="flex flex-wrap ml-8">
                                    <div className="mb-8">
                                        <label htmlFor="media-cb" id="media-label"
                                               onClick={(e) => {
                                                   setColor('media', halfRef)
                                               }}
                                               className="mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer">
                                            {t('Availabilities.half')}
                                        </label>
                                        <input type="checkbox" ref={halfRef}
                                               onChange={(e) => handleAvailabilityCheck(halfRef, e.target.value)}
                                               id="media-cb" value={ids.availabilities[0]}
                                               style={{visibility: "hidden"}}/>
                                    </div>
                                    <div>
                                        <label htmlFor="completa-cb" id="completa-label"
                                               onClick={() => setColor('completa', fullRef)}
                                               className="mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer">
                                            {t('Availabilities.complete')}
                                        </label>
                                        <input type="checkbox" ref={fullRef}
                                               onChange={(e) => handleAvailabilityCheck(fullRef, e.target.value)}
                                               id="completa-cb" value={ids.availabilities[1]}
                                               style={{visibility: "hidden"}}/>
                                    </div>
                                    <div>
                                        <label htmlFor="cama-cb" id="cama-label"
                                               onClick={() => setColor('cama', overRef)}
                                               className="mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer">
                                            {t('Availabilities.bed')}
                                        </label>
                                        <input type="checkbox" ref={overRef}
                                               onChange={(e) => handleAvailabilityCheck(overRef, e.target.value)}
                                               id="cama-cb" value={ids.availabilities[2]}
                                               style={{visibility: "hidden"}}/>
                                    </div>
                                </div>
                            }
                            {availabilities.length < 1 &&
                                <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('RegisterEmployee.availabilityError')}</p>
                            }                            <div className="mt-5 col-start-2 col-span-4 row-span-3">
                            <button type="submit"
                                    className="text-lg w-full focus:outline-none text-violet-900 bg-purple-900 bg-opacity-30 hover:bg-purple-900 hover:bg-opacity-50 font-small rounded-lg text-sm px-5 py-2.5">
                                {t('RegisterEmployee.button')}
                            </button>
                        </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    )
}

export default EmployeeForm