import {useEffect, useState} from "react";
import {EmployeeService} from "../service/EmployeeService";
import {Link, useLocation, useNavigate} from "react-router-dom";
import {ReviewService} from "../service/ReviewService";
import ReviewCard from "../components/ReviewCard";
import {verify} from "crypto";
import {RatingService} from "../service/RatingService";
import {useTranslation} from "react-i18next";
import {UserService} from "../service/UserService";
import MyReviewCard from "../components/MyReviewCard";
import useFormPersist from "react-hook-form-persist";
import {useForm} from "react-hook-form";

export const ProfileEmployee = () => {

    const [employee, setEmployee]: any = useState()
    const [image, setImage]: any = useState()
    const [rating, setRating]: any = useState()

    const [review, setReview]: any = useState()
    const [myReview, setMyReview]: any = useState()

    const {id, status} = useLocation().state

    console.log(useLocation().state)

    const { t } = useTranslation();
    const nav = useNavigate();


    type FormData = {
        content: string;
    };

    const {register, handleSubmit, watch, formState: {errors}, getValues, setValue} = useForm<FormData>();

    watch("content")

    useFormPersist("form", {
        watch,
        setValue,
        storage: window.localStorage,
    })

    const onSubmit = async (data: any, e: any) => {
        const post = await ReviewService.putEmployeeReview(e, employee.id, data.content)
        localStorage.clear()
        setMyReview(post)
    }

    function delEmployee() {
        UserService.deleteUser(id).then(() => {
                nav('/', {replace: true})
            }
        );
    }

    useEffect(() => {
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
        ReviewService.getMyEmployeeReview(id).then(
            (rsp) => {
                setMyReview(rsp)
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
                          {rating && rating.count !== 0 &&
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
                      <div className="ml-3 col-start-5 row-start-3">
                          <button type="submit" onClick={delEmployee}
                                  className="text-sm focus:outline-none text-white bg-red-500 hover:bg-red-700 font-small rounded-lg text-sm px-5 py-2.5">
                              <div className="grid grid-rows-1 grid-cols-3">
                                  <img src={'./images/bin.png'} alt="bin"
                                       className="mr-3 h-6 sm:h-5 col-start-1"/>
                                  <p className="col-span-2">
                                      {t('EmployerProfile.delete')}
                                  </p>
                              </div>
                          </button>
                      </div>
                  </div>
                  <div className="grid grid-cols-2">
                      <div className="col-span-1">
                          <h1 className="pb-3 pt-3 font-semibold">
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
                          {myReview == null && (
                              <form onSubmit={handleSubmit(onSubmit)}>
                                  <div className="">
                                      <label htmlFor="title" className="block pb-3 pt-3 font-semibold text-gray-900">
                                          {t('ReviewForm.label_title')}
                                      </label>
                                      <textarea
                                          value={getValues("content")}
                                          {...register("content", {required: true, maxLength: 1000, minLength: 10})}
                                          className="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-violet-500 focus:border-violet-500"/>
                                      {errors.content && (
                                          <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">
                                              {t('ReviewForm.error')}
                                          </p>

                                      )}
                                      <div className="mt-5 col-start-2 col-span-4 row-span-3">
                                          <button type="submit"
                                                  className="text-lg w-full focus:outline-none text-violet-900 bg-purple-900 bg-opacity-30 hover:bg-purple-900 hover:bg-opacity-50 font-small rounded-lg text-sm px-5 py-2.5">
                                              {t('ReviewForm.button')}
                                          </button>
                                      </div>
                                  </div>
                              </form>)}
                          {myReview && <MyReviewCard review={myReview}/>}
                          {review && review.length > 0 && review.map((rev: any) => <ReviewCard review={rev}/>)}
                          {review && review.length === 0 && !myReview &&
                              <div className="grid content-center justify-center h-5/6 mt-16">
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