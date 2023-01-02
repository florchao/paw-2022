import {Rating} from "react-simple-star-rating";
import {useTranslation} from "react-i18next";

const RatingModal = ({employee, handleRating}: {employee: any, handleRating: any}) => {
    const {t} = useTranslation();


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
                    {t('Rating.thanks')}
                </p>
            </div>
        </div>
    )
}

export default RatingModal