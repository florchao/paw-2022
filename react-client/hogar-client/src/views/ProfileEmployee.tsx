import {useEffect, useState} from "react";
import {EmployeeService} from "../service/EmployeeService";
import {useLocation} from "react-router-dom";

export const ProfileEmployee = () => {

    const [employee, setEmployee]: any = useState()
    const [image, setImage]: any = useState()

    const {id} = useLocation().state

    console.log(id)

    // useEffect(() => {
    //     const loadEmployee = async () => {
    //         const val = await EmployeeService.getEmployee(id);
    //         console.log(val)
    //         setEmployee(val[0])
    //     }
    //     loadEmployee()
    // }, [])

    useEffect(() => {
        EmployeeService.getEmployee(id).then((val) => setEmployee(val[0]));
    }, [])

    useEffect(() => {
        EmployeeService.loadImage(id).then((img) => setImage(URL.createObjectURL(img)))
    }, [])



    const loadImage = () => {
        return fetch('http://localhost:8080/api/profile/image/' + id , {
            method: 'GET',
            headers: {
                "Access-Control-Allow-Origin": "*",
                "Content-Type": "application/json"
            },
        }).then((resp) => {
            resp.blob().then((img) => {
                    const urlImg = URL.createObjectURL(img)
                    console.log(urlImg)
                    setImage(urlImg)
                }
            )
            }
        )
            .catch(
                (error) => {
                    console.log(error)
                    throw error
                })
    }

    return (
      <div className="grid overflow-auto h-screen grid-cols-6">
          {employee &&
          <div className=" grid grid-row-4 col-span-4 col-start-2 h-fit">
              <div className=" bg-gray-200 rounded-3xl p-5 mt-24 mb-5 shadow-2xl">
                  <div className="grid grid-cols-5 justify-center">
                      <div className="row-span-3 col-span-2 ml-6 mr-6 mb-6 justify-self-center">
                          <img className="object-cover mb-3 w-52 h-52 rounded-full shadow-lg" src={image}/>
                               {/*onError="this.src = '<c:url value="/public/user.png"/>'" alt="profile pic"*/}
                      </div>
                      <div className="ml-3 col-span-2">
                          <p className="text-2xl font-semibold whitespace-nowrap text-ellipsis overflow-hidden">
                              {employee.name}
                          </p>
                      </div>
                      <div className="ml-3 col-span-2">
                          <h1 className="block mb-2 font-medium text-gray-900 font-semibold">
                              {/*<spring:message code="viewProfile.location"/>*/}
                              Location
                          </h1>
                          <h1 className="block mb-2 text-sm font-medium text-gray-600 text-ellipsis overflow-hidden">
                              {employee.location}
                          </h1>
                      </div>
                      <div className="ml-3 col-span-2">
                          <h1 className="block mb-2 font-medium text-gray-900 font-semibold">
                              {/*<spring:message code="viewProfile.experience"/>*/}
                              Experience Years
                          </h1>
                          <h1 className="block mb-2 text-sm font-medium text-gray-600 ">
                              {employee.experienceYears}
                          </h1>
                      </div>
                  </div>
                  <div className="grid grid-cols-2">
                      <div className="col-span-1">
                          <h1 className="pb-3 pt-3 font-semibold">
                              {/*<spring:message code="viewProfile.abilities"/>*/}
                              Abilities
                          </h1>
                          <ul role="list"
                              className="list-inside marker:text-purple-900 list-disc pl-5 space-y-3 text-gray-500">
                              {employee.abilitiesArr.map((ability: String) => (
                                  <li>
                                      {ability}
                                  </li>
                              ))}
                          </ul>
                      </div>
                      <div className="col-span-1 col-start-2">
                          <h1 className="pb-3 pt-3 font-semibold">
                              {/*<spring:message code="viewProfile.availability"/>*/}
                              Availability
                          </h1>
                          <ul role="list"
                              className="list-inside marker:text-purple-900 list-disc pl-5 space-y-3 text-gray-500">
                              {employee.availabilityArr.map((availability: String) => (
                                  <li>
                                      {availability}
                                  </li>
                              ))}
                          </ul>
                      </div>
                  </div>
              </div>
          </div> }
      </div>

                      )
}

export default ProfileEmployee;