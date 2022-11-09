import {Link} from "react-router-dom";
import {Component, useEffect, useState} from "react";
import {EmployeeService} from "../service/EmployeeService";

const EmployeeCard = (employee: any)=> {
    const e = employee.employee
    const [image, setImage]: any = useState()
    useEffect(() => {
        EmployeeService.loadImage(e.id.id).then(
            (img) => {
                if (img.size == 0)
                    setImage("./images/user.png")
                else
                    setImage(URL.createObjectURL(img))
            }
        )
    }, [])

    return (
        <div className="w-full col-span-5">
            {/*<c:url value="/user/profile-image/${param.id}" var="image"/>*/}
            <Link to={"/profile"} state={{id: e.id.id}}
                  className="grid grid-cols-8 items-center w-full bg-white rounded-lg border shadow-md mb-5 md:flex-row md:max-w-full hover:bg-gray-100 ">
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
                        {e.experienceYears}
                        {/*<spring:message code="employeeCardComponent.experience" htmlEscape="true" arguments="${param.years}"/>*/}
                    </p>
                </div>
                {/*<c:if test="${param.contacted != null && param.contacted}">
              <div className="col-start-6 col-span-2 w-fit ml-8 mr-8">
                  <p className="h-fit w-full text-xs text-white bg-gray-400 border border-gray-900 font-medium rounded-full px-5 py-2.5 mr-2.5 mb-2">
                      <spring:message code="viewProfile.alreadyConnected"/>
                  </p>
              </div>
          </c:if>
          <ul className="flex items-center gap-x-1 col-start-8">
              <c:choose>
                  <c:when test="${param.rating >= 0.75}">
                      <li>
                          <i className=" text-yellow-300 fa-lg fa fa-star"></i>
                      </li>
                  </c:when>
                  <c:otherwise>
                      <li>
                          <i className="text-yellow-300 fa-lg fa fa-star-o"></i>
                      </li>
                  </c:otherwise>
              </c:choose>
              <c:choose>
                  <c:when test="${param.rating >= 1.75}">
                      <li>
                          <i className="text-yellow-300 fa fa-lg fa-star"></i>
                      </li>
                  </c:when>
                  <c:otherwise>
                      <li>
                          <i className="text-yellow-300 fa fa-lg fa-star-o"></i>
                      </li>
                  </c:otherwise>
              </c:choose>
              <c:choose>
                  <c:when test="${param.rating >= 2.75}">
                      <li>
                          <i className="text-yellow-300 fa fa-lg fa-star"></i>
                      </li>
                  </c:when>
                  <c:otherwise>
                      <li>
                          <i className="text-yellow-300 fa-lg fa fa-star-o"></i>
                      </li>
                  </c:otherwise>
              </c:choose>
              <c:choose>
                  <c:when test="${param.rating >= 3.75}">
                      <li>
                          <i className="text-yellow-300 fa-lg fa fa-star"></i>
                      </li>
                  </c:when>
                  <c:otherwise>
                      <li>
                          <i className="text-yellow-300 fa-lg fa fa-star-o"></i>
                      </li>
                  </c:otherwise>
              </c:choose>
              <c:choose>
                  <c:when test="${param.rating >= 4.75}">
                      <li>
                          <i className="text-yellow-300 fa-lg fa fa-star"></i>
                      </li>
                  </c:when>
                  <c:otherwise>
                      <li>
                          <i className="text-yellow-300 fa-lg fa fa-star-o"></i>
                      </li>
                  </c:otherwise>
              </c:choose>
          </ul>*/}
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