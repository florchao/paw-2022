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
import {Contacts} from "./views/Contacts";

export default function App() {
    return (
        <Router>
            <Navbar />
            <Background />
            <Routes>
                <Route path="/contact/employee" element={<ContactEmployee/>}/>
                <Route path="/contacts" element={<Contacts/>}/>
                <Route path="/profile" element={<ProfileEmployee/>}/>
                <Route path="/login" element={<Login/>}/>
                <Route path="/register" element={<Register/>}/>
                <Route path="/explore" element={<Explore/>}/>
                <Route path="/contact" element={<ContactUs/>}/>
                <Route path="/home" element={<EmployerLanding/>}/>
                <Route path="/" element={<Home/>}/>
                <Route path="*" element={<NotFound/>}/>
            </Routes>
        </Router>
    )
}

