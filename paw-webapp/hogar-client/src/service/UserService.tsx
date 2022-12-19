import {USER_URL, USERS_URL} from "../utils/utils";

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

    public static async deleteUser(url: string) {
        return await fetch(url, {
            method: 'DELETE',
            headers: {
                'Access-Control-Allow-Origin': '*',
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem('hogar-jwt') as string
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
}
