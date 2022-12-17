import {useTranslation} from "react-i18next";

const OkFeedback = (type: any) => {

    const {t} = useTranslation();

    return (
        <div id="sent" className="absolute bottom-6 inset-1/3 animated fadeOut">
            <div className="grid justify-items-center bg-purple-400 rounded h-1/3 p-5">
                <h1 className="text-2xl font-semibold text-white">
                    {t('Feedback.congrats')}
                </h1>
                <p className="font-light text-white">
                    {type === "job"? t('Feedback.sentJob') : t('Feedback.sentContact')}
                </p>
            </div>
        </div>
    )
}

export default OkFeedback