import {Link} from "react-router-dom";
import {useState} from "react";
import {useTranslation} from "react-i18next";
import {Rating} from "react-simple-star-rating";
import user from "../assets/user.png";

const EmployeeCard = (employee: any) => {
    const e = employee.employee
    const [image, setImage]: any = useState(e.image)

    const {t} = useTranslation();


    return (
        <div className="w-full col-span-5">
            <Link to={"/employee/"+e.id} state={{self: e.self, status: -1}}
                  className="grid grid-cols-9 items-center w-full bg-white rounded-lg border shadow-md mb-5 md:flex-row md:max-w-full hover:bg-gray-100 ">
                <img style={{maxHeight: 150}}
                     className="object-cover w-full h-96 rounded-t-lg md:h-auto md:w-48 md:rounded-none md:rounded-l-lg col-start-1 col-span-1"
                     src={image} alt="user photo" onError={() => setImage(user)}/>
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
                    }
                </div>
            </Link>
        </div>
    )
}

export default EmployeeCard;