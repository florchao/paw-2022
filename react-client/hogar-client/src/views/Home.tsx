import { Link } from "react-router-dom"

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
                                <Link to="/searchEmployee">
                                    {/*TODO component de Button y pagina searchEmployee*/}
                                    <button
                                        className="bg-violet-300 font-semibold hover:bg-yellow-300 shadow-lg text-violet-900 py-2 px-4 rounded-xl w-2/5 border-solid border-transparent	border-2 hover:border-purple-300">
                                        Buscar Empleadas
                                    </button>
                                </Link>
                            </div>
                            <div className="pb-4 grid col-start-2 col-span-2">
                                <Link to="/register">
                                    {/*TODO component de Button*/}
                                    <button
                                        className="bg-violet-300 font-semibold hover:bg-yellow-300 shadow-lg text-violet-900 py-2 px-4 rounded-xl w-2/5 border-solid border-transparent	border-2 hover:border-purple-300">
                                        Registarse
                                    </button>
                                </Link>
                            </div>
                            <div className="grid col-start-2 col-span-2">
                                <Link to="/login">
                                    {/*TODO component de Button*/}
                                    <button
                                        className="bg-violet-300 font-semibold hover:bg-yellow-300 shadow-lg text-violet-900 py-2 px-4 rounded-xl w-2/5 border-solid border-transparent border-2 hover:border-purple-300">
                                        Iniciar Sesión
                                    </button>
                                </Link>
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