import Button from "../components/Button";

const NotFound = () => {
    return ( 
        <html>
            <body>
                <div className = "grid content-center justify-center h-screen">
                    <div className = "grid justify-items-center">
                        <img src={ './images/warning2.png' } alt="Error" className="mr-3 h-6 sm:h-52"/>
                        <p className="text-3xl font-semibold text-purple-700">404 : Resource not found</p>
                        <br/>
                        <Button name="Go back to home page" link="/"/> 
                        {/* <button type="button" className="text-lg w-full focus:outline-none text-purple-700 bg-yellow-300 hover:bg-yellow-200 font-small rounded-lg text-lg px-5 py-2.5" onclick="history.back()"><spring:message code="404.goBack"/></button> */}
                    </div>
                </div>
            </body>
        </html>
     );
}
 
export default NotFound;