import {useEffect, useState} from "react";
import {EmployerService} from "../service/EmployerService";
import {EmployeeService} from "../service/EmployeeService";
import {ReviewService} from "../service/ReviewService";
import ReviewCard from "../components/ReviewCard";
import {useNavigate} from "react-router-dom";

export const ProfileEmployer = () => {
    const [employer, setEmployer]: any = useState()
    const [image, setImage]: any = useState()
    const [reviews, setReviews]: any = useState(new Array(0))
    const nav = useNavigate();


    //todo cambiar el numero de id
    function delEmployer() {
        EmployerService.deleteEmployer(2).then(() => {
                nav('/', {replace: true})
            }
        );
    }

    useEffect(() => {
        //todo harcodeado el numero de id
        EmployerService.getEmployer(2).then((val) => setEmployer(val));
    }, [])

    //todo harcoded el id de la imagen
    useEffect(() => {
        EmployeeService.loadImage(2).then(
            (img) => {
                if (img.size == 0)
                    setImage("./images/user.png")
                else
                    setImage(URL.createObjectURL(img))
            });
    }, [])

    //todo harcoded el id de las reviews
    useEffect(() => {
            ReviewService.getEmployerReviews(2).then(
                (rsp) => {
                    setReviews(rsp)
                }
            )
        }, []
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
                                                Borrar perfil
                                            </p>
                                        </div>
                                    </button>
                            </div>
                        </div>
                        <ul role="list" className="divide-y divide-gray-300">
                        {reviews &&
                        <div className="flow-root">
                            <h1 className="pb-3 pt-3 font-semibold">
                                Opiniones
                            </h1>
                            {reviews.length === 0 &&
                                <div className="grid content-center justify-center h-5/6 mt-16">
                                    <div className="grid justify-items-center">
                                        <img src={'./images/sinEmpleadas.png'} alt="sinEmpleadas"
                                             className="mr-3 h-6 sm:h-52"/>
                                        <p className="text-3xl font-semibold text-purple-700">
                                            No hay opiniones
                                        </p>
                                    </div>
                                </div>
                            }
                            {reviews.length > 0 && reviews.map((rev: any) => <ReviewCard review={rev}/>)}
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