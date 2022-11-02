import './style.css'
import Button from "../components/Button";
import {useState} from "react";
import exp from "constants";

export const invalidEmail = (email : String) => {
    if( email.length == 0)
        return true
    return !String(email)
        .toLowerCase()
        .match(
            /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
        );
};

export const invalidInput = (input : String) => {
    return input.length == 0;
};

export const ContactUs = () => {

    const [name, setName] = useState('');
    const [content, setContent] = useState('');
    const [mail, setMail] = useState('');

    const handleSubmit = (e: any) => {
        e.preventDefault();
        const blog = {name, mail, content};
        fetch('http://localhost:8080/api/contact/us', {
            method: 'POST',
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(blog)
        }).then(() => {
            // history.go(-1);
        })
    }
    return (
        <div className="grid grid-cols-7 content-start justify-center h-full pt-5 overflow-auto">
    <div className="my-9 w-full col-span-7"></div>
    <form className = "col-start-3 col-span-3 grid h-full w-full" onSubmit={handleSubmit}>

        <p className="text-3xl font-semibold text-violet-900 mb-4 mt-4 text-center">Contact Us</p>
            <div className="block p-6 rounded-3xl shadow-lg bg-gray-200">
                <div className="form-group mb-6">
                    <h3>Full Name</h3>
                    <input
                        type="text"
                        required
                        onInvalid={e => (e.target as HTMLInputElement).setCustomValidity("Please enter a valid name")}
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                        className="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-blue-500 focus:border-violet-500"
                    />
                    {invalidInput(name) && (
                        <text className="text-red-500">
                            Please enter a valid name
                        </text>
                    )}
                </div>
                <div className="form-group mb-6">
                    <h3>E-mail</h3>
                    <input
                        type="email"
                        required
                        value={mail}
                        onInvalid={e => (e.target as HTMLInputElement).setCustomValidity("Please enter a valid e-mail")}
                        onChange={(e) => setMail(e.target.value)}
                        className="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-blue-500 focus:border-violet-500"
                    />
                    {invalidEmail(mail) && (
                        <text className="text-red-500">
                            Please enter a valid e-mail
                        </text>
                    )}
                </div>
                <div className="form-group mb-6">
                    <h3>Message</h3>
                    <textarea
                        required
                        value={content}
                        onInvalid={e => (e.target as HTMLInputElement).setCustomValidity("Please enter a message")}
                        onChange={(e) => setContent(e.target.value)}
                        className="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-violet-500 focus:border-violet-500"
                    ></textarea>
                    {content.length == 0 && (
                        <text className="text-red-500">
                            Please enter a message
                        </text>
                    )}
                </div>

                <button type="submit" className="text-lg w-full focus:outline-none text-violet-900 bg-purple-900 bg-opacity-30 hover:bg-purple-900 hover:bg-opacity-50 font-small rounded-lg text-sm px-5 py-2.5">Send</button>
            </div>
    </form>
</div>
    )
}

export default ContactUs;