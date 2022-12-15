import './index.css'
import { BrowserRouter as Router,
    Routes, 
    Route, 
    useLocation} from 'react-router-dom'
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
import NewPassword from "./views/NewPassword";
import EditEmployee from "./views/EditEmployee";

export default function App() {
    console.log(localStorage.getItem("hogar-role"))
    return (
        <Router>
            <Navbar />
            <Background />
            <Routes>
                {localStorage.getItem("hogar-role") && localStorage.getItem("hogar-role") == "EMPLOYER" &&
                    <Route path="/contact/employee" element={<ContactEmployee/>}/>
                }
                {localStorage.getItem("hogar-role") && localStorage.getItem("hogar-role") == "EMPLOYEE" &&
                    <Route path="/contacts" element={<Contacts/>}/>
                }
                
                <Route path="/contact" element={<ContactUs/>}/>
                
                <Route path="/login" element={<Login/>}/>
                <Route path="/newPassword" element={<NewPassword/>}/>
                <Route path="/register" element={<Register/>}/>
                <Route path="/register/employer" element={<RegisterEmployer/>}/>
                <Route path="/register/employee" element={<RegisterEmployee/>}/>

                {!localStorage.getItem("hogar-role") && <Route path="/explore" element={<Explore/>}/>}
                {localStorage.getItem("hogar-role") && localStorage.getItem("hogar-role") == "EMPLOYEE" && <Route path="/explore" element={<ExploreJobs/>}/>}
                {localStorage.getItem("hogar-role") && localStorage.getItem("hogar-role") == "EMPLOYER" && <Route path="/explore" element={<Explore/>}/>}
                
                {localStorage.getItem("hogar-role") && localStorage.getItem("hogar-role") == "EMPLOYER" && <Route path="/employee" element={<ProfileEmployee/>}/>}
                {!localStorage.getItem("hogar-role") && <Route path="/employee" element={<ProfileEmployee/>}/>}

                {localStorage.getItem("hogar-role") && localStorage.getItem("hogar-role") == "EMPLOYER" && <Route path="/profile" element={<ProfileEmployer/>}/>}
                {localStorage.getItem("hogar-role") && localStorage.getItem("hogar-role") == "EMPLOYEE" && <Route path="/profile" element={<ProfileEmployee/>}/>}

                {localStorage.getItem("hogar-role") && localStorage.getItem("hogar-role") == "EMPLOYEE" && <Route path="/edit" element={<EditEmployee/>}/>}
                
                <Route path="/job" element={<Job/>}/>
                {localStorage.getItem("hogar-role") && localStorage.getItem("hogar-role") == "EMPLOYER" && <Route path="/job/applicants" element={<Applicants/>}/>}
                {localStorage.getItem("hogar-role") && localStorage.getItem("hogar-role") == "EMPLOYER" &&<Route path="/create/job" element={<CreateJob/>}/>}
                
                {localStorage.getItem("hogar-role") && localStorage.getItem("hogar-role") == "EMPLOYER" && <Route path="/jobs" element={<CreatedJobs/>}/>}
                {localStorage.getItem("hogar-role") && localStorage.getItem("hogar-role") == "EMPLOYEE" && <Route path="/jobs" element={<AppliedJobs/>}/>}
                
                {!localStorage.getItem("hogar-role") && <Route path="/" element={<Home/>}/>}
                {localStorage.getItem("hogar-role") && localStorage.getItem("hogar-role") == "EMPLOYEE" && <Route path="/" element={<ExploreJobs/>}/>}
                {localStorage.getItem("hogar-role") && localStorage.getItem("hogar-role") == "EMPLOYER" && <Route path="/" element={<EmployerLanding/>}/>}
                
                <Route path="*" element={<NotFound/>}/>
            </Routes>
        </Router>
    )
}

