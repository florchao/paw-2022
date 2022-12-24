import {BACK_SLASH, CONTACT_URL, CONTACTS, EMPLOYEE_URL} from "../utils/utils";

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

    public static async contactEmployee(e: any, phone: string, content: string, employee_id: number, employer_id: number) {
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
    }

    public static async contacts(id: number, page: number) {
        let url = EMPLOYEE_URL + BACK_SLASH + id + CONTACTS
        if(page > 0) {
            url = url + "?page=" + page
        }
        return await fetch(url, {
            method: 'GET',
            headers: {
                "Access-Control-Allow-Origin": "*",
                "Content-Type": "application/json",
                'Authorization': 'Bearer ' + localStorage.getItem('hogar-jwt') as string
            },
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
                "Access-Control-Allow-Origin": "*",
                "Content-Type": "application/json",
                'Authorization': 'Bearer ' + localStorage.getItem('hogar-jwt') as string
            },
        }).then((resp) => {
            return resp.json()
        })
            .catch(
                (error) => {
                    console.log(error)
                    throw error
                })
    }
}