import {useEffect, useState} from "react";
import {useLocation} from "react-router-dom";
import {EmployerService} from "../service/EmployerService";

export const ProfileEmployer = () => {

    const [employer, setEmployer]: any = useState()

    const {id} = useLocation().state

    console.log(id)

    useEffect(() => {
        const algo = async () => {
            const val = await EmployerService.getEmployer(id);
            console.log(val)
            setEmployer(val)
        }
        algo()
    }, [])

    return (
        <div className="grid overflow-auto h-screen grid-cols-6">
            <div className=" grid grid-row-4 col-span-4 col-start-2 h-fit">
                <div className=" bg-gray-200 rounded-3xl p-5 mt-24 mb-5 shadow-2xl">
                    <div className="grid grid-cols-5 justify-center">
                        <div className="row-span-3 col-span-2 ml-6 mr-6 mb-6 justify-self-center">
                            <img className="object-cover mb-3 w-52 h-52 rounded-full shadow-lg" src="${image}"/>
                                 {/*onerror="this.src = '<c:url value="/public/user.png"/>'" alt="profile pic"*/}
                        </div>
                        <div className="ml-3 col-span-2">
                            <p className="text-2xl font-semibold whitespace-nowrap text-ellipsis overflow-hidden">
                                {employer.name}
                            </p>
                        </div>
                        <div className="ml-3 col-start-5 row-start-2">
                            <form action="${deletePath}" method="delete">
                                <button type="submit"
                                        className="text-sm focus:outline-none text-white bg-red-500 hover:bg-red-700 font-small rounded-lg text-sm px-5 py-2.5">
                                    <div className="grid grid-rows-1 grid-cols-3">
                                        <img src="<c:url value='/public/bin.png'/>" alt="bin"
                                             className="mr-3 h-6 sm:h-5 col-start-1"/>
                                            <p className="col-span-2">
                                                Borrar perfil
                                            </p>
                                    </div>
                                </button>
                            </form>
                        </div>
                    </div>
                    <div className="flow-root">
                        <h1 className="pb-3 pt-3 font-semibold">
                            Opiniones
                        </h1>
                        {employer.reviewList.length == 0 && (
                                <div className="grid content-center justify-center h-5/6 mt-16">
                                    <div className="grid justify-items-center">
                                        <img src="<c:url value='/public/sinEmpleadas.png'/>" alt="sinEmpleadas"
                                             className="mr-3 h-6 sm:h-52"/>
                                            <p className="text-3xl font-semibold text-purple-700">
                                                No hay opiniones
                                            </p>
                                    </div>
                                </div>
                        )}
                        {employer.reviewList.length != 0 && (
                            <p className="text-3xl font-semibold text-purple-700">
                                No hay opiniones
                            </p>
                            /*
                                <ul role="list" className="divide-y divide-gray-300">
                                    <c:forEach var="review" items="${ReviewList}">
                                        <c:url value="/user/profile-image/${review.employeeId.id.id}" var="image"/>
                                        <li className="py-3 sm:py-4">
                                            <div className="flex items-center space-x-4">
                                                <div className="flex-shrink-0 self-start">
                                                    <img className="w-8 h-8 rounded-full object-cover" src="${image}"
                                                         alt="Employee Photo"
                                                         onerror="this.src = '<c:url value="/public/user.png"/>'"/>
                                                </div>
                                                <div className="flex-1 min-w-0">
                                                    <p className="text-xl font-medium text-gray-900 text-ellipsis">
                                                        <c:out value="${review.review}"/>
                                                    </p>
                                                    <div className="grid grid-cols-2">
                                                        <p className="text-sm text-gray-500 col-start-1">
                                                            <c:out value="${review.employeeId.name}"/>
                                                        </p>
                                                        <p className="text-sm text-gray-500 col-start-2 text-end">
                                                            <c:out value="${review.created}"/>
                                                        </p>
                                                    </div>
                                                </div>
                                            </div>
                                        </li>
                                    </c:forEach>
                                    <c:url value="/verPerfil" var="getPath"/>
                                    <form method="get" action="${getPath}">
                                        <c:if test="${maxPage > 0 && page + 1 <= maxPage}">
                                            <div className="flex flex-row justify-center mt-4">
                                                <c:choose>
                                                    <c:when test="${page < 1}">
                                                        <button type="submit"
                                                                className="font-semibold border shadow-md focus:outline-none text-violet-900 bg-gray-300 border-purple-900 rounded-lg px-2"
                                                                disabled="true" onclick="previousPage(${page})">
                                                            <
                                                        </button>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <button type="submit"
                                                                className="font-semibold border shadow-md focus:outline-none text-violet-900 bg-purple-400 border-purple-900 hover:bg-yellow-300 hover:bg-opacity-50 rounded-lg px-2"
                                                                onclick="previousPage(${page})">
                                                            <
                                                        </button>
                                                    </c:otherwise>
                                                </c:choose>
                                                <div className="bg--300 w-16 flex justify-center">
                                                    <h1 className="text-purple-900">${page + 1} of ${maxPage}</h1>
                                                </div>
                                                <c:choose>
                                                    <c:when test="${page + 1 == maxPage}">
                                                        <button type="submit"
                                                                className="font-semibold border shadow-md focus:outline-none text-violet-900 bg-gray-300 border-purple-900 rounded-lg px-2"
                                                                disabled="true" onclick="nextPage(${page})">>
                                                        </button>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <button type="submit" id="prevPageButton"
                                                                className=" font-semibold border shadow-md focus:outline-none text-violet-900 bg-purple-400 border-purple-900 hover:bg-yellow-300 hover:bg-opacity-50 rounded-lg px-2"
                                                                onclick="nextPage(${page})">>
                                                        </button>
                                                    </c:otherwise>
                                                </c:choose>
                                        </c:if>
                    </div>
                    <input style="visibility: hidden" type="number" name="page" id="pageNumber"/>
                </form>
            </ul>
                             */
                            )}
</div>
</div>
</div>
        </div>

)
}

export default ProfileEmployer;