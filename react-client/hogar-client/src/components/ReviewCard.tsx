import {useEffect, useState} from "react";
import {EmployeeService} from "../service/EmployeeService";

const ReviewCard = (review: any)=> {
    const [image, setImage]: any = useState()

    review = review.review

    useEffect(() => {
        //TODO:usar el de Employer
        EmployeeService.loadImage(review.employerId.id.id).then(
            (img) => {
                if (img.size == 0)
                    setImage("./images/user.png")
                else
                    setImage(URL.createObjectURL(img))
            }
        )
    }, [])

    return (
        <li className="py-3 px-3 sm:py-4">
            <div className="flex items-center space-x-4">
                <div className="flex-shrink-0 self-start">
                    <img className="w-8 h-8 rounded-full object-cover" src={image} alt="employer photo"/>
                </div>
                <div className="flex-1 min-w-0">
                    <p className="text-xl font-medium text-gray-900 text-ellipsis">
                        {review.review}
                    </p>
                    <div className="grid grid-cols-2">
                        <p className="text-sm text-gray-500 col-start-1">
                            {review.employerId.name}
                        </p>
                        <p className="text-sm text-gray-500 col-start-2 text-end">
                            {review.created}
                        </p>
                    </div>
                </div>
            </div>
        </li>
    )
}

export default ReviewCard;