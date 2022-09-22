import './index.css'
import { BrowserRouter as Router,
    Routes, 
    Route } from 'react-router-dom'
import Login from "./views/Login";
import Home from "./views/Home"
import Register from "./views/Register";
import Navbar from './components/Navbar';
import SearchEmployee from "./views/SearchEmployee";

export const App = () => (
    <Router>
        <Navbar />
        <Routes>
            <Route path="/login" element={<Login/>}/>
            <Route path="/register" element={<Register/>}/>
            <Route path="/searchEmployee" element={<SearchEmployee/>}/>
            <Route path="/" element={<Home/>}/>
        </Routes>
    </Router>
)

export default App;
