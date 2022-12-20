import {BACK_SLASH, REVIEWS_URL} from "../utils/utils";

export class ReviewService {

    public static async getEmployeeReviews(url: string, page:number) {
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

    public static async getEmployerReviews(url: string, page: number) {
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

    public static async getMyEmployerReview(url: string) {
        return await fetch(url + BACK_SLASH + localStorage.getItem("hogar-uid"), {
            method: 'GET',
            headers: {
                "Access-Control-Allow-Origin": "*",
                "Content-Type": "application/json",
                'Authorization': 'Bearer ' + localStorage.getItem('hogar-jwt') as string
            },
        }).then((resp) => {
                if (resp.status == 200) {
                    return resp.json()
                }
            }
        )
            .catch(
                (error) => {
                    console.log(error)
                    throw error
                })
    }

    public static async getMyEmployeeReview(url: number) {
        return await fetch(url + BACK_SLASH + localStorage.getItem("hogar-uid"), {
            method: 'GET',
            headers: {
                "Access-Control-Allow-Origin": "*",
                "Content-Type": "application/json",
                'Authorization': 'Bearer ' + localStorage.getItem('hogar-jwt') as string

            },
        }).then((resp) => {
                if (resp.status == 200) {
                    return resp.json()
                }
            }
        )
            .catch(
                (error) => {
                    console.log(error)
                    throw error
                })
    }

    public static async postEmployerReview(e: any, employerId: number, message: string) {
        e.preventDefault();
        const formData: any = new FormData();
        formData.append("content", message)
        formData.append("employeeId", localStorage.getItem("hogar-uid"))
        formData.append("employerId", employerId)
        formData.append("forEmployee", false)

        return await fetch(REVIEWS_URL, {
            method: 'POST',
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('hogar-jwt') as string
            },
            body: formData
        })
    }

    public static async postEmployeeReview(e: any, employeeId: number, message: string) {
        e.preventDefault();
        const formData: any = new FormData();
        formData.append("content", message)
        formData.append("employeeId", employeeId)
        formData.append("employerId", localStorage.getItem("hogar-uid"))
        formData.append("forEmployee", true)

        return await fetch(REVIEWS_URL, {
            method: 'POST',
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('hogar-jwt') as string
            },
            body: formData
        })
    }
}