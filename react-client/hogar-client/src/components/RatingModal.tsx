import {Rating} from "react-simple-star-rating";
import {RatingService} from "../service/RatingService";

const RatingModal = ({employee, handleRating}: {employee: any, handleRating: any}) => {


    return (
        <div className="modal w-96 justify-items-stretch">
            <div className="flex justify-center">
                <p className="text-lg font-semibold">
                    Rate_ {employee.name}
                </p>
            </div>
            <br></br>
            <div className="flex justify-center">
                <Rating
                    SVGclassName="inline-block"
                    onClick={handleRating}
                />
            </div>
            <br></br>
            <div className="flex justify-center">
                <p className="text-lg font-semibold">
                    Thanks!
                </p>
            </div>
        </div>
    )
}

export default RatingModal