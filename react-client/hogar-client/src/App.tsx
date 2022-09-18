
import { BrowserRouter as Router,
    Routes, 
    Route } from 'react-router-dom'
import Login from "./views/Login";
import Home from "./views/Home"
import Register from "./views/Register";
import Navbar from './components/Navbar';

export const App = () => (
    <Router>
        <Navbar />
        <Routes>
            <Route path="/login" element={<Login/>}/>
            <Route path="/register" element={<Register/>}/>
            <Route path="/" element={<Home/>}/>
        </Routes>
    </Router>
)

export default App;
