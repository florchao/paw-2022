import {useEffect, useState} from "react";
import {UserService} from "../service/UserService";
import {Link} from "react-router-dom";
import {useTranslation} from "react-i18next";
import {ApplicantService} from "../service/ApplicantService";

const ApplicantCard = (applicant: any) =>{
    const a = applicant.applicant
    const [image, setImage]: any = useState()
    const [status, setStatus] = useState<string>(a.status)
    const { t } = useTranslation();

    async function acceptApplicant(){
        const s = await ApplicantService.updateStatus(a.employee.id, a.jobId, 1)
        setStatus(s)
    }

    async function rejectApplicant(){
        const s = await ApplicantService.updateStatus(a.employee.id, a.jobId, 2)
        setStatus(s)
    }
    useEffect(() => {
        UserService.loadImage(a.employee.image).then(
            (img) => {
                if (img.size == 0)
                    setImage("../images/user.png")
                else
                    setImage(URL.createObjectURL(img))
            }
        )
    }, [])
    return(
        <li className="py-3 sm:py-4 hover:bg-gray-300 rounded">
                <div className="flex items-center space-x-4">

                    <div className="flex-shrink-0">
                        <img className="w-8 h-8 rounded-full object-cover" src={image} alt="employee photo"/>
                    </div>
                    <div className="flex-1 min-w-0">
                        <Link to={"/employee"} state={{self: a.employee.self, image: a.employee.image}}>

                        <p className="text-xl font-medium text-gray-900 truncate">
                            {a.employee.name}
                        </p>
                        <p className="text-sm text-gray-500 truncate">
                            {a.mail}
                        </p>
                        </Link>

                    </div>

                    {status == '1' &&
                        <p className="font-semibold text-lg text-green-400 px-8">{t('Applicants.accepted')}</p>
                    }
                    {status == '2' &&
                        <p className="font-semibold text-lg text-rose-400 px-8">{t('Applicants.rejected')}</p>
                    }
                    {status == '0' &&
                        <div>
                            <button onClick={acceptApplicant} className="h-fit w-fit text-xs text-purple-900 bg-green-300 border border-purple-900 hover:bg-green-200 focus:outline-none focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2">
                                {t('Applicants.accept')}
                            </button>
                            <button onClick={rejectApplicant} className="h-fit w-fit text-xs text-purple-900 bg-red-300 border border-purple-900 hover:bg-red-200 focus:outline-none focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2">
                                {t('Applicants.reject')}
                            </button>
                        </div>

                    }
                </div>
                </li>
    )
}

export default ApplicantCard;