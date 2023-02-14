import './index.css'
import {
    BrowserRouter as Router,
    Routes,
    Route,
} from 'react-router-dom'
import Login from "./views/Login";
import Home from "./views/Home"
import Register from "./views/Register";
import Navbar from './components/Navbar';
import Explore from "./views/Explore";
import ContactUs from './views/ContactUs';
import Background from './components/Background/Background';
import NotFound from './views/NotFound';
import EmployerLanding from "./views/EmployerLanding";
import ContactEmployee from "./views/ContactEmployee";
import ProfileEmployee from "./views/ProfileEmployee";
import ProfileEmployer from "./views/ProfileEmployer";
import {Contacts} from "./views/Contacts";
import RegisterEmployer from "./views/RegisterEmployer";
import ExploreJobs from "./views/ExploreJobs";
import Job from "./views/Job";
import Applicants from "./views/Applicants";
import AppliedJobs from "./views/AppliedJobs";
import CreatedJobs from "./views/CreatedJobs";
import CreateJob from "./views/CreateJob";
import RegisterEmployee from "./views/RegisterEmployee";
import EditEmployee from "./views/EditEmployee";
import {useState} from "react";

export default function App() {

    const [role, setRole] = useState<string>(localStorage.getItem("hogar-role")?
        localStorage.getItem("hogar-role") as string
        :
        "ANONYMOUS")

    window.addEventListener('hogar', () => {
        console.log(localStorage.getItem("hogar-role"))
        setRole(localStorage.getItem("hogar-role")?
            localStorage.getItem("hogar-role") as string
            :
            "ANONYMOUS")
    })

    return (
        <Router>
            <Navbar />
            <Background />
            {role &&
                <Routes>
                    {role === "EMPLOYER" &&
                        <Route path="/contact/employee/:id" element={<ContactEmployee/>}/>
                    }
                    {role === "EMPLOYEE" &&
                        <Route path="/contacts" element={<Contacts/>}/>
                    }

                    <Route path="/contact" element={<ContactUs/>}/>

                    {role === "ANONYMOUS" && <Route path="/login" element={<Login/>}/>}
                    {role === "ANONYMOUS" && <Route path="/register" element={<Register/>}/>}
                    {role === "ANONYMOUS" && <Route path="/register/employer" element={<RegisterEmployer/>}/>}
                    {role === "ANONYMOUS" && <Route path="/register/employee" element={<RegisterEmployee/>}/>}

                    {role === "ANONYMOUS" && <Route path="/explore" element={<Explore/>}/>}
                    {role === "EMPLOYEE" && <Route path="/explore" element={<ExploreJobs/>}/>}
                    {role === "EMPLOYER" && <Route path="/explore" element={<Explore/>}/>}

                    {role === "EMPLOYER" && <Route path="/employee/:id" element={<ProfileEmployee/>}/>}
                    {role === "ANONYMOUS" && <Route path="/employee/:id" element={<ProfileEmployee/>}/>}

                    {role === "EMPLOYER" && <Route path="/profile" element={<ProfileEmployer/>}/>}
                    {role === "EMPLOYEE" && <Route path="/profile" element={<ProfileEmployee/>}/>}

                    {role === "EMPLOYEE" && <Route path="/edit" element={<EditEmployee/>}/>}

                    {role !== "ANONYMOUS" && <Route path="/job/:id" element={<Job/>}/>}
                    {role === "EMPLOYER" && <Route path="/job/applicants/:id" element={<Applicants/>}/>}
                    {role === "EMPLOYER" && <Route path="/create/job" element={<CreateJob/>}/>}

                    {role === "EMPLOYER" && <Route path="/jobs" element={<CreatedJobs/>}/>}
                    {role === "EMPLOYEE" && <Route path="/jobs" element={<AppliedJobs/>}/>}

                    {role === "ANONYMOUS" && <Route path="/" element={<Home/>}/>}
                    {role === "EMPLOYEE" && <Route path="/" element={<ExploreJobs/>}/>}
                    {role === "EMPLOYER" && <Route path="/" element={<EmployerLanding/>}/>}

                    <Route path="*" element={<NotFound/>}/>
                </Routes>
            }
        </Router>
    )
}

