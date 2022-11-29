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
import NewPassword from "./views/NewPassword";

export default function App() {
    return (
        <Router>
            <Navbar />
            <Background />
            <Routes>
                <Route path="/contact/employee" element={<ContactEmployee/>}/>
                <Route path="/contacts" element={<Contacts/>}/>
                <Route path="/employee" element={<ProfileEmployee/>}/>
                <Route path="/employee/jobs" element={<AppliedJobs/>}/>
                <Route path="/login" element={<Login/>}/>
                <Route path="/newPassword" element={<NewPassword/>}/>
                <Route path="/register" element={<Register/>}/>
                <Route path="/explore" element={<Explore/>}/>
                <Route path="/explore/jobs" element={<ExploreJobs/>}/>
                <Route path="/contact" element={<ContactUs/>}/>
                <Route path="/home" element={<EmployerLanding/>}/>
                <Route path="/employer" element={<ProfileEmployer/>}/>
                <Route path="/register/employer" element={<RegisterEmployer/>}/>
                <Route path="/job" element={<Job/>}/>
                <Route path="/jobs" element={<CreatedJobs/>}/>
                <Route path="/job/applicants" element={<Applicants/>}/>
                <Route path="/create/job" element={<CreateJob/>}/>
                <Route path="/" element={<Home/>}/>
                <Route path="*" element={<NotFound/>}/>
            </Routes>
        </Router>
    )
}

