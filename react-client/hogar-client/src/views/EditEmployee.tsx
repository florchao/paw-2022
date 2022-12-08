import {RefObject, useEffect, useRef, useState} from "react";
import {JobService} from "../service/JobService";
import {EmployeeService} from "../service/EmployeeService";
import {useLocation, useNavigate} from "react-router-dom";
import {useTranslation} from "react-i18next";
import EmployeeForm from "../components/EmployeeForm";

const EditEmployee = () => {

    const { id }  = useLocation().state

    const nav = useNavigate();

    const handleSubmit = async (data: any, e: any, image:File) => {
        const edit = await EmployeeService.editEmployee(e, id, data.name, data.location, data.experienceYears, data.availabilities, data.abilities, image!)
        localStorage.clear()
        nav('/employee', {replace: true, state: {id: edit, status: -1}})
    }

    return(
        <EmployeeForm onSubmit={handleSubmit} from="edit" id={id}/>
        // <div className="h-screen overflow-auto pb-5">
        //     <form onSubmit={handleSubmit}>
        //         <div className="grid grid-cols-6">
        //             <div className="grid grid-row-4 col-span-4 col-start-2 mt-20 ">
        //                 <div className="bg-gray-200 rounded-3xl p-5 shadow-2xl">
        //                     <div className="grid grid-cols-5 gap-6">
        //                         <div className="row-span-4 col-span-2 m-6 justify-items-center">
        //                             <img className="col-span-1 mb-3 w-48 h-48 rounded-full shadow-lg object-cover"
        //                                  id="picture" src='/images/user.png' alt="profile pic"/>
        //                             <label>
        //                                 Image
        //                                 {/*<spring:message code="employeeForm.insertImage"/>*/}
        //                             </label>
        //                             {/*<input id="file" type="file" path="image" accept="image/png, image/jpeg"*/}
        //                             {/*            onchange="loadFile(event);"/>*/}
        //                             {/*<form:errors path="image" element="p" cssStyle="color:red;margin-left: 10px"/>*/}
        //                         </div>
        //                         <div className="ml-3 col-span-3 col-start-3 w-4/5 justify-self-center">
        //                             <label htmlFor="name"
        //                                         className="block mb-2 text-sm font-medium text-gray-900 ">
        //                                 Name
        //                                 {/*<spring:message code="employeeForm.label.name"/>*/}
        //                             </label>
        //                             <input type="text" defaultValue={name}
        //                                         className="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-blue-500 focus:border-violet-500"/>
        //                             {/*<form:errors path="name" element="p" cssStyle="color:red"/>*/}
        //                         </div>
        //                         <div className="ml-3 col-span-3 w-4/5 justify-self-center">
        //                             <label htmlFor="location"
        //                                         className="block mb-2 text-sm font-medium text-gray-900 ">
        //                                 location
        //                                 {/*<spring:message code="employeeForm.label.location"/>*/}
        //                             </label>
        //                             <input type="text" defaultValue={location}
        //                                         className="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-violet-500 focus:border-violet-500"/>
        //                             {/*<form:errors path="location" element="p" cssStyle="color:red"/>*/}
        //                         </div>
        //                         <div className="ml-3 col-span-3 w-4/5 justify-self-center">
        //                             <label htmlFor="expYears"
        //                                         className="block mb-2 text-sm font-medium text-gray-900 ">
        //                                 Years
        //                                 {/*<spring:message code="employeeForm.label.experienceYears"/>*/}
        //                             </label>
        //                             <input type="number" id="expYears" defaultValue={location}
        //                                         className="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-violet-500 focus:border-violet-500"/>
        //                             {/*<form:errors path="experienceYears" element="p" cssStyle="color:red"/>*/}
        //                         </div>
        //                     </div>
        //                     <div>
        //                         <h1 className="pb-3 pt-3 font-bold">
        //                             Abilities
        //                             {/*<spring:message code="employeeForm.abilities"/>*/}
        //                         </h1>
        //                     </div>
        //                     {ids &&
        //                         <div className="flex flex-wrap ml-8">
        //                             <div className="mb-8">
        //                                 <label htmlFor="cocinar-cb" id="cocinar-label"
        //                                        onClick={(e) => {
        //                                            setColor('cocinar', cookRef)
        //                                        }}
        //                                        className="mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer">
        //                                     {t('Abilities.cook')}
        //                                 </label>
        //                                 <input type="checkbox" ref={cookRef}
        //                                        onChange={(e) => handleAbilityCheck(cookRef, e.target.value)}
        //                                        id="cocinar-cb" value={ids.abilities[0]}
        //                                        style={{visibility: "hidden"}}/>
        //                             </div>
        //                             <div>
        //                                 <label htmlFor="planchar-cb" id="planchar-label"
        //                                        onClick={() => setColor('planchar', ironRef)}
        //                                        className="mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer">
        //                                     {t('Abilities.iron')}
        //                                 </label>
        //                                 <input type="checkbox" ref={ironRef}
        //                                        onChange={(e) => handleAbilityCheck(ironRef, e.target.value)}
        //                                        id="planchar-cb" value={ids.abilities[1]}
        //                                        style={{visibility: "hidden"}}/>
        //                             </div>
        //                             <div>
        //                                 <label htmlFor="menores-cb" id="menores-label"
        //                                        onClick={() => setColor('menores', childRef)}
        //                                        className="mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer">
        //                                     {t('Abilities.child')}
        //                                 </label>
        //                                 <input type="checkbox" ref={childRef}
        //                                        onChange={(e) => handleAbilityCheck(childRef, e.target.value)}
        //                                        id="menores-cb" value={ids.abilities[2]}
        //                                        style={{visibility: "hidden"}}/>
        //                             </div>
        //                             <div>
        //                                 <label htmlFor="mayores-cb" id="mayores-label"
        //                                        onClick={() => setColor('mayores', elderRef)}
        //                                        className="mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer">
        //                                     {t('Abilities.older')}
        //                                 </label>
        //                                 <input type="checkbox" ref={elderRef}
        //                                        onChange={(e) => handleAbilityCheck(elderRef, e.target.value)}
        //                                        id="mayores-cb" value={ids.abilities[3]}
        //                                        style={{visibility: "hidden"}}/>
        //                             </div>
        //                             <div>
        //                                 <label htmlFor="especiales-cb" id="especiales-label"
        //                                        onClick={() => setColor('especiales', specialRef)}
        //                                        className="mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer">
        //                                     {t('Abilities.specialNeeds')}
        //                                 </label>
        //                                 <input type="checkbox" ref={specialRef}
        //                                        onChange={(e) => handleAbilityCheck(specialRef, e.target.value)}
        //                                        id="especiales-cb" value={ids.abilities[4]}
        //                                        style={{visibility: "hidden"}}/>
        //                             </div>
        //                             <div>
        //                                 <label htmlFor="mascotas-cb" id="mascotas-label"
        //                                        onClick={() => setColor('mascotas', petRef)}
        //                                        className="mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer">
        //                                     {t('Abilities.pets')}
        //                                 </label>
        //                                 <input type="checkbox" ref={petRef}
        //                                        onChange={(e) => handleAbilityCheck(petRef, e.target.value)}
        //                                        id="mascotas-cb" value={ids.abilities[5]}
        //                                        style={{visibility: "hidden"}}/>
        //                             </div>
        //                         </div>
        //                     }
        //                     <form:errors path="abilities" element="p" cssStyle="color: red; margin-top: 1.25rem"/>
        //                     <div>
        //                         <h1 className="pb-3 pt-3 font-bold">
        //                             <spring:message code="employeeForm.availability"/>
        //                         </h1>
        //                     </div>
        //                     <div className="flex flex-wrap ml-8">
        //                         <div>
        //                             <form:label path="abilities" htmlFor="media-cb" id="media-label"
        //                                         onclick="setColor('media');"
        //                                         className="mt-1 h-fit w-fit text-xs text-gray-900 active:bg-violet-300 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer">
        //                                 <spring:message code="employeeForm.availability.half"/>
        //                             </form:label>
        //                             <form:checkbox path="availability" id="media-cb" value="${availability[0]}"
        //                                            cssStyle="visibility: hidden"/>
        //                         </div>
        //                         <div>
        //                             <form:label path="abilities" htmlFor="completa-cb" id="completa-label"
        //                                         onclick="setColor('completa');"
        //                                         className="mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer">
        //                                 <spring:message code="employeeForm.availability.complete"/>
        //                             </form:label>
        //                             <form:checkbox path="availability" id="completa-cb" value="${availability[1]}"
        //                                            cssStyle="visibility: hidden"/>
        //                         </div>
        //                         <div>
        //                             <form:label path="abilities" htmlFor="cama-cb" id="cama-label"
        //                                         onclick="setColor('cama');"
        //                                         className="mt-1 h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-violet-300 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 cursor-pointer">
        //                                 <spring:message code="employeeForm.availability.bed"/>
        //                             </form:label>
        //                             <form:checkbox path="availability" id="cama-cb" value="${availability[2]}"
        //                                            cssStyle="visibility: hidden"/>
        //                         </div>
        //                     </div>
        //                     <form:errors path="availability" element="p" cssStyle="color: red; margin-top: 1.25rem"/>
        //                     <div className="mt-5 col-start-2 col-span-4 row-span-3">
        //                         <button type="submit"
        //                                 className="text-lg w-full focus:outline-none text-violet-900 bg-purple-900 bg-opacity-30 hover:bg-purple-900 hover:bg-opacity-50 font-small rounded-lg text-sm px-5 py-2.5">
        //                             <spring:message code="editProfile.button"/>
        //                         </button>
        //                     </div>
        //                 </div>
        //             </div>
        //         </div>
        //     </form>
        // </div>
    )

}

export default EditEmployee