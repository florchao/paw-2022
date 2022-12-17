import {BACK_SLASH, RATINGS_URL} from "../utils/utils";

export class RatingService {

    public static async getEmployeeRating(url: string, employerId: number) {
        return await fetch(url + BACK_SLASH + employerId, {
            method: 'GET',
            headers: {
                "Access-Control-Allow-Origin": "*",
                "Content-Type": "application/json",
                'Authorization': 'Bearer ' + localStorage.getItem('hogar-jwt') as string
            },
        }).then((resp) => resp.json())
            .catch(
                (error) => {
                    console.log(error)
                    throw error
                })
    }

    public static async postEmployeeRating(e: any, employeeId: number, employerId: number, rating: number) {
        e.preventDefault();
        const formData: any = new FormData();
        formData.append("employeeId", employeeId)
        formData.append("employerId", employerId)
        formData.append("rating", rating)

        return await fetch(RATINGS_URL, {
            method: 'POST',
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('hogar-jwt') as string
            },
            body: formData
        }).then((r) => r.json())
    }

}
