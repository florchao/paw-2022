import {useEffect, useState} from "react";
import {EmployerService} from "../service/EmployerService";
import {ReviewService} from "../service/ReviewService";
import ReviewCard from "../components/ReviewCard";
import {Link, useNavigate} from "react-router-dom";
import {UserService} from "../service/UserService";
import {useTranslation} from "react-i18next";
import {JobService} from "../service/JobService";
import JobCard from "../components/JobCard";
import PaginationButtons from "../components/PaginationButtons";
import bin from "../assets/bin.png";
import noEmployees from "../assets/sinEmpleadas.png";
import noJobs from "../assets/sinTrabajos.png";
import user from "../assets/user.png";
import {MagnifyingGlass} from "react-loader-spinner";
import {parseLink} from "../utils/utils";
import editing from "../assets/editing.png";

export const ProfileEmployer = () => {
    const [employer, setEmployer]: any = useState()
    const [image, setImage] = useState<{image: string, version: number}>()
    const [typeError, setTypeError] = useState(false)
    const [noImage, setNoImage] = useState(false)
    const [reviews, setReviews]: any = useState(new Array(0))
    const [pages, setPages]: any = useState(0)
    const [jobs, setJobs]: any = useState(new Array(0))
    const [current, setCurrent]: any = useState(0)

    const [nextPage, setNextPage]: any = useState("")
    const [prevPage, setPrevPage]: any = useState("")

    const nav = useNavigate();
    let id  = localStorage.getItem("hogar-uid") as unknown as number

    const {t} = useTranslation();


    async function delEmployer() {
        const post = await UserService.deleteUser(employer.users)
        if (post?.status === 204) {
            localStorage.removeItem("hogar-uid")
            localStorage.removeItem("hogar-jwt")
            localStorage.removeItem("hogar-role")
            window.dispatchEvent(new Event("hogar"));
            nav('/', {replace: true})
        }
    }

    useEffect(() => {
        EmployerService.getEmployer(id).then((val) => {
                setEmployer(val)
                setImage({image: val.image, version: 1})
            }
        );
    }, [])


    useEffect(() => {
        if (employer) {
            ReviewService.getEmployerReviews(employer.reviews, 0).then(
                (rsp) => {
                    rsp?.headers.get("Total-Count") ? setPages(rsp.headers.get("Total-Count")) : setPages(0)
                    let linkHeader = rsp?.headers.get("link")
                    if (linkHeader !== null && linkHeader !== undefined) {
                        parseLink(linkHeader, setNextPage, setPrevPage)
                    }
                    if(rsp?.status === 200)
                        rsp.json().then((reviews) => {
                            setReviews(reviews)
                        })
                    else
                        setReviews([])
                }
            )
        }
        }, [employer]
    )

    useEffect(() => {
            if (employer) {
                JobService.getCreatedJobs(employer.jobs, true, 0).then(
                    async (rsp) => {
                        rsp?.status === 204 ? setJobs([]) : setJobs(await rsp?.json())
                    }
                )
            }
        }, [employer]
    )

    const changePage = async (page: number, linkUrl?: string) => {
        setReviews(null)
        setCurrent(page)
        const get = await ReviewService.getEmployerReviews(employer.reviews, page, undefined, linkUrl)
        get?.headers.get("Total-Count") ? setPages(get.headers.get("Total-Count")) : setPages(0)
        let linkHeader = get?.headers.get("link")
        if (linkHeader !== null && linkHeader !== undefined) {
            parseLink(linkHeader, setNextPage, setPrevPage)
        }
        get?.json().then((reviews) => {
            setReviews(reviews)
        })
    }

    const updateImageHandler = async (e: any) => {
        if (e.target.files.length) {
            if(!e.target.files[0].type.match("image/jpeg|image/png|image/jpg"))
                setTypeError(true)
            else {
                const put = await UserService.updateImage(e, e.target.files[0], employer.id)
                if (put?.status === 200) {
                    put.json().then((rsp) => {
                        setImage({image: rsp.value, version: image!.version + 1})
                    })
                }
                setTypeError(false);
            }
        }
    }

    const addImageHandler = async (e: any) => {
        if (e.target.files.length) {
            if (!e.target.files[0].type.match("image/jpeg|image/png|image/jpg"))
                setTypeError(true)
            else {
                const post = await UserService.addImage(e, e.target.files[0], employer.id)
                if (post?.status === 201) {
                   setImage({image: post.headers.get("Location")!, version: 0})

                }
                setNoImage(false)
                setTypeError(false)
            }
        }
    }

    return (
        <div className="grid overflow-auto h-screen grid-cols-6">
            {!employer &&
                <div className={'flex items-center justify-center h-screen w-screen'}>
                    <MagnifyingGlass
                        visible={true}
                        height="160"
                        width="160"
                        ariaLabel="MagnifyingGlass-loading"
                        wrapperStyle={{}}
                        wrapperClass="MagnifyingGlass-wrapper"
                        glassColor = '#c0efff'
                        color = '#e5de00'
                    />
                </div>
            }
            {employer &&
                <div className=" grid grid-row-4 col-span-4 col-start-2 h-fit">
                    <div className=" bg-gray-200 rounded-3xl p-5 mt-24 mb-5 shadow-2xl">
                        <div className="grid grid-cols-5 justify-center">
                            <div className="row-span-3 col-span-2 ml-6 mr-6 mb-6 justify-self-center">
                                <div className="row-span-3 col-span-2 ml-6 mr-6 mb-6 justify-self-center">
                                    <img className="object-cover mb-3 w-52 h-52 rounded-full shadow-lg" key={image!.version} src={image!.image}
                                         alt="profile photo" onError={() => {
                                        setImage({image: user, version: -1});
                                        setNoImage(true)
                                    }}/>
                                    <form>
                                        <div className={"cursor-pointer grid grid-rows-1 grid-cols-3 text-sm w-full focus:outline-none text-white bg-violet-400 hover:bg-violet-700 font-small rounded-full text-sm px-5 py-2.5 items-center"}>
                                            <img src={editing} alt="edit" className="mr-3 h-6"/>
                                            <label htmlFor="upload-button" className=" col-start-2 col-span-2 cursor-pointer">
                                                {noImage? t('Profile.addImage') : t('Profile.editImage')}
                                            </label>
                                        </div>
                                        <input
                                            type="file"
                                            id="upload-button"
                                            style={{display: "none"}}
                                            onChange={noImage? addImageHandler : updateImageHandler}
                                        />
                                        {typeError &&
                                            <p className="block mb-2 text-sm font-medium text-red-700 margin-top: 1.25rem">{t('Profile.typeError')}</p>
                                        }
                                    </form>
                                </div>
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
                                        <img src={bin} alt="bin"
                                             className="mr-3 h-6 sm:h-5 col-start-1"/>
                                        <p className="col-span-2">
                                            {t('EmployerProfile.delete')}
                                        </p>
                                    </div>
                                </button>
                            </div>
                        </div>
                        {!jobs &&
                            <div className={'flex items-center justify-center h-3/4'}>
                                <MagnifyingGlass
                                    visible={true}
                                    height="160"
                                    width="160"
                                    ariaLabel="MagnifyingGlass-loading"
                                    wrapperStyle={{}}
                                    wrapperClass="MagnifyingGlass-wrapper"
                                    glassColor = '#c0efff'
                                    color = '#e5de00'
                                />
                            </div>
                        }
                        {jobs &&
                            <h1 className="pb-3 pt-3 font-semibold">
                                {t('EmployerProfile.jobs')}
                            </h1>
                        }
                        {jobs && jobs.length === 0 &&
                                <div className="grid justify-items-center">
                                    <img src={noJobs} alt="sinTrabajos"
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
                            {!reviews &&
                                <div className={'flex items-center justify-center h-3/4'}>
                                    <MagnifyingGlass
                                        visible={true}
                                        height="160"
                                        width="160"
                                        ariaLabel="MagnifyingGlass-loading"
                                        wrapperStyle={{}}
                                        wrapperClass="MagnifyingGlass-wrapper"
                                        glassColor = '#c0efff'
                                        color = '#e5de00'
                                    />
                                </div>
                            }
                            {reviews &&
                                <div className="flow-root">
                                    <h1 className="pb-3 pt-3 font-semibold">
                                        {t('EmployerProfile.reviews')}
                                    </h1>
                                    {reviews.length === 0 &&
                                        <div className="grid content-center justify-center h-5/6 mt-16">
                                            <div className="grid justify-items-center">
                                                <img src={noEmployees} alt="sinEmpleadas"
                                                     className="mr-3 h-6 sm:h-52"/>
                                                <p className="text-3xl font-semibold text-purple-700">
                                                    {t('EmployerProfile.noReviews')}
                                                </p>
                                            </div>
                                        </div>
                                    }
                                    {reviews.length > 0 &&
                                        <div >
                                            {reviews.map((rev: any) => <ReviewCard key={rev.employee.id}
                                                                                   review={rev}/>)}
                                            <PaginationButtons
                                                changePages={changePage}
                                                pages={pages}
                                                current={current}
                                                nextPage={nextPage}
                                                prevPage={prevPage}
                                            />
                                        </div>
                                    }
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