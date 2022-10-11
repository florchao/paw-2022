import './style.css'
import Button from "../components/Button";
import {useState} from "react";

export const ContactUs = () => {

    const [name, setName] = useState('');
    const [content, setContent] = useState('');
    const [mail, setMail] = useState('');

    const handleSubmit = (e: any) => {
        e.preventDefault();
        const blog = { name, mail, content };

        fetch('http://localhost:8080/api/contact', {
            method: 'POST',
            headers: { "Content-Type": "application/json" },
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
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                        className="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-blue-500 focus:border-violet-500"
                    />
                </div>
                <div className="form-group mb-6">
                    <h3>E-mail</h3>
                    <input
                        type="email"
                        required
                        value={mail}
                        onChange={(e) => setMail(e.target.value)}
                        className="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-blue-500 focus:border-violet-500"
                    />
                </div>
                <div className="form-group mb-6">
                    <h3>Message</h3>
                    <textarea
                        required
                        value={content}
                        onChange={(e) => setContent(e.target.value)}
                        className="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-violet-500 focus:border-violet-500"
                    ></textarea>
                </div>

                <button type="submit" className="text-lg w-full focus:outline-none text-violet-900 bg-purple-900 bg-opacity-30 hover:bg-purple-900 hover:bg-opacity-50 font-small rounded-lg text-sm px-5 py-2.5">Send</button>
            </div>
    </form>
</div>
    )
}

export default ContactUs;