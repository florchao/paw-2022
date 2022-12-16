import {useEffect, useState} from "react";
import {EmployerService} from "../service/EmployerService";
import {ReviewService} from "../service/ReviewService";
import ReviewCard from "../components/ReviewCard";
import {Link, useNavigate} from "react-router-dom";
import {UserService} from "../service/UserService";
import {useTranslation} from "react-i18next";
import {JobService} from "../service/JobService";
import JobCard from "../components/JobCard";

export const ProfileEmployer = () => {
    const [employer, setEmployer]: any = useState()
    const [image, setImage]: any = useState()
    const [reviews, setReviews]: any = useState(new Array(0))
    const [jobs, setJobs]: any = useState(new Array(0))


    const nav = useNavigate();
    let id  = localStorage.getItem("hogar-uid") as unknown as number

    const {t} = useTranslation();


    function delEmployer() {
        UserService.deleteUser(employer.delete).then(() => {
                localStorage.removeItem("hogar-uid")
                localStorage.removeItem("hogar-jwt")
                localStorage.removeItem("hogar-role")
                nav('/', {replace: true})
                window.location.reload()
            }
        );
    }

    useEffect(() => {
        EmployerService.getEmployer(id).then((val) => setEmployer(val));
    }, [])

    useEffect(() => {
        if (employer) {
            UserService.loadImage(employer.image).then(
                (img) => {
                    if (img.size === 0)
                        setImage("./images/user.png")
                    else
                        setImage(URL.createObjectURL(img))
                });
        }
    }, [employer])

    useEffect(() => {
        if (employer) {
            ReviewService.getEmployerReviews(employer.reviews).then(
                (rsp) => {
                    setReviews(rsp)
                }
            )
        }
        }, [employer]
    )

    useEffect(() => {
            if (employer) {
                JobService.getCreatedJobs(employer.jobs, true).then(
                    async (rsp) => {
                        rsp.status === 204 ? setJobs([]) : setJobs(await rsp.json())
                    }
                )
            }
        }, [employer]
    )

    return (
        <div className="grid overflow-auto h-screen grid-cols-6">
            {employer &&
                <div className=" grid grid-row-4 col-span-4 col-start-2 h-fit">
                    <div className=" bg-gray-200 rounded-3xl p-5 mt-24 mb-5 shadow-2xl">
                        <div className="grid grid-cols-5 justify-center">
                            <div className="row-span-3 col-span-2 ml-6 mr-6 mb-6 justify-self-center">
                                {image &&
                                    <img className="object-cover mb-3 w-52 h-52 rounded-full shadow-lg" src={image}
                                         alt="profile pic"/>
                                }
                            </div>
                            <div className="ml-3 col-span-2">
                                <p className="text-2xl font-semibold whitespace-nowrap text-ellipsis overflow-hidden">
                                    {employer.name}
                                </p>
                            </div>
                            <div className="ml-3 col-start-5 row-start-2">
                                <button type="submit" onClick={delEmployer}
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
                        {jobs &&
                            <h1 className="pb-3 pt-3 font-semibold">
                                {t('EmployerProfile.jobs')}
                            </h1>
                        }
                        {jobs && jobs.length === 0 &&
                                <div className="grid justify-items-center">
                                    <img src="/images/sinTrabajos.png" alt="sinTrabajos"
                                         className="mr-3 h-6 sm:h-52"/>
                                    <p className="text-3xl font-semibold text-purple-700">
                                        {t('CreatedJobs.noJobs')}
                                    </p>
                                    <br/>
                                    <Link to="/create/job">
                                        <button type="button"
                                                className="text-lg focus:outline-none text-purple-700 bg-yellow-300 hover:bg-yellow-200 font-small rounded-lg text-lg px-5 py-2.5">
                                            {t('CreatedJobs.addFirst')}
                                        </button>
                                    </Link>
                                </div>
                        }
                        {jobs && jobs.length > 0 &&
                            <div className="flex flex-wrap content-start justify-center">
                                {jobs.map((j: any) => (
                                    <JobCard key={j.jobId} job={j}/>
                                ))}
                                <div className="grid content-center justify-center">
                                    <Link to="/jobs">
                                        <button type="button"
                                                className="text-lg focus:outline-none text-purple-700 bg-yellow-300 hover:bg-yellow-200 font-small rounded-lg text-lg px-5 py-2.5 w-52">
                                            {t('EmployerProfile.viewMore')}
                                        </button>
                                    </Link>
                                </div>
                            </div>
                        }
                        <ul role="list" className="divide-y divide-gray-300">
                            {reviews &&
                                <div className="flow-root">
                                    <h1 className="pb-3 pt-3 font-semibold">
                                        {t('EmployerProfile.reviews')}
                                    </h1>
                                    {reviews.length === 0 &&
                                        <div className="grid content-center justify-center h-5/6 mt-16">
                                            <div className="grid justify-items-center">
                                                <img src={'./images/sinEmpleadas.png'} alt="sinEmpleadas"
                                                     className="mr-3 h-6 sm:h-52"/>
                                                <p className="text-3xl font-semibold text-purple-700">
                                                    {t('EmployerProfile.noReviews')}
                                                </p>
                                            </div>
                                        </div>
                                    }
                                    {reviews.length > 0 && reviews.map((rev: any) => <ReviewCard key={rev.employee.id} review={rev}/>)}
                                </div>
                            }
                        </ul>
                    </div>
                </div>
            }
        </div>

    )
}

export default ProfileEmployer;