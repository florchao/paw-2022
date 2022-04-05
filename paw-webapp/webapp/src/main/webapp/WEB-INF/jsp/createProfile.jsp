<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
    <title>Crear Perfil</title>
</head>
<body>
    <nav class="bg-white absolute w-full px-2 sm:px-4 py-2.5 dark:bg-violet-500">
        <div class="container flex flex-row items-center mx-auto">
            <!-- La imagen tiene que estar en una carpeta assets -->
            <img src="/docs/images/logo.svg" class="mr-3 h-6 sm:h-9">
            <span class="text-xl font-semibold whitespace-nowrap dark:text-white">Hogar</span>
        </div>
    </nav>
    <div class="grid grid-cols-6">
        <div class=" grid grid-row-4 col-span-4 col-start-2 h-screen">
            <div class=" bg-gray-200 rounded-t-3xl overflow-auto p-5 mt-24">
                <div class="grid grid-cols-5 justify-center">
                    <div class="row-span-4 col-span-2">
                        <div class="relative w-40 h-40 overflow-hidden bg-gray-100 rounded-full">
                            <svg class="text-gray-400 -left-1" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M10 9a3 3 0 100-6 3 3 0 000 6zm-7 9a7 7 0 1114 0H3z" clip-rule="evenodd"></path></svg>
                        </div>
                    </div>
                    <div class="ml-3 col-span-3 row-span-2">
                        <label for="small-input" class="block mb-2 text-sm font-medium text-gray-900 ">Nombre y Apellido</label>
                        <input type="text" id="small-input" class="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-blue-500 focus:border-violet-500">
                    </div>
                    <div class="ml-3 col-span-3 row-span-2">
                        <label for="small-input" class="block mb-2 text-sm font-medium text-gray-900 ">Zona</label>
                        <input type="text" id="small-input" class="block p-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-violet-300 sm:text-xs focus:ring-violet-500 focus:border-violet-500">
                    </div>
                </div>
                <div>
                    <h1 class="pb-3">Experiencia</h1>
                </div>
                <div class="flex justify-center">
                    <button type="button" class="text-3xl focus:outline-none h-14 w-5/6 text-white bg-purple-900 bg-opacity-30 hover:bg-purple-900 hover:bg-opacity-50 font-medium rounded-lg text-sm px-5 py-2.5 mb-2">+</button>
                </div>
                <div>
                    <h1 class="pb-3 pt-3">Habilidades</h1>
                </div>
                <div class="flex flex-wrap">
                    <button type="button" class="h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-gray-100 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2">Cocinar</button>
                    <button type="button" class="h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-gray-100 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2">Planchar</button>
                    <button type="button" class="h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-gray-100 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2">Cuidado de niños</button>
                    <button type="button" class="h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-gray-100 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2">Cuidado de mayores</button>
                    <button type="button" class="h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-gray-100 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2">Cuidados especiales</button>
                    <button type="button" class="h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-gray-100 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2">Cuidado de mascotas</button>

                </div>
                <div>
                    <h1 class="pb-3 pt-3">Jornada</h1>
                </div>
                <div class="flex flex-wrap">
                    <button type="button" class="h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-gray-100 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2">Media jornada</button>
                    <button type="button" class="h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-gray-100 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2">Jornada completa</button>
                    <button type="button" class="h-fit w-fit text-xs text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-gray-100 focus:ring-4 focus:ring-gray-200 font-medium rounded-full text-sm px-5 py-2.5 mr-2 mb-2">Con cama</button>
                </div>
            </div>

            </div>
        </div>
    </div>

</body>
</html>