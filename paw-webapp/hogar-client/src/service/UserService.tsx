import {JWTExpired, USER_URL} from "../utils/utils";

export class UserService {

    public static async getUser(e: any, email: string, password: string) {
        e.preventDefault()

        return await fetch(USER_URL, {
            method: 'GET',
            headers: {
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
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem('hogar-jwt') as string
            }
        }).then((response) => {
            if (response.status === 401) {
                return JWTExpired()
            }
            return response
        })
    }

    public static async addImage(e: any, url: string, image: File, id: number) {
        e.preventDefault();
        const formData = new FormData();
        formData.append('id', id.toString());
        formData.append('image', image, image.name);
        return await fetch(url, {
            method: 'POST',
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('hogar-jwt') as string
            }
        })
    }

    public static async updateImage(e: any, url:string, image:File, id:number) {
        e.preventDefault();
        const formData = new FormData();
        formData.append('image', image, image.name);
        return await fetch(url, {
            method: 'PUT',
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('hogar-jwt') as string
            }
        })
    }

    public static async loadImage(url: string) {
        return fetch(url, {
            method: 'GET',
            headers: {
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
