import './style.css'
import Button from "../components/Button";

export const EmployerLanding = () => {
    return (
        <html>
        <body>
        <div className="area absolute">
            <ul className="circles">
                <li></li>
                <li></li>
                <li></li>
                <li></li>
                <li></li>
                <li></li>
                <li></li>
                <li></li>
                <li></li>
                <li></li>
            </ul>
        </div>
        <div className="flex h-screen overflow-auto pl-5 pr-5 justify-center">
            <div id="roles" className="grid grid-cols-2 items-start p-10 gap-y-7 mt-9 content-center">
                <div className="grid grid-cols-3">
                    <div className="h-48 w-48">
                        <img className="object-cover w-full h-full rounded-full" src={ './images/searchJob.jpeg'} alt="employer"/>
                    </div>
                    <div className="col-span-2 ">
                        <h3 className="text-2xl text-purple-700 justify-self-center">
                            ¡Busca una empleada!
                        </h3>
                        <p className="font-thin text-lg mt-7">
                            Elige de nuestra comunidad el perfil que encaje con lo que estes buscando
                        </p>
                        <div className="pb-4 grid col-start-2 col-span-2 mt-7">
                            <Button name="Buscar Empleadas" link="/explore"/>
                        </div>
                    </div>
                </div>
                <div className="col-start-2 grid grid-cols-3">
                    <div className="h-48 w-48">
                        <img className="object-cover w-full h-full rounded-full" src={ './images/joboffer.jpeg' } alt="job offer picture"/>
                    </div>
                    <div className="col-span-2">
                        <h3 className="text-2xl text-purple-700 justify-self-center">
                            ¡Publica un trabajo!
                        </h3>
                        <p className="font-thin text-lg mt-7">
                            Publique una oferta de trabajo y espere a que nuestra comunidad aplique al mismo
                        </p>
                        <div className="pb-4 grid col-start-2 col-span-2 mt-7">
                            <Button name="Mis trabajos publicados" link="/myjobs"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        </body>

        </html>
    )
}

export default EmployerLanding;