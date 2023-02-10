import {BACK_SLASH, CONTACT_URL, CONTACTS, EMPLOYEE_URL, JWTExpired} from "../utils/utils";

export class ContactService {

    public static async contactUs (e: any, name: string, mail: string, content: string) {
        e.preventDefault();
        const contactForm = JSON.stringify({
            name: name,
            mail: mail,
            content: content
        });
        return await fetch(CONTACT_URL + BACK_SLASH + 'us', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: contactForm
        })
    }

    public static async contactEmployee(e: any, phone: string, content: string, employee_id: string, employer_id: number) {
        e.preventDefault();

        const contactForm = JSON.stringify({
            phone: phone,
            content: content,
            employeeId: employee_id,
            employerId: employer_id
        });

        return await fetch(CONTACT_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem('hogar-jwt') as string
            },
            body: contactForm
        })
            .then((response) => {
                if (response.status === 401) {
                    return JWTExpired()
                }
                return response
            })
    }

    public static async contacts(id: number, page: number, linkUrl?: string) {
        let url = EMPLOYEE_URL + BACK_SLASH + id + CONTACTS
        if (page > 0) {
            url = url + "?page=" + page
        }
        if (linkUrl !== undefined) {
            url = linkUrl
        }
        return await fetch(url, {
            method: 'GET',
            headers: {
                "Content-Type": "application/json",
                'Authorization': 'Bearer ' + localStorage.getItem('hogar-jwt') as string
            },
        }).then((response) => {
                if (response.status === 401) {
                    return JWTExpired()
                }
                return response
            })
            .catch(
                (error) => {
                    console.log(error)
                    throw error
                })
    }

    public static async getContact(id: number, employerId: number) {
        return await fetch(CONTACT_URL + BACK_SLASH + id + BACK_SLASH + employerId , {
            method: 'GET',
            headers: {
                "Content-Type": "application/json",
                'Authorization': 'Bearer ' + localStorage.getItem('hogar-jwt') as string
            },
        }).then((response) => {
            if (response.status === 401) {
                return JWTExpired()
            }
            return response.json()
        })
            .catch(
                (error) => {
                    console.log(error)
                    throw error
                })
    }
}