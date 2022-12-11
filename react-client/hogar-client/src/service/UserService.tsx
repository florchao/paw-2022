import {BACK_SLASH, USER_URL, USERS_URL} from "../utils/utils";

export class UserService {

    public static async getUser(e: any, email: string, password: string) {
        e.preventDefault()

        return await fetch(USER_URL, {
            method: 'GET',
            headers: {
                'Access-Control-Allow-Origin': '*',
                'Content-Type': 'application/json',
                'Authorization': 'Basic ' + btoa(email + ":" + password),
            }
        })
            .catch(
                (error) => {
                    console.log(error)
                    throw error
                })
    }

    public static async deleteUser(id: number) {
        return await fetch(USERS_URL + BACK_SLASH + id, {
            method: 'DELETE',
            headers: {
                'Access-Control-Allow-Origin': '*',
                'Content-Type': 'application/json'
            }
        })
    }

    public static async loadImage(url: string) {
        return fetch(url, {
            method: 'GET',
            headers: {
                "Access-Control-Allow-Origin": "*",
                "Content-Type": "application/json"
            },
        }).then((resp) => resp.blob()
        )
            .catch(
                (error) => {
                    console.log(error)
                    throw error
                })
    }

    public static async newPassword(e: any, mail: string, password: string, confirmPassword: string){
        e.preventDefault();
        const form = {mail, password, confirmPassword};
        return await fetch(USERS_URL,{
            method: 'PUT',
            headers: {"Content-Type": "application/json",
            },
            body: JSON.stringify(form)
        })
    }
}
