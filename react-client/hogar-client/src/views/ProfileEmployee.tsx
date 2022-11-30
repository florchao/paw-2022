import {useEffect, useState} from "react";
import {EmployeeService} from "../service/EmployeeService";
import {Link, useLocation, useNavigate} from "react-router-dom";
import {ReviewService} from "../service/ReviewService";
import ReviewCard from "../components/ReviewCard";
import {verify} from "crypto";
import {RatingService} from "../service/RatingService";
import {useTranslation} from "react-i18next";
import {UserService} from "../service/UserService";

export const ProfileEmployee = () => {

    const [employee, setEmployee]: any = useState()
    const [image, setImage]: any = useState()
    const [rating, setRating]: any = useState()

    const [review, setReview]: any = useState()

    const {id, status} = useLocation().state

    console.log(useLocation().state)

    const { t } = useTranslation();

    useEffect(() => {
        console.log(id)
        EmployeeService.getEmployee(id).then((e) => setEmployee(e));
    }, [])

    useEffect(() => {
        UserService.loadImage(id).then(
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

    useEffect(() => {
            RatingService.getEmployeeRating(id).then(
                (rsp) => {
                    setRating(rsp)
                }
            )
        }, []
    )

    return (
      <div className="grid overflow-auto h-screen grid-cols-6">
          {employee &&
          <div className=" grid grid-row-4 col-span-4 col-start-2 h-fit">
              <div className=" bg-gray-200 rounded-3xl p-5 mt-24 mb-5 shadow-2xl">
                  <div className="grid grid-cols-5 justify-center">
                      <div className="row-span-3 col-span-2 ml-6 mr-6 mb-6 justify-self-center">
                          <img className="object-cover mb-3 w-52 h-52 rounded-full shadow-lg" src={image} alt="profile pic"/>
                      </div>
                      <div className="ml-3 col-span-2">
                          <p className="text-2xl font-semibold whitespace-nowrap text-ellipsis overflow-hidden">
                              {employee.name}
                          </p>
                      </div>
                      <div className="ml-3 col-span-2">
                          <h1 className="block mb-2 font-medium text-gray-900 font-semibold">
                              {t('Profile.location')}
                          </h1>
                          <h1 className="block mb-2 text-sm font-medium text-gray-600 text-ellipsis overflow-hidden">
                              {employee.location}
                          </h1>
                      </div>
                      <div className="ml-3 col-span-2">
                          <h1 className="block mb-2 font-medium text-gray-900 font-semibold">
                              {t('Profile.experience')}
                          </h1>
                          <h1 className="block mb-2 text-sm font-medium text-gray-600 ">
                              {employee.experienceYears}
                          </h1>
                      </div>
                      <div className="ml-3 col-start-5 row-start-2 w-fit">
                          <Link to="/contact/employee" state={{id: employee.id, name: employee.name}}>
                              <button
                                  className="h-fit  text-xs text-white bg-violet-400 border border-purple-900 focus:outline-none focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2 hover:bg-yellow-300 hover:bg-opacity-70 hover:text-purple-900">
                                  {t('Profile.connect')}
                              </button>
                          </Link>
                          {rating && rating.count != 0 &&
                              <ul className="flex items-center gap-x-1">
                                  {rating && rating.rating >= 0.75 &&
                                      <li>
                                          <svg aria-hidden="true" className="w-5 h-5 text-yellow-400"
                                               fill="currentColor" viewBox="0 0 20 20"
                                               xmlns="http://www.w3.org/2000/svg"><title>First star</title>
                                              <path
                                                  d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z"></path>
                                          </svg>
                                      </li>
                                  }
                                  {rating && rating.rating < 0.75 &&
                                      <li>
                                          <svg aria-hidden="true" className="w-5 h-5 text-gray-300 dark:text-gray-500"
                                               fill="currentColor" viewBox="0 0 20 20"
                                               xmlns="http://www.w3.org/2000/svg"><title>First star</title>
                                              <path
                                                  d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z"></path>
                                          </svg>
                                      </li>
                                  }
                                  {rating && rating.rating >= 1.75 &&
                                      <svg aria-hidden="true" className="w-5 h-5 text-yellow-400"
                                           fill="currentColor" viewBox="0 0 20 20"
                                           xmlns="http://www.w3.org/2000/svg"><title>Second star</title>
                                          <path
                                              d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z"></path>
                                      </svg>
                                  }
                                  {rating && rating.rating < 1.75 &&
                                      <li>
                                          <svg aria-hidden="true" className="w-5 h-5 text-gray-300 dark:text-gray-500"
                                               fill="#E5E7EB" viewBox="0 0 20 20"
                                               xmlns="http://www.w3.org/2000/svg"><title>Second star</title>
                                              <path
                                                  d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z"></path>
                                          </svg>
                                      </li>
                                  }
                                  {rating && rating.rating >= 2.75 &&
                                      <svg aria-hidden="true" className="w-5 h-5 text-yellow-400"
                                           fill="currentColor" viewBox="0 0 20 20"
                                           xmlns="http://www.w3.org/2000/svg"><title>Third star</title>
                                          <path
                                              d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z"></path>
                                      </svg>
                                  }
                                  {rating && rating.rating < 2.75 &&
                                      <li>
                                          <svg aria-hidden="true" className="w-5 h-5 text-gray-300 dark:text-gray-500"
                                               fill="currentColor" viewBox="0 0 20 20"
                                               xmlns="http://www.w3.org/2000/svg"><title>Third star</title>
                                              <path
                                                  d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z"></path>
                                          </svg>
                                      </li>
                                  }
                                  {rating && rating.rating >= 3.75 &&
                                      <svg aria-hidden="true" className="w-5 h-5 text-yellow-400"
                                           fill="currentColor" viewBox="0 0 20 20"
                                           xmlns="http://www.w3.org/2000/svg"><title>Fourth star</title>
                                          <path
                                              d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z"></path>
                                      </svg>
                                  }
                                  {rating && rating.rating < 3.75 &&
                                      <li>
                                          <svg aria-hidden="true" className="w-5 h-5 text-gray-300 dark:text-gray-500"
                                               fill='%23E5E7EB' viewBox="0 0 20 20"
                                               xmlns="http://www.w3.org/2000/svg"><title>Fourth star</title>
                                              <path
                                                  d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z"></path>
                                          </svg>
                                      </li>
                                  }
                                  {rating && rating.rating >= 4.75 &&
                                      <svg aria-hidden="true" className="w-5 h-5 text-yellow-400"
                                           fill="currentColor" viewBox="0 0 20 20"
                                           xmlns="http://www.w3.org/2000/svg"><title>Fifth star</title>
                                          <path
                                              d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z"></path>
                                      </svg>
                                  }
                                  {rating && rating.rating < 4.75 &&
                                      <li>
                                          <svg aria-hidden="true" className="w-5 h-5 text-gray-300 dark:text-gray-500" viewBox="0 0 20 20"
                                               fill='%23E5E7EB'
                                               xmlns="http://www.w3.org/2000/svg"><title>Fifth star</title>
                                              <path
                                                  d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z"></path>
                                          </svg>
                                      </li>
                                  }
                                  <p>({rating && rating.count})</p>

                              </ul>
                          }
                      </div>
                  </div>
                  <div className="grid grid-cols-2">
                      <div className="col-span-1">
                          <h1 className="pb-3 pt-3 font-semibold">
                              {/*<spring:message code="viewProfile.abilities"/>*/}
                              {t('Profile.abilities')}
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
                              {t('Profile.availability')}
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
                          {t('Profile.reviews')}
                      </h1>
                      <ul role="list" className="divide-y divide-gray-300">
                          {review && review.length > 0 && review.map((rev: any) => <ReviewCard review={rev}/>)}
                          {review && review.length == 0 && <div
                              className="grid content-center justify-center h-5/6 mt-16">
                              <div className="grid justify-items-center">
                                  <img src='/images/sinEmpleadas.png' alt="sinEmpleadas"
                                       className="mr-3 h-6 sm:h-52"/>
                                      <p className="text-3xl font-semibold text-purple-700">
                                          {t('Profile.noReviews')}
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