import './style.css'
import Button from "../components/Button";

export const Home = () => {
    return (
        <html>
            <body>
            <div className="grid grid-cols-2 gap-4 pt-16 h-screen">
                <div className="grid grid-rows-2 gap-4 pl-8">
                    <div className="grid content-center">
                        <h3 className="text-3xl font-semibold text-purple-700">
                            ¡Bienvenido a Hogar!
                        </h3>
                        <h3 className="text-2xl text-purple-700">
                            Un lugar para conectarse y encontrar lo que estas buscando.
                        </h3>
                    </div>
                    <div className="flex flex-col">
                        <div className="grid">
                            <div className="pb-4 grid col-start-2 col-span-2">
                                <Button name="Buscar Empleadas" link="/explore"/>
                            </div>
                            <div className="pb-4 grid col-start-2 col-span-2">
                                <Button name="Registarse" link="/register"/>
                            </div>
                            <div className="grid col-start-2 col-span-2">
                                <Button name="Iniciar Sesión" link="/login"/>
                            </div>
                        </div>
                    </div>

                </div>
                <div className="pt-8">
                    <img src={ './images/indexImage.jpg' } alt="primera foto"/>
                </div>
            </div>
            </body>
        </html>
    )
}

export default Home;