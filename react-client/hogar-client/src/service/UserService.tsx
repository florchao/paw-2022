import {USER_URL} from "../utils/utils";

export class UserService{

    public static async deleteUser(id: number) {
        return await fetch(USER_URL + id, {
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
        return await fetch(USER_URL,{
            method: 'PUT',
            headers: {"Content-Type": "application/json",
            },
            body: JSON.stringify(form)
        })
    }
}
