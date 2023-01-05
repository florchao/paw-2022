import {useState} from "react";
import {useTranslation} from "react-i18next";
import user from "../assets/user.png";

const ContactCardModal = (contact: any) => {

    const { t } = useTranslation();

    contact = contact.contact

    const [image, setImage]: any = useState(contact.employer.image)

    return (
        <div id="${contact.employerID.id.id}" className="modal w-96">
            <div className="flex grid grid-cols-3 items-center py-8 w-fill">
                <img className="col-span-1 mb-3 w-24 h-24 rounded-full shadow-lg object-cover"
                     src={image} alt="employer photo" onError={() => setImage(user)}/>
                <div className="col-span-2 row-span-2">
                    <h5 className="mb-1 text-xl font-medium text-gray-900">
                        {contact.employer.name}
                    </h5>
                    <p className="text-gray-500 text-sm">
                        {contact.employer.email}
                    </p>
                    <p className="text-gray-500 text-sm">
                        {t('Contacts.phone') + " "}
                        {contact.phoneNumber}
                    </p>
                </div>
                <div className="col-span-3 h-80 overflow-y-auto py-5">
                    <span className="flex flex-wrap text-gray-500 text-justify text-ellipsis px-5"
                        style={{width:"100%",  display:"inline-block"}}>
                        {contact.message}
                    </span>
                </div>
            </div>
        </div>
    )
}

export default ContactCardModal