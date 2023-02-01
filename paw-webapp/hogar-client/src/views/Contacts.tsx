import {useTranslation} from "react-i18next";
import {useEffect, useState} from "react";
import {ContactService} from "../service/ContactService";
import {useLocation} from "react-router-dom";
import ContactCard from "../components/ContactCard";
import ContactCardModal from "../components/ContactCardModal";
import Modal from "react-modal";
import PaginationButtons from "../components/PaginationButtons";
import noEmployees from "../assets/sinEmpleadas.png";
import {MagnifyingGlass} from "react-loader-spinner";
import {parseLink} from "../utils/utils";

export const Contacts = () => {

    const { t } = useTranslation();
    const [contacts, setContacts]: any = useState()
    const [pages, setPages]: any = useState(0)
    const [modalData, setModalData]: any = useState()
    const [current, setCurrent]: any = useState(0)
    const [nextPage, setNextPage]: any = useState("")
    const [prevPage, setPrevPage]: any = useState("")


    const {id} = useLocation().state

    Modal.setAppElement('#root')

    const [modalIsOpen, setIsOpen] = useState(false);

    function openModal() {
        setIsOpen(true);
    }

    function closeModal() {
        setIsOpen(false);
    }

    useEffect(() => {
        changePage(0)
    }, [])

    const changePage = (page: number, linkUrl?: string) => {
        setContacts(null)
        setCurrent(page)
        ContactService.contacts(id, page, linkUrl).then(
            (rsp) => {
                rsp.headers.get("X-Total-Count") ? setPages(rsp.headers.get("X-Total-Count")) : setPages(0)
                let linkHeader = rsp.headers.get("link")
                if (linkHeader !== null) {
                    parseLink(linkHeader, setNextPage, setPrevPage)
                }
                if(rsp.status === 200)
                    rsp.json().then((conts: any) => {
                        setContacts(conts)
                    })
                else
                    setContacts([])
            }
        );
    }

    return (
        <div className="grid content-start h-screen overflow-auto pl-5 pr-5" id="main">
            <div className="my-9 w-full"></div>
            <p className="text-3xl font-semibold text-violet-900 mb-4 mt-4 text-center">
                {t('Contacts.title')}
            </p>
            {!contacts &&
                <div className={'flex items-center justify-center h-3/4'}>
                    <MagnifyingGlass
                        visible={true}
                        height="160"
                        width="160"
                        ariaLabel="MagnifyingGlass-loading"
                        wrapperStyle={{}}
                        wrapperClass="MagnifyingGlass-wrapper"
                        glassColor = '#c0efff'
                        color = '#e5de00'
                    />
                </div>
            }
            {contacts && contacts.length === 0 &&
                <div className="grid content-center justify-center h-5/6 mt-16">
                    <div className="grid justify-items-center">
                        <img src={noEmployees} alt="sinEmpleadas" className="mr-3 h-6 sm:h-52"/>
                        <p className="text-3xl font-semibold text-purple-700">
                            {t('Contacts.noContacts')}
                        </p>
                    </div>
                </div>
            }
                <div className="flex flex-wrap content-center justify-center pl-5 pr-5">
                    {contacts && contacts.length > 0 &&
                        <div>
                            {contacts.map((contact: any) =>
                            <button key={contact.employer.id} onClick={() => {
                                setModalData(contact)
                                openModal()
                            }}>
                                <div className="flex flex-wrap content-center justify-center">
                                    <ContactCard contact={contact}/>
                                </div>
                            </button>
                            )}
                            <PaginationButtons changePages={changePage} pages={pages} current={current} nextPage={nextPage} prevPage={prevPage}/>
                        </div>
                    }
                        <Modal
                            isOpen={modalIsOpen}
                            onRequestClose={closeModal}
                            style={{
                                overlay: {
                                    backgroundColor: 'rgb(0,0,0,0.50)'
                                },
                                content: {
                                    top: '55%',
                                    left: '50%',
                                    right: 'auto',
                                    bottom: 'auto',
                                    borderRadius: '10px',
                                    marginRight: '-50%',
                                    overflow: 'visible',
                                    transform: 'translate(-50%, -50%)',
                                },
                            }}
                        >
                            <button type="button"
                                    className=" text-violet-900 bg-violet-300 font-semibold hover:bg-yellow-300 shadow-lg rounded-full text-sm py-1 px-2.5 text-center inline-flex items-center mr-2 border-solid border-transparent border-2 hover:border-purple-300"
                                    style={{position: "absolute", right: 0}}
                                    onClick={closeModal}
                            >
                                x
                            </button>
                            <ContactCardModal contact={modalData}/>
                        </Modal>
            </div>

        </div>
    )
}
