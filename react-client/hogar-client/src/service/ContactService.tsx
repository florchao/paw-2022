import {CONTACT_URL, CONTACTS, EMPLOYEE_URL} from "../utils/utils";

export class ContactService {

    public static async contactUs (e: any, name: string, mail: string, content: string) {
        e.preventDefault();
        const blog = {name, mail, content};
        return await fetch(CONTACT_URL + 'us', {
        method: 'POST',
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(blog)
        }).then(() => {
        // history.go(-1);
        })
    }

    public static async contactEmployee(e: any, phone: string, content: string, employee_id: number, employer_id: number) {
        e.preventDefault();

        const formData:any = new FormData();
        formData.append('phone', phone);
        formData.append('content', content);
        formData.append('employee_id', employee_id);
        formData.append('employer_id', employer_id);

        return await fetch(CONTACT_URL + employee_id + '/' + employer_id, {
            method: 'POST',
            headers: {},
            body: formData
        }).then((r) => r.text())
    }

    public static async contacts(id: number) {
        return await fetch(EMPLOYEE_URL + id + CONTACTS , {
            method: 'GET',
            headers: {
                "Access-Control-Allow-Origin": "*",
                "Content-Type": "application/json"
            },
        }).then((resp) => resp.json())
            .catch(
                (error) => {
                    console.log(error)
                    throw error
                })
    }

    public static async getContact(id: number, employerId: number) {
        return await fetch(CONTACT_URL + id +'/'+ employerId , {
            method: 'GET',
            headers: {
                "Access-Control-Allow-Origin": "*",
                "Content-Type": "application/json"
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