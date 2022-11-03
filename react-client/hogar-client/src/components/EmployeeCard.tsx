import { useTranslation } from "react-i18next";
import {Link} from "react-router-dom";

export const EmployeeCard = () => {
const { t } = useTranslation();

  return (
      <body>
      {/*<c:url value="/user/profile-image/${param.id}" var="image"/>*/}
      <Link to="/verPerfil" className="grid grid-cols-8 items-center bg-white rounded-lg border shadow-md mb-5 md:flex-row md:max-w-full hover:bg-gray-100 ">
          <img style={{maxHeight: 150}}
               className="object-cover w-full h-96 rounded-t-lg md:h-auto md:w-48 md:rounded-none md:rounded-l-lg col-start-1 col-span-1"
               src={ './images/user.png' } alt="user photo"/>
          <div className="flex flex-col justify-between p-4 leading-normal col-start-2 col-span-4">
              <h5 className="mb-2 text-2xl font-bold tracking-tight text-black text-ellipsis overflow-hidden">
                  {t('EmployeeCard.name')}
                  {/*<c:out value="${param.name}"/>*/}
              </h5>
              <p className="mb-3 font-normal text-gray-400 text-ellipsis overflow-hidden">
              {t('EmployeeCard.location')}
                  {/*<c:out value="${param.location}"/>*/}
              </p>
              <p className="mb-3 font-normal text-gray-700 dark:text-gray-400 ">
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
      </body>
  )
}

export default EmployeeCard;