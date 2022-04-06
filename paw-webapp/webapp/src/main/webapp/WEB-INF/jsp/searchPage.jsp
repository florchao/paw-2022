<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en" class="scroll-smooth">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
    <title>Buscar</title>
</head>
<body>
<nav class="bg-white absolute w-full px-2 sm:px-4 py-2.5 dark:bg-violet-500">
    <div class="container flex flex-row items-center mx-auto">
        <!-- La imagen tiene que estar en una carpeta assets -->
        <img src="/docs/images/logo.svg" class="mr-3 h-6 sm:h-9">
        <span class="text-xl font-semibold whitespace-nowrap dark:text-white">Hogar</span>
    </div>
</nav>

<div class="grid grid-cols-5">
    <div class="grid grid-rows-6">
        <div class="grid content-start bg-purple-300 row-start-2 row-end-6 pt-5 rounded-r-2xl">
            <h1 class="pl-2 font-semibold  text-gray-500">Filtrar</h1>
            <div id="accordion-open" data-accordion="open">
                <h2 id="accordion-open-heading-1">
                    <button type="button" class="flex justify-between items-center p-5 w-full text-left text-gray-500" data-accordion-target="#accordion-open-body-1" aria-expanded="false" aria-controls="accordion-open-body-1">
                        <span class="flex items-center">Habilidades</span>
                    </button>
                </h2>
                <h2 id="accordion-open-heading-2">
                    <button type="button" class="flex justify-between items-center p-5 w-full text-left text-gray-500 " data-accordion-target="#accordion-open-body-1" aria-expanded="false" aria-controls="accordion-open-body-1">
                        <span class="flex items-center">Jornada</span>
                    </button>
                </h2>
                <h2 id="accordion-open-heading-3">
                    <button type="button" class="flex justify-between items-center p-5 w-full text-left text-gray-500 " data-accordion-target="#accordion-open-body-1" aria-expanded="false" aria-controls="accordion-open-body-1">
                        <span class="flex items-center">Zona</span>
                    </button>
                </h2>
            </div>
        </div>
    </div>
    <div class="grid content-start col-span-4 h-screen overflow-auto pl-5 pr-5">
        <a href="#" class="flex flex-col items-center bg-white rounded-lg border shadow-md  mt-20 mb-5 md:flex-row md:max-w-full hover:bg-gray-100 ">
            <img class="object-cover w-full h-96 rounded-t-lg md:h-auto md:w-48 md:rounded-none md:rounded-l-lg" src="/docs/images/blog/image-4.jpg" alt="">
            <div class="flex flex-col justify-between p-4 leading-normal">
                <h5 class="mb-2 text-2xl font-bold tracking-tight text-black">Sol Konfederak</h5>
                <p class="mb-3 font-normal text-gray-700 dark:text-gray-400">Capital Federal - Barrio Norte</p>
                <p class="mb-3 font-normal text-gray-700 dark:text-gray-400">10+ experiencia</p>
            </div>
        </a>
        <a href="#" class="flex flex-col items-center bg-white rounded-lg border shadow-lg mb-5 md:flex-row md:max-w-full hover:bg-gray-100 w-full">
            <img class="object-cover w-full h-96 rounded-t-lg md:h-auto md:w-48 md:rounded-none md:rounded-l-lg" src="/docs/images/blog/image-4.jpg" alt="">
            <div class="flex flex-col justify-between p-4 leading-normal">
                <h5 class="mb-2 text-2xl font-bold tracking-tight text-black">Sol Konfederak</h5>
                <p class="mb-3 font-normal text-gray-700 dark:text-gray-400">Capital Federal - Barrio Norte</p>
                <p class="mb-3 font-normal text-gray-700 dark:text-gray-400">10+ experiencia</p>
            </div>
        </a>
        <a href="#" class="flex flex-col items-center bg-white rounded-lg border shadow-lg mb-5 md:flex-row md:max-w-full hover:bg-gray-100 w-full">
            <img class="object-cover w-full h-96 rounded-t-lg md:h-auto md:w-48 md:rounded-none md:rounded-l-lg" src="/docs/images/blog/image-4.jpg" alt="">
            <div class="flex flex-col justify-between p-4 leading-normal">
                <h5 class="mb-2 text-2xl font-bold tracking-tight text-black">Sol Konfederak</h5>
                <p class="mb-3 font-normal text-gray-700 dark:text-gray-400">Capital Federal - Barrio Norte</p>
                <p class="mb-3 font-normal text-gray-700 dark:text-gray-400">10+ experiencia</p>
            </div>
        </a>
        <a href="#" class="flex flex-col items-center bg-white rounded-lg border shadow-lg  mb-5 md:flex-row md:max-w-full hover:bg-gray-100 w-full">
            <img class="object-cover w-full h-96 rounded-t-lg md:h-auto md:w-48 md:rounded-none md:rounded-l-lg" src="/docs/images/blog/image-4.jpg" alt="">
            <div class="flex flex-col justify-between p-4 leading-normal">
                <h5 class="mb-2 text-2xl font-bold tracking-tight text-black">Sol Konfederak</h5>
                <p class="mb-3 font-normal text-gray-700 dark:text-gray-400">Capital Federal - Barrio Norte</p>
                <p class="mb-3 font-normal text-gray-700 dark:text-gray-400">10+ experiencia</p>
            </div>
        </a>
        <a href="#" class="flex flex-col items-center bg-white rounded-lg border shadow-lg  mb-5 md:flex-row md:max-w-full hover:bg-gray-100 w-full">
            <img class="object-cover w-full h-96 rounded-t-lg md:h-auto md:w-48 md:rounded-none md:rounded-l-lg" src="/docs/images/blog/image-4.jpg" alt="">
            <div class="flex flex-col justify-between p-4 leading-normal">
                <h5 class="mb-2 text-2xl font-bold tracking-tight text-black">Sol Konfederak</h5>
                <p class="mb-3 font-normal text-gray-700 dark:text-gray-400">Capital Federal - Barrio Norte</p>
                <p class="mb-3 font-normal text-gray-700 dark:text-gray-400">10+ experiencia</p>
            </div>
        </a>

    </div>
</div>
</body>
</html>