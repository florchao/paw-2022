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

        const formData:any = new FormData();
        formData.append("mail", mail)
        formData.append("password", password)
        formData.append("confirmPassword", confirmPassword)
        formData.append("name", name)
        formData.append("last", lastname)
        formData.append("image", image, image.name)

        return await fetch(EMPLOYER_URL, {
            method: 'POST',
            headers: {},
            body: formData
        })
    }
}