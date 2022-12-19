import {useTranslation} from "react-i18next";

const ErrorFeedback = ({message}: {message: string}) => {

    const {t} = useTranslation();

    return (
        <div id="error" className="absolute bottom-6 inset-1/3 animated fadeOut" >
            <div className="grid justify-items-center bg-white rounded-lg h-1/3 p-5">
                <h1 className="text-2xl font-semibold text-red-700">
                    {t('Feedback.error')}
                </h1>
                <p className="font-light text-red-700">
                    {message}
                </p>
            </div>
        </div>
    )
}

export default ErrorFeedback