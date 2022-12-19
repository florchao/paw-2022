import {Link} from "react-router-dom";
import {useEffect, useState} from "react";
import {UserService} from "../service/UserService";
import {useTranslation} from "react-i18next";
import {Rating} from "react-simple-star-rating";

const EmployeeCard = (employee: any) => {
    const e = employee.employee
    const [image, setImage]: any = useState()

    const {t} = useTranslation();

    useEffect(() => {
        UserService.loadImage(e.image).then(
            (img) => {
                if (img.size == 0)
                    setImage("./assets/user.png")
                else
                    setImage(URL.createObjectURL(img))
            }
        )
    }, [])

    return (
        <div className="w-full col-span-5">
            <Link to={"/employee"} state={{self: e.self, status: -1}}
                  className="grid grid-cols-9 items-center w-full bg-white rounded-lg border shadow-md mb-5 md:flex-row md:max-w-full hover:bg-gray-100 ">
                <img style={{maxHeight: 150}}
                     className="object-cover w-full h-96 rounded-t-lg md:h-auto md:w-48 md:rounded-none md:rounded-l-lg col-start-1 col-span-1"
                     src={image} alt="user photo"/>
                <div className="flex flex-col justify-between p-4 leading-normal col-start-2 col-span-4">
                    <h5 className="mb-2 text-2xl font-bold tracking-tight text-black text-ellipsis overflow-hidden">
                        {e.name}
                    </h5>
                    <p className="mb-3 font-normal text-gray-400 text-ellipsis overflow-hidden">
                        {e.location}
                    </p>
                    <p className="mb-3 font-normal text-gray-700 dark:text-gray-400 ">
                        {t('EmployeeCard.yearsExp')}
                        {e.experienceYears}
                    </p>
                </div>

                {e.contacted &&
                    <div className="col-start-6 col-span-2 w-fit">
                        <p className="h-fit w-full text-xs text-white bg-gray-400 border border-gray-900 font-medium rounded-full px-5 py-2.5 mr-2.5 mb-2">
                            {t('EmployeeCard.connected')}
                        </p>
                    </div>
                }

                <div className="flex items-center gap-x-1 col-start-8 col-span-2">
                    {e.rating != 0 &&
                        <div className={"flex items-center"}>
                            <Rating
                                initialValue={e.rating}
                                readonly
                                size={25}
                                SVGclassName="inline-block"
                                SVGstyle={{zIndex: "0"}}
                            />
                        </div>
                        // <ul className="flex items-center gap-x-1">
                        //     {e.rating && e.rating >= 0.75 &&
                        //         <li>
                        //             <svg aria-hidden="true" className="w-5 h-5 text-yellow-400"
                        //                  fill="currentColor" viewBox="0 0 20 20"
                        //                  xmlns="http://www.w3.org/2000/svg"><title>First star</title>
                        //                 <path
                        //                     d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z"></path>
                        //             </svg>
                        //         </li>
                        //     }
                        //     {e.rating && e.rating < 0.75 &&
                        //         <li>
                        //             <svg aria-hidden="true" className="w-5 h-5 text-gray-300 dark:text-gray-500"
                        //                  fill="currentColor" viewBox="0 0 20 20"
                        //                  xmlns="http://www.w3.org/2000/svg"><title>First star</title>
                        //                 <path
                        //                     d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z"></path>
                        //             </svg>
                        //         </li>
                        //     }
                        //     {e.rating && e.rating >= 1.75 &&
                        //         <svg aria-hidden="true" className="w-5 h-5 text-yellow-400"
                        //              fill="currentColor" viewBox="0 0 20 20"
                        //              xmlns="http://www.w3.org/2000/svg"><title>Second star</title>
                        //             <path
                        //                 d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z"></path>
                        //         </svg>
                        //     }
                        //     {e.rating && e.rating < 1.75 &&
                        //         <li>
                        //             <svg aria-hidden="true" className="w-5 h-5 text-gray-300 dark:text-gray-500"
                        //                  fill="#E5E7EB" viewBox="0 0 20 20"
                        //                  xmlns="http://www.w3.org/2000/svg"><title>Second star</title>
                        //                 <path
                        //                     d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z"></path>
                        //             </svg>
                        //         </li>
                        //     }
                        //     {e.rating && e.rating >= 2.75 &&
                        //         <svg aria-hidden="true" className="w-5 h-5 text-yellow-400"
                        //              fill="currentColor" viewBox="0 0 20 20"
                        //              xmlns="http://www.w3.org/2000/svg"><title>Third star</title>
                        //             <path
                        //                 d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z"></path>
                        //         </svg>
                        //     }
                        //     {e.rating && e.rating < 2.75 &&
                        //         <li>
                        //             <svg aria-hidden="true" className="w-5 h-5 text-gray-300 dark:text-gray-500"
                        //                  fill="currentColor" viewBox="0 0 20 20"
                        //                  xmlns="http://www.w3.org/2000/svg"><title>Third star</title>
                        //                 <path
                        //                     d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z"></path>
                        //             </svg>
                        //         </li>
                        //     }
                        //     {e.rating && e.rating >= 3.75 &&
                        //         <svg aria-hidden="true" className="w-5 h-5 text-yellow-400"
                        //              fill="currentColor" viewBox="0 0 20 20"
                        //              xmlns="http://www.w3.org/2000/svg"><title>Fourth star</title>
                        //             <path
                        //                 d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z"></path>
                        //         </svg>
                        //     }
                        //     {e.rating && e.rating < 3.75 &&
                        //         <li>
                        //             <svg aria-hidden="true" className="w-5 h-5 text-gray-300 dark:text-gray-500"
                        //                  fill='%23E5E7EB' viewBox="0 0 20 20"
                        //                  xmlns="http://www.w3.org/2000/svg"><title>Fourth star</title>
                        //                 <path
                        //                     d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z"></path>
                        //             </svg>
                        //         </li>
                        //     }
                        //     {e.rating && e.rating >= 4.75 &&
                        //         <svg aria-hidden="true" className="w-5 h-5 text-yellow-400"
                        //              fill="currentColor" viewBox="0 0 20 20"
                        //              xmlns="http://www.w3.org/2000/svg"><title>Fifth star</title>
                        //             <path
                        //                 d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z"></path>
                        //         </svg>
                        //     }
                        //     {e.rating && e.rating < 4.75 &&
                        //         <li>
                        //             <svg aria-hidden="true" className="w-5 h-5 text-gray-300 dark:text-gray-500"
                        //                  viewBox="0 0 20 20"
                        //                  fill='%23E5E7EB'
                        //                  xmlns="http://www.w3.org/2000/svg"><title>Fifth star</title>
                        //                 <path
                        //                     d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z"></path>
                        //             </svg>
                        //         </li>
                        //     }
                        // </ul>
                    }
                </div>
            </Link>
        </div>
    )
}

// export class EmployeeCard extends Component<{employee: any}> {
//   render () {
//       let {employee} = this.props;
//       let image
//       EmployeeService.loadImage(employee.id.id).then((img) => image = URL.createObjectURL(img))
//       return (
//       <div className="w-full col-span-5">
//       {/*<c:url value="/user/profile-image/${param.id}" var="image"/>*/}
//       <Link to={"/profile"} state={{id: employee.id.id}}
//             className="grid grid-cols-8 items-center w-full bg-white rounded-lg border shadow-md mb-5 md:flex-row md:max-w-full hover:bg-gray-100 ">
//           <img style={{maxHeight: 150}}
//                className="object-cover w-full h-96 rounded-t-lg md:h-auto md:w-48 md:rounded-none md:rounded-l-lg col-start-1 col-span-1"
//                src={image} alt="user photo"/>
//           <div className="flex flex-col justify-between p-4 leading-normal col-start-2 col-span-4">
//               <h5 className="mb-2 text-2xl font-bold tracking-tight text-black text-ellipsis overflow-hidden">
//                   {employee.name}
//                   {/*<c:out value="${param.name}"/>*/}
//               </h5>
//               <p className="mb-3 font-normal text-gray-400 text-ellipsis overflow-hidden">
//                   {employee.location}
//                   {/*<c:out value="${param.location}"/>*/}
//               </p>
//               <p className="mb-3 font-normal text-gray-700 dark:text-gray-400 ">
//                   {employee.experienceYears}
//                   {/*<spring:message code="employeeCardComponent.experience" htmlEscape="true" arguments="${param.years}"/>*/}
//               </p>
//           </div>
//           {/*<c:if test="${param.contacted != null && param.contacted}">
//               <div className="col-start-6 col-span-2 w-fit ml-8 mr-8">
//                   <p className="h-fit w-full text-xs text-white bg-gray-400 border border-gray-900 font-medium rounded-full px-5 py-2.5 mr-2.5 mb-2">
//                       <spring:message code="viewProfile.alreadyConnected"/>
//                   </p>
//               </div>
//           </c:if>
//           <ul className="flex items-center gap-x-1 col-start-8">
//               <c:choose>
//                   <c:when test="${param.rating >= 0.75}">
//                       <li>
//                           <i className=" text-yellow-300 fa-lg fa fa-star"></i>
//                       </li>
//                   </c:when>
//                   <c:otherwise>
//                       <li>
//                           <i className="text-yellow-300 fa-lg fa fa-star-o"></i>
//                       </li>
//                   </c:otherwise>
//               </c:choose>
//               <c:choose>
//                   <c:when test="${param.rating >= 1.75}">
//                       <li>
//                           <i className="text-yellow-300 fa fa-lg fa-star"></i>
//                       </li>
//                   </c:when>
//                   <c:otherwise>
//                       <li>
//                           <i className="text-yellow-300 fa fa-lg fa-star-o"></i>
//                       </li>
//                   </c:otherwise>
//               </c:choose>
//               <c:choose>
//                   <c:when test="${param.rating >= 2.75}">
//                       <li>
//                           <i className="text-yellow-300 fa fa-lg fa-star"></i>
//                       </li>
//                   </c:when>
//                   <c:otherwise>
//                       <li>
//                           <i className="text-yellow-300 fa-lg fa fa-star-o"></i>
//                       </li>
//                   </c:otherwise>
//               </c:choose>
//               <c:choose>
//                   <c:when test="${param.rating >= 3.75}">
//                       <li>
//                           <i className="text-yellow-300 fa-lg fa fa-star"></i>
//                       </li>
//                   </c:when>
//                   <c:otherwise>
//                       <li>
//                           <i className="text-yellow-300 fa-lg fa fa-star-o"></i>
//                       </li>
//                   </c:otherwise>
//               </c:choose>
//               <c:choose>
//                   <c:when test="${param.rating >= 4.75}">
//                       <li>
//                           <i className="text-yellow-300 fa-lg fa fa-star"></i>
//                       </li>
//                   </c:when>
//                   <c:otherwise>
//                       <li>
//                           <i className="text-yellow-300 fa-lg fa fa-star-o"></i>
//                       </li>
//                   </c:otherwise>
//               </c:choose>
//           </ul>*/}
//       </Link>
//       </div>
//       )
//   }
// }

export default EmployeeCard;