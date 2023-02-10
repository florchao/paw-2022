import {useState} from "react";
import {useTranslation} from "react-i18next";

const PaginationButtons = ({changePages, pages, current, nextPage, prevPage }: {changePages: any ,pages: number, current?: any, nextPage?: any, prevPage?: any}) => {
    const [page, setPage] = useState(current? current : 0)

    const { t } = useTranslation();

    return (
        <div className="flex justify-center items-center">
            {page > 0 &&
                <button onClick={() => {
                        changePages(page - 1, prevPage)
                        setPage(page - 1)
                    }
                }
                        className="bg-violet-300 w-auto hover:bg-yellow-300 shadow-lg text-violet-900 py-2 px-4 rounded-xl border-solid border-transparent border-2 hover:border-purple-300">
                    <svg aria-hidden="true" className="w-4 h-5" fill="currentColor" viewBox="0 0 20 20"
                         xmlns="http://www.w3.org/2000/svg">
                        <path fillRule="evenodd"
                              d="M7.707 14.707a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414l4-4a1 1 0 011.414 1.414L5.414 9H17a1 1 0 110 2H5.414l2.293 2.293a1 1 0 010 1.414z"
                              clipRule="evenodd"></path>
                    </svg>
                </button>
            }
            {page === 0 &&
                <div className={"w-11"}></div>
            }
            {pages > 1 &&
                <p className={"mx-2"}>{page + 1} {t('Pagination.of')} {pages}</p>
            }
            {page < pages - 1 &&
                <button onClick={() => {
                        changePages(page + 1, nextPage)
                        setPage(page + 1)
                    }
                }
                        className="bg-violet-300 w-auto font-semibold hover:bg-yellow-300 shadow-lg text-violet-900 py-2 px-4 rounded-xl border-solid border-transparent border-2 hover:border-purple-300">
                <svg aria-hidden="true" className="w-4 h-5" fill="currentColor" viewBox="0 0 20 20"
                         xmlns="http://www.w3.org/2000/svg">
                        <path fillRule="evenodd"
                              d="M12.293 5.293a1 1 0 011.414 0l4 4a1 1 0 010 1.414l-4 4a1 1 0 01-1.414-1.414L14.586 11H3a1 1 0 110-2h11.586l-2.293-2.293a1 1 0 010-1.414z"
                              clipRule="evenodd"></path>
                    </svg>
                </button>
            }
            {page === pages - 1 &&
                <div className={"w-11"}></div>
            }
        </div>
    )
}

export default PaginationButtons