import {useEffect, useRef} from "react";
import {useNavigate} from "react-router-dom";

let BASE_URL = 'http://localhost:8080/';
let BACK_SLASH = '/';
let QUERY_PARAM = '?';
let EMPLOYEE_URL = BASE_URL + 'employees';
let EMPLOYER_URL = BASE_URL + 'employers';
let IMAGE_URL = BASE_URL + 'images'
let JOB_URL = BASE_URL + 'jobs';
let IDS_URL = BASE_URL + 'ids';
let LOCATION_URL = BASE_URL + "locations";
let AVAIL_URL = BASE_URL + "availabilities";
let ABIL_URL = BASE_URL + "abilities";
let APPLICANT_URL = BASE_URL + 'applicants';
let CONTACT_URL = BASE_URL + 'contacts';
let RATINGS_URL = BASE_URL + 'ratings';
let REVIEWS_URL = BASE_URL + 'reviews';
let USERS_URL = BASE_URL + 'users';
let USER_URL = BASE_URL + 'user';
let JOBS = '/jobs';
let CONTACTS = '/contacts';


export function JWTExpired() {
    const nav = useNavigate();
    nav("/login", {replace: true})
    localStorage.removeItem("hogar-uid");
    localStorage.removeItem("hogar-role");
    localStorage.removeItem("hogar-jwt");
    return undefined
}

function parseLink(fullLink: string, setNextPage: any, setPrevPage: any) {
    const links = fullLink.split(',')
    for (let i = 0; i < links.length; i++) {
        const link = links[i].split(';');
        const url = link[0].substring(i+1, link[0].length - 1);
        const page = link[1].split('"');
        if (page[1] === 'next') {
            setNextPage(url);
        }
        if (page[1] === 'prev') {
            setPrevPage(url);
        }
    }
}

// It works like a normal useEffect but doesnt trigger on first render
const useDidMountEffect = (func: any, deps: any) => {
    const didMount = useRef(false);
    useEffect(() => {
        if (didMount.current) func();
        else didMount.current = true;
    }, deps);
}

export {
    BASE_URL,
    IDS_URL,
    QUERY_PARAM,
    EMPLOYEE_URL,
    IMAGE_URL,
    EMPLOYER_URL,
    JOB_URL,
    REVIEWS_URL,
    RATINGS_URL,
    APPLICANT_URL,
    USER_URL,
    JOBS,
    CONTACT_URL,
    CONTACTS,
    USERS_URL,
    BACK_SLASH,
    LOCATION_URL,
    AVAIL_URL,
    ABIL_URL,
    parseLink,
    useDidMountEffect
};