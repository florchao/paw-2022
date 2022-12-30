import {BACK_SLASH, EMPLOYER_URL} from "../utils/utils";

export class EmployerService {

    public static async getEmployer(id: number) {
        return await fetch(EMPLOYER_URL + BACK_SLASH + id, {
            method: 'GET',
            headers: {
                'Access-Control-Allow-Origin': '*',
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem('hogar-jwt') as string
            }
        }).then((resp) => resp.json())
            .catch(
                (error) => {
                    console.log(error)
                    throw error
                })
    }

    public static async registerEmployer (e: any, name: string,lastname: string, mail: string, password: string, confirmPassword: string, image: File) {
        e.preventDefault();

        const employerForm = JSON.stringify({
            name: name,
            lastname: lastname,
            mail: mail,
            password: password,
            confirmPassword: confirmPassword,
            image: image
        });

        //TODO: Arreglar lo de las imágenes

        return await fetch(EMPLOYER_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: employerForm
        })
    }
}