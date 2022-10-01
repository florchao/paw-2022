import './style.css'
import RegisterCard from "../components/RegisterCard";
import Background from "../components/Background/Background";
import Button from "../components/Button";

const Register = () => {
    return (
        <body>
            <Background />
            <div className="grid content-start h-screen overflow-auto pl-5 pr-5">
                <div id="roles" className="grid grid-cols-3 items-start p-10 gap-y-7 mt-9">
                    <div className="h-96 w-96">
                        <img className="object-cover w-full h-full rounded-full"
                             src={ './images/empleador.jpg' } alt="employer"/>
                    </div>
                    <div className="col-span-2 ">
                        <h3 className="text-2xl text-purple-700 justify-self-center">
                            Empleador
                        </h3>
                        <p className="font-thin text-lg mt-7">
                            Si sos un empleador vas a poder encontrar a la persona perfecta para que limpie tu casa, cuide tus mascotas ¡y mucho más! Tenemos dos maneras de ayudarte a encontrar tu mejor opción: <br/><br/> - Creando una publicación según tus preferencias y necesidades. <br/> - Eligiendo en nuestra comunidad el perfil que encaje con lo que estes buscando y a la vez vas a poder ver opiniones de otros empleadores.
                        </p>
                        <div className="pb-4 grid col-start-2 col-span-2 mt-7">
                            {/*TODO pagina registerEmployer*/}
                            <Button link="/registerEmployer" name="Crear cuenta de empleador"/>
                        </div>
                    </div>
                    <div className="row-start-2 h-96 w-96">
                        <img className="object-cover w-full h-full rounded-full" src={ './images/empl.jpg' }
                             alt="employee"/>
                    </div>
                    <div className="row-start-2 col-span-2">
                        <h3 className="text-2xl text-purple-700 justify-self-center">
                            Empleada
                        </h3>
                        <p className="font-thin text-lg mt-7">
                            Si estás buscando trabajo, este es el lugar perfecto para vos. Te ayudaremos para que puedas: <br/><br/> - Cargar tu perfil con tus habilidades, disponibilidad e información para que nuestra comunidad de empleadores te pueda ver. <br/> - Explorar las distintas ofertas de trabajo ofrecidas en nuestra comunidad.
                        </p>
                        <div className="pb-4 grid col-start-2 col-span-2 mt-7">
                            {/*TODO pagina registerEmployee*/}
                            <Button link="/registerEmployee" name="Crear cuenta de empleada"/>
                        </div>
                    </div>
                </div>
            </div>
            <RegisterCard />
             {/*Aca podria ir un <RegisterCard> component*/}
        </body>
    )
}

export default Register;