import {useTranslation} from "react-i18next";
import {Link} from "react-router-dom";

const JobCard  = (job: any)=> {

    const { t } = useTranslation();
    let status;
    job = job.job;
    if(job.status !== null && job.status !== undefined) {
        status = job.status
        job = job.job
    }
    return (
        <div className="max-w-sm mb-5 mr-5 w-80 h-52 bg-white rounded-lg border border-gray-200 shadow-md overflow-hidden">
            <div className="flex justify-end px-4 pt-4">
                <p className="hidden sm:inline-block text-gray-500 text-sm p-1.5">
                    {job.location}
                </p>
            </div>
            <div className="flex flex-col items-center pb-6 ">
                {job && job.title.length <= 25 &&
                    <h5 className="mb-1 text-xl font-medium text-gray-900 flex flex-wrap">
                        {job.title}
                    </h5>
                }
                {job && job.title.length > 25 &&
                    <h5 className="mb-1 text-xl font-medium text-gray-900">
                        {job.title.substring(0, 25)}
                        ...
                    </h5>
                }
                {job && job.description.length <= 60 &&
                    <span className="flex flex-wrap text-sm text-gray-500 text-ellipsis px-5"
                          style= {{width:"18rem", display:"inline-block", wordWrap: "break-word"}}>
                            {job.description}
                        </span>
                }
                {job && job.description.length > 60 &&
                    <span className="flex flex-wrap text-sm text-gray-500 text-ellipsis px-5"
                          style={{width:"18rem", display:"inline-block", wordWrap: "break-word"}}>
                            {job.description.substring(0, 60)}
                        </span>
                }
            </div>
            <div className="grid grid-col-2 w-80 justify-center">
                <div className="mb-2">
                    <Link to="/job" state={{self: job.self, id: job.id}} style={{marginRight: "15px"}} className="text-sm focus:outline-none text-violet-900 bg-purple-900 bg-opacity-30 hover:bg-purple-900 hover:bg-opacity-50 font-small rounded-lg text-sm px-5 py-2.5">
                        {t("JobCard.publication")}
                    </Link>
                    {status === undefined &&
                        <Link to="/job/applicants" state={{applicants: job.applicants, title : job.title}} className="text-sm focus:outline-none text-violet-900 bg-purple-900 bg-opacity-30 hover:bg-purple-900 hover:bg-opacity-50 font-small rounded-lg text-sm px-5 py-2.5">
                            {t("JobCard.applicants")}
                        </Link>
                    }
                    {status === 0 &&
                        <a className="text-sm focus:outline-none text-purple-900 bg-yellow-300 font-small rounded-lg text-sm px-5 py-2.5">
                            {t("JobCard.pending")}
                        </a>
                    }
                    { status === 1 &&
                        <a className="text-sm focus:outline-none text-purple-900 bg-green-300 font-small rounded-lg text-sm px-5 py-2.5">
                            {t("JobCard.accepted")}
                        </a>
                    }
                    { status === 2 &&
                        <a className="text-sm focus:outline-none text-purple-900 bg-red-300 font-small rounded-lg text-sm px-5 py-2.5">
                            {t("JobCard.denied")}
                        </a>
                    }

                </div>
            </div>
        </div>
    )
}
export default JobCard