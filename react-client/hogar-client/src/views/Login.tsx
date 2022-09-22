import React from 'react'
import LoginCard from '../components/LoginCard';
import Background from "../components/Background/Background";
const Login = () => {
    return (
        <div>
            <Background/>
            <LoginCard />
            {/*Aca podria ir un <LoginCard> component*/}
        </div>
    )
}

export default Login;