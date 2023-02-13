import {BACK_SLASH, JWTExpired, REVIEWS_URL} from "../utils/utils";

export class ReviewService {
    public static async getEmployeeReviews(url: string, page: number, employerId?: number, linkUrl?: string) {
        if (page > 0) {
            url = url + "?page=" + page
        }

        if (employerId && employerId !== 0) {
            if(page > 0){
                url = url + "&except=" + employerId
            } else
                url = url + "?except=" + employerId
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
        })
            .then((response) => {
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

    public static async getEmployerReviews(url: string, page: number, employeeId?: number, linkUrl?: string) {
        if (page > 0) {
            url = url + "?page=" + page
        }

        if (employeeId && employeeId !== 0) {
            if(page > 0){
                url = url + "&except=" + employeeId
            } else
                url = url + "?except=" + employeeId
        }

        if (linkUrl !== "" && linkUrl !== undefined) {
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

    public static async getMyEmployerReview(url: string) {
        return await fetch(url + BACK_SLASH + localStorage.getItem("hogar-uid"), {
            method: 'GET',
            headers: {
                "Content-Type": "application/json",
                'Authorization': 'Bearer ' + localStorage.getItem('hogar-jwt') as string
            },
        }).then((resp) => {
            if (resp.status === 401) {
                return JWTExpired()
            }
            else if (resp.status === 200) {
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

    public static async getMyEmployeeReview(url?: string) {
        return await fetch(url + BACK_SLASH + localStorage.getItem("hogar-uid"), {
            method: 'GET',
            headers: {
                "Content-Type": "application/json",
                'Authorization': 'Bearer ' + localStorage.getItem('hogar-jwt') as string

            },
        }).then((resp) => {
            if (resp.status === 401) {
                return JWTExpired()
            }
            else if (resp.status === 200) {
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

        const reviewFrom = JSON.stringify({
            employerId: employerId,
            employeeId: localStorage.getItem("hogar-uid"),
            content: message,
            forEmployee: false
        })

        return await fetch(REVIEWS_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem('hogar-jwt') as string
            },
            body: reviewFrom
        }).then((response) => {
            if (response.status === 401) {
                return JWTExpired()
            }
            return response
        })
    }

    public static async postEmployeeReview(e: any, employeeId: number, message: string) {
        e.preventDefault();

        const reviewForm = JSON.stringify({
            employeeId: employeeId,
            employerId: localStorage.getItem("hogar-uid"),
            content: message,
            forEmployee: true
        })

        return await fetch(REVIEWS_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem('hogar-jwt') as string
            },
            body: reviewForm
        }).then((response) => {
            if (response.status === 401) {
                return JWTExpired()
            }
            return response
        })
    }
}