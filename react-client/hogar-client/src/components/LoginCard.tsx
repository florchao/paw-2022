export const LoginCard = () => {
    return (
        <div className="grid grid-cols-7 space-between content-start justify-center h-screen pt-5">
        <div className="my-16 w-full col-span-7"></div>
            <form className="col-span-3 col-start-3" action="${loginUrl}" method="post">
                <div className="block p-6 rounded-lg shadow-lg bg-white">
                    <div className="form-group mb-6 grid grid-cols-6">
                        <label className="text-sm font-medium text-gray-900">E-Mail</label>
                        <input id="username" name="j_username" type="text" className=" col-span-5 block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-blue-500 focus:border-violet-500"/>
                    </div>
                    <div className="form-group mb-6 grid grid-cols-6">
                        <label className="text-sm font-medium text-gray-900">Contraseña</label>
                        <input id="password" name="j_password" type="password" className=" col-span-5 block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-blue-500 focus:border-violet-500"/>
                    </div>
                    <div className="form-group mb-6">
                                        </div>
                     <div className="form-group mb-6">
                        <label className="text-sm font-medium text-gray-900">
                        <input name="j_rememberme" type="checkbox"/>
                        Recordarme
                        </label>
                     </div>
                    <div className="form-group mb-6">
                    <button type="submit" className="text-lg w-full focus:outline-none text-violet-900 bg-purple-900 bg-opacity-30 hover:bg-purple-900 hover:bg-opacity-50 font-small rounded-lg text-sm px-5 py-2.5">Login</button>
                     </div>
                    <div className="form-group mb-6">
                        <p className="text-sm font-semibold text-gray-900">
                            Ya tenes una cuenta? 
                            <a className="text-violet-900">Registrate</a>
                        </p>
                    </div>
                    <div className="form-group mb-6 grid grid-cols-6">
                        <p className="text-sm font-semibold text-gray-900 col-span-3">
                            Te olvidaste tu contraseña?
                            <a className="text-violet-800">Setear contraseña</a>
                        </p>
                    </div>
                </div>
            </form>
    </div>
    )
}

export default LoginCard