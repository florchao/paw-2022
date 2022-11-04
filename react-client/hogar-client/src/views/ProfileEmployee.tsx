import {useEffect, useState} from "react";
import {EmployeeService} from "../service/EmployeeService";
import {Link, useLocation} from "react-router-dom";
import {ReviewService} from "../service/ReviewService";
import ReviewCard from "../components/ReviewCard";
import {verify} from "crypto";
import {RatingService} from "../service/RatingService";

export const ProfileEmployee = () => {

    const [employee, setEmployee]: any = useState()
    const [image, setImage]: any = useState()
    const [rating, setRating]: any = useState()

    const [review, setReview]: any = useState()

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
        EmployeeService.loadImage(id).then(
            (img) => {
                if (img.size == 0)
                    setImage("./images/user.png")
                else
                    setImage(URL.createObjectURL(img))
            })
    }, [])

    useEffect(() => {
            ReviewService.getEmployeeReviews(id).then(
                (rsp) => {
                    setReview(rsp)
                }
            )
        }, []
    )

    // useEffect(() => {
    //         RatingService.getEmployeeRating(id).then(
    //             (rsp) => {
    //                 setRating(rsp)
    //                 console.log(rating)
    //             }
    //         )
    //     }, []
    // )

    return (
      <div className="grid overflow-auto h-screen grid-cols-6">
          {employee &&
          <div className=" grid grid-row-4 col-span-4 col-start-2 h-fit">
              <div className=" bg-gray-200 rounded-3xl p-5 mt-24 mb-5 shadow-2xl">
                  <div className="grid grid-cols-5 justify-center">
                      <div className="row-span-3 col-span-2 ml-6 mr-6 mb-6 justify-self-center">
                          <img className="object-cover mb-3 w-52 h-52 rounded-full shadow-lg" src={image} alt="profile pic"/>
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
                      <div className="ml-3 col-start-5 row-start-2 w-fit">
                          <Link to="/contact/employee" state={{id: employee.id.id, name: employee.name}}>
                              <button
                                  className="h-fit  text-xs text-white bg-violet-400 border border-purple-900 focus:outline-none focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 hover:bg-yellow-300 hover:bg-opacity-70 hover:text-purple-900">
                                  CONNECT
                              </button>
                          </Link>
                          {rating &&
                              <ul className="flex items-center gap-x-1">
                                  {rating && rating >= 0.75 &&
                                      <li>
                                          <i className=" text-yellow-300 fa-lg fa fa-star"></i>
                                      </li>
                                  }
                                  {rating && rating < 0.75 &&
                                      <li>
                                          <i className="text-yellow-300 fa-lg fa fa-star-o"></i>
                                      </li>
                                  }
                                  {/*    </c:otherwise>*/}
                                  {/*</c:choose>*/}
                                  {/*<c:choose>*/}
                                  {/*    <c:when test="${rating >= 1.75}">*/}
                                  {/*        <li>*/}
                                  {/*            <i className="text-yellow-300 fa fa-lg fa-star"></i>*/}
                                  {/*        </li>*/}
                                  {/*    </c:when>*/}
                                  {/*    <c:otherwise>*/}
                                  {/*        <li>*/}
                                  {/*            <i className="text-yellow-300 fa fa-lg fa-star-o"></i>*/}
                                  {/*        </li>*/}
                                  {/*    </c:otherwise>*/}
                                  {/*</c:choose>*/}
                                  {/*<c:choose>*/}
                                  {/*    <c:when test="${rating >= 2.75}">*/}
                                  {/*        <li>*/}
                                  {/*            <i className="text-yellow-300 fa fa-lg fa-star"></i>*/}
                                  {/*        </li>*/}
                                  {/*    </c:when>*/}
                                  {/*    <c:otherwise>*/}
                                  {/*        <li>*/}
                                  {/*            <i className="text-yellow-300 fa-lg fa fa-star-o"></i>*/}
                                  {/*        </li>*/}
                                  {/*    </c:otherwise>*/}
                                  {/*</c:choose>*/}
                                  {/*<c:choose>*/}
                                  {/*    <c:when test="${rating >= 3.75}">*/}
                                  {/*        <li>*/}
                                  {/*            <i className="text-yellow-300 fa-lg fa fa-star"></i>*/}
                                  {/*        </li>*/}
                                  {/*    </c:when>*/}
                                  {/*    <c:otherwise>*/}
                                  {/*        <li>*/}
                                  {/*            <i className="text-yellow-300 fa-lg fa fa-star-o"></i>*/}
                                  {/*        </li>*/}
                                  {/*    </c:otherwise>*/}
                                  {/*</c:choose>*/}
                                  {/*<c:choose>*/}
                                  {/*    <c:when test="${rating >= 4.75}">*/}
                                  {/*        <li>*/}
                                  {/*            <i className="text-yellow-300 fa-lg fa fa-star"></i>*/}
                                  {/*        </li>*/}
                                  {/*    </c:when>*/}
                                  {/*    <c:otherwise>*/}
                                  {/*        <li>*/}
                                  {/*            <i className="text-yellow-300 fa-lg fa fa-star-o"></i>*/}
                                  {/*        </li>*/}
                                  {/*    </c:otherwise>*/}
                                  {/*</c:choose>*/}
                                  {/*<c:out value="(${voteCount})"/>*/}
                              </ul>
                          }
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
                  <div className="flow-root">
                      <h1 className="pb-3 pt-3 font-semibold">
                          Reviews
                      </h1>
                      <ul role="list" className="divide-y divide-gray-300">
                          {review && review.length > 0 && review.map((rev: any) => <ReviewCard review={rev}/>)}
                          {review && review.length == 0 && <div
                              className="grid content-center justify-center h-5/6 mt-16">
                              <div className="grid justify-items-center">
                                  <img src='/images/sinEmpleadas.png' alt="sinEmpleadas"
                                       className="mr-3 h-6 sm:h-52"/>
                                      <p className="text-3xl font-semibold text-purple-700">
                                          No Reviews
                                      </p>
                              </div>
                          </div>}
                      </ul>
                  </div>
              </div>
          </div> }
      </div>

                      )
}

export default ProfileEmployee;