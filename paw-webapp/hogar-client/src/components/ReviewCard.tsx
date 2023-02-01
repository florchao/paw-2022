import {useState} from "react";
import user from "../assets/user.png";

const ReviewCard = (review: any)=> {

    review = review.review


    const [image, setImage]: any = useState(review.employer !== undefined? review.employer.image: review.employee.image)

    return (
        <li className="py-3 px-3 sm:py-4">
            {review &&
                <div className="flex items-center space-x-4">
                    <div className="flex-shrink-0 self-start">
                        <img className="w-8 h-8 rounded-full object-cover" src={image} alt="user photo" onError={() => setImage(user)}/>
                    </div>
                    <div className="flex-1 min-w-0">
                        <p className="text-xl font-medium text-gray-900 text-ellipsis">
                            {review.review}
                        </p>
                        <div className="grid grid-cols-2">
                            <p className="text-sm text-gray-500 col-start-1">
                                {review.employer && review.employer.name}
                                {review.employee && review.employee.name}
                            </p>
                            <p className="text-sm text-gray-500 col-start-2 text-end">
                                {review.created}
                            </p>
                        </div>
                    </div>
                </div>
            }
        </li>
    )
}

export default ReviewCard;