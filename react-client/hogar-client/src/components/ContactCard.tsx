import {useEffect, useState} from "react";
import {EmployeeService} from "../service/EmployeeService";

const ContactCard = (contact: any) => {

    const [image, setImage]: any = useState()

    contact = contact.contact
    console.log(contact)


    useEffect(() => {
        //TODO:usar el de Employer
        EmployeeService.loadImage(contact.employerID.id.id).then(
            (img) => {
                if (img.size == 0)
                    setImage("./images/user.png")
                else
                    setImage(URL.createObjectURL(img))
            }
        )
    }, [])

    return (
        <div
            className="max-w-sm mb-5 mr-5 px-5 w-80 h-80 bg-white rounded-lg border border-gray-200 shadow-md overflow-hidden">
            <div className="flex justify-end px-4 pt-4">
                <p className="hidden sm:inline-block text-gray-500 text-sm p-1.5">
                    {contact && contact.created}
                </p>
            </div>
            <div className="flex flex-col items-center pb-6 ">
                <img className="object-cover mb-3 w-24 h-24 rounded-full shadow-lg"
                     src={image} alt="Employer Image"/>
                <h5 className="mb-1 text-xl font-medium text-gray-900">
                    {contact.employerID.name}
                </h5>
                {contact.message.length <= 180 &&
                    <span className="flex flex-wrap text-sm text-gray-500 text-ellipsis px-5" style={{width:"90%", display:"inline-block", wordWrap: "break-word"}}>
                        {contact.message}
                    </span>}
                {contact.message.length > 180 &&
                    <span className="flex flex-wrap text-sm text-gray-500 text-ellipsis px-5" style={{width:"90%", display:"inline-block", wordWrap: "break-word"}}>
                        {contact.message.substring(0, 180)}
                    </span>}

            </div>
        </div>
    )
}

export default ContactCard